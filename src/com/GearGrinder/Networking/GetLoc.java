package com.GearGrinder.Networking;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.GearGrinder.Game;
import com.GearGrinder.entity.Entity;

public class GetLoc extends Entity{
public static int xp1; // xlocation
public static int yp1; // ylocation
public static int op1; // online status
public static String pspr = null; // player's sprite
public static String psdir = null; // sprite direction
public static String pname = null; // player's name
public static String pzone = null; // player's zone
public static int getlocI; // row index
public static Boolean RENDER = false;
	public static void GetLoc(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			for (getlocI = 1; getlocI < UserVerify.rowCount; getlocI++) {
				ResultSet rs = null;
				rs = GetLocThread.stmt
						.executeQuery("SELECT Online, Sprite, SpriteDirection, PlayerName, Zone, Xloc, Yloc FROM accounts WHERE AccountID = " + getlocI);
				while (rs.next()) {
					op1 = rs.getInt("Online");
					if (op1 == 1 && getlocI != UserVerify.clientID) {
						pspr = rs.getString("Sprite");
						psdir = rs.getString("SpriteDirection");
						pname = rs.getString("PlayerName");
						pzone = rs.getString("Zone");
						xp1 = rs.getInt("Xloc");
						yp1 = rs.getInt("Yloc");
						//System.out.println("c:" + Game.currentzone + "M p:" + pzone);
						if (Game.currentzone.equals(pzone)) {
							//System.out.println("this should be rendering other players now");
							RENDER = true;
						}
					} else {
						RENDER = false;
					}
				}
			}
		}catch(SQLException se){
			se.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
