package com.mooneyserver.account.utils;

import java.io.IOException;
import java.util.Random;

import com.mooneyserver.account.businesslogic.validate.PasswordValidator;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class StringUtils {
	
	private static Random rand = new Random();
	private static String defaultCharSet;
	private static int PWD_LOWER_BOUND = 8;
	private static int PWD_UPPER_BOUND = 20;
	
	static {
		defaultCharSet = "0123456789!#$%&-?<>@abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	}
	
	private static void setRandSeed() {
		rand.setSeed(System.currentTimeMillis());
	}

	
	/**
	 * Generate a random (compliant) password
	 * 
	 * @return
	 * 	<b>String</b>
	 *  The generated password
	 */
	public static String generatePassword() {
		return generatePassword(defaultCharSet);
	}
	
	
	/**
	 * Generate a random (compliant) password
	 * using a custom character set
	 * 
	 * @param charSet
	 * 	<b>String</b>
	 * 
	 * @return
	 * 	<b>String</b>
	 *  The generated password
	 */
	public static String generatePassword(String charSet) {
		setRandSeed();
		
		// Choose a random length between the allowed boundaries
		int passwordLength = rand.nextInt(PWD_UPPER_BOUND) + PWD_LOWER_BOUND;
		
		PasswordValidator pwdVal = new PasswordValidator();
		
		// Loop creating passwords until a compliant password is created
		// A bit stupid, potentially slow, theoretically run forever. 
		// TODO: Look into Xeger library
		String pwd;
		while (true) {
			pwd = generator(charSet, passwordLength);
			if (pwdVal.validate(pwd))
				break;
		}
		
		return pwd;
	}
	
	private static String generator(String charSet, int pwdLen) {
		StringBuilder pwd = new StringBuilder();
		for (int i = 0; i < pwdLen; i++) {
			char randChar = charSet.charAt(rand.nextInt(charSet.length()));
			pwd.append(randChar);
		}
		
		return pwd.toString();
	}
	
	
	/**
	 * From a base 64 representation, returns the corresponding byte[] 
	 * 
	 * @param data 
	 * 		<b>String</b> 
	 * 		The base64 representation
	 * 
	 * @return 
	 * 		<b>byte[]</b>
	 * 
	 * @throws IOException
	 */
	 public static byte[] base64ToByte(String data) throws IOException {
		 BASE64Decoder decoder = new BASE64Decoder();
		 return decoder.decodeBuffer(data);
	 }
	 
	/**
	 * From a byte[] returns a base 64 representation
	 * 
	 * @param data 
	 * 		<b>byte[]</b>
	 * 
	 * @return 
	 * 		<b>String</b>
	 * 
	 * @throws IOException
	 */
	 public static String byteToBase64(byte[] data){
		 BASE64Encoder endecoder = new BASE64Encoder();
	     return endecoder.encode(data);
	 }
}