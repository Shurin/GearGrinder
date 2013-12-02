package com.GearGrinder.Networking;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.GearGrinder.Game;
import com.GearGrinder.entity.mob.Player;

public class InitialStat {

	//PLAYER STATS
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
		public static int questcount = -1;
		
		//dummy values for the quest data
		private static String QuestID;
		private static String Type;
		private static String Started;
		private static String Complete;
		private static String Title;
		private static String MinLevel;
		private static String MaxLevel;
		private static String Solo;
		private static String Timer;
		private static String TimeLeft;
		private static String MobName;
		private static String MobAmount;
		private static String MobKilled;
		private static String Collect1;
		private static String Collect2;
		private static String Collect3;
		private static String Collect4;
		private static String Collect5;
		private static String Collect6;
		private static String Amount1;
		private static String Amount2;
		private static String Amount3;
		private static String Amount4;
		private static String Amount5;
		private static String Amount6;
		private static String Left1;
		private static String Left2;
		private static String Left3;
		private static String Left4;
		private static String Left5;
		private static String Left6;
		private static String Item1;
		private static String Item2;
		private static String Item3;
		private static String Item4;
		private static String Item5;
		private static String Item6;
		private static String QXP;
		private static String QGold;
		private static int QuestIDint;
		private static int Startedint;
		private static int Completeint;
		private static int Soloint;
		private static int MinLevelint;
		private static int MaxLevelint;
		private static int QXPint;
		private static int QGoldint;
		private static int Timerint;
		private static int TimeLeftint;
		private static int MobAmountint;
		private static int MobKilledint;
		private static int Amount1int;
		private static int Amount2int;
		private static int Amount3int;
		private static int Amount4int;
		private static int Amount5int;
		private static int Amount6int;
		private static int Left1int;
		private static int Left2int;
		private static int Left3int;
		private static int Left4int;
		private static int Left5int;
		private static int Left6int;
		
		public static ArrayList<ArrayList<String>> listofquests = new ArrayList<ArrayList<String>>();
		public static ArrayList<String> questbloat = new ArrayList<String>();
		
		private static String test = "qweqweqwe";
	
	public static void InitialStat(){
		ResultSet rs = null;
		// initial player stat load
		try{
			Class.forName("com.mysql.jdbc.Driver");
			
			System.out.println("Loading Player Stats ...");
			
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
		
		rs = null;
		
	    try {
	      rs = UserVerify.stmt.executeQuery("SELECT COUNT(*) FROM Quests_" + Game.Playername);
	      // get the number of rows from the result set
	      rs.next();
	      questcount = rs.getInt(1);
	    }catch(SQLException se){
			// Handle errors for JDBC
			se.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}

	    
	    rs = null;
		
		// quest loader
	    for(int j = 0; j < 39; j++){
	    	questbloat.add(" ");
	    }
	    
	  for(int i = 0; i< questcount; i++){
	    	listofquests.add(new ArrayList<String>(questbloat));
	  }
	    
		try{
			Class.forName("com.mysql.jdbc.Driver");
			
			System.out.println("Loading Quest Data ...");
			
			for(int i = 0; i < questcount ; i++){
				rs = UserVerify.stmt.executeQuery("SELECT QuestID, Type, Started, Complete, Title, MinLevel, MaxLevel, Solo, Timer, TimeLeft, MobName, MobAmount, MobKilled, Collect1, Amount1, Left1, Collect2, Amount2, Left2, Collect3, Amount3, Left3, Collect4, Amount4, Left4, Collect5, Amount5, Left5, Collect6, Amount6, Left6, Gold, XP, Item1, Item2, Item3, Item4, Item5, Item6 FROM Quests_" + Game.Playername + " WHERE QuestID = " + i);
				
				while(rs.next()){
					QuestIDint = rs.getInt("QuestID");
					Type = rs.getString("Type");
					Startedint = rs.getInt("Started");
					Completeint = rs.getInt("Complete");
					Title = rs.getString("Title");
					MinLevelint = rs.getInt("MinLevel");//
					MaxLevelint = rs.getInt("MaxLevel");//
					Soloint = rs.getInt("Solo");//
					Timerint = rs.getInt("Timer");//
					TimeLeftint = rs.getInt("TimeLeft");//
					MobName = rs.getString("MobName");
					MobAmountint = rs.getInt("MobAmount");//
					MobKilledint = rs.getInt("MobKilled");//
					Collect1 = rs.getString("Collect1");
					Amount1int = rs.getInt("Amount1");//
					Left1int = rs.getInt("Left1");//
					Collect2 = rs.getString("Collect2");
					Amount2int = rs.getInt("Amount2");//
					Left2int = rs.getInt("Left2");//
					Collect3 = rs.getString("Collect3");
					Amount3int = rs.getInt("Amount3");//
					Left3int = rs.getInt("Left3");//
					Collect4 = rs.getString("Collect4");
					Amount4int = rs.getInt("Amount4");//
					Left4int = rs.getInt("Left4");//
					Collect5 = rs.getString("Collect5");
					Amount5int = rs.getInt("Amount5");//
					Left5int = rs.getInt("Left5");//
					Collect6 = rs.getString("Collect6");
					Amount6int = rs.getInt("Amount6");//
					Left6int = rs.getInt("Left6");//
					QGoldint = rs.getInt("Gold");//
					QXPint = rs.getInt("XP");//
					Item1 = rs.getString("Item1");
					Item2= rs.getString("Item2");
					Item3= rs.getString("Item3");
					Item4= rs.getString("Item4");
					Item5= rs.getString("Item5");
					Item6= rs.getString("Item6");
					
					
					//convert the ints to strings
					QuestID = Integer.toString(QuestIDint);
					Started = Integer.toString(Startedint);
					Complete = Integer.toString(Completeint);
					MinLevel = Integer.toString(MinLevelint);
					MaxLevel = Integer.toString(MaxLevelint);
					Solo = Integer.toString(Soloint);
					Timer = Integer.toString(Timerint);
					TimeLeft = Integer.toString(TimeLeftint);
					MobAmount = Integer.toString(MobAmountint);
					MobKilled = Integer.toString(MobKilledint);
					Amount1 = Integer.toString(Amount1int);
					Left1 = Integer.toString(Left1int);
					Amount2 = Integer.toString(Amount2int);
					Left2 = Integer.toString(Left2int);
					Amount3 = Integer.toString(Amount3int);
					Left3 = Integer.toString(Left3int);
					Amount4 = Integer.toString(Amount4int);
					Left4 = Integer.toString(Left4int);
					Amount5 = Integer.toString(Amount5int);
					Left5 = Integer.toString(Left5int);
					Amount6 = Integer.toString(Amount6int);
					Left6 = Integer.toString(Left6int);
					QGold = Integer.toString(QGoldint);
					QXP = Integer.toString(QXPint);
					
					
					// Load the quest data into an array list
					System.out.println("loading data in ...");
					listofquests.get(i).set(0, QuestID);
					listofquests.get(i).set(1, Type);
					listofquests.get(i).set(2, Started);
					listofquests.get(i).set(3,  Complete);
					listofquests.get(i).set(4, Title);
					listofquests.get(i).set(5, MinLevel);
					listofquests.get(i).set(6, MaxLevel);
					listofquests.get(i).set(7, Solo);
					listofquests.get(i).set(8, Timer);
					listofquests.get(i).set(9, TimeLeft);
					listofquests.get(i).set(10, MobName);
					listofquests.get(i).set(11, MobAmount);
					listofquests.get(i).set(12, MobKilled);
					listofquests.get(i).set(13, Collect1);
					listofquests.get(i).set(14, Amount1);
					listofquests.get(i).set(15, Left1);
					listofquests.get(i).set(16, Collect2);
					listofquests.get(i).set(17, Amount2);
					listofquests.get(i).set(18, Left2);
					listofquests.get(i).set(19, Collect3);
					listofquests.get(i).set(20, Amount3);
					listofquests.get(i).set(21, Left3);
					listofquests.get(i).set(22, Collect4);
					listofquests.get(i).set(23, Amount4);
					listofquests.get(i).set(24, Left4);
					listofquests.get(i).set(25, Collect5);
					listofquests.get(i).set(26, Amount5);
					listofquests.get(i).set(27, Left5);
					listofquests.get(i).set(28, Collect6);
					listofquests.get(i).set(29, Amount6);
					listofquests.get(i).set(30, Left6);
					listofquests.get(i).set(31, QGold);
					listofquests.get(i).set(32, QXP);
					listofquests.get(i).set(33, Item1);
					listofquests.get(i).set(34, Item2);
					listofquests.get(i).set(35, Item3);
					listofquests.get(i).set(36, Item4);
					listofquests.get(i).set(37, Item5);
					listofquests.get(i).set(38, Item6);	
				}				
			}
		}catch(SQLException se){
			// Handle errors for JDBC
			se.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("Loaded " + questcount + " Quests ...");
		System.out.println("Loading Player's Quest Log ...");
		questlog();
		System.out.println("Loading World ...");
		Game.launch();
	}
	
	public static void questlog(){
		Player.quests.clear();
		Player.activequests = 0;
		for(int i = 0; i < InitialStat.questcount; i++){
			if(InitialStat.listofquests.get(i).get(2).equals("1") && InitialStat.listofquests.get(i).get(3).equals("0")){
				Player.activequests++;
				Player.quests.add(new ArrayList<String>(InitialStat.questbloat));
				Player.quests.get(i).set(0, listofquests.get(i).get(0));
				Player.quests.get(i).set(1, listofquests.get(i).get(1));
				Player.quests.get(i).set(2, listofquests.get(i).get(2));
				Player.quests.get(i).set(3, listofquests.get(i).get(3));
				Player.quests.get(i).set(4, listofquests.get(i).get(4));
				Player.quests.get(i).set(5, listofquests.get(i).get(5));
				Player.quests.get(i).set(6, listofquests.get(i).get(6));
				Player.quests.get(i).set(7, listofquests.get(i).get(7));
				Player.quests.get(i).set(8, listofquests.get(i).get(8));
				Player.quests.get(i).set(9, listofquests.get(i).get(9));
				Player.quests.get(i).set(10, listofquests.get(i).get(10));
				Player.quests.get(i).set(11, listofquests.get(i).get(11));
				Player.quests.get(i).set(12, listofquests.get(i).get(12));
				Player.quests.get(i).set(13, listofquests.get(i).get(13));
				Player.quests.get(i).set(14, listofquests.get(i).get(14));
				Player.quests.get(i).set(15, listofquests.get(i).get(15));
				Player.quests.get(i).set(16, listofquests.get(i).get(16));
				Player.quests.get(i).set(17, listofquests.get(i).get(17));
				Player.quests.get(i).set(18, listofquests.get(i).get(18));
				Player.quests.get(i).set(19, listofquests.get(i).get(19));
				Player.quests.get(i).set(20, listofquests.get(i).get(20));
				Player.quests.get(i).set(21, listofquests.get(i).get(21));
				Player.quests.get(i).set(22, listofquests.get(i).get(22));
				Player.quests.get(i).set(23, listofquests.get(i).get(23));
				Player.quests.get(i).set(24, listofquests.get(i).get(24));
				Player.quests.get(i).set(25, listofquests.get(i).get(25));
				Player.quests.get(i).set(26, listofquests.get(i).get(26));
				Player.quests.get(i).set(27, listofquests.get(i).get(27));
				Player.quests.get(i).set(28, listofquests.get(i).get(28));
				Player.quests.get(i).set(29, listofquests.get(i).get(29));
				Player.quests.get(i).set(30, listofquests.get(i).get(30));
				Player.quests.get(i).set(31, listofquests.get(i).get(31));
				Player.quests.get(i).set(32, listofquests.get(i).get(32));
				Player.quests.get(i).set(33, listofquests.get(i).get(33));
				Player.quests.get(i).set(34, listofquests.get(i).get(34));
				Player.quests.get(i).set(35, listofquests.get(i).get(35));
				Player.quests.get(i).set(36, listofquests.get(i).get(36));
				Player.quests.get(i).set(37, listofquests.get(i).get(37));
				Player.quests.get(i).set(38, listofquests.get(i).get(38));
			}
		}
	}
	
}
