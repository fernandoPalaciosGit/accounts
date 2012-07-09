package com.mooneyserver.account.ui.i18n;

import java.util.HashMap;
import java.util.Map;
import java.util.Locale;

public class SupportedLanguages {

	@SuppressWarnings("serial")
	public static final Map<String, Object[]> SUPPORTED_LANGS 
		= new HashMap<String, Object[]>() {
			{
				put("English", new Object[] {Locale.ENGLISH, "img/irish_flag.png"}); 
				put("中文", new Object[] {Locale.CHINESE, "img/china_flag.png"}); // Trad Chinese
			}
	};
}
