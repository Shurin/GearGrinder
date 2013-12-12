package com.GearGrinder.Networking;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.GearGrinder.Game;

public class GetLocThread implements Runnable {
	public static Thread GetLocThread;
	public static Boolean asdf = true;

	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://184.168.194.136/GGdb";
	// Database credentials
	static final String USER = "GGdb";
	static final String PASS = "GGgg##12";

	// local settings for dev
	// static final String DB_URL = "jdbc:mysql://localhost/ggdb";
	// static final String USER = "root";
	// static final String PASS = "RRRRrrrr$$$$4444r4";

	public static Connection conn = null;
	public static Statement stmt = null;
	public static ResultSet rs = null;

	public GetLocThread() {

	}

	public synchronized void start(String time) {
		try {
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		Game.running = true;
		GetLocThread = new Thread(this);
		GetLocThread.start();
	}

	public static synchronized void stop() {
		try {
			GetLocThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void run() {

		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60.0;
		double delta = 0;
		while (asdf == true) {
			if (System.currentTimeMillis() - timer > 100) {
				timer = timer + 100; // adds a second to above criteria
				try {
					// g.drawString("TIME INIT...", width / 3, height / 2);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					// g.drawString("TIME INIT FAILED!", width / 4, height / 2);
					e.printStackTrace();
				}
				GetLoc.GetLoc();
			}
		}
	}

	public static void GetLocThread() {
		GetLocThread L = new GetLocThread();
		L.start("GETLOC");
	}
}