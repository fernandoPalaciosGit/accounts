package com.mooneyserver.account.ui.view.subwindow.accounts;

import com.mooneyserver.account.AccountsApplication;
import com.mooneyserver.account.businesslogic.accounts.IBalanceSheetMgmtService;
import com.mooneyserver.account.businesslogic.exception.accounts.AccountsSheetException;
import com.mooneyserver.account.i18n.AccountsMessages;
import com.mooneyserver.account.lookup.BusinessProcess;
import com.mooneyserver.account.persistence.entity.AccountsUser;
import com.mooneyserver.account.persistence.entity.BalanceSheet;
import com.mooneyserver.account.ui.manager.IconManager;
import com.mooneyserver.account.ui.view.subwindow.BaseSubwindow;
import com.vaadin.ui.Button;
import com.vaadin.ui.Form;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Button.ClickEvent;

public class CreateNewBalanceSheet extends BaseSubwindow implements Button.ClickListener {

	private static final long serialVersionUID = 1L;
	
	@BusinessProcess
	private IBalanceSheetMgmtService accSvc;

	private Form createNewbalSheetFrm;
	private final String SHEET_NAME = "SheetName";
	private final String SHEET_DESCRIPTION = "SheetDescription";
	
	public CreateNewBalanceSheet() {
		super(AccountsMessages.CREATE_NEW_USER); // TODO: Correct Text
		
		setWidth("480px");
		setIcon(IconManager.getIcon(IconManager.ADD_NEW_BALANCE_SHEET_SMALL));
		
		createNewbalSheetFrm = new Form();
		
		TextField sheetName = new TextField("Balance Sheet Name");
		sheetName.setRequired(true);
		createNewbalSheetFrm.addField(SHEET_NAME, sheetName);
		
		TextArea description = new TextArea("Balance Sheet Description");
		description.setRows(10);
		description.setColumns(20);
		createNewbalSheetFrm.addField(SHEET_DESCRIPTION, description);
		
		addComponent(createNewbalSheetFrm);
		
		Button save = new Button("Create");
		save.addListener(this);
		addComponent(save);
	}

	@Override
	public void buttonClick(ClickEvent event) {
		try {
			createNewbalSheetFrm.commit();
			if (createNewbalSheetFrm.isValid()) {
				BalanceSheet balSheet = new BalanceSheet();
				
				balSheet.setOwner((AccountsUser) AccountsApplication
						.getInstance().getUser());
				balSheet.setDescription(((String) createNewbalSheetFrm
						.getField(SHEET_DESCRIPTION).getValue()).trim());
				balSheet.setSheetName(((String) createNewbalSheetFrm
						.getField(SHEET_NAME).getValue()).trim());
				
				accSvc.createNewBalanceSheet(balSheet); 
				
				close();
			}
		} catch(AccountsSheetException e) {
			// TODO: something
			e.printStackTrace();
		} catch(Exception e) {
			// TODO: something
			e.printStackTrace();
		}
	}
}