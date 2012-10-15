package com.mooneyserver.account.businesslogic.user;

import java.io.IOException;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.mooneyserver.account.businesslogic.exception.AccountsBaseException;
import com.mooneyserver.account.businesslogic.exception.user.AccountsUserAlreadyExistsException;
import com.mooneyserver.account.businesslogic.exception.user.AccountsUserDoesNotExistException;
import com.mooneyserver.account.businesslogic.exception.user.AccountsUserException;
import com.mooneyserver.account.businesslogic.exception.user.AccountsUserInvalidPasswordException;
import com.mooneyserver.account.businesslogic.exception.user.AccountsUserNotActiveException;
import com.mooneyserver.account.businesslogic.validate.ClassFieldValidator;
import com.mooneyserver.account.businesslogic.validate.PasswordValidator;
import com.mooneyserver.account.messaging.GenericJmsDispatcher;
import com.mooneyserver.account.messaging.UserActivationMessage;
import com.mooneyserver.account.messaging.UserChangePasswordMessage;
import com.mooneyserver.account.persistence.entity.AccountsUser;
import com.mooneyserver.account.persistence.service.logging.LoggingService;
import com.mooneyserver.account.persistence.service.user.UserActivationService;
import com.mooneyserver.account.persistence.service.user.UserService;
import com.mooneyserver.account.utils.EncryptionProvider;
import com.mooneyserver.account.utils.StringUtils;

/**
 * Session Bean implementation class UserBusinessService
 */
@Stateless
@EJB(name = "UserBusinessService", beanInterface = IUserService.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class UserBusinessService implements IUserService {

	@EJB
	private UserService userService;
	
	@EJB
	private LoggingService logService;
	
	@EJB
	private UserActivationService userActivationService;
	
	@Resource
	private SessionContext context;
	
	@Override
	public void createNewUser(AccountsUser user) throws AccountsUserException {
		
		// Check if requested user details are valid
		// Does the username already exist?
		if (userService.findByUsername(user.getUsername()) != null)
			throw new AccountsUserAlreadyExistsException(user.getUsername());
		
		// Is the password valid?
		PasswordValidator pwv = new PasswordValidator();
		if (!pwv.validate(user.getPassword()))
			throw new AccountsUserInvalidPasswordException(user.getPassword());
		
		// Are all required fields present?
		ClassFieldValidator cfv = new ClassFieldValidator(user, IUserService.requiredUserFields);
		try {
			if (cfv.areAnyFieldsNull())
				throw new AccountsUserException("Creation Failed: Some User Fields are null");
		} catch (AccountsBaseException e) {
			throw new AccountsUserException("Rethrowing wrapped base exception", e);
		}
		
		// encrypt the password
		EncryptionProvider encrypter = new EncryptionProvider();
		byte[] salt = encrypter.generateSalt();
		user.setSalt(StringUtils.byteToBase64(salt));
		user.setPassword(StringUtils.byteToBase64
				(encrypter.encryptString
						(user.getPassword(), salt)));
		
		// Create the user
		try {
			userService.create(user);
			logService.quickInfoEvent(userService.findByUsername(user.getUsername()), 
					"User {"+user+"} has been added successfully");
			
		} catch (Exception e) {
			throw new AccountsUserException("Rethrowing wrapped base exception", e);
		}
		
		UserActivationMessage msg 
			= new UserActivationMessage(user.getUsername(), 
					user.getFirstname());
		GenericJmsDispatcher.sendMessage(msg);
		logService.quickInfoEvent(userService.findByUsername(user.getUsername()), 
				"Activation Mail has been dispatched");
	}

	
	@Override
	public void updateExistingUser(AccountsUser user)
			throws AccountsUserException {
		// Check if requested user details are valid
		// Is it a valid user
		if (userService.findByUsername(user.getUsername()) 
				== null)
			throw new AccountsUserException("MarkInactive Failed: The Requested Username ["
				+user.getUsername()+"] does not exist");
		
		// Are all required fields present?
		ClassFieldValidator cfv = new ClassFieldValidator(user, IUserService.requiredUserFields);
		try {
			if (cfv.areAnyFieldsNull())
				throw new AccountsUserException("Creation Failed: Some User Fields are null");
		} catch (AccountsBaseException e) {
			throw new AccountsUserException("Rethrowing wrapped base exception", e);
		}
		
		try {
			userService.modify(user);
			logService.quickInfoEvent(userService.findByUsername(user.getUsername()), 
					"User {"+user+"} has been updated!");
		} catch(Exception e) {
			throw new AccountsUserException("Rethrowing wrapped base exception", e);
		}
	}

	
	@Override
	public void markUserInactive(AccountsUser user)
			throws AccountsUserException {
		// Check if requested user details are valid
		// Is it a valid user
		if (userService.findByUsername(user.getUsername()) 
				== null)
			throw new AccountsUserException("MarkInactive Failed: The Requested Username ["
				+user.getUsername()+"] does not exist");
		
		user.setActive(false);
		
		updateExistingUser(user);
		logService.quickInfoEvent(userService.findByUsername(user.getUsername()), 
				"User {"+user+"} has been DisActivated");
	}

	
	@Override
	public void changePassword(ChangePasswordRequest changePwd)
			throws AccountsUserException {
		// Check if requested user details are valid
		// Is it a valid user
		String username = context.getCallerPrincipal().getName();
		AccountsUser user = userService.findByUsername(username);
		
		if (user == null)
			throw new AccountsUserException("Password Change Failed: The request user ["+username+"] does not exist");
		
		try {
			// Does the old password match
			if (user.getPassword() != changePwd.getOldPassword(user.getSalt()))
				throw new AccountsUserException("Password Change Failed: Old Password does not match expected");
		} catch (IOException e) {
			throw new AccountsUserException("Password Change Failed: Password Compare Error", e);
		}
		
		// Set the new password
		user.setPassword(changePwd.getNewPassword());
		
		// update the user
		updateExistingUser(user);
		logService.quickInfoEvent(userService.findByUsername(user.getUsername()), 
				"User {"+user+"} password has been changed");
		
		// TODO: Send mail stating password change has occurred
	}


	@Override
	public AccountsUser userLoginRequest(String emailAddress, String password) throws AccountsUserException {
		AccountsUser user = userService.findByUsername(emailAddress);
		
		if (user == null) {
			try {
				Thread.sleep(100); // Sleep to avoid brute force
			} catch (InterruptedException e) {} 
			
			return null;
		}
		
		EncryptionProvider encrypter = new EncryptionProvider();
		try {
			password = StringUtils.byteToBase64(
					encrypter.encryptString(
							password, 
							StringUtils.base64ToByte(user.getSalt())));
		} catch (IOException e) {
			throw new AccountsUserException("Password Check Failed, converting password to hash", e);
		}
		
		if (!user.getActive())
			throw new AccountsUserNotActiveException(user.getUsername());
		
		if (password.equals(user.getPassword())) {
			userService.markUserLoggedIn(user);
			logService.quickInfoEvent(userService.findByUsername(user.getUsername()), 
					"User {"+user+"} has logged in!");
			return user;
		} else {
			return null;
		}
	}
	
	@Override
	public void userLogout(AccountsUser user) throws AccountsUserException {
		if (userService.findById(user.getId()).getUsername().equalsIgnoreCase(user.getUsername())) {
			userService.markUserLoggedOut(user);
			logService.quickInfoEvent(userService.findByUsername(user.getUsername()), 
					"User {"+user+"} has logged out!");
		}
	}


	@Override
	public void passwordReset(String emailAddress) throws AccountsUserException {
		AccountsUser user = userService.findByUsername(emailAddress);
		
		if (user == null) {
			try {
				Thread.sleep(100); // Sleep to avoid brute force
			} catch (InterruptedException e) {} 
			
			throw new AccountsUserDoesNotExistException(emailAddress);
		}
		
		String newPassword = StringUtils.generatePassword();
		
		// encrypt the password
		EncryptionProvider encrypter = new EncryptionProvider();
		byte[] salt = encrypter.generateSalt();
		user.setSalt(StringUtils.byteToBase64(salt));
		user.setPassword(StringUtils.byteToBase64
				(encrypter.encryptString
						(newPassword, salt)));
		
		updateExistingUser(user);
		
		UserChangePasswordMessage msg 
			= new UserChangePasswordMessage(user.getUsername(), 
				user.getFirstname(), newPassword);
		GenericJmsDispatcher.sendMessage(msg);
		
		logService.quickInfoEvent(userService.findByUsername(user.getUsername()), 
				"User Password has been reset and notification mail has been dispatched");
	}

	@Override
	public boolean markUserActive(String activationId) throws AccountsUserException {
		AccountsUser user = userActivationService.findUserByActivationId(activationId);
		
		if (user == null)
			return false;
		
		if (user.getActive())
			return false;
		
		user.setActive(true);
		updateExistingUser(user);
		
		logService.quickInfoEvent(userService.findByUsername(user.getUsername()), 
				"User {"+user+"} has been marked active");
		
		return true;
	}
}