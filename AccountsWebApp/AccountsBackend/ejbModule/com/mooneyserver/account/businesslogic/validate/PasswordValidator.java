package com.mooneyserver.account.businesslogic.validate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Help from:
 * 	http://www.mkyong.com/regular-expressions/how-to-validate-password-with-regular-expression/
 */
public final class PasswordValidator {
	
	private Pattern pattern;
	private Matcher matcher;

	// Password criteria:
	//	Must contain one digit from 0-9
	//	Must contain one lower case character
	//	Must contain one upper case character
	//	Must contain one special character in the list [!#$%&-?<>@]
	// Must be between 8 and 20 characters
	private static final String PASSWORD_PATTERN = 
            "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!#$%&-?<>@]).{8,20})";

	public PasswordValidator(){
		pattern = Pattern.compile(PASSWORD_PATTERN);
	}

	/**
	 * Validate if password meets security requirements
	 * 
	 * @param password 
	 * 		<b>String</b>
	 * 		Password for validation
	 * 
	 * @return 
	 * 		<b>boolean</b>
	 * 		true for a valid password, false otherwise
	 */
	public boolean validate(final String password){
		matcher = pattern.matcher(password);
		return matcher.matches();
	}
	
	/**
	 * A human readable message outlining the
	 * required composition of passwords to 
	 * match the system security requirements
	 * 
	 * @return
	 * 		<b>String</b>
	 * 		Message outlining the required composition
	 * 		of passwords to pass validation
	 */
	public static String getPasswordRequirementMsg() {
		return "Passwords must match the following " +
			   "criteria to meet security requirements:\n" +
				"\tMust contain one digit from 0-9\n" +
				"\tMust contain one lower case character\n" +
				"\tMust contain one upper case character\n" +
				"\tMust contain one special character in the list [!#$%&-?<>@]\n" +
				"\tMust be between 8 and 20 characters";
	}
}