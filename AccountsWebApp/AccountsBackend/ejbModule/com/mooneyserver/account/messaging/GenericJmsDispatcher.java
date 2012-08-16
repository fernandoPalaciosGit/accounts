package com.mooneyserver.account.messaging;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;

import com.mooneyserver.account.messaging.iface.ISupportedMessage;

public class GenericJmsDispatcher {
	// Set up all the default values
    private static final String DEFAULT_CONNECTION_FACTORY = "java:/ConnectionFactory";
    private static final String ACTIVATION_MAIL_QUEUE = "java:/queue/activationMail";

    public static void sendMessage(ISupportedMessage msg) {

        ConnectionFactory connectionFactory = null;
        Connection connection = null;
        Session session = null;
        MessageProducer producer = null;
        Destination destination = null;
        ObjectMessage message = null;
        Context context = null;

        try {
            // Set up the context for the JNDI lookup
            context = new InitialContext();

            // Perform the JNDI lookups
            connectionFactory = (ConnectionFactory) context.lookup(DEFAULT_CONNECTION_FACTORY);
            destination = (Destination) context.lookup(ACTIVATION_MAIL_QUEUE);

            // Create the JMS connection, session, producer, and consumer
            connection = connectionFactory.createConnection();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            producer = session.createProducer(destination);
            connection.start();

            // Send the specified number of messages
            message = session.createObjectMessage(msg);
            producer.send(message);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
            	if (context != null) {
                    context.close();
                }

                // closing the connection takes care of the session, producer, and consumer
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
            	// Blah
            }
        }
    }
}