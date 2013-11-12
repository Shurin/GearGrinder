package com.GearGrinder.Networking;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.GearGrinder.Game;
import com.GearGrinder.entity.Entity;

public class InitialStat {

	//PLAYER STATS
		public static String charname = "CharName";
		public static String PSprite = null;
		public static String PSpriteDir = null;
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
		public static double leveltotalxp = 11;
		public static double XP2lvl = 1;
		public static double XPpercent = 725 / leveltotalxp * XP; 
		public static String DB_Zone = null;
		public static int Onlineint = 1;
		public static List<Entity> onlineplayersID = new ArrayList<Entity>();//stores the ID of online players
		public static List<Entity> onlineplayersX = new ArrayList<Entity>();//stores the ID of online players
		public static List<Entity> onlineplayersY = new ArrayList<Entity>();//stores the ID of online players
	
	
	public static void InitialStat(){
		ResultSet rs = null;
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			
			
				rs = UserVerify.stmt.executeQuery("SELECT Sprite, SpriteDirection, PlayerName, Zone, Xloc, Yloc, Level, XP, Health, Magic, Stamina, Strength, Defense, Vitality, Agility, Intelligence, Dexterity, Luck, Gold FROM accounts WHERE AccountID = " + UserVerify.clientID);
				while(rs.next()){
					PSprite = rs.getString("Sprite");
					PSpriteDir = rs.getString("SpriteDirection");
					Game.Playername = rs.getString("PlayerName");
					DB_Zone = rs.getString("Zone");
					Game.PlayerSpawnX = rs.getInt("Xloc");
					Game.PlayerSpawnY = rs.getInt("Yloc");
					XP = rs.getInt("XP");
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
					XPpercent = 725 / leveltotalxp * XP; 					
				}			
		}catch(SQLException se){
			// Handle errors for JDBC
			se.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		Game.launch();
	}
	
	public static void whosonline(){
		
	}
}
