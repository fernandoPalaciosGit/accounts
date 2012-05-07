package com.mooneyserver.account.businesslogic.user;

import java.util.HashSet;
import java.util.Set;

import com.mooneyserver.account.persistence.entity.AccountsUser;

public interface IUserService {
	
	/**
	 * List of required fields for the AccountsUser object
	 */
	@SuppressWarnings("serial")
	final Set<String> requiredUserFields = new HashSet<String>() {
		{
			add("firstname"); 
			add("lastname"); 
			add("password"); 
			add("username");
		}};
	
	/**
	 * Add a new User to the Accounts System.
	 * This method will verify that the passed
	 * User object is valid for creating a new 
	 * user and then persist it to the database
	 * 
	 * @throws AccountsUserException
	 * 
	 * @param
	 * 		<b>AccountsUser</b>
	 * 		The user object to be created
	 * 
	 * @return
	 * 		<b>void</b>
	 */
	public void createNewUser(AccountsUser user) throws AccountsUserException;
	
	
	/**
	 * Updates the User in the Accounts System.
	 * This method will verify that the passed
	 * User object is valid before updating the
	 * user in the database
	 * 
	 * @throws AccountsUserException
	 * 
	 * @param
	 * 		<b>AccountsUser</b>
	 * 		The user object to be created
	 * 
	 * @return
	 * 		<b>void</b>
	 */
	public void updateExistingUser(AccountsUser user) throws AccountsUserException;
	
	/**
	 * Mark a user as inactive (delete)
	 * 
	 * @throws AccountsUserException
	 * 
	 * @param
	 * 		<b>AccountsUser</b>
	 * 		The user to be deleted
	 * 
	 * @return
	 * 		<b>void</b>
	 */
	public void markUserInactive(AccountsUser user) throws AccountsUserException;
	
	/**
	 * Update the password of an existing user
	 * 
	 * @throws AccountsUserException
	 * 
	 * @param
	 * 		<b>AccountsUser</b>
	 * 		The user object to be created
	 * 
	 * @param
	 * 		<b>ChangePasswordRequest</b>
	 * 		The Change Password details object		
	 * 
	 * @return
	 * 		<b>void</b>
	 */
	public void changePassword(ChangePasswordRequest changePwd) throws AccountsUserException;
}
