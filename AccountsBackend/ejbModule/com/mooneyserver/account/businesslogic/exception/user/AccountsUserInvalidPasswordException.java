package com.mooneyserver.account.businesslogic.exception.user;

import com.mooneyserver.account.businesslogic.validate.PasswordValidator;

public class AccountsUserInvalidPasswordException extends AccountsUserException {

	private static final long serialVersionUID = 1L;
	
	public AccountsUserInvalidPasswordException(String password) {
		super("Creation Failed: The Requested Password ["
				+password+"] is not valid. " 
				+ PasswordValidator.getPasswordRequirementMsg());
	}
}
