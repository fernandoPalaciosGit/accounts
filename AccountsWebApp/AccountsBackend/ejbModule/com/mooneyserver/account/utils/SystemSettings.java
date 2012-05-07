package com.mooneyserver.account.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.mooneyserver.account.businesslogic.AccountsBaseException;

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
	
	private Properties props = new Properties();
	private final ReadWriteLock lock = new ReentrantReadWriteLock();
	
	// TODO: Remove init block when DB setup
	{
		try (InputStream in = getClass().getResourceAsStream("accounts.properties")) {
			props.load(in);
		} catch (IOException e) {
			// Ignoring as this init block is purely for test purposes
		}
	}
	
	/**
	 * Update the SystemSettings stored 
	 * properties. This method acquired a
	 * write lock on SystemSettings to 
	 * ensure that no reads can occur during
	 * the write.
	 * 
	 * @throws AccountsBaseException
	 */
	public void reloadProps() throws AccountsBaseException {
		// TODO: Read System Settings from DB
		// Probably best to have DB read outside 
		// of lock as a time-saving precaution
		Properties tmpProps = new Properties();
		// for (Prop p : DBProps) {
		//		tmpProps.put(p.key, p.val);
		// }
		
		lock.writeLock().lock();
		try {
			props = tmpProps;
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
		if (props.size() <= 0) {
			try {
				// Init if first read comes before
				// first write.
				reloadProps();
			} catch (AccountsBaseException e) {
				return null;
			}
		}
		
		lock.readLock().lock();
		try {
			return props.getProperty(key);
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