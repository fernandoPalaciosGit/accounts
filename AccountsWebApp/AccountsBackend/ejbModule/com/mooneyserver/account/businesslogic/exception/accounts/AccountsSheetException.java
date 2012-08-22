package com.mooneyserver.account.businesslogic.exception.accounts;

import com.mooneyserver.account.businesslogic.exception.AccountsBaseException;

public class AccountsSheetException extends AccountsBaseException {

	private static final long serialVersionUID = 1L;
	
	public AccountsSheetException() {
		super();
	}
	
	public AccountsSheetException(String msg) {
		super(msg);
	}
	
	public AccountsSheetException(Throwable cause) {
		super(cause);
	}
	
	public AccountsSheetException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
