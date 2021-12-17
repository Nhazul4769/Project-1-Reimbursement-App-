package com.revature.exception;

public class ReimbursementAlreadyResolvedException extends Exception {

	public ReimbursementAlreadyResolvedException() {
		super();
	}

	public ReimbursementAlreadyResolvedException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ReimbursementAlreadyResolvedException(String message, Throwable cause) {
		super(message, cause);
	}

	public ReimbursementAlreadyResolvedException(String message) {
		super(message);
	}

	public ReimbursementAlreadyResolvedException(Throwable cause) {
		super(cause);
	}
	
}