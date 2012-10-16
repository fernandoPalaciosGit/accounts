package com.mooneyserver.account.ui.widget;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.mooneyserver.account.AccountsApplication;
import com.mooneyserver.account.businesslogic.exception.AccountsBaseException;
import com.mooneyserver.account.businesslogic.logs.ILogService;
import com.mooneyserver.account.i18n.AccountsMessages;
import com.mooneyserver.account.lookup.BackendServiceLookup;
import com.mooneyserver.account.lookup.BusinessProcess;
import com.mooneyserver.account.persistence.entity.AccountsUser;
import com.mooneyserver.account.persistence.entity.AuditLog;
import com.mooneyserver.account.ui.iface.IContainsCustomAnnotations;
import com.mooneyserver.account.ui.manager.IconManager;
import com.mooneyserver.account.ui.notification.Messenger;
import com.mooneyserver.account.ui.notification.Messenger.MessageSeverity;
import com.vaadin.data.Item;
import com.vaadin.ui.Table;

public class EventLogTable extends Table implements IContainsCustomAnnotations {

	private static final long serialVersionUID = 1L;
	
	private String WARN_EVENT = "WARN";
	private String ERROR_EVENT = "ERROR";
	
	@BusinessProcess
	ILogService logSvc;

	public EventLogTable() {
		super();
		
		loadBackendServices();
		
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
	}
	
	public void refreshTableData(Date from, Date to) {
		from = trimToDay(from, TimeOfDay.START_OF_DAY);
		to = trimToDay(to, TimeOfDay.END_OF_DAY);
		
		try {
			List<AuditLog> logs = logSvc.queryLogsByPeriod(
					(AccountsUser) AccountsApplication.getInstance().getUser(), 
					from, 
					to);
			
			if (logs.size() == 0) {
				Messenger.genericMessage(
						MessageSeverity.WARNING, 
						"No Logs found for the selected period {"+from+" -> "+to+"}", 
						null, 
						null);
				return;
			}
			
			this.removeAllItems();
			int itemId = 1;
			for (AuditLog log : logs) {
				addItem(new Object[]{
							log.getOwner().getUsername(), 
							log.getEventType().toString(), 
							log.getLogDate(), 
							log.getDetail()}
					, itemId++);
			}
		} catch (AccountsBaseException e) {
			Messenger.genericMessage(
				MessageSeverity.ERROR, 
				AccountsApplication.getResourceBundle().getString(AccountsMessages.MSGR_UNRECOVERABLE_ERROR), 
				"Exception thrown while trying to query logs", 
				e);
		}
	}

	enum TimeOfDay {
		START_OF_DAY,
		END_OF_DAY;
	}
	
	/*
	 * Make sure time filters go from start of day to
	 * end of day
	 */
	private Date trimToDay(Date date, TimeOfDay tod) {
		 Calendar cal = Calendar.getInstance();

	     cal.setTime(date);
	     
	     switch (tod) {
	     	case START_OF_DAY:
	     		cal.set(Calendar.HOUR_OF_DAY, 0);
	     		cal.set(Calendar.MINUTE, 0);
	     		cal.set(Calendar.SECOND, 0);
	     		cal.set(Calendar.MILLISECOND, 0);
	     		break;
	     	case END_OF_DAY:
	     		cal.set(Calendar.HOUR_OF_DAY, 23);
	     		cal.set(Calendar.MINUTE, 59);
	     		cal.set(Calendar.SECOND, 59);
	     		cal.set(Calendar.MILLISECOND, 999);
	     }
	     
	     return cal.getTime();
	}
	
	@Override
	public void loadBackendServices() {
		BackendServiceLookup.injectBackendServices(this);
	}
}
