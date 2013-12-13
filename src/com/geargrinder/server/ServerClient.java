package com.geargrinder.server;

import java.net.InetAddress;

public class ServerClient {

	public String name;
	public InetAddress address;
	public int port;
	private final int ID;
	public int attempt = 0;
	public int worldID;
	public int PosX;
	public int PosY;
	public boolean updated = false;

	public ServerClient(String name, InetAddress address, int port, final int ID) {
		this.name = name;
		this.address = address;
		this.port = port;
		this.ID = ID;
	}

	public int getID() {
		return ID;
	}

	public void updatePos(int x, int y, int world) {
		PosX = x;
		PosY = y;
		worldID = world;
		updated = false;
	}

	public void save() {

	}

	public boolean isUpdated() {
		return updated;
	}

	public void update() {
		updated = true;
	}

}