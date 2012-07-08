package com.mooneyserver.account.ui.i18n;

public class AccountsMessages_en extends AccountsMessages {

	@Override
	protected Object[][] getContents() {
		return externStrings;
	}
	
	static final Object[][] externStrings = {
		/* Main Window Strings*/
		{APP_TITLE, "Accounts"},
		
		/* Login Screen */
		{LOGIN_BUTTON, "Login"},
		{USERNAME, "Username"},
		{PASSWORD, "Password"}
	};
}