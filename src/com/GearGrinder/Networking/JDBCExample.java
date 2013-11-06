package com.GearGrinder.Networking;

//Step 1: Use interfaces from java.sql package
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCExample{

	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/ggdb";
	
	// Database credentials
	static final String USER = "root";
	static final String PASS = "RRRRrrrr$$$$4444r4";
	
	public static void DBTEST(){
		Connection conn = null;
		Statement stmt = null;
		
		try{
			// Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");
			
			// Open a connection
			System.out.println("Connecting to database ...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			System.out.println("Connected database succesfully ...");
			
			// Execute a query
			System.out.println("Creating statement ...");
			stmt = conn.createStatement();
			
			String sql = "SELECT AccountID, AccountName, AccountPwd, PlayerName FROM accounts";
			ResultSet rs = stmt.executeQuery(sql);
			
			// Extract data from result set
			while(rs.next()){
				int ID = rs.getInt("AccountID");
				String NAME = rs.getString("AccountName");
				String PWD = rs.getString("AccountPwd");
				String PLAYER = rs.getString("PlayerName");
				
				// Display values
				System.out.println("ID: " + ID);
				System.out.println("NAME: " + NAME);
				System.out.println("PWD: " + PWD);
				System.out.println("PLAYER: " + PLAYER);
			}
			rs.close();
		}catch(SQLException se){
			// Handle errors for JDBC
			se.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			// finally block used to close resources
			try{
				if(stmt!=null) conn.close();
			}catch(SQLException se){
				se.printStackTrace();
			}// end finally try
		}// end try
		System.out.println("GOODBYE!!!!!!!");
	}//end main
}//end 
