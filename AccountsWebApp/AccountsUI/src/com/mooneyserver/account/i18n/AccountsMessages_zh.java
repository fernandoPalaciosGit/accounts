package com.mooneyserver.account.i18n;

public class AccountsMessages_zh extends AccountsMessages {

	private static final long serialVersionUID = 1L;

	@Override
	protected Object[][] getContents() {
		return externStrings;
	}
	
	static final Object[][] externStrings = {
		/* Generic Localized Text */
		{ADD_NEW, "添加新"},
		{FIN, "毕"},
		{NAME, "名称"},
		{INSERT, "插入"},
		{TODAY, "今天"},
		{DESCRIPTION, "描述"},
		{CREATE, "Create"},
		{CURRENT, "Current"},
		
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
		{USER_CREATED, "您的用户已创建！"
				+ " 激活详细信息，请检查您的电子邮件"},
		{USER_NOT_ACTIVE, "用户还没有激活"},
		{FAILED_LOGIN, "错误的用户名或密码错误"},
		{USER_DOES_NOT_EXIST, "该用户不存在"},

		/* User Activation Screen */
		{ACTIVATE_USER_WIN_HEADER, "点击激活您的用户"},
		{ACTIVATE_USER, "点击激活您的用户"},
		{ACTIVATE_USER_FAILED, "这里有一个问题与激活用户"},
		
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
		{VALIDATE_DUPLICATE_CATG_NAME, "This Category Name Already Exists"},
		{VALIDATE_DUPLICATE_TYPE_NAME, "This Type Name Already Exists"},
		{VALIDATE_NOT_AN_AMMOUNT, "This is Not a valid money amount"},
		
		/* Messenger */
		{MSGR_TITLE_INFO, "信息"},
		{MSGR_TITLE_WARN, "警告"},
		{MSGR_TITLE_ERROR, "错误"},
		{MSGR_APOLOGIES, "对不起"},
		{MSGR_BODY_NO_FEATURE, "尚未实现该功能已"},
		{MSGR_UNRECOVERABLE_ERROR, "发生了系统错误"},
		
		/* Dashboard */
		{DASHBRD_WIN_HEADER, "仪表盘"},
		{DASHBRD_BAL_SHEET_TOOLTIP, "资产负债表管理"},
		{DASHBRD_REP_TOOLTIP, "调查报告"},
		{DASHBRD_GRAPH_TOOLTIP, "图"},
		{DASHBRD_SETTINGS_TOOLTIP, "设置管理"},
		
		/* Balance Sheet */
		{BAL_SHEET_WIN_HEADER, "我的资产负债表"},
		{BAL_SHEET_CLOSE_SHEET, "选择资产负债表，关闭"},
		{BAL_SHEET_ADD_SHEET, "创建一个新的资产负债表"},
		{BAL_SHEET_TOOLBAR_ADD, "添加新的资产负债表"},
		{BAL_SHEET_TOOLBAR_REMOVE, "关闭的资产负债表"},
		{BAL_SHEET_COUNT, "你有 [{0}] 资产负债表!"},
		{BAL_SHEET_SUB_MENU_NAME, "资产负债表 [{0}]"},
		{BAL_SHEET_ADD_SHEET_FAIL, "你没有权限创建一个资产负债表"},
		{BAL_SHEET_NO_CURRENT_SHEET, "你有没有资产负债表已创建"},
		{BAL_SHEET_CREATE_SHEET_QUESTION, "创建一个资产负债表吗？"},
		{BAL_SHEET_REMOVE_SELECT, "请选择资产负债表关闭"},
		{BAL_SHEET_REMOVE_REASON, "对于关闭的原因"},
		{BAL_SHEET_INSERT_ENTRY_DT, "输入一个新的条目 (借方)"},
		{BAL_SHEET_INSERT_ENTRY_CT, "输入一个新的条目 (帐)"},
		{BAL_SHEET_PAYMENT_CATEGORY, "类别"},
		{BAL_SHEET_DEBIT, "借方"},
		{BAL_SHEET_CREDIT, "帐"},
		{BAL_SHEET_PAYMENT_TYPE, "类型"},
		{BAL_SHEET_PAYMENT_VALUE, "量"},
		{BAL_SHEET_PAYMENT_PERIOD, "学时"},
		{BAL_SHEET_PAYMENT_YEAR, "年"},
		{BAL_SHEET_PAYMENT_MONTH, "月"},
		{BAL_SHEET_PAYMENT_DATE, "日期"},
		{BAL_SHEET_NEW_PAY_TYPE, "类别管理"},
		
		/* Admin Screen */
		{ADMIN_WIN_HEADER, "管理"},
		
		/* Graphical Reports Screen */
		{GRAPHS_WIN_HEADER, "图形和图表"},
		
		/* Reporting Screen */
		{REPORTS_WIN_HEADER, "报告"}
	};
}