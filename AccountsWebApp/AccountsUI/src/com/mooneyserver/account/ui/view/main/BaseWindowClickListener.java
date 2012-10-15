package com.mooneyserver.account.ui.view.main;

import org.vaadin.aboutbox.AboutBox;

import com.mooneyserver.account.AccountsApplication;
import com.mooneyserver.account.businesslogic.exception.user.AccountsUserException;
import com.mooneyserver.account.businesslogic.user.IUserService;
import com.mooneyserver.account.i18n.AccountsMessages;
import com.mooneyserver.account.lookup.BackendServiceLookup;
import com.mooneyserver.account.lookup.BusinessProcess;
import com.mooneyserver.account.persistence.entity.AccountsUser;
import com.mooneyserver.account.ui.iface.IContainsCustomAnnotations;
import com.mooneyserver.account.ui.notification.Messenger;
import com.mooneyserver.account.ui.notification.Messenger.MessageSeverity;
import com.mooneyserver.account.ui.view.subwindow.toolbar.LanguageSelectDialog;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

class BaseWindowClickListener implements ClickListener,
		org.vaadin.aboutbox.AboutBox.ClickListener, IContainsCustomAnnotations {

	private static final long serialVersionUID = 3276588169214968557L;
    
	@BusinessProcess
	private static IUserService userSvc;
	
    public BaseWindowClickListener() {
    	loadBackendServices();
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
		switch((String)event.getButton().getData()) {
			case LANG:
				AccountsApplication.getInstance().
					getMainWindow().addWindow(new LanguageSelectDialog());
				break;
			case GEN_SETTINGS:
                Messenger.notYetImplemented();
				break;
			case SIGNOUT:
				try {
					userSvc.userLogout((AccountsUser) AccountsApplication.getInstance().getUser());
					AccountsApplication.getInstance().setUser(null);
					AccountsApplication.getInstance().nav.endSession();
				} catch (AccountsUserException e) {
					Messenger.genericMessage(MessageSeverity.ERROR, 
							AccountsApplication.getResourceBundle()
								.getString(AccountsMessages.MSGR_UNRECOVERABLE_ERROR),
							"Exception Thrown For UI when trying to logout",
							e);
				}
				break;
		}
	}

	@Override
	public void loadBackendServices() {
		BackendServiceLookup.injectBackendServices(this);
	}
}