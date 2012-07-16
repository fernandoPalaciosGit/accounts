package com.mooneyserver.account.ui.view.subwindow.user;

import com.mooneyserver.account.ui.view.subwindow.BaseSubwindow;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class ResetPasswordDialog extends BaseSubwindow implements ClickListener {
	
	private static final long serialVersionUID = 1L;
	
	public ResetPasswordDialog() {
		super("Reset Password");
		
		setWidth("250px");
		
		VerticalLayout vl = (VerticalLayout) this.getContent();
		vl.setSpacing(true);
		
		TextField email = new TextField("E-mail Address");
		Button sendNewPassword = new Button("Send Password");
		sendNewPassword.addListener(this);
		
		vl.addComponent(email);
		vl.addComponent(sendNewPassword);
		
		setIcon(new ThemeResource("img/forgot_password_large.png"));
	}

	@Override
	public void buttonClick(ClickEvent event) {
		System.out.println("Email sent to the forgetful fool!");
	}
}