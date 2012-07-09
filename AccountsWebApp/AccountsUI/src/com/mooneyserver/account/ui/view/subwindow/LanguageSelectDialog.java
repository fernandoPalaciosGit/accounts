package com.mooneyserver.account.ui.view.subwindow;

import java.util.Locale;

import com.mooneyserver.account.ui.AccountsApplication;
import com.mooneyserver.account.ui.i18n.AccountsMessages;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import static com.mooneyserver.account.ui.i18n.SupportedLanguages.SUPPORTED_LANGS;

public class LanguageSelectDialog extends Window implements ValueChangeListener {
	
	private static final long serialVersionUID = 1L;
	
	public LanguageSelectDialog() {
		// TODO: Subclass to hide this
		super(AccountsApplication.getResourceBundle().getString(AccountsMessages.SELECT_LANGUAGE));
		
		this.setModal(true);
		this.setBorder(Window.BORDER_NONE);
		this.setResizable(false);
		this.setWidth("235px");
		
		VerticalLayout vl = (VerticalLayout) this.getContent();
		vl.setSpacing(true);
		
		ComboBox langSelect = new ComboBox();
		langSelect.setNullSelectionAllowed(false);
		langSelect.setImmediate(true);
		langSelect.addListener(this);
		
		for (String lang : SUPPORTED_LANGS.keySet()) {
			langSelect.addItem(lang);
			langSelect.setItemIcon(lang, 
					new ThemeResource((String) SUPPORTED_LANGS.get(lang)[1]));
		}
		
		Locale currLocale = AccountsApplication.getInstance().getLocale();	
		String selected = currLocale.getDisplayLanguage();
		System.out.println(selected); // TODO: This is a bug. But I need to go to bed.
									  // selected shows chinese instead of the characters
									  // So default to chinese does not work.
		langSelect.setValue(selected);
		
		vl.addComponent(langSelect);
	}

	@Override
	public void valueChange(ValueChangeEvent event) {
		String lang = (String) event.getProperty().getValue();
		if (lang == null)
			return;
		
		AccountsApplication.getInstance().setLocale(
				(Locale) SUPPORTED_LANGS.get(lang)[0]);
	}
}
