package com.mooneyserver.account.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the accounts_user database table.
 * 
 */
@Entity
@Table(name="user_activation")
@NamedQueries({
	@NamedQuery(name = "accounts.schema.UserActivation.findUserByActivationId", 
			query = "SELECT u.accountsUser FROM UserActivation u where u.userActivationId = :activationId")
})
public class UserActivation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="p_key")
	private int primaryKey;

	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="userId")
	private AccountsUser accountsUser;
	
	@Column(name="activation_string")
	private String userActivationId;

	
    public UserActivation() {}
	
    
    public int getPrimaryKey() {
    	return primaryKey;
    }
    
    public void setPrimaryKey(int primaryKey) {
    	this.primaryKey = primaryKey;
    }
    
    
	public String getUserActivationId() {
		return userActivationId;
	}
	
	public void setUserActivationId(String activationId) {
		this.userActivationId = activationId;
	}
	
	
	public AccountsUser getAccountsUser() {
		return accountsUser;
	}
	
	public void setAccountsUser(AccountsUser user) {
		this.accountsUser = user;
	}
}