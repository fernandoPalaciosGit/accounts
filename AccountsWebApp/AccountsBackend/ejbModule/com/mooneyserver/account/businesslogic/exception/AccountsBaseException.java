package com.mooneyserver.account.businesslogic.exception;

import javax.ejb.ApplicationException;

@ApplicationException(rollback = true)
public class AccountsBaseException extends Exception {

	private static final long serialVersionUID = -4846794145521544339L;
	
	public AccountsBaseException() {
		super();
	}
	
	public AccountsBaseException(String msg) {
		super(msg);
	}
	
	public AccountsBaseException(Throwable cause) {
		super(cause);
	}
	
	public AccountsBaseException(String msg, Throwable cause) {
		super(msg, cause);
	}
}