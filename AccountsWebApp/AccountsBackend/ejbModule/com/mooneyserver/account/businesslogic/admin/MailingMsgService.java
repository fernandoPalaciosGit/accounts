package com.mooneyserver.account.businesslogic.admin;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.mooneyserver.account.logging.AccountsLoggingConstants;
import com.mooneyserver.account.messaging.iface.IUserActivationMessage;
import com.mooneyserver.account.utils.settings.SettingsKeys;

import static com.mooneyserver.account.utils.settings.SystemSettings.SETTINGS;

/**
 * Message-Driven Bean implementation class for: MailingMsgService
 *
 */
@MessageDriven(
		activationConfig = { 
				@ActivationConfigProperty(propertyName = "destinationType", 
						propertyValue = "javax.jms.Queue"),
				@ActivationConfigProperty(propertyName = "destination", 
						propertyValue = "queue/activationMail")
		}, 
		mappedName = "java:/queue/activationMail")
public class MailingMsgService implements MessageListener {

	private static Logger log = Logger.getLogger(AccountsLoggingConstants.LOG_AREA_BACKEND);
	
	private Session mailSession;
	
	/*
	 * Too Tightly linked to gmail solution. Need to revisit
	 * At the moment, with this setup I can only use mail servers which 
	 * require TLS authentication...
	 */
    public MailingMsgService() {
    	if (mailSession == null) {
    		final String username = SETTINGS.getProp(SettingsKeys.SMTP_USER); 
    		final String password = SETTINGS.getProp(SettingsKeys.SMTP_PASSWORD);
     
    		Properties props = new Properties();
    		props.put("mail.smtp.auth", "true");
    		props.put("mail.smtp.starttls.enable", "true");
    		props.put("mail.smtp.host", SETTINGS.getProp(SettingsKeys.SMTP_HOST));
    		props.put("mail.smtp.port", SETTINGS.getProp(SettingsKeys.SMTP_PORT));
     
    		mailSession = Session.getInstance(props,
    				new javax.mail.Authenticator() {
    					protected PasswordAuthentication getPasswordAuthentication() {
    						return new PasswordAuthentication(username, password);
    					}
    		  		});
    	}
    }
	
	/**
     * If an activation request is pushed onto the activationMail queue
     * then we will pick up that request here and send a mail to the user
     * requesting they activate their account
     */
    public void onMessage(Message message) {
    	if (!(message instanceof ObjectMessage)) {
    		log.log(Level.WARNING, "Received Message is not an Object Message");
    		return;
    	}
    	ObjectMessage objMessage = (ObjectMessage) message;
    	
    	
    	IUserActivationMessage userActMsg = null;
    	try {
    		if (!(objMessage.getObject() instanceof IUserActivationMessage)) {
    			log.log(Level.WARNING, "Received Message is a User Activation Messsage");
    			return;
    		} else {
    			userActMsg = (IUserActivationMessage) objMessage.getObject();
    		}
    	} catch (JMSException e) {
    		log.log(Level.SEVERE, "Failure trying to read object from message", e);
    	}
    	 
    	
    	try {
			MimeMessage m = new MimeMessage(mailSession);
			Address from = new InternetAddress (SETTINGS.getProp(SettingsKeys.SMTP_MAIL_FROM));
			Address[] to = new InternetAddress[] {new InternetAddress(userActMsg.getToAddress())};
			
			m.setFrom(from);
			m.setRecipients(javax.mail.Message.RecipientType.TO, to);
			m.setSubject(SETTINGS.getProp(SettingsKeys.SMTP_MAIL_ACTIVATION_SUBJECT));
			m.setContent(formatMailBody(userActMsg), "text/html");
			
			Transport.send(m);
			
			log.log(Level.INFO, "Activation email has been dispatched to ["+userActMsg.getToAddress()+"]");
		}
		catch (MessagingException e) {
			log.log(Level.SEVERE, "Unable to send mail", e);
		}
    }
    
    private String formatMailBody(IUserActivationMessage msg) {
    	String mailBody = SETTINGS.getProp(SettingsKeys.SMTP_MAIL_ACTIVATION_BODY);
    	
    	mailBody = mailBody.replace("%FIRSTNAME%", msg.getFirstName());
    	mailBody = mailBody.replace("%ACCOUNTS_HOST%", SETTINGS.getProp(SettingsKeys.APP_HOST_URL));
    	mailBody = mailBody.replace("%LINK%", generateLink(msg.getToAddress()));
    	
    	return mailBody;
    }
    
    // TODO: Need to do this!
    // Possibly a WebService call link
    private String generateLink(String toAddress) {
    	return "<a>" + SETTINGS.getProp(SettingsKeys.APP_HOST_URL) 
    			+ "/activate?uid=ABCDEFGHIJKLM123</a>";
    }
}