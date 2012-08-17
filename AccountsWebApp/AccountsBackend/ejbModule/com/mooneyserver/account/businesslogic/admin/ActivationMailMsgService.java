package com.mooneyserver.account.businesslogic.admin;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

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
public class ActivationMailMsgService implements MessageListener {

	private static Logger log = Logger.getLogger(AccountsLoggingConstants.LOG_AREA_BACKEND);
	
	
    public ActivationMailMsgService() {}
	
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
    	 
    	Mailer.sendMail(userActMsg.getToAddress(), 
    			SETTINGS.getProp(SettingsKeys.SMTP_MAIL_ACTIVATION_SUBJECT), 
    			formatMailBody(userActMsg));
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