package com.GearGrinder.Networking;

import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.GearGrinder.Game;

public class SaveStat {

	// JDBC driver name and database URL
		static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
		static final String DB_URL = "jdbc:mysql://184.168.194.136/GGdb";
		
		// Database credentials
		static final String USER = "GGdb";
		static final String PASS = "GGgg##12";
		
		public static boolean saving = false;
		
		public static void SaveStat(){
			saving = true;
			int rs = 0;
			
			try{
				// Register JDBC driver
				Class.forName("com.mysql.jdbc.Driver");
				
					rs = UserVerify.stmt.executeUpdate("UPDATE `accounts` SET `Online`='"+InitialStat.Onlineint+"',`Zone`='"+Game.currentzone+"',`Xloc`='"+Game.currentx+"', `Yloc`='"+Game.currenty+"', `Level`='"+InitialStat.PlayerLevel+""
							+ "', `XP`='"+InitialStat.XP+"', `Health`='"+InitialStat.health+"', `Magic`='"+InitialStat.magic+"', `Stamina`='"+InitialStat.stamina+""
							+ "', `Strength`='"+InitialStat.strength+"', `Defense`='"+InitialStat.defense+"', `Vitality`='"+InitialStat.vitality+""
							+ "', `Agility`='"+InitialStat.agility+"', `Intelligence`='"+InitialStat.intelligence+"', `Dexterity`='"+InitialStat.dexterity+""
							+ "', `Luck`='"+InitialStat.luck+"', `Gold`='"+InitialStat.gold+"' WHERE (`AccountID`='"+UserVerify.clientID+"') LIMIT 1");							
			}catch(SQLException se){
				// Handle errors for JDBC
				se.printStackTrace();
			}catch(Exception e){
				e.printStackTrace();
			}/*finally{
				try{
					if(stmt!=null) conn.close();
				}catch(SQLException se){
					se.printStackTrace();
				}
			}*/
			//System.out.println("Closed Database connection.");
			saving = false;
		}
	
}
