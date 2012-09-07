package com.mooneyserver.account.ui.notification;

import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.mooneyserver.account.AccountsApplication;
import com.mooneyserver.account.i18n.AccountsMessages;
import com.mooneyserver.account.logging.AccountsLoggingConstants;
import com.mooneyserver.account.ui.manager.IconManager;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Window.Notification;

public final class Messenger {

	public static Logger log = Logger.getLogger(AccountsLoggingConstants.LOG_AREA_UI);
	
	public enum MessageSeverity {
		INFO,
		WARNING,
		ERROR,
		NOT_IMPLEMENTED;
	}
	
	private static ResourceBundle STRINGS = AccountsApplication.getResourceBundle();
	
	public static void notYetImplemented() {
		genericMessage(MessageSeverity.NOT_IMPLEMENTED, 
        		STRINGS.getString(AccountsMessages.MSGR_BODY_NO_FEATURE),
        		null, null);
	}
	
	public static void genericMessage(MessageSeverity sev, String msg, String logMsg, Throwable stack) {
		String caption = "";
		ThemeResource icon = null;
		
		switch(sev) {
			case INFO:
				caption = STRINGS.getString(AccountsMessages.MSGR_TITLE_INFO);
				icon = IconManager.getIcon(IconManager.NOTIFICATION_INFO);
				writeToLogs(Level.INFO, logMsg, stack);
				break;
			case WARNING:
				caption = STRINGS.getString(AccountsMessages.MSGR_TITLE_WARN);
				icon = IconManager.getIcon(IconManager.NOTIFICATION_WARN);
				writeToLogs(Level.WARNING, logMsg, stack);
				break;
			case ERROR:
				caption = STRINGS.getString(AccountsMessages.MSGR_TITLE_ERROR);
				icon = IconManager.getIcon(IconManager.NOTIFICATION_ERR);
				writeToLogs(Level.SEVERE, logMsg, stack);
				break;
			case NOT_IMPLEMENTED:
				caption = STRINGS.getString(AccountsMessages.MSGR_APOLOGIES);
				icon = IconManager.getIcon(IconManager.FEATURE_MISSING);
				break;
		}
		
        AccountsApplication.getInstance()
        	.getMainWindow().showNotification(getNotification(caption, msg, icon));
	}
	
	static Notification getNotification(String title, String body, ThemeResource icon) {
		Notification n = new Notification(
	            "","",Notification.TYPE_HUMANIZED_MESSAGE);
		
		n.setCaption(title);
    	n.setDescription("<br/>" + body);
    	n.setIcon(icon);
    	n.setPosition(Notification.POSITION_CENTERED);
        n.setDelayMsec(-1);
        
        return n;
	}
	
	static void writeToLogs(Level level, String msg, Throwable e) {
		if (msg == null)
			return;
		
		if (e == null)
			log.log(level, msg);
		else
			log.log(level, msg, e);
	}
}