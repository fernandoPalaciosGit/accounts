package com.mooneyserver.account.ui.notification;

import com.mooneyserver.account.AccountsApplication;
import com.mooneyserver.account.i18n.AccountsMessages;
import com.mooneyserver.account.ui.view.IconManager;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Window.Notification;

public class Messenger {

	public enum MessageSeverity {
		INFO,
		WARNING,
		ERROR;
	}
	
	public static void notYetImplemented() {
		Notification n = new Notification(
	            "","",Notification.TYPE_HUMANIZED_MESSAGE);
		
		// TODO; Localize
		n.setCaption("Apologies");
    	n.setDescription("<br/>This feature hasn't been implemented yet");
    	n.setIcon(IconManager.getIcon(IconManager.FEATURE_MISSING));
    	n.setPosition(Notification.POSITION_CENTERED);
        n.setDelayMsec(-1); // No Timeout - ie. click to close
        
        AccountsApplication.getInstance()
        	.getMainWindow().showNotification(n);
	}
	
	public static void genericMessage(MessageSeverity sev, String msg) {
		Notification n = new Notification(
	            "","",Notification.TYPE_HUMANIZED_MESSAGE);
		
		String caption = "";
		ThemeResource icon = null;
		switch(sev) {
			case INFO:
				caption = AccountsApplication.getResourceBundle()
					.getString(AccountsMessages.MSGR_TITLE_INFO);
				icon = IconManager.getIcon(IconManager.NOTIFICATION_INFO);
				break;
			case WARNING:
				caption = AccountsApplication.getResourceBundle()
					.getString(AccountsMessages.MSGR_TITLE_WARN);
				icon = IconManager.getIcon(IconManager.NOTIFICATION_WARN);
				break;
			case ERROR:
				caption = AccountsApplication.getResourceBundle()
					.getString(AccountsMessages.MSGR_TITLE_ERROR);
				icon = IconManager.getIcon(IconManager.NOTIFICATION_ERR);
				break;
		}
		n.setCaption(caption);
    	n.setDescription("<br/>" + msg);
    	n.setIcon(icon);
    	n.setPosition(Notification.POSITION_CENTERED);
        n.setDelayMsec(-1);
        
        AccountsApplication.getInstance()
        	.getMainWindow().showNotification(n);
	}
}