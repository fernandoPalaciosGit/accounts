package com.mooneyserver.account.ui.view.subwindow.accounts;

import java.util.ArrayList;
import java.util.ResourceBundle;

import com.mooneyserver.account.AccountsApplication;
import com.mooneyserver.account.businesslogic.accounts.IPaymentTypeMgmt;
import com.mooneyserver.account.i18n.AccountsMessages;
import com.mooneyserver.account.lookup.BusinessProcess;
import com.mooneyserver.account.ui.manager.IconManager;
import com.mooneyserver.account.ui.view.subwindow.BaseSubwindow;
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TextField;

// TODO: Finish this page properly
public class InsertNewBalanceSheetEntry extends BaseSubwindow {

	private static final long serialVersionUID = 1L;
	
	@BusinessProcess
	private IPaymentTypeMgmt accSvc;

	ResourceBundle STRINGS = AccountsApplication.getResourceBundle();
	
	public InsertNewBalanceSheetEntry() {
		super(AccountsMessages.BAL_SHEET_ADD_SHEET);
		
		
		setWidth("480px");
		setIcon(IconManager.getIcon(IconManager.ADD_NEW_BALANCE_SHEET_SMALL));
		
		HorizontalLayout hl = new HorizontalLayout();
		hl.setSpacing(true);
		
		ComboBox categoryComboBox = new ComboBox("Category");
		categoryComboBox.setNewItemsAllowed(true);
		categoryComboBox.setImmediate(true);
		categoryComboBox.setNewItemHandler(new AbstractSelect.NewItemHandler() {

			private static final long serialVersionUID = 1L;

			@Override
			public void addNewItem(String newItemCaption) {
				// TODO Auto-generated method stub
			}
		});
		hl.addComponent(categoryComboBox);
		
		ComboBox typeComboBox = new ComboBox("Type");
		typeComboBox.setNewItemsAllowed(true);
		typeComboBox.setNewItemHandler(new AbstractSelect.NewItemHandler() {

			private static final long serialVersionUID = 1L;

			@Override
			public void addNewItem(String newItemCaption) {
				// TODO Auto-generated method stub
			}
		});
		hl.addComponent(typeComboBox);
		
		addComponent(hl);
		
		HorizontalLayout hl2 = new HorizontalLayout();
		hl2.setSpacing(true);
		
		hl2.addComponent(new TextField("Value"));
		hl2.addComponent(new OptionGroup("Period", new ArrayList<String>() {{add("year"); add("month");}}));
		addComponent(hl2);
		
		addComponent(new PopupDateField("Date"));
		
		addComponent(new Button("Insert"));
	}
}