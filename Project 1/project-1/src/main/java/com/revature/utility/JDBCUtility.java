package com.revature.utility;

import java.sql.Connection; 
import java.sql.DriverManager;
import java.sql.SQLException;

import org.postgresql.Driver;

public class JDBCUtility {
	
	public static Connection getConnection() throws SQLException {
		
		String url = "jdbc:postgresql://localhost:5432/reimbursement";
		String username = "postgres";
		String password = "Xmovpno5";

		Driver postgresDriver = new Driver();		
		DriverManager.registerDriver(postgresDriver);

		Connection con = DriverManager.getConnection(url, username, password);
		
		return con;
	}
	
}
