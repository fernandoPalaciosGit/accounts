package com.mooneyserver.account.businesslogic.exception.accounts;

import com.mooneyserver.account.persistence.entity.AccountsUser;

public class AccountsSheetInvalidOwnerException extends AccountsSheetException {

	private static final long serialVersionUID = 1L;
	
	private AccountsUser user;
	
	public AccountsSheetInvalidOwnerException(AccountsUser user) {
		super("The requested owner is not valid for this action");
		this.user = user;
	}
	
	public AccountsUser getUser() {
		return this.user;
	}
}
