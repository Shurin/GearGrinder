package com.GearGrinder.DataIO;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import com.GearGrinder.Game;

public class Save {

	public static PrintWriter writer;
	
	public static void Save(){
		try {
			writer = new PrintWriter("stats.txt", "UTF-8");
			writer.println(Game.PlayerSpawnX);
			writer.println(Game.PlayerSpawnY);
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		
	}
}
