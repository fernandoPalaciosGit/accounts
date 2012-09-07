package com.mooneyserver.account.ui.view.main;

import org.vaadin.aboutbox.AboutBox;

import com.mooneyserver.account.AccountsApplication;
import com.mooneyserver.account.ui.notification.Messenger;
import com.mooneyserver.account.ui.view.subwindow.toolbar.LanguageSelectDialog;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

class BaseWindowClickListener implements ClickListener,
		org.vaadin.aboutbox.AboutBox.ClickListener {

	private static final long serialVersionUID = 3276588169214968557L;
    
    public BaseWindowClickListener() {}
    
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
		switch((String)event.getButton().getData()) {
			case LANG:
				AccountsApplication.getInstance().
					getMainWindow().addWindow(new LanguageSelectDialog());
				break;
			case GEN_SETTINGS:
                Messenger.notYetImplemented();
				break;
			case SIGNOUT:
				AccountsApplication.getInstance().setUser(null);
				//AccountsApplication.getInstance().displayMgr.closeAllViews();
				// TODO: Do a logout logic
				break;
		}
	}
}