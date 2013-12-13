package com.geargrinder.server.data;

import java.net.InetAddress;

public class ServerClient {

	public InetAddress address;
	public int port;
	public boolean updated = false;
	public Inventory inv;
	public Quests[] quests;

	private final int ID;
	public int AccountID;
	public String AccountName;
	public String Sprite;
	public String SpriteDirection;
	public String PlayerName;
	public String Zone;
	public int locX;
	public int locY;
	public int Level;
	public int XP;
	public int Health;
	public int Magic;
	public int Stamina;
	public int Strenghth;
	public int Defense;
	public int Vitality;
	public int Agility;
	public int Intelligence;
	public int Dexterity;
	public int Luck;
	public int Gold;
	public int worldID;

	public ServerClient(String name, InetAddress address, int port, final int ID) {
		this.AccountName = name;
		this.address = address;
		this.port = port;
		this.ID = ID;
	}

	public int getID() {
		return ID;
	}

	public void updatePos(int x, int y, int world) {
		locX = x;
		locY = y;
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