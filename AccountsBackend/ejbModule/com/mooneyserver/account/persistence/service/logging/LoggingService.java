package com.mooneyserver.account.persistence.service.logging;

import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.mooneyserver.account.persistence.entity.AccountsUser;
import com.mooneyserver.account.persistence.entity.AuditLog;
import com.mooneyserver.account.persistence.entity.AuditLog.EventType;
import com.mooneyserver.account.persistence.service.BaseServiceLayer;

/**
 * Session Bean implementation class UserService
 */
@Stateless
@LocalBean
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class LoggingService extends BaseServiceLayer {
       
    public LoggingService() {
        super();
    }


	public void create(AuditLog entity) {
		em.persist(entity);	
	}
	
	
	public void quickEvent(AccountsUser owner, EventType type, Date when, String detail) {
		AuditLog userCreateEvent = new AuditLog(
				owner, 
				type, 
				when,
				detail);
		create(userCreateEvent);
	}
	
	public void quickNowEvent(AccountsUser owner, EventType type, String detail) {
		quickEvent(owner, type, new Date(), detail);
	}
	
	public void quickInfoEvent(AccountsUser owner, String detail) {
		quickNowEvent(owner, EventType.INFO, detail);
	}
	
	public void quickWarnEvent(AccountsUser owner, String detail) {
		quickNowEvent(owner, EventType.WARN, detail);
	}
	
	public void quickErrorEvent(AccountsUser owner, String detail) {
		quickNowEvent(owner, EventType.ERROR, detail);
	}
	
	public List<AuditLog> queryLogs(AccountsUser user, Date from, Date to) {
		return em.createNamedQuery("accounts.schema.AuditLog.findUserLogsForPeriod", 
				AuditLog.class)
				.setParameter("uid", user)
				.setParameter("from", from)
				.setParameter("to", to)
				.getResultList();
	}
}