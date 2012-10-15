package com.mooneyserver.account.ui.view.main.admin;

import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import com.mooneyserver.account.AccountsApplication;
import com.mooneyserver.account.ui.iface.INonClosableAccountsSubView;
import com.mooneyserver.account.ui.manager.EMainView;
import com.mooneyserver.account.ui.manager.IconManager;
import com.mooneyserver.account.ui.widget.EventLogTable;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.BaseTheme;

class UserSessionTab extends VerticalLayout implements INonClosableAccountsSubView {

	private static final long serialVersionUID = 1L;
	
	private PopupDateField tableFromDate, tableToDate;
	private Button refreshTable, downloadAsExcel, downloadAsPDF;
	private EventLogTable logTable;
	
	protected ResourceBundle STRINGS = 
			AccountsApplication.getResourceBundle();
	
	public UserSessionTab() {
		setSpacing(true);
		
		// Add Event Log Table
		logTable = new EventLogTable();
		addComponent(logTable);
		
		// Add Table Refresh Controls
		HorizontalLayout hl = new HorizontalLayout();
		hl.setSpacing(true);
		
		tableFromDate = new PopupDateField();
		tableFromDate.setResolution(PopupDateField.RESOLUTION_DAY);
		tableToDate = new PopupDateField();
		tableToDate.setResolution(PopupDateField.RESOLUTION_DAY);
		
		Calendar cal = Calendar.getInstance();
		tableToDate.setValue(cal.getTime());
		cal.add(Calendar.DAY_OF_YEAR, -10);
		tableFromDate.setValue(cal.getTime());
		
		refreshTable = new Button();
		
		refreshTable.setStyleName(BaseTheme.BUTTON_LINK);
		refreshTable.setIcon(IconManager.getIcon(IconManager.REFRESH));
		
		hl.addComponent(tableFromDate);
		hl.addComponent(tableToDate);
		hl.addComponent(refreshTable);
		hl.setComponentAlignment(refreshTable, Alignment.BOTTOM_CENTER);
		
		hl.setMargin(false, false, false, true);
		addComponent(hl);
		
		// Add Table Export Options
		HorizontalLayout hl2 = new HorizontalLayout();
		hl2.setSpacing(true);
		
		downloadAsExcel = new Button();
		downloadAsExcel.setStyleName(BaseTheme.BUTTON_LINK);
		downloadAsExcel.setIcon(IconManager.getIcon(IconManager.CSV));
		
		downloadAsPDF = new Button();
		downloadAsPDF.setStyleName(BaseTheme.BUTTON_LINK);
		downloadAsPDF.setIcon(IconManager.getIcon(IconManager.PDF));
		
		hl2.addComponent(downloadAsExcel);
		hl2.setComponentAlignment(downloadAsExcel, Alignment.MIDDLE_RIGHT);
		hl2.addComponent(downloadAsPDF);
		hl2.setComponentAlignment(downloadAsPDF, Alignment.MIDDLE_RIGHT);
		
		hl2.setMargin(true, false, false, true);
		addComponent(hl2);
		
		
		logTable.refreshTableData((Date) tableFromDate.getValue(), 
				(Date) tableToDate.getValue());
		
		buildStringsFromLocale();
	}

	@Override
	public String getDisplayName() {
		return null;
	}

	@Override
	public void refreshView() {
		buildStringsFromLocale();
	}

	@Override
	public void buildStringsFromLocale() {
		STRINGS = AccountsApplication.getResourceBundle();
		
		tableFromDate.setCaption("From");
		tableToDate.setCaption("To");
		refreshTable.setCaption("Refresh");
		downloadAsExcel.setCaption("Download (as CSV)");
		downloadAsPDF.setCaption("Download (as PDF)");
	}

	@Override
	public void loadBackendServices() {}

	@Override
	public EMainView getParentType() {
		// TODO Auto-generated method stub
		return null;
	}
}
