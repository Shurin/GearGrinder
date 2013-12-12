package com.GearGrinder.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import com.GearGrinder.Game;
import com.GearGrinder.Networking.GetLoc;

public class GameServer extends Thread {
	private DatagramSocket socket;
	private Game game;

	public GameServer(Game game) {
		this.game = game;
		try {
			this.socket = new DatagramSocket(4578);
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		while (true) {
			byte[] data = new byte[1024];
			DatagramPacket packet = new DatagramPacket(data, data.length);
			try {
				socket.receive(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
			String message = new String(packet.getData());
			String[] words = message.split(",");
			String a = words[0]; // x
			String b = words[1]; // y
			String c = b.trim(); // y - the packet padding

			GetLoc.xp1 = Integer.valueOf(a);
			GetLoc.yp1 = Integer.valueOf(c);
			GetLoc.RENDER = true;

			// System.out.println("x:" + a +"y:" + c + "M");
			/*if(message.trim().equalsIgnoreCase("ping")){
				sendData("pong".getBytes(), packet.getAddress(), packet.getPort());
			}*/
		}
	}

	public void sendData(byte[] data, InetAddress ipAddress, int port) {
		DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, port);
		try {
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
