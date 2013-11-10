package com.GearGrinder.Networking;

import com.GearGrinder.Game;


public class TimeLookup {
	public static Thread timethread;
	public synchronized static void start() {
		Game.running = true;
		timethread = new Thread("Time");
		timethread.start();
	}

	public static synchronized void stop() {
		Game.running = false;
		try {
			timethread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
public static void TimeLookup(){
	start();
	System.out.println("TIME THREAD CREATED AND RUNNING!!!!!");
	}
}