package com.mooneyserver.account.ui.view.main.accounts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;

import com.mooneyserver.account.AccountsApplication;
import com.mooneyserver.account.businesslogic.accounts.IBalanceSheetMgmtService;
import com.mooneyserver.account.businesslogic.exception.accounts.AccountsSheetException;
import com.mooneyserver.account.i18n.AccountsMessages;
import com.mooneyserver.account.lookup.BusinessProcess;
import com.mooneyserver.account.persistence.entity.AccountsUser;
import com.mooneyserver.account.persistence.entity.BalanceSheet;
import com.mooneyserver.account.ui.iface.IMainView;
import com.mooneyserver.account.ui.manager.IconManager;
import com.mooneyserver.account.ui.view.main.AbstractBaseView;
import com.mooneyserver.account.ui.view.subwindow.accounts.CloseBalanceSheet;
import com.mooneyserver.account.ui.view.subwindow.accounts.CreateNewBalanceSheet;

import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.themes.BaseTheme;

public class BalanceSheetMain extends AbstractBaseView implements IMainView {
	
	private static final long serialVersionUID = 1L;
	
	@BusinessProcess
	IBalanceSheetMgmtService accSvc;
	
	private Button addNewBalanceSheet, closeBalanceSheet;
	private Panel mainContent;
	private Label myBalSheetDetails;
	
	private final int NUM_COLS = 4;
	
	public BalanceSheetMain() {
		HorizontalSplitPanel hsp = new HorizontalSplitPanel();
		hsp.setSplitPosition(12, Sizeable.UNITS_PERCENTAGE);
		hsp.setSizeFull();
		hsp.setLocked(true);
        addComponent(hsp);

        VerticalLayout vl = new VerticalLayout();
        vl.setSpacing(true);
        vl.setStyleName("side-panel");
        
        addNewBalanceSheet = new Button();
        addNewBalanceSheet.setStyleName(BaseTheme.BUTTON_LINK);
        addNewBalanceSheet.setIcon(IconManager.getIcon(IconManager.ADD_NEW_BALANCE_SHEET));
        addNewBalanceSheet.addListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				AccountsApplication.getInstance().
					getMainWindow().
						addWindow(new CreateNewBalanceSheet());
			}
		});
        vl.addComponent(addNewBalanceSheet);
        vl.setComponentAlignment(addNewBalanceSheet, Alignment.MIDDLE_CENTER);

        closeBalanceSheet = new Button();
        closeBalanceSheet.setStyleName(BaseTheme.BUTTON_LINK);
        closeBalanceSheet.setIcon(IconManager.getIcon(IconManager.CLOSE_BALANCE_SHEET));
        closeBalanceSheet.addListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				AccountsApplication.getInstance().
					getMainWindow().
						addWindow(new CloseBalanceSheet());
			}
		});
        vl.addComponent(closeBalanceSheet);
        vl.setComponentAlignment(closeBalanceSheet, Alignment.MIDDLE_CENTER);
        
        hsp.addComponent(vl);
        hsp.addComponent(generateMyBalanceSheetContent());

        hsp.setSizeFull();
        addComponent(hsp);
        setExpandRatio(hsp, 1);

		buildStringsFromLocale();
	}
	
	@Override
	public void buildStringsFromLocale() {
		STRINGS = AccountsApplication.getResourceBundle();
		
		addNewBalanceSheet.setCaption(STRINGS.getString(
				AccountsMessages.BAL_SHEET_TOOLBAR_ADD));
        addNewBalanceSheet.setDescription(STRINGS.getString(
        		AccountsMessages.BAL_SHEET_TOOLBAR_ADD));
        
        closeBalanceSheet.setCaption(STRINGS.getString(
        		AccountsMessages.BAL_SHEET_TOOLBAR_REMOVE));
        closeBalanceSheet.setDescription(STRINGS.getString(
        		AccountsMessages.BAL_SHEET_TOOLBAR_REMOVE));
        
        mainContent.setCaption(STRINGS.getString(
        		AccountsMessages.BAL_SHEET_WIN_HEADER));
        
        int numBalSheets = 0;
        if (myBalSheetDetails.getData() != null)
        	numBalSheets = ((Integer) myBalSheetDetails.getData()).intValue();
        
        myBalSheetDetails.setCaption(STRINGS.getString(
        		AccountsMessages.BAL_SHEET_COUNT)
        		.replace("{0}", String.valueOf(numBalSheets)));
	}
	
	private Panel generateMyBalanceSheetContent() {
		mainContent = new Panel();
        mainContent.setSizeFull();
        
		VerticalLayout mainPanelVl = new VerticalLayout();
        
        List<BalanceSheet> myBalSheets = null;
        try {
        	myBalSheets = accSvc.getMyBalanceSheets((AccountsUser) 
        			AccountsApplication.getInstance().getUser());
        	Collections.sort(myBalSheets, new Comparator<BalanceSheet>() {
				@Override
				public int compare(BalanceSheet o1, BalanceSheet o2) {
					if (o1.isActive() == o2.isActive())
						return 0;
					else if (o1.isActive() && !o2.isActive())
						return -1;
					else
						return 1;
				}
        	});
		} catch (AccountsSheetException e) {
			log.log(Level.SEVERE, "Error trying to query number of balance sheets", e);
			mainContent.addComponent(new Label(STRINGS.getString
					(AccountsMessages.MSGR_UNRECOVERABLE_ERROR)));
			
			return mainContent;
		}
        
        myBalSheetDetails = new Label();
        myBalSheetDetails.setData(myBalSheets.size());
        
        mainPanelVl.addComponent(myBalSheetDetails);
        mainPanelVl.addComponent(displayMyBalSheetsInRows(myBalSheets));
        
        mainContent.addComponent(mainPanelVl);
        
        return mainContent;
	}
	
	private VerticalLayout displayMyBalSheetsInRows(List<BalanceSheet> myBalSheets) {		
		VerticalLayout vl = new VerticalLayout();
		vl.setSizeFull();
		vl.setSpacing(true);
		vl.setStyleName("bal-sheet-panel");
		
		// Display placeholder if no current balance sheets
		if (myBalSheets.size() == 0) {
			Button balSheet = new Button();
	        balSheet.setStyleName(BaseTheme.BUTTON_LINK);
	        balSheet.setIcon(IconManager.getIcon(IconManager.BALANCE_SHEET));
	        balSheet.setEnabled(false);
	        
	        vl.addComponent(balSheet);
	        
	        return vl;
		}
		
		// Build the viewable list of my balance sheets
		List<HorizontalLayout> balSheetRows = new ArrayList<>();
		for (int i = 0; i < myBalSheets.size(); i++) {
			HorizontalLayout row;
			if (i % NUM_COLS == 0) {
				row = new HorizontalLayout();
				balSheetRows.add(row);
			} else {
				row = balSheetRows.get(balSheetRows.size() - 1);
			}
			
			final BalanceSheet balSheet = myBalSheets.get(i);
			Button balSheetBtn = new Button(balSheet.getSheetName());
			balSheetBtn.setDescription(balSheet.getDescription());
			balSheetBtn.setStyleName(BaseTheme.BUTTON_LINK);
			balSheetBtn.setIcon(IconManager.getIcon(IconManager.BALANCE_SHEET));
			if (!balSheet.isActive()) {
				balSheetBtn.setEnabled(false);
			} else {
				balSheetBtn.addListener(new Button.ClickListener() {

					private static final long serialVersionUID = 1L;

					@Override
					public void buttonClick(ClickEvent event) {
						AccountsApplication.getInstance().nav
							.loadNewView(new SingleBalanceSheet(balSheet));
					}
				});
			}
	        
			row.addComponent(balSheetBtn);
		}
		
		for (HorizontalLayout hl : balSheetRows) {
			vl.addComponent(hl);
		}
		
		return vl;
	}

	@Override
	public String getDisplayName() {return STRINGS.getString(AccountsMessages.BAL_SHEET_WIN_HEADER);}
}