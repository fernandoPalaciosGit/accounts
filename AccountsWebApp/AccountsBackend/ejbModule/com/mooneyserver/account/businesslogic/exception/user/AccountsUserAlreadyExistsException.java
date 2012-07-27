package com.mooneyserver.account.businesslogic.exception.user;

public class AccountsUserAlreadyExistsException extends AccountsUserException {

	private static final long serialVersionUID = 1L;
	
	public AccountsUserAlreadyExistsException(String userName) {
		super("Creation Failed: The Requested Username ["
				+userName+"] already exists");
	}

}
