package com.mooneyserver.account.ui.view;

import org.vaadin.aboutbox.AboutBox;

import com.mooneyserver.account.ui.AccountsApplication;
import com.mooneyserver.account.ui.view.subwindow.LanguageSelectDialog;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Window.Notification;

class BaseWindowClickListener implements ClickListener,
		org.vaadin.aboutbox.AboutBox.ClickListener {

	private static final long serialVersionUID = 3276588169214968557L;
	
	private Notification n = new Notification(
            "","",Notification.TYPE_HUMANIZED_MESSAGE);
    
    
    public BaseWindowClickListener() {
    	n.setCaption("Apologies");
    	n.setDescription("<br/>This feature hasn't been implemented yet");
    	n.setIcon(new ThemeResource("img/feature-not-present.gif"));
    	n.setPosition(Notification.POSITION_CENTERED);
        n.setDelayMsec(-1); // No Timeout
	}
    
	public static final String LANG = "LANG";
	public static final String GEN_SETTINGS = "GEN_SETTINGS";
	public static final String SIGNOUT = "SIGNOUT";

	@Override
	public void aboutBoxClicked(AboutBox sender) {
		AccountsApplication.getInstance().getMainWindow().executeJavaScript(
	            "window.open('http://github.com/irishshagua/mooney-server', 'forkFromGitHub')");
	}

	@Override
	public void buttonClick(ClickEvent event) {
		
        
        AccountsApplication.getInstance().
			getMainWindow().showNotification(n);
        
        switch((String)event.getButton().getData()) {
			case LANG:
				AccountsApplication.getInstance().
					getMainWindow().addWindow(new LanguageSelectDialog());
				break;
			case GEN_SETTINGS:
                AccountsApplication.getInstance().
					getMainWindow().showNotification(n);

				break;
			case SIGNOUT:
				AccountsApplication.getInstance().
					getMainWindow().showNotification(n);
				break;
		}
	}
}