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
		{PASSWORD_REQUIREMENTS, "Your password must follow the below guidelines<br/><ul>"
			+ "<li>Must contain at least one number from 0-9</li>"
			+ "<li>Must contain one lower case letter</li>"
			+ "<li>Must contain one upper case letter</li>"
			+ "<li>Must contain one special character from [!#$%&-?<>@]</li>"
			+ "<li>Must be between 8 and 20 characters in length</li></ul>"},
		{DUPLICATE_USERNAME, "The requested username is already in use,"
			+ " please provide a different email address"},
		{INVALID_PASSWORD, "The entered password is not valid," 
			+ " please check the password info dialog"},
		{USER_CREATED, "Your user has been created,"
			+ " please check your email for activation details"},
			
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
		{VALIDATE_EMAIL, "{0} is not a valid e-mail address"},
		{VALIDATE_CONFIRM_PASSWORD, "The two passwords do not match"},
		
		/* Messenger */
		{MSGR_TITLE_INFO, "Information"},
		{MSGR_TITLE_WARN, "Warning"},
		{MSGR_TITLE_ERROR, "Error"},
		{MSGR_APOLOGIES, "Apologies"},
		{MSGR_BODY_NO_FEATURE, "This feature hasn't been implemented yet"},
		
		/* Dashboard */
		{DASHBRD_BAL_SHEET_TOOLTIP, "Balance Sheet Mgmt"},
		{DASHBRD_REP_TOOLTIP, "Balance Sheet Reporting"},
		{DASHBRD_GRAPH_TOOLTIP, "Graphical View of Accounts Status"},
		{DASHBRD_SETTINGS_TOOLTIP, "Global Settings Mgmt"}
		
	};
}