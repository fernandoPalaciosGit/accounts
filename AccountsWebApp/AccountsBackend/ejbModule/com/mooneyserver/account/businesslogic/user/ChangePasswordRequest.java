package com.mooneyserver.account.businesslogic.user;

import com.mooneyserver.account.utils.EncryptionProvider;

public class ChangePasswordRequest {

	private final String oldPassword, newPassword;
	
	public ChangePasswordRequest(String oldPwd, String newPwd) {
		if (oldPwd == null || newPwd == null)
			throw new IllegalArgumentException("null is not an acceptable value for old or new password");
		
		this.oldPassword = oldPwd;
		this.newPassword = newPwd;
	}
	
	/**
	 * Returns the encrypted version of this 
	 * String ready for writing or comparison
	 * against an equivalent DB version
	 * 
	 * @return
	 * 		<b>String</b>
	 */
	public String getOldPassword() {
		EncryptionProvider encrypter = new EncryptionProvider();
		return encrypter.encryptString(this.oldPassword);
	}
	
	/**
	 * Returns the encrypted version of this 
	 * String ready for writing or comparison
	 * against an equivalent DB version
	 * 
	 * @return
	 * 		<b>String</b>
	 */
	public String getNewPassword() {
		EncryptionProvider encrypter = new EncryptionProvider();
		return encrypter.encryptString(this.newPassword);
	}
}