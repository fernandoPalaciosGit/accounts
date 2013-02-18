package com.mooneyserver.account.ui.view.subwindow.accounts;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ResourceBundle;

import com.mooneyserver.account.AccountsApplication;
import com.mooneyserver.account.businesslogic.accounts.IPaymentTypeMgmt;
import com.mooneyserver.account.businesslogic.exception.accounts.AccountsSheetException;
import com.mooneyserver.account.i18n.AccountsMessages;
import com.mooneyserver.account.lookup.BusinessProcess;
import com.mooneyserver.account.persistence.entity.BalanceSheet;
import com.mooneyserver.account.persistence.entity.CategoryType;
import com.mooneyserver.account.persistence.entity.PaymentType;
import com.mooneyserver.account.ui.manager.IconManager;
import com.mooneyserver.account.ui.notification.Messenger;
import com.mooneyserver.account.ui.notification.Messenger.MessageSeverity;
import com.mooneyserver.account.ui.validate.ConfirmValidMoneyValueFieldValidator;
import com.mooneyserver.account.ui.view.subwindow.BaseSubwindow;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.ui.AbstractSelect.Filtering;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class InsertNewBalanceSheetEntry extends BaseSubwindow 
	implements Property.ValueChangeListener, Button.ClickListener {

	private static final long serialVersionUID = 1L;
	
	@BusinessProcess
	private IPaymentTypeMgmt accSvc;
	
	private ResourceBundle STRINGS = AccountsApplication.getResourceBundle();
	
	private BalanceSheet sheet;
	private LinkedHashMap<String, CategoryType> categories;
	private LinkedHashMap<String, PaymentType> types;
	private EntryType entryType;
	
	private ComboBox categoryComboBox, typeComboBox;
	private PopupDateField dateField;
	private Button insert;
	private TextField value;
	private OptionGroup period;
	private TextArea description;
	
	private BalanceSheetEntryForm frm;
	
	public enum EntryType {
		DEBIT,
		CREDIT;
	}
	
	public InsertNewBalanceSheetEntry(BalanceSheet sheet, EntryType entryType) {
		super(entryType.equals(EntryType.DEBIT) ? AccountsMessages.BAL_SHEET_INSERT_ENTRY_DT 
				: AccountsMessages.BAL_SHEET_INSERT_ENTRY_CT);
		
		this.sheet = sheet;
		this.entryType = entryType;
		
		setWidth("480px");
		if (entryType.equals(EntryType.CREDIT)) {
			setIcon(IconManager.getIcon(IconManager.INSERT_CREDIT_ENTRY_SM));
		} else {
			setIcon(IconManager.getIcon(IconManager.INSERT_DEBIT_ENTRY_SM));
		}
		
		VerticalLayout vl = new VerticalLayout();
		vl.setSpacing(true);
		
		
		categoryComboBox = new ComboBox(STRINGS
				.getString(AccountsMessages.BAL_SHEET_PAYMENT_CATEGORY)); 
		categoryComboBox.setImmediate(true);
		categoryComboBox.setNullSelectionAllowed(false);
		categoryComboBox.setFilteringMode(Filtering.FILTERINGMODE_STARTSWITH);
		categoryComboBox.addListener(this);
		categoryComboBox.setRequired(true);
		
		typeComboBox = new ComboBox(STRINGS
				.getString(AccountsMessages.BAL_SHEET_PAYMENT_TYPE));
		typeComboBox.setImmediate(true);
		typeComboBox.setNullSelectionAllowed(false);
		typeComboBox.setFilteringMode(Filtering.FILTERINGMODE_STARTSWITH);
		typeComboBox.setRequired(true);
		
		categories = new LinkedHashMap<>();		
		types = new LinkedHashMap<>();
		
		populatePaymentCategories();
		if (categories.size() > 0)
			categoryComboBox.select(categories.keySet().iterator().next());
		populatePaymentTypes();
		
		value = new TextField(STRINGS
				.getString(AccountsMessages.BAL_SHEET_PAYMENT_VALUE));
		value.setRequired(true);
		value.addValidator(new ConfirmValidMoneyValueFieldValidator());
		
		period = new OptionGroup(STRINGS
				.getString(AccountsMessages.BAL_SHEET_PAYMENT_VALUE),
			new ArrayList<String>() {
				private static final long serialVersionUID = 1L;

				{
					add(STRINGS.getString(AccountsMessages.BAL_SHEET_PAYMENT_YEAR)); 
					add(STRINGS.getString(AccountsMessages.BAL_SHEET_PAYMENT_MONTH));
				}
			}
		);
		period.select(STRINGS.getString(AccountsMessages.BAL_SHEET_PAYMENT_MONTH));
		
		dateField = new PopupDateField(STRINGS.getString(AccountsMessages.BAL_SHEET_PAYMENT_DATE));
		dateField.setValue(new Date());
		dateField.setResolution(PopupDateField.RESOLUTION_DAY);
		dateField.setLocale(getLocale());
		dateField.setRequired(true);
		
		description = new TextArea(STRINGS.getString(AccountsMessages.DESCRIPTION));
		description.setRows(4);
		
		frm = new BalanceSheetEntryForm();
		vl.addComponent(frm);
		
		insert = new Button(entryType.equals(EntryType.DEBIT) ? 
				STRINGS.getString(AccountsMessages.BAL_SHEET_DEBIT) 
				: STRINGS.getString(AccountsMessages.BAL_SHEET_CREDIT)); 
		insert.addListener((Button.ClickListener) this);
		
		vl.addComponent(insert);
		
		addComponent(vl);
	}
	
	private void populatePaymentCategories() {
		try {
			List<CategoryType> svcCategories = accSvc.getCategoriesForSheet(sheet);
					
			for (CategoryType cat : svcCategories) {
				if (entryType == EntryType.CREDIT && cat.isCredit()) {
					categoryComboBox.addItem(cat.getName());
					categories.put(cat.getName(), cat);
				} 
				
				if (entryType == EntryType.DEBIT && !cat.isCredit()) {
					categoryComboBox.addItem(cat.getName());
					categories.put(cat.getName(), cat);
				}
			}
		} catch (AccountsSheetException e) {
			close();
			Messenger.genericMessage(MessageSeverity.ERROR, 
					STRINGS.getString(AccountsMessages.MSGR_UNRECOVERABLE_ERROR), 
					"Failed trying to query Payment Categories for Balance Sheet", 
					e);
		}
	}
	
	private void populatePaymentTypes() {
		try {
			List<PaymentType> svcTypes = accSvc.getTypesForCategory
					(categories.get(categoryComboBox.getValue()));
			
			typeComboBox.removeAllItems();
			
			for (PaymentType type : svcTypes) {
				typeComboBox.addItem(type.getName());
				types.put(type.getName(), type);
			}
		} catch (AccountsSheetException e) {
			close();
			Messenger.genericMessage(MessageSeverity.ERROR, 
					STRINGS.getString(AccountsMessages.MSGR_UNRECOVERABLE_ERROR), 
					"Failed trying to query Payment Types for Balance Sheet", 
					e);
		}
	}

	@Override
	public void valueChange(ValueChangeEvent event) {
		populatePaymentCategories();
		populatePaymentTypes();
	}

	@Override
	public void buttonClick(ClickEvent event) {
		try {
			frm.commit();
			if (frm.isValid()) {
				PaymentType type = types.get(typeComboBox.getValue());
				boolean isMonthly = period.getValue().equals(
						STRINGS.getString(AccountsMessages.BAL_SHEET_PAYMENT_MONTH));
				Date insertionTime = (Date) dateField.getValue();
				String entryDesc = (String) description.getValue();
				BigDecimal decimal = new BigDecimal((String) value.getValue());
				
				switch(entryType) {
					case DEBIT:
						accSvc.addNewDebitEntry(decimal, sheet, type, isMonthly, insertionTime, entryDesc);
						break;
					case CREDIT:
						accSvc.addNewCreditEntry(decimal, sheet, type, isMonthly, insertionTime, entryDesc);
						break;
				}
				close();
				AccountsApplication.getInstance()
					.nav.peek().refreshView();
			}
		} catch (InvalidValueException e) {
			// Handled by UI framework
		} catch (Exception e) {
			close();
			Messenger.genericMessage(MessageSeverity.ERROR, 
					STRINGS.getString(AccountsMessages.MSGR_UNRECOVERABLE_ERROR), 
					"Failed trying to insert [Debit|Credit] Balance Sheet", 
					e);
		}
	}
	
	class BalanceSheetEntryForm extends Form {
		
		private static final long serialVersionUID = 1L;

		private GridLayout layout;
		
		private final int FIELD_CATG = 0;
		private final int FIELD_TYPE = 1;
		private final int FIELD_VALUE = 2;
		private final int FIELD_PERIOD = 3;
		private final int FIELD_DATE = 4;
		private final int FIELD_DESCRIPTION = 5;
		
		public BalanceSheetEntryForm() {
			layout = new GridLayout(2, 4);
			layout.setSpacing(true);
			layout.setMargin(true, true, true, true);
			
			setContent(layout);
			setWriteThrough(false);
			
			this.addField(FIELD_CATG, categoryComboBox);
			this.addField(FIELD_TYPE, typeComboBox);
			this.addField(FIELD_VALUE, value);
			this.addField(FIELD_PERIOD, period);
			this.addField(FIELD_DATE, dateField);
			this.addField(FIELD_DESCRIPTION, description);
		}
		
		 @Override
        protected void attachField(Object propertyId, Field field) {
            if (propertyId.equals(FIELD_CATG)) {
                layout.addComponent(field, 0, 0);
            } else if (propertyId.equals(FIELD_TYPE)) {
            	layout.addComponent(field, 1, 0);
            } else if (propertyId.equals(FIELD_VALUE)) {
            	layout.addComponent(field, 0, 1);
            } else if (propertyId.equals(FIELD_PERIOD)) {
            	layout.addComponent(field, 1, 1);
            } else if (propertyId.equals(FIELD_DATE)) {
            	layout.addComponent(field, 0, 2);
            } else if (propertyId.equals(FIELD_DESCRIPTION)) {
            	layout.addComponent(field, 0, 3);
            } 
        }
	}
}