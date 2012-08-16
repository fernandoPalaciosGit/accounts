package com.mooneyserver.account.utils.settings;

public class SettingsKeys {
	// Settings related to Encryption
	public static final String ENCRYPTION_KEY = "accounts.encryption.type";
	
	// Settings relating to system mails
	public static final String SMTP_USER = "accounts.smtp.user";
	public static final String SMTP_PASSWORD = "accounts.smtp.password";
	public static final String SMTP_HOST = "accounts.smtp.server";
	public static final String SMTP_PORT = "accounts.smtp.server.port";
	public static final String SMTP_MAIL_FROM = "accounts.smtp.mail.from_addr";
	public static final String SMTP_MAIL_ACTIVATION_SUBJECT = "accounts.smtp.mail.template.user_actv.subject";
	public static final String SMTP_MAIL_ACTIVATION_BODY = "accounts.smtp.mail.template.user_actv.body";
	
	// Application Settings (Global)
	public static final String APP_HOST_URL = "accounts.app.server.url";
}
