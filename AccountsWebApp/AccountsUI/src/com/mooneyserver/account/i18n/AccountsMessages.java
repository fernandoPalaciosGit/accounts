package com.mooneyserver.account.i18n;

import java.io.Serializable;
import java.util.ListResourceBundle;

public class AccountsMessages extends ListResourceBundle implements Serializable {
	
	private static final long serialVersionUID = 1L;


	/*
	 * Generic Localized Text
	 */
	public static final String ADD_NEW = "Accounts.Generic.AddNew";
	public static final String FIN = "Accounts.Generic.Finished";
	public static final String NAME = "Accounts.Generic.Name";
	public static final String INSERT = "Accounts.Generic.Insert";
	public static final String TODAY = "Accounts.Generic.Today";
	public static final String DESCRIPTION = "Accounts.Generic.Description";
	
	/*
	 * Main Windows Strings
	 */
	public static final String APP_TITLE = "Accounts.MainWindow.AppTitle";
	public static final String TOOLBAR_LANG_BTN_TOOLTIP = "Accounts.MainWindow.LangChange.Tooltip";
	
	
	/*
	 * Login Window String
	 */
	public static final String LOGIN_BUTTON = "Accounts.LoginView.LoginBtn";
	public static final String LOGIN_WELCOME = "Accounts.LoginView.Welcome";
	public static final String PASSWORD = "Accounts.LoginView.Password";
	public static final String FORGOT_PWD = "Accounts.LoginView.ForgotPassword";
	public static final String CREATE_NEW_USER = "Accounts.LoginView.CreateUser";
	public static final String EMAIL_ADDRESS = "Accounts.LoginView.ForgotPwd.Email";
	public static final String SEND_NEW_PASSWORD = "Accounts.LoginView.ForgotPwd.SendBtn";
	public static final String FIRST_NAME = "Accounts.LoginView.CreateUser.FirstName";
	public static final String LAST_NAME = "Accounts.LoginView.CreateUser.LastName";
	public static final String CONFIRM_PASSWORD = "Accounts.LoginView.CreateUser.ConfirmPassword";
	public static final String PASSWORD_REQUIREMENTS = "Accounts.LoginView.CreateUser.PasswordRequirements";
	public static final String DUPLICATE_USERNAME = "Accounts.LoginView.CreateUser.Error.DuplicateUser";
	public static final String INVALID_PASSWORD = "Accounts.LoginView.CreateUser.Error.InvalidPassword";
	public static final String USER_CREATED = "Accounts.LoginView.CreateUser.Success.UserCreated";
	public static final String USER_NOT_ACTIVE = "Accounts.LoginView.Error.UserNotActive";
	public static final String FAILED_LOGIN = "Accounts.LoginView.Error.InvalidUserLoginAttempt";
	public static final String USER_DOES_NOT_EXIST = "Accounts.LoginView.Error.NoUserExists";
	
	/*
	 * User Activation Screen
	 */
	public static final String ACTIVATE_USER_WIN_HEADER = "Accounts.LoginView.ActivateUser.WindowTitle";
	public static final String ACTIVATE_USER = "Accounts.LoginView.ActivateUser.ClickToActivate";
	public static final String ACTIVATE_USER_FAILED = "Accounts.LoginView.ActivateUser.UserActivationFailed";
	
	/*
	 * Language Settings sub window
	 */
	public static final String SELECT_LANGUAGE = "Accounts.Settings.Language.ChooseLanguage";
	public static final String NEXT_LANGUAGE_UP = "Accounts.Settings.Language.NextUp";
	public static final String NEXT_LANGUAGE_DOWN = "Accounts.Settings.Language.NextDown";
	
	/*
	 * About Dialog Strings
	 */
	public static final String ABOUT_MAIN_HEADER = "Accounts.About.Header";
	public static final String ABOUT_MAIN = "Accounts.About.MainText";
	public static final String ABOUT_LINK = "Accounts.About.LinkCaption";
	
	/*
	 * Header Toolbar
	 */
	public static final String HEADER_SETTINGS = "Accounts.Header.Settings";
	public static final String HEADER_SETTINGS_LANG = "Accounts.Header.Settings.ChangeLanguage";
	public static final String HEADER_SETTINGS_SIGNOUT = "Accounts.Header.Settings.SignOut";
	
	/*
	 * Validation Localization
	 */
	public static final String VALIDATE_EMAIL = "Accounts.Validate.Email";
	public static final String VALIDATE_CONFIRM_PASSWORD = "Accounts.Validate.ConfirmPassword";
	
	/*
	 * Messenger
	 */
	public static final String MSGR_TITLE_INFO = "Accounts.Messenger.Title.Info";
	public static final String MSGR_TITLE_WARN = "Accounts.Messenger.Title.Warning";
	public static final String MSGR_TITLE_ERROR = "Accounts.Messenger.Title.Error";
	public static final String MSGR_APOLOGIES = "Accounts.Messenger.Heading.Apologies";
	public static final String MSGR_BODY_NO_FEATURE = "Accounts.Messenger.Body.Feature_Not_Implemented";
	public static final String MSGR_UNRECOVERABLE_ERROR = "Accounts.Messenger.Body.Unrecoverable_Error";
	
	/*
	 * Dashboard
	 */
	public static final String DASHBRD_WIN_HEADER = "Accounts.Dashboard.WindowHeading";
	public static final String DASHBRD_BAL_SHEET_TOOLTIP = "Accounts.Dashboard.Tooltip.BalanceSheet";
	public static final String DASHBRD_REP_TOOLTIP = "Accounts.Dashboard.Tooltip.Reports";
	public static final String DASHBRD_GRAPH_TOOLTIP = "Accounts.Dashboard.Tooltip.Graphs";
	public static final String DASHBRD_SETTINGS_TOOLTIP = "Accounts.Dashboard.Tooltip.Settings";
	
	/*
	 * Balance Sheet
	 */
	public static final String BAL_SHEET_WIN_HEADER = "Accounts.BalanceSheet.WindowHeading";
	public static final String BAL_SHEET_TOOLBAR_ADD = "Accounts.BalanceSheet.SideBar.AddBalSheet";
	public static final String BAL_SHEET_TOOLBAR_REMOVE = "Accounts.BalanceSheet.SideBar.RemoveBalSheet";
	public static final String BAL_SHEET_COUNT = "Accounts.BalanceSheet.Info.BalSheetCount";
	public static final String BAL_SHEET_CLOSE_SHEET = "Accounts.BalanceSheet.SubWindow.CloseSheet.Heading";
	public static final String BAL_SHEET_ADD_SHEET = "Accounts.BalanceSheet.SubWindow.AddSheet.Heading";
	public static final String BAL_SHEET_NO_CURRENT_SHEET = "Accounts.BalanceSheet.SubWindow.RemoveSheet.NoSheetFound";
	public static final String BAL_SHEET_CREATE_SHEET_QUESTION = "Accounts.BalanceSheet.SubWindow.RemoveSheet.AddSheet";
	public static final String BAL_SHEET_SUB_MENU_NAME = "Accounts.BalanceSheet.SingleSheet.Title";
	public static final String BAL_SHEET_ADD_SHEET_FAIL = "Accounts.BalanceSheet.SingleSheet.Error.AddSheet";
	public static final String BAL_SHEET_REMOVE_SELECT = "Accounts.BalanceSheet.SubWindow.RemoveSheet.Select";
	public static final String BAL_SHEET_REMOVE_REASON = "Accounts.BalanceSheet.SubWindow.RemoveSheet.Reason";
	public static final String BAL_SHEET_INSERT_ENTRY_DT = "Accounts.SingleBalanceSheet.SubWindow.InsertEntry.Heading.Debit";
	public static final String BAL_SHEET_INSERT_ENTRY_CT = "Accounts.SingleBalanceSheet.SubWindow.InsertEntry.Heading.Credit";
	public static final String BAL_SHEET_PAYMENT_CATEGORY = "Accounts.SingleBalanceSheet.SubWindow.InsertEntry.Category";
	public static final String BAL_SHEET_PAYMENT_TYPE = "Accounts.SingleBalanceSheet.SubWindow.InsertEntry.Type";
	public static final String BAL_SHEET_PAYMENT_VALUE = "Accounts.SingleBalanceSheet.SubWindow.InsertEntry.Value";
	public static final String BAL_SHEET_PAYMENT_PERIOD = "Accounts.SingleBalanceSheet.SubWindow.InsertEntry.Period";
	public static final String BAL_SHEET_PAYMENT_YEAR = "Accounts.SingleBalanceSheet.SubWindow.InsertEntry.Year";
	public static final String BAL_SHEET_PAYMENT_MONTH = "Accounts.SingleBalanceSheet.SubWindow.InsertEntry.Month";
	public static final String BAL_SHEET_PAYMENT_DATE = "Accounts.SingleBalanceSheet.SubWindow.InsertEntry.Date";
	public static final String BAL_SHEET_NEW_PAY_TYPE = "Accounts.SingleBalanceSheet.SubWindow.PaymentAndCategoryMgmt.Heading";
	public static final String BAL_SHEET_DEBIT = "Accounts.BalanceSheet.Debit";
	public static final String BAL_SHEET_CREDIT = "Accounts.BalanceSheet.Credit";
	
	
	/*
	 * Admin Screen
	 */
	public static final String ADMIN_WIN_HEADER = "Accounts.Administration.WindowHeading";
	
	/*
	 * Graphical Reports Screen
	 */
	public static final String GRAPHS_WIN_HEADER = "Accounts.GraphedReports.WindowHeading";
	
	/*
	 * Reporting Screen
	 */
	public static final String REPORTS_WIN_HEADER = "Accounts.Report.WindowHeading";
	
	@Override
	protected Object[][] getContents() {
		return null;
	}
}