package com.mooneyserver.account.messaging;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.mooneyserver.account.logging.AccountsLoggingConstants;
import com.mooneyserver.account.messaging.iface.ISupportedMessage;
import com.mooneyserver.account.messaging.iface.IUserActivationMessage;
import com.mooneyserver.account.messaging.iface.IUserChangePasswordMessage;

public class GenericJmsDispatcher {
	
	private static Logger log = Logger.getLogger(AccountsLoggingConstants.LOG_AREA_BACKEND);
	
    private static final String DEFAULT_CONNECTION_FACTORY = "java:/ConnectionFactory";
    private static final HashMap<Class<? extends ISupportedMessage>, String> 
    	queueDestinations = new HashMap<>();
    
    static {
    	queueDestinations.put(IUserActivationMessage.class, "java:/queue/activationMail");
    	queueDestinations.put(IUserChangePasswordMessage.class, "java:/queue/resetPassword");
    }

    public static void sendMessage(ISupportedMessage msg) {
        Connection connection = null;
        Context context = null;

        String MAIL_QUEUE = getQueueForMessage(msg);
        if (MAIL_QUEUE == null) {
        	log.log(Level.SEVERE, "Message pushing failed. No supported queue");
        	return;
        }
        
        try {
            // Set up the context for the JNDI lookup
            context = new InitialContext();

            // Perform the JNDI lookups
            ConnectionFactory connectionFactory = (ConnectionFactory) context.lookup(DEFAULT_CONNECTION_FACTORY);
            Destination destination = (Destination) context.lookup(MAIL_QUEUE);

            // Create the JMS connection, session, producer, and consumer
            connection = connectionFactory.createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer producer = session.createProducer(destination);
            connection.start();

            // Send the specified number of messages
            ObjectMessage message = session.createObjectMessage(msg);
            producer.send(message);

        } catch (NamingException | JMSException e) {
        	log.log(Level.SEVERE, "Message pushing failed.", e);
        } finally {
            try {
            	if (context != null) {
                    context.close();
                }

                // closing the connection takes care of the session, producer, and consumer
                if (connection != null) {
                    connection.close();
                }
            } catch (NamingException | JMSException e) {
            	log.log(Level.WARNING, "Errors thrown in resource closure", e);
            }
        }
    }

    private static String getQueueForMessage(ISupportedMessage msg) {
    	for (Class<? extends ISupportedMessage> clazz : queueDestinations.keySet()) {
    		if (clazz.isInstance(msg)) {
    			return queueDestinations.get(clazz);
    		}
    	}

    	log.log(Level.WARNING, "Queue Lookup Failed for obj of type ["+msg.getClass()+"]");
    	return null;
    }
}