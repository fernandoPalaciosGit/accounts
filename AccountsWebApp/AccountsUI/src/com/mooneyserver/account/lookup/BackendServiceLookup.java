package com.mooneyserver.account.lookup;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.mooneyserver.account.ui.view.AbstractBaseView;

public class BackendServiceLookup {
	
	static Properties props = new Properties();
    static InitialContext ctx;
   
    static Map<String, String> backendServicesLookup;
    static Map<String, Object> cacheBackendServices;
   
    static {
        props.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.enterprise.naming.SerialInitContextFactory");
        props.setProperty("org.omg.CORBA.ORBInitialHost", "localhost");
       
        props.setProperty("org.omg.CORBA.ORBInitialPort", "3700");
       
        try {
            ctx = new InitialContext(props);
        } catch (NamingException e) {
            System.err.println("JNDI Lookup error");
            e.printStackTrace();
        }
       
        cacheBackendServices = new HashMap<>();
        
        backendServicesLookup = new HashMap<>();
        backendServicesLookup.put("com.mooneyserver.account.businesslogic.user.IUserService", 
        		"/Accounts/AccountsBackend/UserBusinessService");
    }
   
   
    private static Object getService(String publicServiceCanonicalName) throws NamingException {
        if (!cacheBackendServices.containsKey(publicServiceCanonicalName)) {
            for (String key : backendServicesLookup.keySet()) {
                if (key.equalsIgnoreCase(publicServiceCanonicalName)) {
                    cacheBackendServices.put(publicServiceCanonicalName,
                            ctx.lookup("java:global" + backendServicesLookup.get(key) + "!" + key));
                }
            }
        }
       
        return cacheBackendServices.get(publicServiceCanonicalName);
    }
   
   
    public static void injectBackendServices(AbstractBaseView view) {
        for (Field field : view.getClass().getDeclaredFields()) {
            Annotation annotation = field.getAnnotation(BusinessProcess.class);
           
            if(annotation instanceof BusinessProcess){
                boolean fieldStateChange = false;
                if(!field.isAccessible()) {
                    field.setAccessible(true);
                    fieldStateChange = true;
                }
                   
               
                try {
                    field.set(view, getService(field.getType().getCanonicalName()));
                } catch (IllegalArgumentException | IllegalAccessException | NamingException e) {
                    e.printStackTrace();
                } finally {
                    if (fieldStateChange)
                        field.setAccessible(false);
                }
            }
        }
    }
}