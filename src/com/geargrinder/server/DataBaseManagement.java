package com.geargrinder.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.geargrinder.Networking.InitialStat;

public class DataBaseManagement {

	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://184.168.194.136/GGdb";
	static final String USER = "GGdb";
	static final String PASS = "GGgg##12";

	static final String tablename = "accounts";
	public static Connection conn = null;
	public static Statement stmt = null;
	public static ResultSet rs = null;

	public static boolean login(String user, String pass) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();

			rs = stmt.executeQuery("SELECT * FROM accounts WHERE AccountName='" + user + "' AND AccountPwd='" + pass + "'");
			while (rs.next()) {
				String index = rs.getString("AccountName");
				InitialStat.InitialStat();
				return true;
			}
			return false;
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}