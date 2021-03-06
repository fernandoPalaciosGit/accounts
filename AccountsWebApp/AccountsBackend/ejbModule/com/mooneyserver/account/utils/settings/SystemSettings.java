package com.mooneyserver.account.utils.settings;

import java.util.Properties;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.mooneyserver.account.logging.AccountsLoggingConstants;

/**
 * System Settings singleton
 * Access point for settings in any 
 * project classes. Will be loaded 
 * from DB but this can be changed 
 * if required.
 * 
 * <br/>
 * This class uses a ReentrantLock 
 * system to enable updating of system 
 * properties
 */
public enum SystemSettings {
	
	SETTINGS;
	
	private static Logger log = Logger.getLogger(AccountsLoggingConstants.LOG_AREA_SETTINGS);
	
	private Properties props = new Properties();
	private final ReadWriteLock lock = new ReentrantReadWriteLock();
	private boolean settingsUpdated = false;
	
	
	/**
	 * Update the SystemSettings stored 
	 * properties. This method acquired a
	 * write lock on SystemSettings to 
	 * ensure that no reads can occur during
	 * the write.
	 * 
	 */
	public void reloadProps(Properties newProps) {
		lock.writeLock().lock();
		try {
			props = newProps;
		} finally {
			lock.writeLock().unlock();
			settingsUpdated = true;
		}
	}
	
	/**
	 * Get the associated value of the 
	 * passed property key. This method
	 * acquires a read lock on SystemSettings
	 * 
	 * @param key
	 * 		<b>String</b>
	 * 		The key of the desired property
	 * 
	 * @return
	 * 		<b>String</b>
	 * 		The value of the desired property
	 */
	public String getProp(String key) {
		lock.readLock().lock();
		try {
			String val = props.getProperty(key);
			if (val == null)
				log.log(Level.WARNING, "No Setting found for Key ["+key+"]. Props ["+props+"]");
				
			return val;
		} finally {
			lock.readLock().unlock();
		}
	}
	
	/**
	 * Get the associated value of the 
	 * passed property key. This method
	 * acquires a read lock on SystemSettings
	 * 
	 * @param key
	 * 		<b>String</b>
	 * 		The key of the desired property
	 * 
	 * @return
	 * 		<b>int</b>
	 * 		The value of the desired property
	 * 		or -1 on fail
	 */
	public int getIntProp(String key) {
		try {
			return Integer.valueOf(getProp(key));
		} catch (NumberFormatException e) {
			return -1;
		}
	}
	
	/**
	 * Check whether the initial update from the DB 
	 * has taken place.
	 *  
	 * @return
	 * 	boolean
	 */
	/* This is pretty ugly but I'm
	 * running into a race condition with 
	 * JMS otherwise
	 */
	public boolean areSettingsInitialised() {
		return settingsUpdated;
	}
}