package com.GearGrinder.Networking;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.GearGrinder.Game;
import com.GearGrinder.LoginPage;

public class UserVerify {

	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://184.168.194.136/GGdb";
	
	// Database credentials
	static final String USER = "GGdb";
	static final String PASS = "GGgg##12";
	
	static final String tablename = "accounts";
	public static int rowCount = -1;
	public static int clientID = 0;
	public static String Pusername = null;
	public static String Ppassword = null;
	static boolean loggedin = false;
	public static Connection conn = null;
	public static Statement stmt = null;
	public static ResultSet rs = null;
	
	
	public static void UserVerify(){
		
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
			
			//finds total number of rows in accounts table
			countRows(conn, tablename);
			System.out.println("TOTAL ROWS: " + rowCount);
			
			String []accounts = new String[rowCount + 1];
			String []passwords = new String[rowCount + 1];
			
			for(int Rowi = 0; Rowi < rowCount + 1; Rowi ++){
				rs = stmt.executeQuery("SELECT AccountName, AccountPwd FROM accounts WHERE AccountID = " + Rowi);
				while(rs.next()){
					String index = rs.getString("AccountName");
					String index2 = rs.getString("AccountPwd");
					accounts[Rowi] = index;
					passwords[Rowi] = index2;
				}
			}
			
			for(int i = 0; i < rowCount; i ++){
				if((accounts[i+1].equals(LoginPage.usrname)) && (passwords[i+1].equals(LoginPage.usrpass))){
					clientID = (i+1);
					Pusername = LoginPage.usrname;
					Ppassword = LoginPage.usrpass;
					System.out.println("LOGGED IN AS: " + Pusername + " : " + Ppassword);
					loggedin = true;
					InitialStat.InitialStat();
				}
			}
			
			if(loggedin == false){
				LoginPage.LoginPage();
			}
			
		}catch(SQLException se){
			// Handle errors for JDBC
			se.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static int countRows(Connection conn, String tableName) throws SQLException {
	    // select the number of rows in the table
	    ResultSet rs = null;
	    try {
	      rs = stmt.executeQuery("SELECT COUNT(*) FROM " + tableName);
	      // get the number of rows from the result set
	      rs.next();
	      rowCount = rs.getInt(1);
	    } finally {
	    }
	    return rowCount;
	  }
}
