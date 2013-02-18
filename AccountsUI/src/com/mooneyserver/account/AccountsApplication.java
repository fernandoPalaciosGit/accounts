package com.mooneyserver.account;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.vaadin.jonatan.contexthelp.ContextHelp;

import com.mooneyserver.account.i18n.AccountsMessages;
import com.mooneyserver.account.ui.iface.IAccountsView;
import com.mooneyserver.account.ui.manager.Navigation;
import com.mooneyserver.account.ui.view.main.MainView;
import com.mooneyserver.account.ui.view.main.user.AccountActivationView;
import com.mooneyserver.account.ui.view.main.user.AccountsLoginView;

import com.vaadin.Application;
import com.vaadin.terminal.ParameterHandler;
import com.vaadin.terminal.gwt.server.HttpServletRequestListener;
import com.vaadin.ui.Window;

public final class AccountsApplication extends Application implements HttpServletRequestListener {


	private static final long serialVersionUID 
		= -1L;
	
	/* Set a specific instance of the App for the connected 
	 * session 
	 */
	private static ThreadLocal<AccountsApplication> sessionInstance = 
			new ThreadLocal<>();
	
	/* Internationalisation Resource Bundle for Strings */
	private ResourceBundle i18nBundle;
	private ContextHelp helpBubble;
	
	public Navigation nav;
	
	Window mainWindow;
	MainView mainView;
	
	
	public static final String APP_VERSION = "0.0.1";
	
	/* Constructor */
	public AccountsApplication() {
		sessionInstance.set(this);
	}	
	
	@Override
	public void init() {
		setTheme("accounts");
		
		i18nBundle = ResourceBundle.getBundle(
				AccountsMessages.class.getName(), getLocale());
		mainWindow = new Window(i18nBundle.getString(
				AccountsMessages.APP_TITLE));
		setMainWindow(mainWindow);
		
		helpBubble = new ContextHelp();
		nav = new Navigation();
		mainView = new MainView();
		
		
		mainWindow.setContent(mainView);
		
		// Perform activation if required
		mainWindow.addParameterHandler(new ParameterHandler() {
			private boolean isActivating = false;
			private String activationVal;
			
			private static final long serialVersionUID = 1L;

			@Override
			public void handleParameters(Map<String, String[]> parameters) {
				for (String key : parameters.keySet()) {
					if (key.equals("userActivationId")) {
						activationVal = parameters.get(key)[0];
						isActivating = true;
					}
				}
				
				// Param Handler is called multiple times for some reason
				// Make sure we only load one view!
				if (nav.getViewCount() < 1) {
					if (isActivating)
						nav.loadNewView(new AccountActivationView(activationVal.toString()));
					else
						nav.loadNewView(new AccountsLoginView());
				}
			}
		});
	}
	
	public static void setDynamicContent(IAccountsView view) {
		sessionInstance.get().mainView.setDynamicContent(view);
	}
	
	public static void refreshMainView() {
		sessionInstance.get().mainView.refreshView();
	}
	
	public static ResourceBundle getResourceBundle() {
		return sessionInstance.get().i18nBundle;
	}
	
	public static ContextHelp getHelpBubble() {
        return sessionInstance.get().helpBubble;
    }
	
	public static AccountsApplication getInstance() {
		return sessionInstance.get();
	}

	@Override
	public void setLocale(Locale locale) {
		super.setLocale(locale);
		i18nBundle = ResourceBundle.getBundle(
				AccountsMessages.class.getName(), getLocale());
		if (nav != null)
			nav.refreshContent();
	}
	
	@Override
	public void onRequestStart(HttpServletRequest request,
			HttpServletResponse response) {
		sessionInstance.set(this);	
	}


	@Override
	public void onRequestEnd(HttpServletRequest request,
			HttpServletResponse response) {
		sessionInstance.remove();
	}
}