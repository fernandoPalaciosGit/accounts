package com.mooneyserver.account.ui.view.subwindow.accounts;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

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
import com.mooneyserver.account.ui.view.subwindow.BaseSubwindow;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Form;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.Select;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class PaymentTypeMgmt extends BaseSubwindow 
	implements Button.ClickListener, ValueChangeListener {

	private static final long serialVersionUID = 1L;
	
	@BusinessProcess
	private IPaymentTypeMgmt accSvc;
	
	private ResourceBundle STRINGS = AccountsApplication.getResourceBundle();
	
	private BalanceSheet balSheet;
	private ListSelect paymentCategories, paymentTypes;
	private LinkedHashMap<String, CategoryType> categories;
	private LinkedHashMap<String, PaymentType> types;
	
	private Accordion accordion;
	private Tab current, addNewCategory, addNewType;
	private Form catFrm, typeFrm;
	
	private final int NUM_ROWS = 6;
	private final int ADD_NEW_CATEGORY = 0;
	private final int ADD_NEW_TYPE = 1;
	
	private final int ADD_CATG_FLD_NAME = 0;
	private final int ADD_CATG_CREDIT_BOOL = 1;
	private final int ADD_TYPE_FLD_NAME = 0;
	private final int ADD_TYPE_FLD_CAT = 1;
	
	public PaymentTypeMgmt(BalanceSheet sheet) {
		super(AccountsMessages.BAL_SHEET_NEW_PAY_TYPE);
		
		this.balSheet = sheet;
		
		setWidth("320px");
		setIcon(IconManager.getIcon(IconManager.CATG_MGMT_SM));
		
		accordion = new Accordion();
		accordion.setSizeFull();
		
		HorizontalLayout hl = new HorizontalLayout();
		hl.setSpacing(true);
		
		VerticalLayout vl1 = new VerticalLayout();
		vl1.setSpacing(true);
		
		
		paymentCategories = new ListSelect(STRINGS.getString(AccountsMessages.BAL_SHEET_PAYMENT_CATEGORY));
		populatePaymentCategories();
		paymentCategories.setNullSelectionAllowed(false);
		paymentCategories.setImmediate(true);
		paymentCategories.setRows(NUM_ROWS);
		paymentCategories.addListener(this);
		
		Set<String> keys = categories.keySet();
		if (keys.size() > 0)
			paymentCategories.select(keys.iterator().next());
		
		vl1.addComponent(paymentCategories);
		
		hl.addComponent(vl1);
		
		
		VerticalLayout vl2 = new VerticalLayout();
		vl2.setSpacing(true);
		
		
		paymentTypes = new ListSelect(STRINGS.getString(AccountsMessages.BAL_SHEET_PAYMENT_TYPE));
		populatePaymentTypes(categories.get(paymentCategories.getValue()));
		paymentTypes.setNullSelectionAllowed(false);
		paymentTypes.setImmediate(true);
		paymentTypes.setRows(NUM_ROWS);
		
		vl2.addComponent(paymentTypes);
		
		hl.addComponent(vl2);
		
		current = accordion.addTab(hl);
		current.setCaption("Current");
		
		addNewCategory = accordion.addTab(generateCreateNewCategoryForm());
		addNewCategory.setCaption(STRINGS.getString(AccountsMessages.ADD_NEW) 
				+ " " + STRINGS.getString(AccountsMessages.BAL_SHEET_PAYMENT_CATEGORY)); 
		
		addNewType = accordion.addTab(generateCreateNewTypeForm());
		addNewType.setCaption(STRINGS.getString(AccountsMessages.ADD_NEW) 
				+ " " + STRINGS.getString(AccountsMessages.BAL_SHEET_PAYMENT_TYPE));
				
		addComponent(accordion);
	}
	
	private void populatePaymentCategories() {
		try {
			List<CategoryType> svcCategories = accSvc.getCategoriesForSheet(balSheet);
			
			categories = new LinkedHashMap<>(svcCategories.size());
			
			paymentCategories.removeAllItems();
			if (typeFrm != null)
				((Select) typeFrm.getField(ADD_TYPE_FLD_CAT)).removeAllItems();
			
			for (CategoryType cat : svcCategories) {
				categories.put(cat.getName(), cat);
				paymentCategories.addItem(cat.getName());
				if (typeFrm != null)
					((Select) typeFrm.getField(ADD_TYPE_FLD_CAT))
						.addItem(cat.getName());
			}
			
			Set<String> keys = categories.keySet();
			if (keys.size() > 0)
				paymentCategories.select(keys.iterator().next());
			
		} catch (AccountsSheetException e) {
			close();
			Messenger.genericMessage(MessageSeverity.ERROR, 
					STRINGS.getString(AccountsMessages.MSGR_UNRECOVERABLE_ERROR), 
					"Failed trying to query Payment Categories for Balance Sheet", 
					e);
		}
	}
	
	private void populatePaymentTypes(CategoryType selectedCategory) {
		try {
			List<PaymentType> svcTypes = accSvc.getTypesForCategory(selectedCategory);
			
			types = new LinkedHashMap<>();
			
			paymentTypes.removeAllItems();
			for (PaymentType type : svcTypes) {
				types.put(type.getName(), type);
				paymentTypes.addItem(type.getName());
			}
		} catch (AccountsSheetException e) {
			close();
			Messenger.genericMessage(MessageSeverity.ERROR, 
					STRINGS.getString(AccountsMessages.MSGR_UNRECOVERABLE_ERROR), 
					"Failed trying to query Payment Types for Balance Sheet", 
					e);
		}
	}
	
	// TODO: Add Form Field validators to avoid dupes
	private VerticalLayout generateCreateNewCategoryForm() {
		catFrm = new Form();
		
		catFrm.addField(ADD_CATG_FLD_NAME, new TextField(
				STRINGS.getString(AccountsMessages.BAL_SHEET_PAYMENT_CATEGORY) 
				+ " " + STRINGS.getString(AccountsMessages.NAME)));
		catFrm.addField(ADD_CATG_CREDIT_BOOL, new CheckBox(
			STRINGS.getString(AccountsMessages.BAL_SHEET_CREDIT) + "?"));
		
		
		return generateGenericTabSheet(catFrm, ADD_NEW_CATEGORY);
	}
	
	// TODO: Add Form Field validators to avoid dupes
	private VerticalLayout generateCreateNewTypeForm() {
		typeFrm = new Form();
		
		typeFrm.addField(ADD_TYPE_FLD_NAME, new TextField(
				STRINGS.getString(AccountsMessages.BAL_SHEET_PAYMENT_CATEGORY) 
				+ " " 
				+ STRINGS.getString(AccountsMessages.NAME)));
		
		Select selCatg = new Select("Category", categories.keySet());
		selCatg.setNullSelectionAllowed(false);
		selCatg.setImmediate(true);
		typeFrm.addField(ADD_TYPE_FLD_CAT, selCatg);
		
		return generateGenericTabSheet(typeFrm, ADD_NEW_TYPE);
	}
	
	private VerticalLayout generateGenericTabSheet(Form frm, int CREATION_TYPE) {
		VerticalLayout vl = new VerticalLayout();
		vl.setSpacing(true);
		
		vl.addComponent(frm);
		
		HorizontalLayout hl = new HorizontalLayout();
		
		Button btn = new Button("Create");
		btn.addListener((Button.ClickListener)this);
		btn.setData(CREATION_TYPE);
		hl.addComponent(btn);
		hl.setComponentAlignment(btn, Alignment.MIDDLE_RIGHT);
		
		vl.addComponent(hl);
		
		return vl;
	}

	@Override
	public void buttonClick(ClickEvent event) {
		switch ((Integer) event.getButton().getData()) {
			case ADD_NEW_CATEGORY:
				try {
					accSvc.addNewPaymentCategory((String) catFrm.getField(ADD_CATG_FLD_NAME)
							.getValue(), ((CheckBox)catFrm.getField(ADD_CATG_FLD_NAME)).booleanValue(), 
							balSheet);
				} catch (AccountsSheetException e) {
					close();
					Messenger.genericMessage(MessageSeverity.ERROR, 
							STRINGS.getString(AccountsMessages.MSGR_UNRECOVERABLE_ERROR), 
							"Failed trying to add Payment Categories for Balance Sheet", 
							e);
				}
				break;
			case ADD_NEW_TYPE:
				try {
					accSvc.addNewPaymentType((String) typeFrm.getField(ADD_TYPE_FLD_NAME)
							.getValue(), categories.get(typeFrm
									.getField(ADD_TYPE_FLD_CAT).getValue()));
				} catch (AccountsSheetException e) {
					close();
					Messenger.genericMessage(MessageSeverity.ERROR, 
							STRINGS.getString(AccountsMessages.MSGR_UNRECOVERABLE_ERROR), 
							"Failed trying to add Payment Types for Balance Sheet", 
							e);
				}
				break;
		}
		
		populatePaymentCategories();
		populatePaymentTypes(categories.get(paymentCategories.getValue()));
		
		accordion.setSelectedTab(current);
	}

	@Override
	public void valueChange(ValueChangeEvent event) {
		populatePaymentTypes(categories.get(paymentCategories.getValue()));
	}
}