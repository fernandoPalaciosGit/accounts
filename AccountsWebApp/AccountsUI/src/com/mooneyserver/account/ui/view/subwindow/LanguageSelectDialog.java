package com.mooneyserver.account.ui.view.subwindow;

import com.mooneyserver.account.ui.AccountsApplication;
import com.mooneyserver.account.ui.i18n.AccountsMessages;
import com.mooneyserver.account.ui.i18n.SupportedLanguage;
import com.mooneyserver.account.ui.widget.SpinnerList;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import static com.mooneyserver.account.ui.i18n.SupportedLanguage.SUPPORTED_LANGS;

public class LanguageSelectDialog extends Window implements ClickListener {
	
	private static final long serialVersionUID = 1L;
	private final SpinnerList langList;
	
	public LanguageSelectDialog() {
		// TODO: Subclass to hide this
		super(AccountsApplication.getResourceBundle().getString(AccountsMessages.SELECT_LANGUAGE));
		
		this.setModal(true);
		this.setBorder(Window.BORDER_NONE);
		this.setResizable(false);
		this.setWidth("250px");
		
		VerticalLayout vl = (VerticalLayout) this.getContent();
		vl.setSpacing(true);
		
		langList = new SpinnerList();
		for (String lang : SUPPORTED_LANGS.keySet()) {
			langList.addItem(lang, SUPPORTED_LANGS.get(lang).getIcon());
		}
		
		langList.setSelectedItem(AccountsApplication.getInstance().getLocale());
		langList.addListener((ClickListener) this);
		langList.setImmediate(true);
		
		vl.addComponent(langList);
	}

	@Override
	public void buttonClick(ClickEvent event) {
		// Print statements required or else the page refresh doesnt happen???
		System.out.println("Changing locale to ["+langList.getValue()+"]");
		SupportedLanguage currLocale = SUPPORTED_LANGS.get(langList.getValue());
		System.out.println("Trying to set ["+currLocale.getLocale()+"]");
		AccountsApplication.getInstance().setLocale(currLocale.getLocale());
	}
}