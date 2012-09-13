package com.mooneyserver.account.ui.view.subwindow.accounts;

import java.util.List;
import java.util.ResourceBundle;

import com.mooneyserver.account.AccountsApplication;
import com.mooneyserver.account.businesslogic.accounts.IBalanceSheetMgmt;
import com.mooneyserver.account.businesslogic.exception.accounts.AccountsSheetException;
import com.mooneyserver.account.i18n.AccountsMessages;
import com.mooneyserver.account.lookup.BusinessProcess;
import com.mooneyserver.account.persistence.entity.AccountsUser;
import com.mooneyserver.account.persistence.entity.BalanceSheet;
import com.mooneyserver.account.ui.manager.IconManager;
import com.mooneyserver.account.ui.notification.Messenger;
import com.mooneyserver.account.ui.notification.Messenger.MessageSeverity;
import com.mooneyserver.account.ui.view.subwindow.BaseSubwindow;

import com.vaadin.ui.AbstractSelect.Filtering;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.BaseTheme;

public class CloseBalanceSheet extends BaseSubwindow {

	private static final long serialVersionUID = 1L;
	
	@BusinessProcess
	private IBalanceSheetMgmt accSvc;
	
	private ComboBox balSheetSelect;
	private TextArea closureReason;
	ResourceBundle STRINGS = AccountsApplication.getResourceBundle();
	
	public CloseBalanceSheet() {
		super(AccountsMessages.BAL_SHEET_CLOSE_SHEET);
		
		setWidth("380px");
		setIcon(IconManager.getIcon(IconManager.CLOSE_BALANCE_SHEET_SMALL));
		
		List<BalanceSheet> myBalSheets = null;
		try {
			myBalSheets = accSvc.getMyBalanceSheets((AccountsUser) 
					AccountsApplication.getInstance().getUser());
		} catch (AccountsSheetException e) {
			Messenger.genericMessage(MessageSeverity.ERROR, 
					STRINGS.getString(AccountsMessages.MSGR_UNRECOVERABLE_ERROR),
					"Trying to query Balance Sheets for User",
					e);
			close();
		}
		
		if (myBalSheets.size() == 0) {
			VerticalLayout vl = new VerticalLayout();
			vl.addComponent(new Label(STRINGS.getString(
					AccountsMessages.BAL_SHEET_NO_CURRENT_SHEET)));
			
			Button createBalSheet = new Button(
					STRINGS.getString(AccountsMessages
							.BAL_SHEET_CREATE_SHEET_QUESTION));
			createBalSheet.setStyleName(BaseTheme.BUTTON_LINK);
			createBalSheet.addListener(new Button.ClickListener() {
				
				private static final long serialVersionUID = 1L;

				@Override
				public void buttonClick(ClickEvent event) {
					close();
					AccountsApplication.getInstance().
						getMainWindow().
						addWindow(new CreateNewBalanceSheet());
				}
			});
			vl.addComponent(createBalSheet);
			
			addComponent(vl);
		} else {
			VerticalLayout vl = new VerticalLayout();
			vl.addComponent(new Label(STRINGS.getString(
					AccountsMessages.BAL_SHEET_REMOVE_SELECT)));
			balSheetSelect = new ComboBox();
			for (BalanceSheet sheet : myBalSheets) {
				if (sheet.isActive())
					balSheetSelect.addItem(sheet.getSheetName());
			}
			balSheetSelect.setFilteringMode(Filtering.FILTERINGMODE_STARTSWITH);
			balSheetSelect.setNullSelectionAllowed(false);
			
			vl.addComponent(balSheetSelect);
			
			closureReason = new TextArea(STRINGS.getString(
					AccountsMessages.BAL_SHEET_REMOVE_REASON)); 
			vl.addComponent(closureReason);
			
			addComponent(vl);
		}
		
		Button save = new Button("Close");
		if (balSheetSelect == null)
			save.setEnabled(false);
		
		save.addListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				String sheetName = (String) balSheetSelect.getValue();
				if (sheetName != null)
					try {
						accSvc.closeBalanceSheet(
								(AccountsUser) AccountsApplication.getInstance().getUser(), 
								sheetName, 
								(String) closureReason.getValue());
					} catch (AccountsSheetException e) {
						Messenger.genericMessage(MessageSeverity.ERROR, 
								STRINGS.getString(AccountsMessages.MSGR_UNRECOVERABLE_ERROR),
								"Trying to mark balance sheet as closed",
								e);
					}
				close();
			}
		});
		
		addComponent(save);
	}
}