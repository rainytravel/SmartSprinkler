package com.smart.sprinkler.sensor;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.smart.sprinkler.motor.Motor;
import com.smart.sprinkler.rs.clients.AutoPilotRestClient;
import com.smart.sprinkler.rs.clients.MotorRequestRestClient;

public class AutopilotThread implements Runnable{

	@Override
	public void run() {
		while(true){
			String autoPilotFlag = AutoPilotRestClient.getConfig();
			String motorFlag = MotorRequestRestClient.getConfig();
			final GpioController gpio = GpioFactory.getInstance();
			PinState pin16 = gpio.getState(gpio.provisionDigitalInputPin( RaspiPin.GPIO_16));
			PinState pin18 = gpio.getState(gpio.provisionDigitalInputPin( RaspiPin.GPIO_18));
			PinState pin22 = gpio.getState(gpio.provisionDigitalInputPin( RaspiPin.GPIO_22));
			if(motorFlag != null && motorFlag.equals("1") && autoPilotFlag != null && autoPilotFlag.equals("0")){
				System.out.println("Mobile app requested Autopilot off and Motor on request received");

				if(pin16.equals(PinState.HIGH) && pin18.equals(PinState.LOW) && pin22.equals(PinState.HIGH)){
					// motor already running
					System.out.println("Motor Already running");
				}else{
					System.out.println("Motor No running, now turnning on as requested by Mobile App");
					try {
						// TODO: the duration must not be specified here
						Motor.turnOnMotor(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			} else if (motorFlag != null && motorFlag.equals("0") && autoPilotFlag != null && autoPilotFlag.equals("0")){
				if(pin16.equals(PinState.LOW) && pin18.equals(PinState.HIGH) && pin22.equals(PinState.HIGH)){
					// motor already running and must be stopped now
					System.out.println("Motor Already running and must be stopped now");
					Motor.turnOffMotor();
				}
			}
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
