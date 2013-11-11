package com.GearGrinder.Networking;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.GearGrinder.entity.Entity;
import com.GearGrinder.graphics.Screen;
import com.GearGrinder.graphics.Sprite;

public class GetLoc extends Entity{
public static int xp1;
public static int yp1;
	public static void GetLoc(){
		sprite = Sprite.player_down;
		try{
			// Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");
			for(int i = 1; i < UserVerify.rowCount; i++){
				//System.out.println("INDEX: " + i);
				ResultSet rs = null;
					rs = GetLocThread.stmt.executeQuery("SELECT Xloc, Yloc FROM accounts WHERE AccountID = " + i);	
					while(rs.next()){
						xp1 = rs.getInt("Xloc");
						yp1 = rs.getInt("Yloc");
						//System.out.println("INDEX: " + i + " ID: " + i + "  X: " + x + "  Y: " + y);
					}
			}
		}catch(SQLException se){
			// Handle errors for JDBC
			se.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
