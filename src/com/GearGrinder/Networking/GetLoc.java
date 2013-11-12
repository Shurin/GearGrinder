package com.GearGrinder.Networking;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.GearGrinder.entity.Entity;

public class GetLoc extends Entity{
public static int xp1;
public static int yp1;
public static int op1;
public static String psdir = null;
public static int getlocI;
public static Boolean RENDER = false;
	public static void GetLoc(){
		try{
			// Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");
			for(getlocI = 1; getlocI < UserVerify.rowCount; getlocI++){
				//System.out.println("INDEX: " + i);
				ResultSet rs = null;
					rs = GetLocThread.stmt.executeQuery("SELECT Online, SpriteDirection, Xloc, Yloc FROM accounts WHERE AccountID = " + getlocI);	
					while(rs.next()){
						op1 = rs.getInt("Online");
						if(op1 == 1 && getlocI != UserVerify.clientID){
							psdir = rs.getString("SpriteDirection");
							xp1 = rs.getInt("Xloc");
							yp1 = rs.getInt("Yloc");
							RENDER = true;
						}else{
							RENDER = false;
						}
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
