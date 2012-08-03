package com.mooneyserver.account.i18n;

import java.util.HashMap;
import java.util.Map;
import java.util.Locale;

import com.mooneyserver.account.ui.view.IconManager;
import com.vaadin.terminal.ThemeResource;

public class SupportedLanguage {

	@SuppressWarnings("serial")
	public static final Map<String, SupportedLanguage> SUPPORTED_LANGS 
		= new HashMap<String, SupportedLanguage>() {
			{
				put("English", new SupportedLanguage(Locale.ENGLISH, 
						IconManager.getIcon(IconManager.IRISH_FLAG_SMALL))); 
				put("中文", new SupportedLanguage(Locale.CHINESE, 
						IconManager.getIcon(IconManager.CHINA_FLAG_SMALL))); // Trad Chinese
			}
	};
	
	private Locale locale;
	private ThemeResource icon;
	
	
	public SupportedLanguage(Locale locale, ThemeResource icon) {
		this.locale = locale;
		this.icon = icon;
	}
	
	public SupportedLanguage(Locale locale, String icon) {
		this.locale = locale;
		this.icon = new ThemeResource(icon);
	}
	
	
	public Locale getLocale() {
		return locale;
	}
	
	public ThemeResource getIcon() {
		return icon;
	}
}
