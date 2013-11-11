package com.GearGrinder.Networking;

import com.GearGrinder.Game;


public class SaveLocThread implements Runnable{
	public static Thread SaveLocThread;
	public static Boolean qwer = true;
	
	public SaveLocThread(){
		
	}
	
	public synchronized void start(String time) {
		Game.running = true;
		SaveLocThread = new Thread(this);
		SaveLocThread.start();
	}

	public static synchronized void stop() {
		try {
			SaveLocThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void run(){
		long lastTime = System.nanoTime();
	    long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60.0;
		double delta = 0;
		while(qwer == true){
			if (System.currentTimeMillis() - timer > 33) {
				timer = timer + 33; // adds a second to above criteria
				try {
					//g.drawString("TIME INIT...", width / 3, height / 2);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					//g.drawString("TIME INIT FAILED!", width / 4, height / 2);
					e.printStackTrace();
				}
				SaveLoc.SaveLoc();
			}
		}		
	}
	
public static void SaveLocThread(){
	SaveLocThread T = new SaveLocThread();
	T.start("SAVELOC");
	System.out.println("SAVELOC THREAD CREATED");
	}
}