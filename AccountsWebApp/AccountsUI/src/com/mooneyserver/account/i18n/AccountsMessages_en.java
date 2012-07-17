package com.mooneyserver.account.i18n;

public class AccountsMessages_en extends AccountsMessages {

	private static final long serialVersionUID = 1L;

	@Override
	protected Object[][] getContents() {
		return externStrings;
	}
	
	static final Object[][] externStrings = {
		/* Main Window Strings*/
		{APP_TITLE, "Accounts"},
		{TOOLBAR_LANG_BTN_TOOLTIP, "Change Language"},
		
		/* Login Screen */
		{LOGIN_BUTTON, "Login"},
		{LOGIN_WELCOME, "Welcome to Accounts!"},
		{PASSWORD, "Password"},
		{FORGOT_PWD, "Forgot My Password"},
		{CREATE_NEW_USER, "Create New User"},
		{EMAIL_ADDRESS, "E-mail Address"},
		{SEND_NEW_PASSWORD, "Send New Password"},
		{FIRST_NAME, "First Name"},
		{LAST_NAME, "Last Name"},
		{CONFIRM_PASSWORD, "Confirm Password"},
		
		/* Language Settings sub window */
		{SELECT_LANGUAGE, "Select your language"},
		{NEXT_LANGUAGE_UP, "Next Language (Up)"},
		{NEXT_LANGUAGE_DOWN, "Next Language (Down)"},
		
		/* About Dialog */
		{ABOUT_MAIN, "Author:<br/>Brian Mooney"},
		{ABOUT_LINK, "@github"},
		
		/* Header Toolbar */
		{HEADER_SETTINGS, "Settings"},
		{HEADER_SETTINGS_LANG, "Language / Locale"},
		{HEADER_SETTINGS_SIGNOUT, "Sign Out"},
		
		/* Validation Localization */
		{VALIDATE_EMAIL, "{0} is not a valid e-mail address"}
		
	};
}