package com.mooneyserver.account.businesslogic.admin;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.mail.MessagingException;

import com.mooneyserver.account.logging.AccountsLoggingConstants;
import com.mooneyserver.account.messaging.iface.IUserChangePasswordMessage;
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
						propertyValue = "queue/resetPassword")
		}, 
		mappedName = "java:/queue/resetPassword")
public class PasswordResetMailMsgService implements MessageListener {

	private static Logger log = Logger.getLogger(AccountsLoggingConstants.LOG_AREA_BACKEND);
	
	
    public PasswordResetMailMsgService() {}
	
	/**
     * If an password reset request is made. A mail will be generated
     * and sent to the user with the new password.
     */
    public void onMessage(Message message) {
    	// FUCKIN UGLY!
    	// Hate this but I cant figure out how clear
    	// In Delivery messages from JBoss otherwise!
    	while (!SETTINGS.areSettingsInitialised()) {
    		try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// Gobble Gobble
			}
    	}
    	
    	if (!(message instanceof ObjectMessage)) {
    		log.log(Level.WARNING, "Received Message is not an Object Message");
    		return;
    	}
    	ObjectMessage objMessage = (ObjectMessage) message;
    	
    	
    	IUserChangePasswordMessage userActMsg = null;
    	try {
    		if (!(objMessage.getObject() instanceof IUserChangePasswordMessage)) {
    			log.log(Level.WARNING, "Received Message is not a User Change Password Messsage");
    			return;
    		} else {
    			userActMsg = (IUserChangePasswordMessage) objMessage.getObject();
    		}
    	} catch (JMSException e) {
    		log.log(Level.SEVERE, "Failure trying to read object from message", e);
    		throw new RuntimeException("Wrapping Failed Password Reset Mail", e);
    	}
    	 
    	try {
			Mailer.sendMail(userActMsg.getToAddress(), 
					SETTINGS.getProp(SettingsKeys.SMTP_MAIL_PWD_RESET_SUBJECT), 
					formatMailBody(userActMsg));
		} catch (MessagingException e) {
			log.log(Level.SEVERE, "Failure trying to send Password Reset Mail", e);
			throw new RuntimeException("Wrapping Failed Password Reset Mail", e);
		}
    }
    
    private String formatMailBody(IUserChangePasswordMessage msg) {
    	String mailBody = SETTINGS.getProp(SettingsKeys.SMTP_MAIL_PWD_RESET_BODY);
    	
    	mailBody = mailBody.replace("%FIRSTNAME%", msg.getFirstName());
    	mailBody = mailBody.replace("%NEW_PASSWORD%", msg.getNewPassword());
    	
    	return mailBody;
    }
}