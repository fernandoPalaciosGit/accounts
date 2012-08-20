package com.mooneyserver.account.messaging;

import com.mooneyserver.account.messaging.iface.IUserActivationMessage;

public class UserActivationMessage implements IUserActivationMessage {

	private static final long serialVersionUID = -7996593516021631133L;
	
	private final String emailAddress;
	private final String firstName;
	
	public UserActivationMessage(String emailAddress, String firstName) {
		this.emailAddress = emailAddress;
		this.firstName = firstName;
	}
	
	@Override
	public String getToAddress() {
		return emailAddress.trim();
	}

	@Override
	public String getFirstName() {
		String tmpFirstName = firstName.trim().toLowerCase(); 
		return tmpFirstName.substring(0, 1).toUpperCase() 
				+ tmpFirstName.substring(1);
	}
}