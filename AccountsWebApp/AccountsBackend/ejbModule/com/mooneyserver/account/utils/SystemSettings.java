package com.mooneyserver.account.utils;

import java.util.Properties;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.jboss.logging.Logger;
import org.jboss.logging.Logger.Level;

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
	
	// Store all DB app_setting keys in one place for refactoring 
	// (Probably move this to its own constants class)
	public static final String ENCRYPTION_KEY = "accounts.encryption.type";
	
	
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
				log.log(Level.INFO, "No Setting found for Key ["+key+"]");
				
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
}