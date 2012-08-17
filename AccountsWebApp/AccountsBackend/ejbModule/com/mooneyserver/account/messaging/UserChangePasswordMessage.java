package com.mooneyserver.account.messaging;

import com.mooneyserver.account.messaging.iface.IUserChangePasswordMessage;

public class UserChangePasswordMessage implements IUserChangePasswordMessage {

	private static final long serialVersionUID = -7996593516021631133L;
	
	private final String emailAddress;
	private final String firstName;
	private final String newPassword;
	
	public UserChangePasswordMessage(String emailAddress, String firstName, String newPassword) {
		this.emailAddress = emailAddress;
		this.firstName = firstName;
		this.newPassword = newPassword;
	}
	
	@Override
	public String getToAddress() {
		return emailAddress.trim();
	}

	@Override
	public String getFirstName() {
		return firstName.trim();
	}
	
	@Override
	public String getNewPassword() {
		return newPassword.trim();
	}
}