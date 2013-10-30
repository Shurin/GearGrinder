package com.GearGrinder.DataIO;

import java.io.BufferedReader;
import java.io.FileReader;

import com.GearGrinder.Game;




public class Load {
	public static String PSX = null;
	public static String PSY = null;
	public static int lineNo = 0;
	public static void Load() {

		BufferedReader br = null;
		FileReader fr = null;
		try {
			fr = new FileReader("stats.txt");
			br = new BufferedReader(fr);
			String line;
			int counter = 0;
			while ((line = br.readLine()) != null) {
				if(counter == 0){
					PSX = line;
				}
				else if(counter == 1){
					PSY = line;
				}
				
				counter++;
				
			}
		} catch (Exception x) {
			x.printStackTrace();
		} finally {
			if (fr != null) {
				try {
					br.close();
				} catch (Exception ignoreMe) {
				}
				try {
					fr.close();
				} catch (Exception ignoreMe) {
				}
			}
		}
		
		//Cast the strings to the actual variables
		StatCaster.StatCaster();
		
	}

}
