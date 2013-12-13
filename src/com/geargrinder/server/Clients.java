package com.geargrinder.server;

import com.geargrinder.server.data.ServerClient;

public class Clients {

	private static ServerClient[] clients;

	private static int errorCode = 999;
	private static int maxClients;

	public static void load(int maxclients) {
		Clients.clients = new ServerClient[maxclients];
		maxClients = maxclients;
	}

	public static void add(ServerClient sc, int id) {
		clients[id] = sc;
	}

	public static void remove(int id) {
		clients[id] = null;
	}

	public static String getName(int id) {
		return clients[id].AccountName;
	}

	public static int getID(String name) {
		for (int i = 0; i < clients.length; i++) {
			if (clients[i].AccountName.equalsIgnoreCase(name)) return i;
		}
		return errorCode;
	}

	public static int getFreeID() {
		for (int i = 0; i < clients.length; i++) {
			if (clients[i] == null) return i;
		}
		return errorCode;
	}

	public static int getErrorCode() {
		return errorCode;
	}

	public static int getmaxClients() {
		return maxClients;
	}

	public static ServerClient getClient(int id) {
		return clients[id];
	}

}