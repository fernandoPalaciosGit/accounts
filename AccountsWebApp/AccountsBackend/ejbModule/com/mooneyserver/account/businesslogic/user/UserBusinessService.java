package com.mooneyserver.account.businesslogic.user;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.mooneyserver.account.businesslogic.AccountsBaseException;
import com.mooneyserver.account.businesslogic.validate.ClassFieldValidator;
import com.mooneyserver.account.persistence.entity.AccountsUser;
import com.mooneyserver.account.persistence.service.UserService;
import com.mooneyserver.account.utils.EncryptionProvider;

/**
 * Session Bean implementation class UserBusinessService
 */
@Stateless
@EJB(name = "UserBusinessService", beanInterface = IUserService.class)
//@RolesAllowed({ "Accounts_Admin" })
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class UserBusinessService implements IUserService {

	@EJB
	private UserService userService;
	
	@Resource
	private SessionContext context;
	
	@Override
	public void createNewUser(AccountsUser user) throws AccountsUserException {
		
		// Check if requested user details are valid
		// Does the username already exist?
		if (userService.findByUsername(user.getUsername()) 
				!= null)
			throw new AccountsUserException("Creation Failed: The Requested Username ["
				+user.getUsername()+"] already exists");
		
		// Is the password valid?
		PasswordValidator pwv = new PasswordValidator();
		if (!pwv.validate(user.getPassword()))
			throw new AccountsUserException("Creation Failed: The Requested Password ["
		+user.getPassword()+"] is not valid. " 
					+ pwv.getPasswordRequirementMsg());
		
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
		user.setPassword(encrypter.encryptString(user.getPassword()));
		
		// Create the user
		userService.create(user);
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
		
		userService.modify(user);
		
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
		
		userService.modify(user);
	}

	
	@Override
	@RolesAllowed({"Accounts_User"})
	public void changePassword(ChangePasswordRequest changePwd)
			throws AccountsUserException {
		// Check if requested user details are valid
		// Is it a valid user
		String username = context.getCallerPrincipal().getName();
		AccountsUser user = userService.findByUsername(username);
		
		if (user == null)
			throw new AccountsUserException("Password Change Failed: The request user ["+username+"] does not exist");
		
		// Does the old password match
		if (user.getPassword() != changePwd.getOldPassword())
			throw new AccountsUserException("Password Change Failed: Old Password does not match expected");
		
		// Set the new password
		user.setPassword(changePwd.getNewPassword());
		
		// update the user
		userService.modify(user);
	}
}