package com.GearGrinder.Networking;

import com.GearGrinder.Game;


public class TimeLookup implements Runnable{
	public static Thread TimeLookup;
	public static Boolean time = true;
	
	public TimeLookup(){
		
	}
	
	public synchronized void start(String time) {
		Game.running = true;
		TimeLookup = new Thread(this);
		TimeLookup.start();
	}

	public static synchronized void stop() {
		Game.running = false;
		try {
			TimeLookup.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void run(){
		while(time == true){
			SaveLoc.SaveLoc();
		}
		
	}
	
public static void TimeLookup(){
	TimeLookup T = new TimeLookup();
	T.start("TIME");
	System.out.println("TIME THREAD CREATED");
	}
}