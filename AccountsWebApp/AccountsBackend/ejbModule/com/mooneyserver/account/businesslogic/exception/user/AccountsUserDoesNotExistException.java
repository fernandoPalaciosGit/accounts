package com.mooneyserver.account.businesslogic.exception.user;

public class AccountsUserDoesNotExistException extends AccountsUserException {

	private static final long serialVersionUID = 1L;
	
	public AccountsUserDoesNotExistException(String userName) {
		super("Password Reset Failed: The Requested Username ["
				+userName+"] does not exist");
	}

}
