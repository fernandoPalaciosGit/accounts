package com.mooneyserver.account.ui.view.main.accounts;

import java.util.Date;

import com.mooneyserver.account.AccountsApplication;
import com.mooneyserver.account.i18n.AccountsMessages;
import com.mooneyserver.account.persistence.entity.BalanceSheet;
import com.mooneyserver.account.ui.manager.EMainView;
import com.mooneyserver.account.ui.manager.IconManager;
import com.mooneyserver.account.ui.view.main.AbstractBaseSubView;
import com.mooneyserver.account.ui.view.subwindow.accounts.InsertNewBalanceSheetEntry;
import com.mooneyserver.account.ui.view.subwindow.accounts.PaymentTypeMgmt;

import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.TreeTable;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.themes.BaseTheme;

public class SingleBalanceSheet extends AbstractBaseSubView {
	
	private static final long serialVersionUID = 1L;
	private BalanceSheet sheet;
	private Button insertEntry, paymentTypeMgmt, closeBalSheet;
	private TreeTable sheetTreeTable;
	
	public SingleBalanceSheet(final BalanceSheet sheet) {
		this.sheet = sheet;
		
		HorizontalSplitPanel hsp = new HorizontalSplitPanel();
		hsp.setSplitPosition(12, Sizeable.UNITS_PERCENTAGE);
		hsp.setSizeFull();
		hsp.setLocked(true);
        addComponent(hsp);

        VerticalLayout vl = new VerticalLayout();
        vl.setSpacing(true);
        vl.setStyleName("side-panel");
        
        insertEntry = new Button();
        insertEntry.setStyleName(BaseTheme.BUTTON_LINK);
        insertEntry.setIcon(IconManager.getIcon(IconManager.ADD_NEW_BALANCE_SHEET));
        insertEntry.addListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				AccountsApplication.getInstance().
					getMainWindow().
						addWindow(new InsertNewBalanceSheetEntry(sheet));
			}
		});
        vl.addComponent(insertEntry);
        vl.setComponentAlignment(insertEntry, Alignment.MIDDLE_CENTER);
        
        
        paymentTypeMgmt = new Button();
        paymentTypeMgmt.setStyleName(BaseTheme.BUTTON_LINK);
        paymentTypeMgmt.setIcon(IconManager.getIcon(IconManager.ADD_NEW_BALANCE_SHEET));
        paymentTypeMgmt.addListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				AccountsApplication.getInstance().
					getMainWindow().
						addWindow(new PaymentTypeMgmt(sheet));
			}
		});
        vl.addComponent(paymentTypeMgmt);
        vl.setComponentAlignment(paymentTypeMgmt, Alignment.MIDDLE_CENTER);
        
        
        closeBalSheet = new Button();
        closeBalSheet.setStyleName(BaseTheme.BUTTON_LINK);
        closeBalSheet.setIcon(IconManager.getIcon(IconManager.ADD_NEW_BALANCE_SHEET));
        closeBalSheet.addListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				closeSubView();
			}
		});
        vl.addComponent(closeBalSheet);
        vl.setComponentAlignment(closeBalSheet, Alignment.MIDDLE_CENTER);
		
        sheetTreeTable = new TreeTable();
        sheetTreeTable.setWidth("100%");
        sheetTreeTable.setHeight("100%");
		
        sheetTreeTable.addContainerProperty("Category", String.class, "");
        sheetTreeTable.addContainerProperty("Debit", String.class, "");
        sheetTreeTable.addContainerProperty("Credit", String.class, "");
        sheetTreeTable.addContainerProperty("Date", Date.class, new Date());

		hsp.addComponent(vl);
	    hsp.addComponent(sheetTreeTable);

	    hsp.setSizeFull();
	    
	    addComponent(hsp);
	    setExpandRatio(hsp, 1);

		buildStringsFromLocale();
	}
	
	@Override // TODO: Localize
	public void buildStringsFromLocale() {
		STRINGS = AccountsApplication.getResourceBundle();
		
		insertEntry.setCaption("Insert Entry");
		paymentTypeMgmt.setCaption("Lodgement Category Management");
		closeBalSheet.setCaption("Finished");
	}

	@Override
	public String getDisplayName() {
		return STRINGS.getString(AccountsMessages
				.BAL_SHEET_SUB_MENU_NAME).replace("{0}", 
						sheet.getSheetName());
	}

	@Override
	public EMainView getParentType() {	return EMainView.BAL_SHEET; }
}