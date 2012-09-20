package com.mooneyserver.account.ui.view.main.accounts;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mooneyserver.account.AccountsApplication;
import com.mooneyserver.account.businesslogic.accounts.IBalanceSheetMgmt;
import com.mooneyserver.account.businesslogic.exception.accounts.AccountsSheetException;
import com.mooneyserver.account.i18n.AccountsMessages;
import com.mooneyserver.account.lookup.BusinessProcess;
import com.mooneyserver.account.persistence.entity.BalanceSheet;
import com.mooneyserver.account.persistence.entity.CategoryType;
import com.mooneyserver.account.persistence.entity.DebitEntry;
import com.mooneyserver.account.persistence.service.accounts.IDebitCredit;
import com.mooneyserver.account.ui.manager.EMainView;
import com.mooneyserver.account.ui.manager.IconManager;
import com.mooneyserver.account.ui.notification.Messenger;
import com.mooneyserver.account.ui.notification.Messenger.MessageSeverity;
import com.mooneyserver.account.ui.view.main.AbstractBaseSubView;
import com.mooneyserver.account.ui.view.subwindow.accounts.InsertNewBalanceSheetEntry;
import com.mooneyserver.account.ui.view.subwindow.accounts.PaymentTypeMgmt;
import com.mooneyserver.account.ui.view.subwindow.accounts.InsertNewBalanceSheetEntry.EntryType;

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
	
	@BusinessProcess
	private IBalanceSheetMgmt accSvc;

	private BalanceSheet sheet;
	private Button insertDebitEntry, insertCreditEntry, paymentTypeMgmt, closeBalSheet;
	private TreeTable sheetTreeTable;
	
	private SimpleDateFormat FORMAT_BY_DAY = new SimpleDateFormat("dd MMM", 
			AccountsApplication.getInstance().getLocale());
	private SimpleDateFormat FORMAT_BY_TIME = new SimpleDateFormat("dd MMM HH:mm", 
			AccountsApplication.getInstance().getLocale());
	private DecimalFormat FORMAT_DECIMAL = new DecimalFormat("#.00");
	
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
        
        insertDebitEntry = new Button();
        insertDebitEntry.setStyleName(BaseTheme.BUTTON_LINK);
        insertDebitEntry.setIcon(IconManager.getIcon(IconManager.INSERT_DEBIT_ENTRY));
        insertDebitEntry.addListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				AccountsApplication.getInstance().
					getMainWindow().
						addWindow(new InsertNewBalanceSheetEntry(sheet, EntryType.DEBIT));
			}
		});
        vl.addComponent(insertDebitEntry);
        vl.setComponentAlignment(insertDebitEntry, Alignment.MIDDLE_CENTER);
        
        insertCreditEntry = new Button();
        insertCreditEntry.setStyleName(BaseTheme.BUTTON_LINK);
        insertCreditEntry.setIcon(IconManager.getIcon(IconManager.INSERT_CREDIT_ENTRY));
        insertCreditEntry.addListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				AccountsApplication.getInstance().
					getMainWindow().
						addWindow(new InsertNewBalanceSheetEntry(sheet, EntryType.CREDIT));
			}
		});
        vl.addComponent(insertCreditEntry);
        vl.setComponentAlignment(insertCreditEntry, Alignment.MIDDLE_CENTER);
        
        
        paymentTypeMgmt = new Button();
        paymentTypeMgmt.setStyleName(BaseTheme.BUTTON_LINK);
        paymentTypeMgmt.setIcon(IconManager.getIcon(IconManager.CATG_MGMT));
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
        closeBalSheet.setIcon(IconManager.getIcon(IconManager.BALANCE_SHEET_CLOSE));
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
        sheetTreeTable.setSelectable(true);
		
        sheetTreeTable.addContainerProperty(STRINGS.getObject(
        		AccountsMessages.BAL_SHEET_PAYMENT_CATEGORY)
        		, String.class, "");
        sheetTreeTable.addContainerProperty(STRINGS.getObject(
        		AccountsMessages.BAL_SHEET_DEBIT), String.class, "");
        sheetTreeTable.addContainerProperty(STRINGS.getObject(
        		AccountsMessages.BAL_SHEET_CREDIT), String.class, "");
        sheetTreeTable.addContainerProperty(STRINGS.getObject(
        		AccountsMessages.BAL_SHEET_PAYMENT_DATE), String.class, "");

        populateTableData();
        
		hsp.addComponent(vl);
	    hsp.addComponent(sheetTreeTable);

	    hsp.setSizeFull();
	    
	    addComponent(hsp);
	    setExpandRatio(hsp, 1);

		buildStringsFromLocale();
	}
	
	@Override
	public void buildStringsFromLocale() {
		STRINGS = AccountsApplication.getResourceBundle();
		
		insertDebitEntry.setCaption(STRINGS.getString(AccountsMessages.INSERT) 
				+ " " + STRINGS.getObject(
        		AccountsMessages.BAL_SHEET_DEBIT));
		insertCreditEntry.setCaption(STRINGS.getString(AccountsMessages.INSERT) 
				+ " " + STRINGS.getString(
		        		AccountsMessages.BAL_SHEET_CREDIT));
		paymentTypeMgmt.setCaption(STRINGS.getString(
        		AccountsMessages.BAL_SHEET_NEW_PAY_TYPE));
		closeBalSheet.setCaption(STRINGS.getString(
        		AccountsMessages.FIN));
	}

	@Override
	public String getDisplayName() {
		return STRINGS.getString(AccountsMessages
				.BAL_SHEET_SUB_MENU_NAME).replace("{0}", 
						sheet.getSheetName());
	}

	@Override
	public EMainView getParentType() {	return EMainView.BAL_SHEET; }
	
	/* To be called on page load or refresh */
	private void populateTableData() {
		List<IDebitCredit> entries = getThisMonthsEntries();
		
		if (entries == null)
			return;
		
		BigDecimal debitTotal = BigDecimal.ZERO;
		BigDecimal creditTotal = BigDecimal.ZERO;
		
		Map<CategoryType, BigDecimal> categories = new HashMap<>();
		Map<String, Object> parents = new HashMap<>();
		
		for (IDebitCredit entry : entries) {			
			CategoryType category = entry.getPaymentType().getCategory();
			
			// Add a Category if it doesn't exist
			if (!categories.containsKey(category)) {
				
				categories.put(category, entry.getPaymentAmmount());
				
				Object parent = sheetTreeTable.addItem(new Object[] 
						{category.getName(), "-", "-", null}, 
						null);
				
				parents.put(category.getName(), parent);
				
				sheetTreeTable.setCollapsed(parent, false);
			} else {
				BigDecimal oldVal = categories.get(category);
				categories.put(category, oldVal.add(entry.getPaymentAmmount()));
			}

			// Add the child Debit or Credit
			Object leaf;
			if (entry instanceof DebitEntry) {
				debitTotal = debitTotal.add(entry.getPaymentAmmount());
				
				leaf = sheetTreeTable.addItem(new Object[] 
						{entry.getPaymentType().getName(), 
						FORMAT_DECIMAL.format(entry.getPaymentAmmount()), 
						"-", 
						FORMAT_BY_TIME.format(entry.getInsertTime())}, 
						null);
			} else {
				creditTotal = creditTotal.add(entry.getPaymentAmmount());
				
				leaf = sheetTreeTable.addItem(new Object[] 
						{entry.getPaymentType().getName(), 
						"-", 
						FORMAT_DECIMAL.format(entry.getPaymentAmmount()), 
						FORMAT_BY_TIME.format(entry.getInsertTime())}, 
						null);
			}
			
			// Attach the leaf to the parent category and dissallow children 
			sheetTreeTable.setParent(leaf, parents.get(category.getName()));
			sheetTreeTable.setChildrenAllowed(leaf, false);
		}
		
		sheetTreeTable.setColumnFooter(STRINGS.getString(
				AccountsMessages.BAL_SHEET_PAYMENT_CATEGORY), "Balance: " 
				+ FORMAT_DECIMAL.format(creditTotal.subtract(debitTotal)));
		sheetTreeTable.setColumnFooter(STRINGS.getString(
				AccountsMessages.BAL_SHEET_DEBIT), 
				FORMAT_DECIMAL.format(debitTotal));
        sheetTreeTable.setColumnFooter(STRINGS.getString(
				AccountsMessages.BAL_SHEET_CREDIT), 
        		FORMAT_DECIMAL.format(creditTotal));
        
        sheetTreeTable.setColumnFooter(STRINGS.getString(
        		AccountsMessages.BAL_SHEET_PAYMENT_DATE), 
        		STRINGS.getString(
                		AccountsMessages.TODAY) + ": " 
        		+ FORMAT_BY_DAY.format(new Date()));
        
        sheetTreeTable.setFooterVisible(true);
	}
	
	private List<IDebitCredit> getThisMonthsEntries() {
		Calendar from = Calendar.getInstance();
		Calendar to = Calendar.getInstance();
		
		from.set(Calendar.DAY_OF_MONTH, 1);
		
		to.add(Calendar.MONTH, 1);
		to.set(Calendar.DAY_OF_MONTH, 1);
		
		try {
			return accSvc.getEntriesWithinRange(sheet, from, to);
		} catch (AccountsSheetException e) {
			Messenger.notYetImplemented(); // TODO: Catch what business exceptions are thrown here
		} catch (Exception e) {
			Messenger.genericMessage(MessageSeverity.ERROR, 
					STRINGS.getString(AccountsMessages.MSGR_UNRECOVERABLE_ERROR), 
					"Failed trying to query Payment Categories for Balance Sheet", 
					e);
		}
		
		return null;
	}
}