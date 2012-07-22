package com.mooneyserver.account.lookup;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.mooneyserver.account.businesslogic.user.IUserService;

// TODO: Need to change this to act as a generic
// business service loader using JNDI.
// Am hoping I can create a custom annotaion to 
// do the job
public class BackendServiceLookup {
	
	static Properties props = new Properties();
	static InitialContext ctx;
	
	static {
		props.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.enterprise.naming.SerialInitContextFactory");
		props.setProperty("org.omg.CORBA.ORBInitialHost", "localhost");
		
		// glassfish default port value will be 3700,
		props.setProperty("org.omg.CORBA.ORBInitialPort", "3700");
		
		try {
			ctx = new InitialContext(props);
		} catch (NamingException e) {
			System.err.println("JNDI Lookup error");
			e.printStackTrace();
		}
	}
	 

	public static IUserService getuserService() {
		try {
			return (IUserService) ctx.lookup("java:global/Accounts/AccountsBackend/UserBusinessService" +
					"!com.mooneyserver.account.businesslogic.user.IUserService");
		} catch (NamingException e) {
			System.err.println("JNDI Lookup error");
			e.printStackTrace();
			return null;
		}
	}
}

