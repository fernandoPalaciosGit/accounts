package com.mooneyserver.account.ui.view.subwindow.user;

import com.mooneyserver.account.i18n.AccountsMessages;
import com.mooneyserver.account.ui.view.subwindow.BaseSubwindow;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class CreateNewUserDialog extends BaseSubwindow implements ClickListener {
	
	private static final long serialVersionUID = 1L;
	
	public CreateNewUserDialog() {
		super(AccountsMessages.SELECT_LANGUAGE);
		
		setWidth("380px");
		
		VerticalLayout vl = (VerticalLayout) this.getContent();
		FormLayout fl = new FormLayout();
		fl.setSpacing(true);
		
		TextField firstName = new TextField("First Name");
		TextField lastName = new TextField("Last Name");
		TextField email = new TextField("E-mail");
		
		PasswordField pwd1 = new PasswordField("Password");
		PasswordField pwd2 = new PasswordField("Confirm Password");
		
		Button create = new Button("Create the mofo!");
		create.addListener(this);
		
		fl.addComponent(firstName);
		fl.addComponent(lastName);
		fl.addComponent(email);
		fl.addComponent(pwd1);
		fl.addComponent(pwd2);
		fl.addComponent(create);
		
		fl.setComponentAlignment(create, Alignment.BOTTOM_RIGHT);
		vl.addComponent(fl);
		
		setIcon(new ThemeResource("img/user_add_large_.png"));
	}

	@Override
	public void buttonClick(ClickEvent event) {
		System.out.println("Creating the user");
		close();
	}
}