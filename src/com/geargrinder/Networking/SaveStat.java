package com.geargrinder.Networking;

import java.sql.SQLException;

import com.geargrinder.Game;

public class SaveStat {

	public static boolean saving = false;

	public static void SaveStat() {
		saving = true;
		int rs = 0;

		try {
			// Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			rs = UserVerify.stmt.executeUpdate("UPDATE `accounts` SET `Online`='" + InitialStat.Onlineint + "',`SpriteDirection`='" + InitialStat.PSpriteDir + "',`Zone`='" + Game.currentzone + "',`Xloc`='" + Game.currentx + "', `Yloc`='" + Game.currenty + "', `Level`='" + InitialStat.PlayerLevel + "" + "', `XP`='" + InitialStat.XP + "', `Health`='" + InitialStat.health + "', `Magic`='" + InitialStat.magic + "', `Stamina`='" + InitialStat.stamina + "" + "', `Strength`='" + InitialStat.strength + "', `Defense`='" + InitialStat.defense + "', `Vitality`='" + InitialStat.vitality + "" + "', `Agility`='" + InitialStat.agility + "', `Intelligence`='" + InitialStat.intelligence + "', `Dexterity`='" + InitialStat.dexterity + "" + "', `Luck`='" + InitialStat.luck + "', `Gold`='" + InitialStat.gold + "' WHERE (`AccountID`='" + UserVerify.clientID + "') LIMIT 1");
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		saving = false;
	}
}