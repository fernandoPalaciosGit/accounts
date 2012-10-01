package com.mooneyserver.account.businesslogic.admin;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.mail.MessagingException;

import com.mooneyserver.account.logging.AccountsLoggingConstants;
import com.mooneyserver.account.messaging.iface.IUserActivationMessage;
import com.mooneyserver.account.persistence.entity.AccountsUser;
import com.mooneyserver.account.persistence.entity.UserActivation;
import com.mooneyserver.account.persistence.service.user.UserActivationService;
import com.mooneyserver.account.persistence.service.user.UserService;
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
						propertyValue = "queue/activationMail"),
				@ActivationConfigProperty(propertyName = "subscriptionDurability",
			            propertyValue = "NonDurable")
		}, 
		mappedName = "java:/queue/activationMail")
public class ActivationMailMsgService implements MessageListener {

	private static Logger log = Logger.getLogger(AccountsLoggingConstants.LOG_AREA_BACKEND);
	
	@EJB
	private UserService userService;
	
	@EJB
	private UserActivationService userActivationService;
	
    public ActivationMailMsgService() {}
	
	/**
     * If an activation request is pushed onto the activationMail queue
     * then we will pick up that request here and send a mail to the user
     * requesting they activate their account
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
    		throw new RuntimeException("Wrapping Failed Activation Mail", e);
    	}
    	 
    	try {
			Mailer.sendMail(userActMsg.getToAddress(), 
					SETTINGS.getProp(SettingsKeys.SMTP_MAIL_ACTIVATION_SUBJECT), 
					formatMailBody(userActMsg));
		} catch (MessagingException e) {
			log.log(Level.SEVERE, "Failure trying to send Activation Mail!", e);
			throw new RuntimeException("Wrapping Failed Activation Mail", e);
		}
    }
    
    private String formatMailBody(IUserActivationMessage msg) {
    	String mailBody = SETTINGS.getProp(SettingsKeys.SMTP_MAIL_ACTIVATION_BODY);
    	
    	mailBody = mailBody.replace("%FIRSTNAME%", msg.getFirstName());
    	mailBody = mailBody.replace("%ACCOUNTS_HOST%", SETTINGS.getProp(SettingsKeys.APP_HOST_URL));
    	mailBody = mailBody.replace("%LINK%", generateLink(msg.getToAddress()));
    	
    	return mailBody;
    }
    
    private String generateLink(String toAddress) {
    	SecureRandom secRand = new SecureRandom();
    	String uniqueActivationId = new BigInteger(130, secRand).toString(32);
    	
    	AccountsUser user = userService.findByUsername(toAddress);
    	UserActivation userActivate = new UserActivation();
    	userActivate.setAccountsUser(user);
    	userActivate.setUserActivationId(uniqueActivationId);
    	
    	userActivationService.create(userActivate);
    	
    	return "<a>" + SETTINGS.getProp(SettingsKeys.APP_HOST_URL) 
    			+ "/?restartApplication&userActivationId="+uniqueActivationId+"</a>";
    }
}