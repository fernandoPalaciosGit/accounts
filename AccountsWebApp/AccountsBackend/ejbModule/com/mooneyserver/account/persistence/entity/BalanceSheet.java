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
@NamedQueries({
	@NamedQuery(name = "accounts.schema.BalanceSheet.findByOwner", 
			query = "SELECT s FROM BalanceSheet s where s.owner = :owner"),
	@NamedQuery(name = "accounts.schema.BalanceSheet.findByName", 
			query = "SELECT s FROM BalanceSheet s where s.owner = :owner and s.sheetName = :name")
})
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

	//bi-directional many-to-one association to AccountsUser
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="owner_id")
	private AccountsUser owner;
	
	@Column(name="sheet_is_active")
	private boolean active;
	
	@Lob()
	@Column(name="inactive_reason")
	private String inactiveReason;

	//bi-directional many-to-one association to CreditMaster
	@OneToMany(mappedBy="balanceSheetBean")
	private Set<CreditMaster> creditEntries;

	//bi-directional many-to-one association to DebitMaster
	@OneToMany(mappedBy="balanceSheetBean")
	private Set<DebitMaster> debitEntries;
	
	@OneToMany(mappedBy="owningSheet")
	private Set<CategoryType> categories;

	
	
    public BalanceSheet() {}
    

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
	
	public AccountsUser getOwner() {
		return this.owner;
	}

	public void setOwner(AccountsUser owner) {
		this.owner = owner;
	}
	
	public Set<CreditMaster> getCreditMasters() {
		return this.creditEntries;
	}

	public void setCreditMasters(Set<CreditMaster> creditMasters) {
		this.creditEntries = creditMasters;
	}
	
	public Set<DebitMaster> getDebitMasters() {
		return this.debitEntries;
	}

	public void setDebitMasters(Set<DebitMaster> debitMasters) {
		this.debitEntries = debitMasters;
	}
	
	public boolean isActive() {
		return active;
	}
	
	public void setActive(boolean bool) {
		this.active = bool;
	}
	
	public String getReasonForClosure() {
		return inactiveReason;
	}
	
	public void setReasonForClosure(String reason) {
		this.inactiveReason = reason;
	}
}