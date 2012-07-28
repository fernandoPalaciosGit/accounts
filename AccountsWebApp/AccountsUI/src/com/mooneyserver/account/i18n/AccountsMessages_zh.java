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
		{PASSWORD_REQUIREMENTS, ""},
		{DUPLICATE_USERNAME, ""},
		{INVALID_PASSWORD, ""},
		
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
		{VALIDATE_CONFIRM_PASSWORD, ""},
		
		/* Messenger */
		{MSGR_TITLE_INFO, "信息"},
		{MSGR_TITLE_WARN, "警告"},
		{MSGR_TITLE_ERROR, "错误"}
	};
}