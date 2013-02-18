package com.mooneyserver.account.businesslogic.logs;

import java.util.Date;
import java.util.List;

import javax.ejb.Remote;

import com.mooneyserver.account.businesslogic.exception.AccountsBaseException;
import com.mooneyserver.account.persistence.entity.AccountsUser;
import com.mooneyserver.account.persistence.entity.AuditLog;

@Remote
public interface ILogService {
	
	/**
	 * Activates a user who is in pending state
	 * 
	 * @param
	 * 		<b>AccountsUser</b>
	 * 		The user for whom the logs are to be returned
	 * 
	 * @param
	 * 		<b>Date</b>
	 * 		The from date for which you want to view logs
	 * 
	 * @param
	 * 		<b>Date</b>
	 * 		The to date for which you want to view logs
	 * 
	 * @return
	 * 		<b>List&lt;AuditLog&gt;</b>
	 * 		The List of valid Audit Events	
	 * 
	 * @throws AccountsBaseException 
	 */
	public List<AuditLog> queryLogsByPeriod(AccountsUser user, Date from, Date to) throws AccountsBaseException;
}
