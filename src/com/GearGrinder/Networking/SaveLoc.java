package com.GearGrinder.Networking;

import java.sql.SQLException;

import com.GearGrinder.Game;

public class SaveLoc {

	public static void SaveLoc() {
		int rs = 0;

		try {
			// Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");
			rs = UserVerify.stmt.executeUpdate("UPDATE `accounts` SET `Online`='" + InitialStat.Onlineint + "', `SpriteDirection`='" + InitialStat.PSpriteDir + "', `Xloc`='" + Game.currentx + "', `Yloc`='" + Game.currenty + "' WHERE (`AccountID`='" + UserVerify.clientID + "') LIMIT 1");
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		SaveQuests();
	}

	public static void SaveQuests() {

		int rs = 0;

		try {
			// Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");
			int a = 0;
			int b = 0;
			for (int i = 0; i < InitialStat.questcount; i++) {
				a = Integer.valueOf(InitialStat.listofquests.get(i).get(2));
				b = Integer.valueOf(InitialStat.listofquests.get(i).get(3));
				rs = UserVerify.stmt.executeUpdate("UPDATE `Quests_" + Game.Playername + "` SET `Started`='" + a + "',`Complete`='" + b + "' WHERE (`QuestID`='" + i + "') LIMIT 1");
				rs = 0;
			}
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}