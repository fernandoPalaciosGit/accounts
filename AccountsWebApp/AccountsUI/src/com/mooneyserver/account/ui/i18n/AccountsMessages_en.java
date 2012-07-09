package com.mooneyserver.account.ui.i18n;

public class AccountsMessages_en extends AccountsMessages {

	private static final long serialVersionUID = 1L;

	@Override
	protected Object[][] getContents() {
		return externStrings;
	}
	
	static final Object[][] externStrings = {
		/* Main Window Strings*/
		{APP_TITLE, "Accounts"},
		
		/* Login Screen */
		{LOGIN_BUTTON, "Login"},
		{LOGIN_WELCOME, "Welcome to Accounts!"},
		{USERNAME, "Username"},
		{PASSWORD, "Password"},
		{FORGOT_PWD, "Forgot My Password"},
		
		/* Language Settings sub window */
		{SELECT_LANGUAGE, "Select your language"}
	};
}