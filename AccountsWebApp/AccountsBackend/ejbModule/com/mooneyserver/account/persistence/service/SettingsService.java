package com.mooneyserver.account.persistence.service;

import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import static com.mooneyserver.account.utils.settings.SystemSettings.SETTINGS;

/**
 * Session Bean for Application Settings
 * persistence.
 */
@Singleton
@Startup
@LocalBean
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class SettingsService extends BaseServiceLayer {
	
    public SettingsService() {
        super();
    }

    /**
     * Initialize the settings when the app server
     * starts. Also used as a callable for refreshing 
     * the settings
     */
    @PostConstruct
    private void initSettings() {
    	SETTINGS.reloadProps(retrieveAllSettings());
    }
    
    /**
     * Timer task to refresh settings every 10 seconds
     */
    @Schedule(second="*/10", minute="*", hour="*", info="Refresh the settings every 10 seconds")
    public void refreshSettings() {
        initSettings();
    }
    
    /**
     * Pull every row from the app_setting table
     * and store the key value pairs in a 
     * Properties object
     * 
     * @return
     * 		<b>Properties</b>
     * 		Properties object mapped from key value 
     * 		pairs in app_setting table
     */
	private Properties retrieveAllSettings() {
    	@SuppressWarnings("unchecked")
		List<Object[]> settings =  em.createNativeQuery("SELECT key_name, value FROM app_setting")
    			.getResultList();
    	
    	Properties props = new Properties();
    	for (Object[] keyVal : settings) {
    		props.put(keyVal[0], keyVal[1]);
    	}
    	
    	return props;
    }
}