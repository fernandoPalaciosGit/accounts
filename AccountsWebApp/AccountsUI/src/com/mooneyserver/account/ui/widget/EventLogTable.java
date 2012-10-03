package com.mooneyserver.account.ui.widget;

import java.util.Date;

import com.mooneyserver.account.ui.manager.IconManager;
import com.vaadin.data.Item;
import com.vaadin.ui.Table;

public class EventLogTable extends Table {

	private static final long serialVersionUID = 1L;
	
	private String WARN_EVENT = "WARN";
	private String ERROR_EVENT = "ERROR";

	public EventLogTable() {
		super();
		
		setCaption("User Event Log");
		setWidth("100%");
		
		addContainerProperty("Owner", String.class, null);
		setColumnIcon("Owner", IconManager.getIcon(IconManager.USER_SETTINGS_LOG_OWNER));
		addContainerProperty("Type", String.class, null);
		setColumnIcon("Type", IconManager.getIcon(IconManager.USER_SETTINGS_LOG_TYPE));
		addContainerProperty("Date", Date.class, null);
		setColumnIcon("Date", IconManager.getIcon(IconManager.USER_SETTINGS_LOG_DATE));
		addContainerProperty("Description", String.class, null);
		setColumnIcon("Description", IconManager.getIcon(IconManager.INFO));
		
		setCellStyleGenerator(new Table.CellStyleGenerator() {
			
			private static final long serialVersionUID = 1L;

			@Override
			public String getStyle(Object itemId, Object propertyId) {
				Item item = getItem(itemId);
		        String eventType = (String) item.getItemProperty("Type").getValue();
		        if (eventType.equalsIgnoreCase(WARN_EVENT)) {
		            return "highlight-blue";
		        } else if (eventType.equalsIgnoreCase(ERROR_EVENT)) {
		            return "highlight-red";
		        } else {
		            return null;
		        }
			}
		});
		
		generateDummyData();
	}
	
	private void generateDummyData() {
		Date now = new Date();

		addItem(new Object[]{"System", "INFO", now, "User logged in {today}"}, 1);
		addItem(new Object[]{"System", "INFO", now, "User access Balance Sheet 1"}, 2);
		addItem(new Object[]{"System", "ERROR", now, "Balance Sheet Populate took 234ms to respond"}, 3);
		addItem(new Object[]{"System", "INFO", now, "Use close Balance Sheet 1"}, 4);
		addItem(new Object[]{"System", "INFO", now, "User access Balance Sheet 1"}, 5);
		addItem(new Object[]{"System", "INFO", now, "User Inserted New Debit Entry {Utilities:Gas -> $124.46}"}, 6);
		addItem(new Object[]{"System", "WARN", now, "User Debits Have Exceeded Credits for Period {Sep, 12}"}, 7);
		addItem(new Object[]{"System", "INFO", now, "Use close Balance Sheet 1"}, 8);
		addItem(new Object[]{"System", "INFO", now, "User logged out {today}"}, 9);
		addItem(new Object[]{"System", "WARN", now, "Invalid Login Attempted for User"}, 10);
		addItem(new Object[]{"System", "INFO", now, "User logged in {today}"}, 11);
		addItem(new Object[]{"System", "INFO", now, "User access Settings Page at {today}"}, 12);
		addItem(new Object[]{"System", "INFO", now, "User settings updated {email -> newEmail@domain.com}"}, 13);
		addItem(new Object[]{"System", "ERROR", now, "User force logged out {today}. System Maintenance Occurs"}, 14);
		addItem(new Object[]{"System", "INFO", now, "User logged in {today}"}, 15);
	}
}
