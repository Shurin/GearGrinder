package com.geargrinder.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class FileManager {
	public static String sep = System.getProperty("file.separator");

	public static void saveUser(String id, int h, int m, int s, int c) {
		try {
			Scanner sc = new Scanner(new BufferedReader(new FileReader("users" + sep + id + ".dat")));
			FileWriter f = new FileWriter(new File("users" + sep + id + ".dat"));
			f.write("hour=" + h + "\n");
			f.write("min=" + m + "\n");
			f.write("sec=" + s + "\n");
			f.write("commits=" + c + "\n");
			f.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void saveUser(String id) {
		try {
			FileWriter f = new FileWriter(new File("users" + sep + id + ".dat"));
			f.write("hour=0\n");
			f.write("min=0\n");
			f.write("sec=0\n");
			f.write("commits=0\n");
			f.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String loadUser(String id) {
		String returns = "0 0 0 0";
		int h = 0;
		int m = 0;
		int s = 0;
		int c = 0;
		try {
			Scanner sc = new Scanner(new BufferedReader(new FileReader("users" + sep + id + ".dat")));
			if (sc.hasNext()) {
				if (sc.hasNextLine()) h = Integer.parseInt(sc.nextLine().split("=")[1]);
				if (sc.hasNextLine()) m = Integer.parseInt(sc.nextLine().split("=")[1]);
				if (sc.hasNextLine()) s = Integer.parseInt(sc.nextLine().split("=")[1]);
				if (sc.hasNextLine()) c = Integer.parseInt(sc.nextLine().split("=")[1]);
				sc.close();
			} else {
				sc.close();
				saveUser(id);
			}
			returns = h + " " + m + " " + s + " " + c;
		} catch (FileNotFoundException e) {
			// e.printStackTrace();
			saveUser(id);
			if (e.getMessage().contains("(No such file or directory)")) saveUser(id, 0, 0, 0, 0);
		}
		return returns;
	}

	public static void addLog(String message, String id) {
		try {
			File file = new File("logs" + sep + id + ".log");
			if (file.exists()) {
				PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("logs" + sep + id + ".log", true)));
				out.println(message + "\n");
				out.close();
			} else {
				PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("logs" + sep + id + ".log", true)));
				out.println("=== New File for " + id + "\n");
				out.println(message);
				out.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}