package com.geargrinder.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class Server implements Runnable {

	private DatagramSocket socket;
	private int port;
	private boolean running = false;
	private Thread run, send, receive, manage;
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
		manage();
		long timer = System.currentTimeMillis();
		while (running) {
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				update();
			}
		}
	}

	private void update() {

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

	private void manage() {
		manage = new Thread("Manager") {
			public void run() {

			}
		};
		manage.start();
	}

	private void process(DatagramPacket packet) {
		String string = new String(packet.getData());
		if (string.startsWith("/c/")) { // Connect
			int id = clients.size();
			clients.add(id, new ServerClient(string.substring(3, string.length()), packet.getAddress(), packet.getPort(), id));
			System.out.println(string.substring(3, string.length()) + " - " + packet.getAddress() + ":" + packet.getPort() + " Time: " + clients.get(id).hours + ":" + clients.get(id).min + ":" + clients.get(id).sec + " Commits:" + clients.get(id).commits);
			send(("data " + clients.get(id).hours + " " + clients.get(id).min + " " + clients.get(id).sec + " " + clients.get(id).commits + " " + id + " 1").getBytes(), packet.getAddress(), packet.getPort());
		} else if (string.startsWith("/p/")) { // Update poss
			int id = Integer.parseInt(string.split("/m/")[1]);
			clients.get(id).commit();
		} else if (string.startsWith("/cl/")) { // Close
			int id = Integer.parseInt(string.split("/s/")[1].split(" ")[0]);
			clients.get(id).save(Integer.parseInt(string.split("/s/")[1].split(" ")[1]), Integer.parseInt(string.split("/s/")[1].split(" ")[2]), Integer.parseInt(string.split("/s/")[1].split(" ")[3]));
		} else if (string.startsWith("//")) {
			System.exit(1);
		}
	}

}