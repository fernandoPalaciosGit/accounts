package com.mooneyserver.account.utils;

import java.io.IOException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class StringUtils {
	
	
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