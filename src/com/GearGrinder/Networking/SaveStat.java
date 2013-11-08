package com.GearGrinder.Networking;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.GearGrinder.Game;

public class SaveStat {

	// JDBC driver name and database URL
		static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
		static final String DB_URL = "jdbc:mysql://localhost/ggdb";
		
		// Database credentials
		static final String USER = "root";
		static final String PASS = "RRRRrrrr$$$$4444r4";
		
		public static void SaveStat(){
			Connection conn = null;
			Statement stmt = null;
			int rs = 0;
			
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
				
					rs = stmt.executeUpdate("UPDATE `accounts` SET `Xloc`='"+Game.currentx+"', `Yloc`='"+Game.currenty+"', `Level`='"+InitialStat.PlayerLevel+""
							+ "', `XP`='"+InitialStat.XP+"', `Health`='"+InitialStat.currenthealth+"', `Magic`='"+InitialStat.currentmagic+"', `Stamina`='"+InitialStat.currentstamina+""
							+ "', `Strength`='"+InitialStat.strength+"', `Defense`='"+InitialStat.defense+"', `Vitality`='"+InitialStat.vitality+""
							+ "', `Agility`='"+InitialStat.agility+"', `Intelligence`='"+InitialStat.intelligence+"', `Dexterity`='"+InitialStat.dexterity+""
							+ "', `Luck`='"+InitialStat.luck+"', `Gold`='"+InitialStat.gold+"' WHERE (`AccountID`='"+UserVerify.clientID+"') LIMIT 1");							
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
		}
	
}
