package com.mooneyserver.account.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the accounts_user database table.
 * 
 */
@Entity
@Table(name="accounts_user")
@NamedQueries({
	@NamedQuery(name = "accounts.schema.AccountsUser.findByUsername", query = "SELECT u FROM AccountsUser u where u.username = :username"),
	@NamedQuery(name = "accounts.schema.AccountsUser.findById", query = "SELECT u FROM AccountsUser u where u.id = :uid")
})
public class AccountsUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idaccounts_user")
	private int id;
	
	@Column(name="user_is_active")
	private boolean userIsActive;

	private Date created;
	
	private String firstname;

	private String lastname;

	private String password;
	
	private String salt;

	private String username;

	//bi-directional many-to-one association to BalanceSheet
	@OneToMany(mappedBy="owner")
	private Set<BalanceSheet> balanceSheets;

    public AccountsUser() {
    }

	public int getId() {
		return this.id;
	}

	public void setId(int idaccountsUser) {
		this.id = idaccountsUser;
	}

	public boolean getActive() {
		return userIsActive;// != (byte) 0;
	}
	
	public void setActive(boolean isActive) {
		userIsActive = isActive;// ? (byte) 1 : (byte) 0;
	}
	public String getFirstname() {
		return this.firstname;
	}

	public void setCreated(Date created) {
		this.created = created;
	}
	public Date getCreated() {
		return this.created;
	}
	
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return this.lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getSalt() {
		return this.salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public Set<BalanceSheet> getBalanceSheets() {
		return this.balanceSheets;
	}

	public void setBalanceSheets(Set<BalanceSheet> balanceSheets) {
		this.balanceSheets = balanceSheets;
	}
	
	
	@Override
	public String toString() {
		return "["+username+"]";
	}
}