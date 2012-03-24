package com.home.accounts.subwindow;

import com.vaadin.terminal.ExternalResource;
import com.vaadin.ui.Link;
import com.vaadin.ui.LoginForm;
import com.vaadin.ui.LoginForm.LoginEvent;
import com.vaadin.ui.Window;

public class LoginPrompt extends Window {
	
	private static final long serialVersionUID = -2005741281198510738L;
	private LoginForm login;
	private Link getMeOutOfHere = null;
	
	private int failedLogin = 0;

	
	@SuppressWarnings("serial")
	public LoginPrompt() {
        setModal(true);
        setClosable(false);

        setWidth("300px");        
        center();
        
        setCaption("Login Form");
        
        login = new LoginForm();
        
        login.addListener(new LoginForm.LoginListener() {
			public void onLogin(LoginEvent event) {
				if (validateLogin(event.getLoginParameter("username"), event.getLoginParameter("password")))
            		closeFormWindow();
            }
        });
        
        addComponent(login);
        
        getMeOutOfHere = new Link("Leave Accounts App", 
				new ExternalResource("http//:mooney-server.dyndns-at-work.com"));
        addComponent(getMeOutOfHere);
        getMeOutOfHere.setVisible(false);
        
    }
	
	
	private boolean validateLogin(String name, String pwd) {
		if (name.equals("bmooney") && pwd.equals("password"))
			return true;
		else {
			failedLogin++;
			if (failedLogin == 1)
				showRedirectLink();
			
			return false;
		}
	}
	
	
	private void showRedirectLink() {
		getMeOutOfHere.setVisible(true);
	}
	
	
	private void closeFormWindow() {
		System.out.println("Clearing Form Window");
		getApplication().removeWindow(getWindow());
		this.close();
	}
}
