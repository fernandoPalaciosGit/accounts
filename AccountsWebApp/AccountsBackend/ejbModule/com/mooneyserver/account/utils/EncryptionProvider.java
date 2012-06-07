package com.mooneyserver.account.utils;

import static com.mooneyserver.account.utils.SystemSettings.SETTINGS;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import com.mooneyserver.account.businesslogic.AccountsBaseException;

/**
 * Security class for any
 * encoding/decoding operations
 */
public class EncryptionProvider {

	private String encryptionType;
	private final int numberOfIterations = 1000;
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
	 * 		<b>byte[]</b>
	 * 		The encoded value
	 */
	public byte[] encryptString(String val, byte[] salt) {
		pullDatabaseSettings();
		
		switch(encryptionType) {
			case "MD5":
				return getHashMD5(val, salt);
			default:
				UncheckedExceptionThrower.throwUnchecked(
						new AccountsBaseException("Encryption method ["+encryptionType
						+"] (val of "+ENCRYPTION_KEY+" key) is not a supprted encryption type"));
				return null; // unreachable
		}
	}
	
	private void pullDatabaseSettings() {
		this.encryptionType = SETTINGS.getProp("accounts.encryption.type");
	}
	
	
	/**
	 * Random Salt Generator too be used
	 * in password hashing
	 * 
	 * @return
	 * 		<b>byte[]</b>
	 * 		Byte Array of randomly generated salt
	 */
	public byte[] generateSalt() {
		try {
			// Uses a secure Random not a simple Random
	        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
	        
	        // Salt generation 64 bits long
	        byte[] bSalt = new byte[8];
	        random.nextBytes(bSalt);
	        
	        return bSalt;
		} catch (NoSuchAlgorithmException e) {
			UncheckedExceptionThrower.throwUnchecked(new 
					AccountsBaseException("Error trying to hash String with [MD5]", e));
	    	return null; //unreachable
		}
	}
	
	/**
	 * From a password, a number of iterations and a salt,
	 * returns the corresponding digest
	 * 
	 * @param password 
	 * 		<b>String</b>
	 * 		String The password to encrypt
	 * 
	 * @param salt
	 * 		<b>byte[]</b>
	 * 		Used for salting the password
	 * 
	 * @return 
	 * 		<b>byte[]</b> 
	 * 		The digested password
	 */
	private byte[] getHashMD5(String password, byte[] salt) {
       try {
    	   MessageDigest digest = MessageDigest.getInstance("MD5");
    	   
           return getHash(password, salt, digest);
       } catch(NoSuchAlgorithmException e) {
    	   UncheckedExceptionThrower.throwUnchecked(new 
    			   AccountsBaseException("Error trying to hash String with [MD5]", e));
    	   return null; //unreachable
       }
    }
	
	/**
	 * From a password, a number of iterations and a salt,
	 * returns the corresponding digest
	 * 
	 * @param password 
	 * 		<b>String</b>
	 * 		String The password to encrypt
	 * 
	 * @param digest
	 * 		<b>MessageDigest</b>
	 * 		The specific MessageDigest Algorithm to be used
	 * 
	 * @return 
	 * 		<b>byte[]</b> 
	 * 		The digested password
	 */
	private byte[] getHash(String password, byte[] salt, MessageDigest digest) {
		try {
    	   digest.reset();
           digest.update(salt);
           byte[] input = digest.digest(password.getBytes("UTF-8"));
           for (int i = 0; i < numberOfIterations; i++) {
               digest.reset();
               input = digest.digest(input);
           }
           return input;
       } catch(UnsupportedEncodingException e) {
    	   UncheckedExceptionThrower.throwUnchecked(new 
    			   AccountsBaseException("Error trying to hash String", e));
    	   return null; //unreachable
       }
	}
}