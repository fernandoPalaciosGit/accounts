package com.mooneyserver.account.persistence.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;


/**
 * The persistent class for the accounts_user database table.
 * 
 */
@Entity
@Table(name="audit_log")
@NamedQueries({
	@NamedQuery(name = "accounts.schema.AuditLog.findUserLogsForPeriod", query = "SELECT l FROM AuditLog l where l.owner = :uid AND l.logDate BETWEEN :from AND :to")
})
public class AuditLog implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@OneToOne
	@JoinColumn(name="owner_id")
	private AccountsUser owner;
	
	@Column(name="event_type")
	private String eventType;
	
	@Column(name="log_date")
	private Date logDate;
	
	private String detail;

	// Control the Event Type using an Enum
	public enum EventType {
		INFO,
		WARN,
		ERROR;
	}
	
    // Constructors
	public AuditLog() {}
    
    public AuditLog(AccountsUser owner, EventType eventType, Date when, String detail) {
    	setOwner(owner);
    	setEventType(eventType);
    	setLogDate(when);
    	setDetail(detail);
    }


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}


	public AccountsUser getOwner() {
		return owner;
	}
	public void setOwner(AccountsUser owner) {
		this.owner = owner;
	}


	public EventType getEventType() {
		return EventType.valueOf(eventType);
	}
	public void setEventType(EventType eventType) {
		this.eventType = eventType.toString();
	}


	public Date getLogDate() {
		return logDate;
	}
	public void setLogDate(Date logDate) {
		this.logDate = logDate;
	}


	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
}