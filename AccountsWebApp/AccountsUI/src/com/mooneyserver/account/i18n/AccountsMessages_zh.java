package com.mooneyserver.account.i18n;

public class AccountsMessages_zh extends AccountsMessages {

	private static final long serialVersionUID = 1L;

	@Override
	protected Object[][] getContents() {
		return externStrings;
	}
	
	static final Object[][] externStrings = {
		/* Main Window Strings*/
		{APP_TITLE, "家庭财务"},
		{TOOLBAR_LANG_BTN_TOOLTIP, "更改语言"},
		
		/* Login Screen */
		{LOGIN_BUTTON, "登录"},
		{LOGIN_WELCOME, "家庭财务软件欢迎您！"},
		{PASSWORD, "密码"},
		{FORGOT_PWD, "忘记密码"},
		{CREATE_NEW_USER, "创建新用户"},
		{EMAIL_ADDRESS, "电子邮件地址"},
		{SEND_NEW_PASSWORD, "寄出新密码"},
		{FIRST_NAME, "名字"},
		{LAST_NAME, "姓"},
		{CONFIRM_PASSWORD, "确认密码"},
		{PASSWORD_REQUIREMENTS, "您的密码必须具备以下所有的条件<br/><ul>"
			+ "<li>必须包含至少一位数字0-9</li>"
			+ "<li>必须包含至少一位小写英文字母</li>"
			+ "<li>必须包含至少一位大写英文字母</li>"
			+ "<li>必须包含至少一位特殊符号例如[!#$%&-?<>@]</li>"
			+ "<li>必须长度在8位-20位字符之间</li></ul>"},
		{DUPLICATE_USERNAME, "用户名已经存在，请输入一个不同的邮箱地址"},
		{INVALID_PASSWORD, "输入的密码不正确，请按照密码必备条件重新输入"},
		{USER_CREATED, "Your user has been created,"
				+ " please check your email for activation details"},
		{ACTIVATE_USER, "Click to activate your user"},
		
		/* Language Settings sub window */
		{SELECT_LANGUAGE, "选择您的语言"},
		{NEXT_LANGUAGE_UP, "上一个语言"},
		{NEXT_LANGUAGE_DOWN, "下一个语言"},
		

		/* About Dialog */
		{ABOUT_MAIN, "软件设计师:<br/>小胖"},
		{ABOUT_LINK, "@github"},
		
		/* Header Toolbar */
		{HEADER_SETTINGS, "设置"},
		{HEADER_SETTINGS_LANG, "更改语言"},
		{HEADER_SETTINGS_SIGNOUT, "注销"},
		
		/* Validation Localization */
		{VALIDATE_EMAIL, "{0} 不是一个有效的电子邮件地址"},
		{VALIDATE_CONFIRM_PASSWORD, "输入的两个密码不吻合"},
		
		/* Messenger */
		{MSGR_TITLE_INFO, "信息"},
		{MSGR_TITLE_WARN, "警告"},
		{MSGR_TITLE_ERROR, "错误"},
		{MSGR_APOLOGIES, "Apologies"},
		{MSGR_BODY_NO_FEATURE, "This feature hasn't been implemented yet"},
		
		/* Dashboard */
		{DASHBRD_BAL_SHEET_TOOLTIP, "Balance Sheet Mgmt"},
		{DASHBRD_REP_TOOLTIP, "Balance Sheet Reporting"},
		{DASHBRD_GRAPH_TOOLTIP, "Graphical View of Accounts Status"},
		{DASHBRD_SETTINGS_TOOLTIP, "Global Settings Mgmt"}
	};
}