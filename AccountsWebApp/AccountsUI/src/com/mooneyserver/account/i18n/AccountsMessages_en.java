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
		{USER_NOT_ACTIVE, "Your User is not active"},
		{FAILED_LOGIN, "Incorrect Username or Password"},
		{USER_DOES_NOT_EXIST, "The requested user does not exist"},
			
		/* User Activation Screen */
		{ACTIVATE_USER_WIN_HEADER, "Click to activate your user"},
		{ACTIVATE_USER, "Click to activate your user"},
		{ACTIVATE_USER_FAILED, "There has been an issue with User Activation"},
			
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
		{MSGR_UNRECOVERABLE_ERROR, "An error has occured in the system."},
		
		/* Dashboard */
		{DASHBRD_WIN_HEADER, "Dashboard"},
		{DASHBRD_BAL_SHEET_TOOLTIP, "Balance Sheet Mgmt"},
		{DASHBRD_REP_TOOLTIP, "Balance Sheet Reporting"},
		{DASHBRD_GRAPH_TOOLTIP, "Graphical View of Accounts Status"},
		{DASHBRD_SETTINGS_TOOLTIP, "Global Settings Mgmt"},
		
		/* Balance Sheet */
		{BAL_SHEET_WIN_HEADER, "My Balance Sheet(s)"},
		{BAL_SHEET_CLOSE_SHEET, "Select My Balance Sheets To Close"},
		{BAL_SHEET_ADD_SHEET, "Create a New Balance Sheet"},
		{BAL_SHEET_TOOLBAR_ADD, "Add New Balance Sheet"},
		{BAL_SHEET_TOOLBAR_REMOVE, "Close A Balance Sheet"},
		{BAL_SHEET_COUNT, "You have [{0}] Balance Sheets!"},
		{BAL_SHEET_SUB_MENU_NAME, "Balance Sheet [{0}]"},
		{BAL_SHEET_ADD_SHEET_FAIL, "You do not have permission to create a balance sheet"},
		{BAL_SHEET_NO_CURRENT_SHEET, "You do not appear to have any Balance Sheet created yet."},
		{BAL_SHEET_CREATE_SHEET_QUESTION, "Create Bal Sheet?"},
		{BAL_SHEET_REMOVE_SELECT, "Please select which Balance Sheet to close"},
		{BAL_SHEET_REMOVE_REASON, "Closure Reason"},
		
		/* Admin Screen */
		{ADMIN_WIN_HEADER, "Administration"},
		
		/* Graphical Reports Screen */
		{GRAPHS_WIN_HEADER, "Graphs and Charts"},
		
		/* Reporting Screen */
		{REPORTS_WIN_HEADER, "Reporting"}
	};
}