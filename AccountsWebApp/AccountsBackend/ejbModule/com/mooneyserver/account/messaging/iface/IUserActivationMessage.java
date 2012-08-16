package com.mooneyserver.account.messaging.iface;

public interface IUserActivationMessage extends ISupportedMessage {
	
	/**
	 * Return the email to address for the user 
	 * to be activated with.
	 * 
	 * @return
	 * 	<b>String</b>
	 *  The email address
	 */
	public String getToAddress();
	
	/**
	 * Return the first name of the registering
	 * user
	 * 
	 * @return
	 * 	<b>String</b>
	 *  The First Name
	 */
	public String getFirstName();
}