package com.mooneyserver.account.messaging.iface;

public interface IUserChangePasswordMessage extends ISupportedMessage {
	
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
	
	/**
	 * Return the new password for the
	 * user
	 * 
	 * @return
	 * 	<b>String</b>
	 *  The Reset Password
	 */
	public String getNewPassword();
}