package com.mooneyserver.account.ui.view.subwindow.accounts;

import java.util.List;

import com.mooneyserver.account.AccountsApplication;
import com.mooneyserver.account.businesslogic.accounts.IBalanceSheetMgmtService;
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

public class CloseBalanceSheet extends BaseSubwindow {

	private static final long serialVersionUID = 1L;
	
	@BusinessProcess
	private IBalanceSheetMgmtService accSvc;
	
	private ComboBox balSheetSelect;
	private TextArea closureReason;
	
	// TODO: Localise
	public CloseBalanceSheet() {
		super(AccountsMessages.CREATE_NEW_USER); // TODO: Correct Text
		
		setWidth("380px");
		setIcon(IconManager.getIcon(IconManager.CLOSE_BALANCE_SHEET_SMALL));
		
		List<BalanceSheet> myBalSheets = null;
		try {
			myBalSheets = accSvc.getMyBalanceSheets((AccountsUser) 
					AccountsApplication.getInstance().getUser());
		} catch (AccountsSheetException e) {
			Messenger.genericMessage(MessageSeverity.ERROR, 
					"Error trying to access your Balance Sheets");
			close();
		}
		
		// TODO: Localise
		if (myBalSheets.size() == 0) {
			VerticalLayout vl = new VerticalLayout();
			vl.addComponent(new Label("You do not appear to have any Balance Sheet created yet."));
			vl.addComponent(new Button("Create Bal Sheet?"));
			
			addComponent(vl);
		} else {
			VerticalLayout vl = new VerticalLayout();
			vl.addComponent(new Label("Please select which Balance Sheet to close"));
			balSheetSelect = new ComboBox();
			for (BalanceSheet sheet : myBalSheets) {
				if (sheet.isActive())
					balSheetSelect.addItem(sheet.getSheetName());
			}
			balSheetSelect.setFilteringMode(Filtering.FILTERINGMODE_STARTSWITH);
			balSheetSelect.setNullSelectionAllowed(false);
			
			vl.addComponent(balSheetSelect);
			
			closureReason = new TextArea("Reason for Closure"); 
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
						Messenger.genericMessage(MessageSeverity.ERROR, "Some Error Msg");
					}
				close();
			}
		});
		
		addComponent(save);
	}
}