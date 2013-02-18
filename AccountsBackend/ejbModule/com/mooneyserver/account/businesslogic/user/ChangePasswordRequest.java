package com.mooneyserver.account.businesslogic.user;

import java.io.IOException;

import com.mooneyserver.account.utils.EncryptionProvider;
import com.mooneyserver.account.utils.StringUtils;

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
	 * String ready for comparison
	 * against an equivalent DB version
	 * 
	 * @return
	 * 		<b>String</b>
	 * 
	 * @throws IOException 
	 */
	public String getOldPassword(String salt) throws IOException {
		EncryptionProvider encrypter = new EncryptionProvider();
		return StringUtils.byteToBase64(
				encrypter.encryptString(
						this.oldPassword, 
						StringUtils.base64ToByte(salt)));
	}
	
	/**
	 * Returns the encrypted version of this 
	 * String ready for writing
	 * to the DB
	 * 
	 * @return
	 * 		<b>String</b>
	 */
	public String getNewPassword() {
		EncryptionProvider encrypter = new EncryptionProvider();
		return StringUtils.byteToBase64(
				encrypter.encryptString(
						this.newPassword, encrypter.generateSalt()));
	}
}