package com.mooneyserver.account.utils;

import static com.mooneyserver.account.utils.SystemSettings.SETTINGS;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import com.mooneyserver.account.businesslogic.AccountsBaseException;

/**
 * Security class for any
 * encoding/decoding operations
 */
public class EncryptionProvider {

	private String encryptionType;
	private final String ENCRYPTION_KEY = "accounts.encryption.type";
	
	/**
	 * Encrypt a String using the encryption pattern
	 * set in the database settings table [accounts.encryption.type]
	 * 
	 * @param val
	 * 		<b>String</b>
	 * 		The value to encode
	 * 
	 * @return
	 * 		<b>String</b>
	 * 		The encoded value
	 */
	public String encryptString(String val) {
		setEncryptionType();
		
		switch(encryptionType) {
			case "MD5":
				return encryptAsMD5(val);
			default:
				UncheckedExceptionThrower.throwUnchecked(
						new AccountsBaseException("Encryption method ["+encryptionType
						+"] (val of "+ENCRYPTION_KEY+" key) is not a supprted encryption type"));
				return null; // unreachable
		}
	}
	
	private void setEncryptionType() {
		this.encryptionType = SETTINGS.getProp("accounts.encryption.type");
	}
	
	private String encryptAsMD5(String val) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] bytes = md.digest(val.getBytes("UTF-8"));
			
			return toBase16String(bytes);
		} catch(NoSuchAlgorithmException | UnsupportedEncodingException e) {
			UncheckedExceptionThrower.throwUnchecked(new AccountsBaseException("Error trying to hash String with [MD5]", e));
			return null; //unreachable
		}
	}

	/*
	 * Provided by http://stackoverflow.com/questions/1033947/mysql-md5-and-java-md5-not-equal
	 */
	private String toBase16String( byte[] bytes ) {
		StringBuffer sb = new StringBuffer();
	    
		for(int i = 0; i < bytes.length; i++) {
			byte b = bytes[ i ];
	        sb.append(( int )( 0x00FF & b ));
	        
	        // Seperate each character group
	        if( i+1 < bytes.length ) {
	        	sb.append( "-" );
	        }
	    }
	    
		return sb.toString();
	}
}