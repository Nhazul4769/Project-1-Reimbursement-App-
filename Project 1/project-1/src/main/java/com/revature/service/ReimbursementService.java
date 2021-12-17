package com.revature.service;

import java.io.InputStream; 
import java.security.InvalidParameterException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.revature.dao.ReimbursementDAO;
import com.revature.exception.ReimbursementAlreadyResolvedException;
import com.revature.exception.ReceiptNotFoundException;
import com.revature.exception.ReimbursementNotFoundException;
import com.revature.exception.UnauthorizedException;
import com.revature.model.Reimbursement;
import com.revature.model.User;

public class ReimbursementService {

	private ReimbursementDAO reimbursementDao;

	public ReimbursementService() {
		this.reimbursementDao = new ReimbursementDAO();
	}

	public ReimbursementService(ReimbursementDAO reimbursementDao) {
		this.reimbursementDao = reimbursementDao;
	}

	public List<Reimbursement> getReimbursement(User currentlyLoggedInUser) throws SQLException {
		List<Reimbursement> reimbursement = null;

		if (currentlyLoggedInUser.getUserRole().equals("Finance Manager")) {
			reimbursement = this.reimbursementDao.getAllReimbursement();
		} else if (currentlyLoggedInUser.getUserRole().equals("Employee")) {
			reimbursement = this.reimbursementDao.getAllReimbursementByEmployee(currentlyLoggedInUser.getUserId());
		}

		return reimbursement;
	}

	public Reimbursement changeReimbursement(User currentlyLoggedInUser, String reimbursementId,
			String status)
			throws SQLException, ReimbursementAlreadyResolvedException, ReimbursementNotFoundException {
		try {
			int id = Integer.parseInt(reimbursementId);

			Reimbursement reimbursement = this.reimbursementDao.getReimbursementById(id);

			if (reimbursement == null) {
				throw new ReimbursementNotFoundException(
						"Reimbursement with an id of " + reimbursementId + " was not found");
			}

			if (reimbursement.getFinanceManagerId() == 0) {
				
				this.reimbursementDao.changeReimbursement(id, status, currentlyLoggedInUser.getUserId());
			} else { 
				throw new ReimbursementAlreadyResolvedException(
						"Reimbursement has already been given an amount, so the amount cannot be changed");
			}

			return this.reimbursementDao.getReimbursementById(id);
		} catch (NumberFormatException e) {
			throw new InvalidParameterException("Reimbursement id supplied must be an int");
		}
	}

	public Reimbursement addReimbursement(User currentlyLoggedInUser, String mimeType, String reimbursementAmount,
			String type, String reimbursementDesc, InputStream content)
			throws SQLException {
		
		int rAmount = Integer.parseInt(reimbursementAmount);

		Set<String> allowedFileTypes = new HashSet<>();
		allowedFileTypes.add("image/jpeg");
		allowedFileTypes.add("image/png");
		allowedFileTypes.add("image/gif");
		 
		if (!allowedFileTypes.contains(mimeType)) {
			throw new InvalidParameterException("When adding an recipt image, only PNG, JPEG, or GIF are allowed");
		}

		int authorId = currentlyLoggedInUser.getUserId();

		Reimbursement addedReimbursement = this.reimbursementDao.addedReimbursement(rAmount, type, reimbursementDesc, content, authorId);

		return addedReimbursement;
	}

	public InputStream getImageFromReimbursementById(User currentlyLoggedInUser, String reimbursementId)
			throws SQLException, UnauthorizedException, ReceiptNotFoundException {
		try {
			int id = Integer.parseInt(reimbursementId);

			if (currentlyLoggedInUser.getUserRole().equals("Employee")) {

				int userId = currentlyLoggedInUser.getUserId();
				List<Reimbursement> reimbursementThatBelongToEmployee = this.reimbursementDao
						.getAllReimbursementByEmployee(userId);

				Set<Integer> reimbursementIdsEncountered = new HashSet<>();
				for (Reimbursement r : reimbursementThatBelongToEmployee) {
					reimbursementIdsEncountered.add(r.getReimburseId());
				}

				if (!reimbursementIdsEncountered.contains(id)) {
					throw new UnauthorizedException(
							"You cannot access the recipt images of reimbursements that do not belong to yourself");
				}
			}

			InputStream image = this.reimbursementDao.getImageFromReimbursementById(id);

			if (image == null) {
				throw new ReceiptNotFoundException("Recipt image was not found for reimbursement id " + id);
			}

			return image;

		} catch (NumberFormatException e) { 
			throw new InvalidParameterException("Reimbursement id supplied must be an int");
		}
	}

}

	

