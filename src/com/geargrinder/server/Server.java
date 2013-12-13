package com.geargrinder.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import com.geargrinder.server.data.ServerClient;

public class Server implements Runnable {

	private DatagramSocket socket;
	private int port;
	private boolean running = false;
	private Thread run, send, receive;
	private int maxClients;

	public Server(int port, int max) {
		this.port = port;
		this.maxClients = max;
		try {
			socket = new DatagramSocket(port);
		} catch (SocketException e) {
			e.printStackTrace();
			return;
		}
		Clients.load(maxClients);
		run = new Thread(this, "Server");
		run.start();
	}

	public void run() {
		running = true;
		System.out.println("Server started on port " + port);
		receive();
		long timer = System.currentTimeMillis();
		while (running) {
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				update();
			}
		}
	}

	private void update() {
		for (int i = 0; i < Clients.getmaxClients(); i++) {
			if (Clients.getClient(i) != null && !Clients.getClient(i).isUpdated()) {
				sendAll("p:" + Clients.getClient(i).AccountName + ";" + Clients.getClient(i).locX + ";" + Clients.getClient(i).locY + ";" + Clients.getClient(i).worldID);
				Clients.getClient(i).update();
			}
		}
	}

	private void receive() {
		receive = new Thread("Receive") {
			public void run() {
				while (running) {
					byte[] data = new byte[1024];
					DatagramPacket packet = new DatagramPacket(data, data.length);
					try {
						socket.receive(packet);
					} catch (IOException e) {
						e.printStackTrace();
					}
					process(packet);
				}
			}
		};
		receive.start();
	}

	private void sendAll(String message) {
		for (int i = 0; i < Clients.getmaxClients(); i++) {
			if (Clients.getClient(i) != null) {
				ServerClient sc = Clients.getClient(i);
				send(message.getBytes(), sc.address, sc.port);
			}
		}
	}

	private void send(final byte[] data, final InetAddress address, final int port) {
		send = new Thread("Send") {
			public void run() {
				DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
				try {
					socket.send(packet);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		send.start();
	}

	private void process(DatagramPacket packet) {
		String string = new String(packet.getData());
		if (string.startsWith("/c/")) { // Connect
			int id = Clients.getFreeID();
			Clients.add(new ServerClient(string.substring(3, string.length()), packet.getAddress(), packet.getPort(), id), id);
			System.out.println(string.substring(3, string.length()) + "/" + packet.getAddress() + ":" + packet.getPort() + " - has connected.");
		} else if (string.startsWith("/p/")) { // Update pos
			int id = Integer.parseInt(string.split("/p/")[1]);
			String pos = string.split("/p/")[2];
			Clients.getClient(id).updatePos(Integer.parseInt(pos.split(" ")[0]), Integer.parseInt(pos.split(" ")[1]), Integer.parseInt(pos.split(" ")[2]));
		} else if (string.startsWith("/cl/")) { // Close
			int id = Integer.parseInt(string.split("/cl/")[1].split(" ")[0]);
			Clients.getClient(id).save();
		} else if (string.startsWith("//")) {
			System.exit(1);
		}
	}

}