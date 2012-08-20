package com.mooneyserver.account.ui.notification;

import java.util.ResourceBundle;

import com.mooneyserver.account.AccountsApplication;
import com.mooneyserver.account.i18n.AccountsMessages;
import com.mooneyserver.account.ui.manager.IconManager;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Window.Notification;

public class Messenger {

	public enum MessageSeverity {
		INFO,
		WARNING,
		ERROR,
		NOT_IMPLEMENTED;
	}
	
	private static ResourceBundle STRINGS = AccountsApplication.getResourceBundle();
	
	public static void notYetImplemented() {
		genericMessage(MessageSeverity.NOT_IMPLEMENTED, 
        		STRINGS.getString(AccountsMessages.MSGR_BODY_NO_FEATURE));
	}
	
	public static void genericMessage(MessageSeverity sev, String msg) {
		
		
		String caption = "";
		ThemeResource icon = null;
		switch(sev) {
			case INFO:
				caption = STRINGS.getString(AccountsMessages.MSGR_TITLE_INFO);
				icon = IconManager.getIcon(IconManager.NOTIFICATION_INFO);
				break;
			case WARNING:
				caption = STRINGS.getString(AccountsMessages.MSGR_TITLE_WARN);
				icon = IconManager.getIcon(IconManager.NOTIFICATION_WARN);
				break;
			case ERROR:
				caption = STRINGS.getString(AccountsMessages.MSGR_TITLE_ERROR);
				icon = IconManager.getIcon(IconManager.NOTIFICATION_ERR);
				break;
			case NOT_IMPLEMENTED:
				caption = STRINGS.getString(AccountsMessages.MSGR_APOLOGIES);
				icon = IconManager.getIcon(IconManager.FEATURE_MISSING);
				break;
		}
		
        AccountsApplication.getInstance()
        	.getMainWindow().showNotification(getNotification(caption, msg, icon));
	}
	
	private static Notification getNotification(String title, String body, ThemeResource icon) {
		Notification n = new Notification(
	            "","",Notification.TYPE_HUMANIZED_MESSAGE);
		
		n.setCaption(title);
    	n.setDescription("<br/>" + body);
    	n.setIcon(icon);
    	n.setPosition(Notification.POSITION_CENTERED);
        n.setDelayMsec(-1);
        
        return n;
	}
}