package com.GearGrinder.Networking;

import java.sql.SQLException;

import com.GearGrinder.Game;

public class SaveLoc {
		
		public static void SaveLoc(){
			int rs = 0;
			
			try{
				// Register JDBC driver
				Class.forName("com.mysql.jdbc.Driver");				
					rs = UserVerify.stmt.executeUpdate("UPDATE `accounts` SET `Online`='"+InitialStat.Onlineint+"', `SpriteDirection`='"+InitialStat.PSpriteDir+"', `Xloc`='"+Game.currentx+"', `Yloc`='"+Game.currenty+"' WHERE (`AccountID`='"+UserVerify.clientID+"') LIMIT 1");							
			}catch(SQLException se){
				// Handle errors for JDBC
				se.printStackTrace();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	
}