package com.smart.sprinkler.motor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStreamReader;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinMode;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

public class Motor {
	private static boolean fistTimeFlag = false;
	private static GpioController gpio = null;
	private static GpioPinDigitalOutput [] outPutPins = new GpioPinDigitalOutput[3];
	
	public  static void turnOnMotor(int duration) throws InterruptedException{
		System.out.println("turnOnMotor()");
		
		try{
			Process p = Runtime.getRuntime().exec("python motor1.py "+duration);
		}catch(Exception e){
			System.out.println(e);
		}
		
		 
		/*if(!fistTimeFlag){
			System.out.println("initializing");
			fistTimeFlag = true;
			// create gpio controller
			 gpio = GpioFactory.getInstance();
			 
			 outPutPins[0] = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_16, PinState.LOW);//a
			 outPutPins[1] = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_18, PinState.HIGH);//b
			 outPutPins[2] = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_22, PinState.HIGH);//enable
			 			 
			// set shutdown options on all pins
			//gpio.setShutdownOptions(true, PinState.LOW, outPutPins);
		}
		
		
		System.out.println("setShutdownOptions()");

		outPutPins[0].setState(PinState.LOW);//a
		outPutPins[1].setState(PinState.HIGH);//b
		outPutPins[2].setState(PinState.HIGH);//enable
		System.out.println("Sleep()");
		
		Thread.sleep(5000);
		
		System.out.println("shutdown()");
		outPutPins[0].setState(PinState.LOW);
		outPutPins[1].setState(PinState.LOW);
		outPutPins[2].setState(PinState.LOW);   */   
		
	}

	public static void turnOffMotor(){


	}
	
	public static void callPy(){
		try{

			Process p = Runtime.getRuntime().exec("python motor1.py ");
			
		}catch(Exception e){
			System.out.println(e);
			}

	}

}
