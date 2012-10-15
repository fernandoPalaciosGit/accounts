package com.mooneyserver.account.businesslogic.logs;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.mooneyserver.account.businesslogic.exception.AccountsBaseException;
import com.mooneyserver.account.businesslogic.exception.user.AccountsUserException;
import com.mooneyserver.account.persistence.entity.AccountsUser;
import com.mooneyserver.account.persistence.entity.AuditLog;
import com.mooneyserver.account.persistence.service.logging.LoggingService;
import com.mooneyserver.account.persistence.service.user.UserService;

/**
 * Session Bean implementation class UserBusinessService
 */
@Stateless
@EJB(name = "LogsBusinessService", beanInterface = ILogService.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class LogsBusinessService implements ILogService {

	@EJB
	private UserService userService;
	
	@EJB
	private LoggingService logService;
	
	@Override
	public List<AuditLog> queryLogsByPeriod(AccountsUser user, Date from,
			Date to) throws AccountsBaseException {
		if (userService.findByUsername(user.getUsername()) == null)
			throw new AccountsUserException("Log Query Failed: The Requested Username ["
				+user.getUsername()+"] does not exist");
		
		return logService.queryLogs(user, from, to);
	}

	
}