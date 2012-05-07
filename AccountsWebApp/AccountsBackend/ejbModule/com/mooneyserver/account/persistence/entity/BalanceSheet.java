package com.mooneyserver.account.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


/**
 * The persistent class for the balance_sheet database table.
 * 
 */
@Entity
@Table(name="balance_sheet")
public class BalanceSheet implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idbalance_sheet")
	private int idbalanceSheet;

    @Lob()
	private String description;

	@Column(name="sheet_name")
	private String sheetName;

	//bi-directional one-to-one association to AccountsUser
	@OneToOne(mappedBy="balanceSheet", fetch=FetchType.LAZY)
	private AccountsUser accountsUser1;

	//bi-directional many-to-one association to AccountsUser
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="owner_id")
	private AccountsUser accountsUser2;

	//bi-directional many-to-one association to CreditMaster
	@OneToMany(mappedBy="balanceSheetBean")
	private Set<CreditMaster> creditMasters;

	//bi-directional many-to-one association to DebitMaster
	@OneToMany(mappedBy="balanceSheetBean")
	private Set<DebitMaster> debitMasters;

    public BalanceSheet() {
    }

	public int getIdbalanceSheet() {
		return this.idbalanceSheet;
	}

	public void setIdbalanceSheet(int idbalanceSheet) {
		this.idbalanceSheet = idbalanceSheet;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSheetName() {
		return this.sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public AccountsUser getAccountsUser1() {
		return this.accountsUser1;
	}

	public void setAccountsUser1(AccountsUser accountsUser1) {
		this.accountsUser1 = accountsUser1;
	}
	
	public AccountsUser getAccountsUser2() {
		return this.accountsUser2;
	}

	public void setAccountsUser2(AccountsUser accountsUser2) {
		this.accountsUser2 = accountsUser2;
	}
	
	public Set<CreditMaster> getCreditMasters() {
		return this.creditMasters;
	}

	public void setCreditMasters(Set<CreditMaster> creditMasters) {
		this.creditMasters = creditMasters;
	}
	
	public Set<DebitMaster> getDebitMasters() {
		return this.debitMasters;
	}

	public void setDebitMasters(Set<DebitMaster> debitMasters) {
		this.debitMasters = debitMasters;
	}
	
}