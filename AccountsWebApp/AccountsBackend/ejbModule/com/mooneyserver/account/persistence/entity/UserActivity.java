package com.mooneyserver.account.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the accounts_user database table.
 * 
 */
@Entity
@Table(name="user_activity")
@NamedQueries({
	@NamedQuery(name = "accounts.schema.UserActivity.findByUser", query = "SELECT u FROM UserActivity u where u.user = :user")
})
public class UserActivity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@OneToOne
	@JoinColumn(name="user_id")
	private AccountsUser user;

	@Column(name="user_logged_in")
	private boolean userLoggedIn;
	
	@Column(name="last_log_in")
	private Date lastLogin;
	
	@Column(name="last_log_out")
	private Date lastLogout;

	
    public UserActivity() {}


    
	public int getId() { return id;}
	public void setId(int id) { this.id = id; }



	public AccountsUser getUser() {
		return user;
	}
	public void setUser(AccountsUser user) {
		this.user = user;
	}


	public boolean isUserLoggedIn() {
		return userLoggedIn;
	}
	public void setUserLoggedIn(boolean userLoggedIn) {
		this.userLoggedIn = userLoggedIn;
	}


	public Date getLastLogin() {
		return lastLogin;
	}
	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}


	public Date getLastLogout() {
		return lastLogout;
	}
	public void setLastLogout(Date lastLogout) {
		this.lastLogout = lastLogout;
	}   
}