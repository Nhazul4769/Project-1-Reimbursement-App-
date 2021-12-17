package com.revature.controller;

import java.io.InputStream; 
import java.util.List;

import org.apache.tika.Tika;

import com.revature.dto.ReimbursementDTO;
import com.revature.dto.MessageDTO;
import com.revature.model.Reimbursement;
import com.revature.model.User;
import com.revature.service.AuthorizationService;
import com.revature.service.ReimbursementService;

import io.javalin.Javalin;
import io.javalin.http.Handler;
import io.javalin.http.UploadedFile;

public class ReimbursementController implements Controller {

	private AuthorizationService authService;
	private ReimbursementService reimbursementService;

	public ReimbursementController() {
		this.authService = new AuthorizationService();
		this.reimbursementService = new ReimbursementService();
	}

	private Handler getReimbursement = (ctx) -> {

		User currentlyLoggedInUser = (User) ctx.req.getSession().getAttribute("currentuser");
		this.authService.authroizeEmployeeAndFinanceManager(currentlyLoggedInUser);

		List<Reimbursement> reimbursements = this.reimbursementService.getReimbursement(currentlyLoggedInUser);

		ctx.json(reimbursements);
	};

	private Handler changeReimbursement = (ctx) -> {
		User currentlyLoggedInUser = (User) ctx.req.getSession().getAttribute("currentuser");
		this.authService.authorizeFinanceManager(currentlyLoggedInUser);

		String reimbursementId = ctx.pathParam("reimburse_id"); 
		ReimbursementDTO dto = ctx.bodyAsClass(ReimbursementDTO.class); 

		Reimbursement changedReimbursement = this.reimbursementService.changeReimbursement(currentlyLoggedInUser,
				reimbursementId, dto.getReimburseStatus());
		ctx.json(changedReimbursement);
	};

	private Handler addReimbursement = (ctx) -> {

		User currentlyLoggedInUser = (User) ctx.req.getSession().getAttribute("currentuser");
		this.authService.authorizeEmployee(currentlyLoggedInUser);

		String reimbursementAmount = ctx.formParam("reimburse_amount");
		String type = ctx.formParam("reimburse_type");
		String reimbursementDesc = ctx.formParam("reimburse_decription");

		UploadedFile file = ctx.uploadedFile("reimburse_recipt");

		if (file == null) {
			ctx.status(400);
			ctx.json(new MessageDTO("Must have an image of the recipt to upload"));
			return;
		}

		InputStream content = file.getContent();

		Tika tika = new Tika();

		String mimeType = tika.detect(content);

		Reimbursement addedReimbursement = this.reimbursementService.addReimbursement(currentlyLoggedInUser, mimeType, reimbursementAmount,
				type, reimbursementDesc, content);
		ctx.json(addedReimbursement);
		ctx.status(201);
	};

	private Handler getImageFromReimbursementById = (ctx) -> {
		
		User currentlyLoggedInUser = (User) ctx.req.getSession().getAttribute("currentuser");
		this.authService.authroizeEmployeeAndFinanceManager(currentlyLoggedInUser);

		String reimbursementId = ctx.pathParam("reimburse_id");

		InputStream image = this.reimbursementService.getImageFromReimbursementById(currentlyLoggedInUser,
				reimbursementId);

		Tika tika = new Tika();
		String mimeType = tika.detect(image);

		ctx.contentType(mimeType);
		ctx.result(image); 
	};

	@Override
	public void mapEndpoints(Javalin app) {
		app.get("/reimbursements", getReimbursement);
		app.patch("/reimbursements/{reimburse_id}/status", changeReimbursement);
		app.post("/reimbursements", addReimbursement);
		app.get("/reimbursement/{reimburse_id}/image", getImageFromReimbursementById);
	}
} 