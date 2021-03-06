package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.model.User;
import com.revature.utility.JDBCUtility;

public class UserDAO {
	public User getUserByUsernameAndPassword(String username, String password) throws SQLException {
		try (Connection con = JDBCUtility.getConnection()) {
			String sql = "SELECT * FROM users WHERE user_username = ? AND user_password = ?";
			
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				int id = rs.getInt("user_id");
				String user = rs.getString("user_username");
				String pass = rs.getString("user_password");
				String firstName = rs.getString("user_first_name");
				String lastName = rs.getString("user_last_name");
				String email = rs.getString("user_email");
				String userRole = rs.getString("user_role");
				
				return new User(id, user, pass, firstName, lastName, email, userRole);
			}
			else {
				return null;
			}
		}
	}
}
