package com.mooneyserver.account.businesslogic.exception.user;

public class AccountsUserNotActiveException extends AccountsUserException {

	private static final long serialVersionUID = 1L;
	
	public AccountsUserNotActiveException(String userName) {
		super("User ["+userName+"] is not active. Rejecting login request");
	}

}
