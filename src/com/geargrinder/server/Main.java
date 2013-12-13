package com.geargrinder.server;

public class Main {

	public static void main(String[] args) {
		if (args.length == 2) {
			try {
				new Server(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
			} catch (Exception e) {
				System.err.println("Failed to enter the port or max of clients. Please add as arguments the server port and max of clients.");
			}
		} else System.err.println("Failed to enter the port or max of clients. Please add as arguments the server port and max of clients.");
	}

}