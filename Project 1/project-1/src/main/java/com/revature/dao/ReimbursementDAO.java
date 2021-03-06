package com.revature.dao;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.revature.model.Reimbursement;
import com.revature.utility.JDBCUtility;

public class ReimbursementDAO {

	public List<Reimbursement> getAllReimbursement() throws SQLException {
		try (Connection con = JDBCUtility.getConnection()) {
			List<Reimbursement> reimbursements = new ArrayList<>();

			String sql = "SELECT reimburse_id, reimburse_amount, reimburse_submitted, reimburse_resolved, "
					+ "reimburse_status, reimburse_type, reimburse_description, "
					+ "reimburse_author, reimburse_resolver FROM reimbursements";
			PreparedStatement pstmt = con.prepareStatement(sql);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				int reimburseId = rs.getInt("reimburse_id");
				int reimburseAmount = rs.getInt("reimburse_amount");
				String reimburseSubmitted = rs.getString("reimburse_submitted");
				String reimburseResolved = rs.getString("reimburse_resolved");
				String reimburseStatus = rs.getString("reimburse_status");
				String reimburseType = rs.getString("reimburse_type");
				String reimburseDescription = rs.getString("reimburse_description");
				int employeeId = rs.getInt("reimburse_author");
				int financeManagerId = rs.getInt("reimburse_resolver");

				Reimbursement reimbursement = new Reimbursement(reimburseId, reimburseAmount, reimburseSubmitted,
						reimburseResolved, reimburseStatus, reimburseType, reimburseDescription, employeeId, financeManagerId);

				reimbursements.add(reimbursement);
			}
			return reimbursements;
		}
	}

	public List<Reimbursement> getAllReimbursementByEmployee(int authorId) throws SQLException {
		try (Connection con = JDBCUtility.getConnection()) {
			List<Reimbursement> reimbursements = new ArrayList<>();

			String sql = "SELECT reimburse_id, reimburse_amount, reimburse_submitted, reimburse_resolved, reimburse_status, "
					+ "reimburse_type, reimburse_description, reimburse_author, reimburse_resolver "
					+ "FROM reimbursements WHERE reimburse_author = ?";

			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, authorId);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				int reimburseId = rs.getInt("reimburse_id");
				int reimburseAmount = rs.getInt("reimburse_amount");
				String reimburseSubmitted = rs.getString("reimburse_submitted");
				String reimburseResolved = rs.getString("reimburse_resolved");
				String reimburseStatus = rs.getString("reimburse_status");
				String reimburseType = rs.getString("reimburse_type");
				String reimburseDescription = rs.getString("reimburse_description");
				int employeeId = rs.getInt("reimburse_author");
				int financeManagerId = rs.getInt("reimburse_resolver");

				Reimbursement reimbursement = new Reimbursement(reimburseId, reimburseAmount, reimburseSubmitted,
						reimburseResolved, reimburseStatus, reimburseType, reimburseDescription, employeeId, financeManagerId);

				reimbursements.add(reimbursement);
			}
			return reimbursements;
		}
	}

	public Reimbursement getReimbursementById(int reimbursementId) throws SQLException {
		try (Connection con = JDBCUtility.getConnection()) {

			String sql = "SELECT reimburse_id, reimburse_amount, reimburse_submitted, reimburse_resolved, reimburse_status, reimburse_type, "
					+ "reimburse_description, reimburse_author, reimburse_resolver "
					+ "FROM reimbursements WHERE reimburse_id = ?";
			
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, reimbursementId);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				int reimburseId = rs.getInt("reimburse_id");
				int reimburseAmount = rs.getInt("reimburse_amount");
				String reimburseSubmitted = rs.getString("reimburse_submitted");
				String reimburseResolved = rs.getString("reimburse_resolved");
				String reimburseStatus = rs.getString("reimburse_status");
				String reimburseType = rs.getString("reimburse_type");
				String reimburseDescription = rs.getString("reimburse_description");
				int employeeId = rs.getInt("reimburse_author");
				int financeManagerId = rs.getInt("reimburse_resolver");

				return new Reimbursement(reimburseId, reimburseAmount, reimburseSubmitted, reimburseResolved,
						reimburseStatus, reimburseType, reimburseDescription, employeeId, financeManagerId);
			} else {
				return null;
			}
		}
	}

	public void changeReimbursement(int reimburseId, String reimburseStatus, int financeManagerId) throws SQLException {
		try (Connection con = JDBCUtility.getConnection()) { 
			String sql = "UPDATE reimbursements SET reimburse_resolved = TO_TIMESTAMP(EXTRACT(epoch FROM NOW())), "
					+ "reimburse_status = ?, reimburse_resolver = ? WHERE reimburse_id = ?;";
			
			PreparedStatement pstmt = con.prepareStatement(sql);

			pstmt.setString(1, reimburseStatus);
			pstmt.setInt(2, financeManagerId);
			pstmt.setInt(3, reimburseId);

			int updatedCount = pstmt.executeUpdate();

			if (updatedCount != 1) {
				throw new SQLException("An error has occured attempting to update the reimbursement amount");
			}
		}
	}

	public Reimbursement addedReimbursement(int reimburseAmount, String reimburseType, String reimburseDescription, 
			InputStream image, int employeeId) throws SQLException {
		try (Connection con = JDBCUtility.getConnection()) {
			con.setAutoCommit(false); 

			String sql = "INSERT INTO reimbursements (reimburse_amount, reimburse_submitted, reimburse_type, reimburse_description, "
					+ "reimburse_recipt, reimburse_author)"
					+ " VALUES (?, TO_TIMESTAMP(EXTRACT(epoch FROM NOW())), ?, ?, ?, ?);";
			 
			PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			pstmt.setInt(1, reimburseAmount);
			pstmt.setString(2, reimburseType);
			pstmt.setString(3, reimburseDescription);
			pstmt.setBinaryStream(4, image); 
			pstmt.setInt(5, employeeId);

			int numberOfInsertedRecords = pstmt.executeUpdate();

			if (numberOfInsertedRecords != 1) {
				throw new SQLException("An error has occurred when attempting to add the reimbursement");
			}

			ResultSet rs = pstmt.getGeneratedKeys();

			rs.next();
			int generatedId = rs.getInt(1);
			Date ResolvedDate = rs.getTimestamp(3);
			String date = "" + ResolvedDate;

			con.commit(); 
			
			return new Reimbursement(generatedId, reimburseAmount, date, null, "PENDING", reimburseType, 
					reimburseDescription, employeeId, 0);
		}
	}

	public InputStream getImageFromReimbursementById(int id) throws SQLException {
		try (Connection con = JDBCUtility.getConnection()) {
			String sql = "SELECT reimburse_recipt FROM reimbursements WHERE reimburse_id = ?";

			PreparedStatement pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, id);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				InputStream image = rs.getBinaryStream("reimburse_recipt");

				return image;
			}
			return null;
		}
	}
}
