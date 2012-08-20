package com.mooneyserver.account.ui.view.user;

import java.util.logging.Level;

import com.mooneyserver.account.AccountsApplication;
import com.mooneyserver.account.businesslogic.user.IUserService;
import com.mooneyserver.account.i18n.AccountsMessages;
import com.mooneyserver.account.lookup.BusinessProcess;
import com.mooneyserver.account.ui.manager.IconManager;
import com.mooneyserver.account.ui.notification.Messenger;
import com.mooneyserver.account.ui.notification.Messenger.MessageSeverity;
import com.mooneyserver.account.ui.view.AbstractBaseView;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.BaseTheme;

public class AccountActivationView extends AbstractBaseView implements Button.ClickListener {
	
	private static final long serialVersionUID = 1L;

	@BusinessProcess
	private static IUserService userSvc;
	
	private Panel activationPanel;
	private Button activateMyUserButton;
	private String activationId;
	
	public AccountActivationView(String activationId) {
		this.activationId = activationId;
		constructHeader();
		
		activationPanel = new Panel();
		activationPanel.setImmediate(true);
		activationPanel.setWidth("400px");
		
		VerticalLayout vl = new VerticalLayout();
		activateMyUserButton = new Button();
		activateMyUserButton.setStyleName(BaseTheme.BUTTON_LINK);
		activateMyUserButton.setIcon(IconManager.getIcon(IconManager.ACTIVATE_USER));
		activateMyUserButton.addListener(this);
		
		vl.addComponent(activateMyUserButton);
		vl.setComponentAlignment(activateMyUserButton, Alignment.MIDDLE_CENTER);
		
		activationPanel.addComponent(vl);
		
		addComponent(activationPanel);
		setComponentAlignment(activationPanel, Alignment.MIDDLE_CENTER);
		setExpandRatio(activationPanel, 1);
		
		constructFooter();
		
		buildStringsFromLocale();
	}
	
	@Override
	public void buildStringsFromLocale() {
		STRINGS = AccountsApplication.getResourceBundle();
		
		activateMyUserButton.setCaption(STRINGS.getString(AccountsMessages.ACTIVATE_USER));
	}

	@Override
	public void buttonClick(ClickEvent event) {
		// TODO: Localise
		if (userSvc.markUserActive(this.activationId)) {
			String baseUrl = AccountsApplication.getInstance().getURL().toString().split("\\?")[0];
			AccountsApplication.getInstance().getMainWindow().open(new ExternalResource(baseUrl + "?restartApplication"));
		} else {
			log.log(Level.WARNING, "Warning thrown while trying to activate with ["+this.activationId+"]");
			AccountsApplication.setCurrentView(new AccountsLoginView());
			Messenger.genericMessage(MessageSeverity.WARNING, "There has been an issue with User Activation");
		}
	}
}