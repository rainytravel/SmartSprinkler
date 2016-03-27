package com.smart.sprinkler.sensor;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.concurrent.Callable;

import com.pi4j.gpio.extension.ads.ADS1015GpioProvider;
import com.pi4j.gpio.extension.ads.ADS1015Pin;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinAnalog;
import com.pi4j.io.gpio.GpioPinAnalogInput;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinAnalogValueChangeEvent;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerAnalog;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import com.pi4j.io.gpio.trigger.GpioCallbackTrigger;
import com.pi4j.io.gpio.trigger.GpioPulseStateTrigger;
import com.pi4j.io.gpio.trigger.GpioSetStateTrigger;
import com.pi4j.io.gpio.trigger.GpioSyncStateTrigger;
import com.pi4j.io.i2c.I2CBus;
import com.smart.sprinkler.data.BaseDao;
import com.smart.sprinkler.motor.Motor;
import com.smart.sprinkler.weather.WeatherAPI;

/**
 * This example code demonstrates how to setup simple triggers for GPIO pins on the Raspberry Pi.
 * 
 * @author Robert Savage
 */
public class MoistureSensor {
	
	public static final int TRIGGER_INTERVAL = 6;
	public static final int MACHINE_LEARNING_CORRECTION = 10;
	static boolean  firstTime = true;
    
    public static void main(final String[] args) throws InterruptedException, IOException {
        
        System.out.println("Moisture sensor Example ... started.");

        // call moisture sensor rest client
        // keep program running until user aborts (CTRL-C)
        for (;;) {
        final Long sensorData=MoistureSensorRestClient.getMoistureData();
        
				if(sensorData.longValue()==0){
					System.out.println("LOW about to turn motor on");
					try {
						BaseDao dao = new BaseDao();
						String cropType = args[0];
						String location = args[1];
						String strBl = dao.getDuration(cropType); // in mins
						int correctedBL = 0;
						
						int baselineDuration = Integer.parseInt(strBl) ;
						System.out.println("baselineDuration from DB: "+baselineDuration);
						
						/**
						 * Machine learning
						 */
						Date tsFromDB = new Date(); //dao.getLastRun(cropType);
						
						if(tsFromDB != null){
							/*Date date = new Date();
							java.sql.Timestamp currentTime = new java.sql.Timestamp(date.getTime());
							long diff = currentTime.getTime()-tsFromDB.getTime();
							long diffHours = diff/((60*60*1000)%24);*/
							//if(diffHours < TRIGGER_INTERVAL)
							
							if(!firstTime && true)
							{
								correctedBL = baselineDuration+((MACHINE_LEARNING_CORRECTION *baselineDuration)/100);
								dao.updateBaseLine(correctedBL, cropType);
							}else{
								correctedBL = baselineDuration;
								firstTime = false;
							}
						}/*else{
							correctedBL = baselineDuration;
						}*/
						
						
						System.out.println("CorrectedBL from DB: "+correctedBL);
						
						
						/**
						 * Weather API and Logic for weather correction
						 */
						int chancesPercentage =  Integer.parseInt(WeatherAPI.getChanceOfRain(location));
						System.out.println("chancesPercentage: "+chancesPercentage);
						
						int newBaselineDuration = correctedBL - ((correctedBL * chancesPercentage)/100);
						
						System.out.println("newBaselineDuration: "+newBaselineDuration);
						
						
						/**
						 * Logic of starting motor
						 */
						if(newBaselineDuration != 0){
							Motor.turnOnMotor(newBaselineDuration);
							dao.insertEvent(cropType);
						}
						
					} catch (InterruptedException e) {
						System.out.println("Exception"+e.getMessage());
					}
				}if(sensorData.longValue()==1){
					System.out.println("No Action");
				}
        
        
       
            Thread.sleep(500);
        }
       
    }
}