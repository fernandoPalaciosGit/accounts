package com.mooneyserver.account.ui.view;

import java.util.HashMap;
import java.util.Map;

import com.vaadin.terminal.ThemeResource;

public class IconManager {
	
	private static int id = 0;
	
	public static final int CHINA_FLAG_SMALL = getId();
	public static final int IRISH_FLAG_SMALL = getId();
	
	public static final int SETTINGS_LARGE = getId();
	public static final int SETTINGS_SMALL = getId();
	public static final int SETTINGS_CHANGE_LANG = getId();
	public static final int SETTINGS_CHANGE_LANG_LARGE = getId();
	public static final int SETTINGS_LOGOUT = getId();
	
	public static final int FORGOT_PASSWORD = getId();
	public static final int FORGOT_PASSWORD_LARGE = getId();
	public static final int CREATE_USER = getId();
	public static final int CREATE_USER_LARGE = getId();
	
	public static final int INFO = getId();
	
	public static final int NOTIFICATION_INFO = getId();
	public static final int NOTIFICATION_WARN = getId();
	public static final int NOTIFICATION_ERR = getId();
	
	public static final int FEATURE_MISSING = getId();
	
	public static final int DASHBOARD_ACCOUNTS = getId();
	public static final int DASHBOARD_REPORTS = getId();
	public static final int DASHBOARD_GRAPHS = getId();
	public static final int DASHBOARD_SETTINGS = getId();
	
	private static final Map<Integer, String> iconLocation;
	
	// Future proofing for possibly selectable icon sets
	private static final String 
		SELECTED_ICON_SET = "icons/default_icon_set/";
	
	static {
		iconLocation = new HashMap<>();
		
		iconLocation.put(CHINA_FLAG_SMALL, "small/china_flag.png");
		iconLocation.put(IRISH_FLAG_SMALL, "small/irish_flag.png");
		iconLocation.put(SETTINGS_LARGE, "large/header-toolbar-settings.png");
		iconLocation.put(SETTINGS_SMALL, "small/header-toolbar-settings-small.png");
		iconLocation.put(SETTINGS_CHANGE_LANG, "small/header-toolbar-change-locale.png");
		iconLocation.put(SETTINGS_LOGOUT, "small/header-toolbar-logout.png");
		iconLocation.put(FORGOT_PASSWORD, "small/forgot_password.png");
		iconLocation.put(CREATE_USER, "small/new_user.png");
		iconLocation.put(INFO, "small/info-icon.png");
		iconLocation.put(NOTIFICATION_INFO, "large/info-icon-32.png");
		iconLocation.put(NOTIFICATION_WARN, "large/warning-icon-32.png");
		iconLocation.put(NOTIFICATION_ERR, "large/error-icon-32.png");
		iconLocation.put(FEATURE_MISSING, "large/feature-not-present.gif");
		iconLocation.put(FORGOT_PASSWORD_LARGE, "large/forgot_password_large.png");
		iconLocation.put(CREATE_USER_LARGE, "large/user_add_large_.png");
		iconLocation.put(SETTINGS_CHANGE_LANG_LARGE, "large/change_locale_large.png");
		iconLocation.put(DASHBOARD_ACCOUNTS, "large/dashboard-accounts.png");
		iconLocation.put(DASHBOARD_REPORTS, "large/dashboard-reports.png");
		iconLocation.put(DASHBOARD_GRAPHS, "large/dashboard-graphs.png");
		iconLocation.put(DASHBOARD_SETTINGS, "large/dashboard-settings.png");
		
	}

	public static ThemeResource getIcon(int icon) {
		return new ThemeResource(SELECTED_ICON_SET + iconLocation.get(icon));
	}
	
	private static int getId() {
		return id++;
	}
}