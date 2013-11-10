package com.GearGrinder.Networking;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.GearGrinder.Game;
import com.GearGrinder.LoginPage;

public class InitialStat {

	//PLAYER STATS
		public static String charname = "CharName";
		public static int gold = 1;
		public static int health = 1;
		public static double currenthealth = health;
		public static double maxhealth = health;
		public static double healthpercent = currenthealth / maxhealth * 100;
		public static int magic = 1;
		public static double currentmagic = magic;
		public static double maxmagic = magic;
		public static double magicpercent = currentmagic / maxmagic * 100;
		public static int stamina = 1;
		public static double currentstamina = stamina;
		public static double maxstamina = stamina;
		public static double staminapercent = currentstamina / maxstamina * 100;
		public static int strength = 1;
		public static int defense = 1;
		public static int vitality = 1;
		public static int agility = 1;
		public static int intelligence = 1;
		public static int dexterity = 1;
		public static int luck = 1;
		public static int PlayerLevel = 1;
		public static int XP = 1;
		public static String DB_Zone = null;
	
	
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://184.168.194.136/GGdb";
	
	// Database credentials
	static final String USER = "GGdb";
	static final String PASS = "GGgg##12";
	
	public static void InitialStat(){
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try{
			System.out.println("verifying username ...");
			// Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");
					
			// Open a connection
			System.out.println("Connecting to database ...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			System.out.println("Connected database succesfully ...");
			
			// Execute a query
			System.out.println("Creating statement ...");
			stmt = conn.createStatement();
			
				rs = stmt.executeQuery("SELECT PlayerName, Zone, Xloc, Yloc, Level, XP, Health, Magic, Stamina, Strength, Defense, Vitality, Agility, Intelligence, Dexterity, Luck, Gold FROM accounts WHERE AccountID = " + UserVerify.clientID);
				while(rs.next()){
					Game.Playername = rs.getString("PlayerName");
					DB_Zone = rs.getString("Zone");
					Game.PlayerSpawnX = rs.getInt("Xloc");
					Game.PlayerSpawnY = rs.getInt("Yloc");
					PlayerLevel = rs.getInt("Level");
					health = rs.getInt("Health");
					magic = rs.getInt("Magic");
					stamina = rs.getInt("Stamina");
					strength = rs.getInt("Strength");
					defense = rs.getInt("Defense");
					vitality = rs.getInt("Vitality");
					agility = rs.getInt("Agility");
					intelligence = rs.getInt("Intelligence");
					dexterity = rs.getInt("Dexterity");
					luck = rs.getInt("Luck");
					gold = rs.getInt("Gold");		
					
					currenthealth = health;
					maxhealth = health;
					healthpercent = currenthealth / maxhealth * 100;
					currentmagic = magic;
					maxmagic = magic;
					magicpercent = currentmagic / maxmagic * 100;
					currentstamina = stamina;
					maxstamina = stamina;
					staminapercent = currentstamina / maxstamina * 100;
				}			
		}catch(SQLException se){
			// Handle errors for JDBC
			se.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(stmt!=null) conn.close();
			}catch(SQLException se){
				se.printStackTrace();
			}
		}
		System.out.println("Closed Database connection.");
		Game.launch();
	}
}
