package com.mooneyserver.account.ui.notification;

import com.mooneyserver.account.AccountsApplication;
import com.mooneyserver.account.i18n.AccountsMessages;
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
		
		n.setCaption("Apologies");
    	n.setDescription("<br/>This feature hasn't been implemented yet");
    	n.setIcon(new ThemeResource("img/feature-not-present.gif"));
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
				icon = new ThemeResource("img/info-icon-32.png");
				break;
			case WARNING:
				caption = AccountsApplication.getResourceBundle()
					.getString(AccountsMessages.MSGR_TITLE_WARN);
				icon = new ThemeResource("img/warning-icon-32.png");
				break;
			case ERROR:
				caption = AccountsApplication.getResourceBundle()
					.getString(AccountsMessages.MSGR_TITLE_ERROR);
				icon = new ThemeResource("img/error-icon-32.png");
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