package com.mooneyserver.account.ui.manager;

import com.mooneyserver.account.ui.iface.IMainView;
import com.mooneyserver.account.ui.view.main.accounts.BalanceSheetMain;
import com.mooneyserver.account.ui.view.main.admin.AdminMain;
import com.mooneyserver.account.ui.view.main.dashboard.Dashboard;
import com.mooneyserver.account.ui.view.main.graphing.GraphedReportsMain;
import com.mooneyserver.account.ui.view.main.reporting.ReportingMain;

/**
 * Enum representing all the main views of the application.
 * These are the views which have an associated menu item 
 * on the main menu bar.
 *
 * This is tightly coupled to the Navifation class. And
 * can return the class type of the dynamic view to be 
 * loaded for each menu selection
 */
public enum EMainView {
	DASHBOARD	(Dashboard.class),
	BAL_SHEET	(BalanceSheetMain.class),
	REPORTS		(ReportingMain.class),
	GRAPHS		(GraphedReportsMain.class),
	ADMIN		(AdminMain.class);
	
	private Class<? extends IMainView> clazz;
	
	private EMainView(Class<? extends IMainView> clazz) {
		this.clazz = clazz;
	}
	
	public Class<? extends IMainView> getClassType() {
		return this.clazz;
	}
}