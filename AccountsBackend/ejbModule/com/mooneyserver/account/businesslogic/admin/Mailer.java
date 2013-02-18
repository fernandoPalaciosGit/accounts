package com.mooneyserver.account.businesslogic.admin;

import static com.mooneyserver.account.utils.settings.SystemSettings.SETTINGS;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.mooneyserver.account.logging.AccountsLoggingConstants;
import com.mooneyserver.account.utils.settings.SettingsKeys;

class Mailer {
	
	private static Logger log = Logger.getLogger(AccountsLoggingConstants.LOG_AREA_BACKEND);
	
	private Session mailSession;
	
	private Mailer() {
		final String username = SETTINGS.getProp(SettingsKeys.SMTP_USER); 
    	final String password = SETTINGS.getProp(SettingsKeys.SMTP_PASSWORD);
     
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", SETTINGS.getProp(SettingsKeys.SMTP_HOST));
		props.put("mail.smtp.port", SETTINGS.getProp(SettingsKeys.SMTP_PORT));
		props.put("mail.smtp.timeout", SETTINGS.getProp(SettingsKeys.SMTP_MAIL_TIMEOUT));
	    props.put("mail.smtp.connectiontimeout", SETTINGS.getProp(SettingsKeys.SMTP_MAIL_TIMEOUT));
 
		mailSession = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
		  		});
	}
	
	public static void sendMail(String toAddress, String subject, String msgBody) throws MessagingException {
		Mailer mailer = new Mailer();
		
		try {
			MimeMessage m = new MimeMessage(mailer.mailSession);
			Address from = new InternetAddress (SETTINGS.getProp(SettingsKeys.SMTP_MAIL_FROM));
			Address[] to = new InternetAddress[] {new InternetAddress(toAddress)};
			
			m.setFrom(from);
			m.setRecipients(javax.mail.Message.RecipientType.TO, to);
			m.setSubject(subject);
			m.setContent(msgBody, "text/html");
			
			Transport.send(m);
			
			log.log(Level.INFO, "["+subject+"] Email has been dispatched to ["+toAddress+"]");
		}
		catch (MessagingException e) {
			log.log(Level.SEVERE, "Unable to send mail", e);
			throw e;
		}
	}
}
