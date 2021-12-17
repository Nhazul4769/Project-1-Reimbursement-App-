package com.revature.service;

import com.revature.exception.UnauthorizedException; 
import com.revature.model.User;

public class AuthorizationService {

	public void authroizeEmployeeAndFinanceManager(User user) throws UnauthorizedException {
		if (user == null || !(user.getUserRole().equals("Employee") || user.getUserRole().equals("Finance Manager"))) {
			throw new UnauthorizedException("You must be an employee or finance manager to access this resource");
		}
	}
	
	public void authorizeEmployee(User user) throws UnauthorizedException {
		if(user == null || !(user.getUserRole().equals("Employee"))) {
			throw new UnauthorizedException("You must be an employee to access this resource");
		}	
	}

	public void authorizeFinanceManager(User user) throws UnauthorizedException {
			if(user == null || !(user.getUserRole().equals("Finance Manager"))) {
				throw new UnauthorizedException("You must be a finance manager to access this resource");
			}	
		}
	}
