package com.mooneyserver.account.ui.view.subwindow.toolbar;

import com.mooneyserver.account.i18n.AccountsMessages;
import com.mooneyserver.account.i18n.SupportedLanguage;
import com.mooneyserver.account.AccountsApplication;
import com.mooneyserver.account.ui.manager.IconManager;
import com.mooneyserver.account.ui.view.subwindow.BaseSubwindow;
import com.mooneyserver.account.ui.widget.SpinnerList;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.VerticalLayout;

import static com.mooneyserver.account.i18n.SupportedLanguage.SUPPORTED_LANGS;

public class LanguageSelectDialog extends BaseSubwindow implements ClickListener {
	
	private static final long serialVersionUID = 1L;
	private final SpinnerList langList;
	
	public LanguageSelectDialog() {
		super(AccountsMessages.SELECT_LANGUAGE);
		
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
		
		setIcon(IconManager.getIcon(IconManager.SETTINGS_CHANGE_LANG_LARGE));
	}

	@Override
	public void buttonClick(ClickEvent event) {
		SupportedLanguage currLocale = SUPPORTED_LANGS.get(langList.getValue());
		AccountsApplication.getInstance().setLocale(currLocale.getLocale());
	}
}