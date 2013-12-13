package com.geargrinder.server;

import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ServerClient {

	public String name;
	public InetAddress address;
	public int port;
	private final int ID;
	public int attempt = 0;
	public int commits;
	public int hours;
	public int min;
	public int sec;
	public int worldID;

	public ServerClient(String name, InetAddress address, int port, final int ID) {
		this.name = name;
		this.address = address;
		this.port = port;
		this.ID = ID;
		String load = FileManager.loadUser(name);
		this.hours = Integer.parseInt(load.split(" ")[0]);
		this.min = Integer.parseInt(load.split(" ")[1]);
		this.sec = Integer.parseInt(load.split(" ")[2]);
		this.commits = Integer.parseInt(load.split(" ")[3]);
	}

	public int getID() {
		return ID;
	}

	public void commit() {
		SimpleDateFormat formats = new SimpleDateFormat("dd HH:mm:ss");
		Date date = new Date();
		FileManager.addLog("Commited at: " + formats.format(date) + " commit number " + commits, name);
		commits++;
		System.out.println(name + " commited at: " + formats.format(date) + " commit number " + commits);
	}

	public void save(int h, int m, int s) {
		hours += h;
		min += m;
		sec += s;
		if (sec > 60) {
			min++;
			sec = sec - 60;
		} else if (sec == 60) {
			min++;
			sec = 0;
		}
		if (min > 60) {
			hours++;
			min = min - 60;
		} else if (min == 60) {
			hours++;
			min = 0;
		}
		FileManager.saveUser(name, hours, min, sec, commits);
		System.out.println(name + " saved with " + hours + ":" + min + ":" + sec + "h and " + commits + " commits");
	}

}