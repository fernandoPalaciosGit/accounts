package com.mooneyserver.account.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Set;


/**
 * The persistent class for the currency database table.
 * 
 */
@Entity
@Table(name="currency")
public class Currency implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idcurrency;

	private String name;

	//bi-directional many-to-one association to CcyConversion
	@OneToMany(mappedBy="currency1")
	private Set<CcyConversion> ccyConversions1;

	//bi-directional many-to-one association to CcyConversion
	@OneToMany(mappedBy="currency2")
	private Set<CcyConversion> ccyConversions2;

	//bi-directional many-to-one association to CreditMaster
	@OneToMany(mappedBy="currencyBean")
	private Set<CreditEntry> creditMasters;

	//bi-directional many-to-one association to DebitMaster
	@OneToMany(mappedBy="currencyBean")
	private Set<DebitEntry> debitMasters;

    public Currency() {
    }

	public int getIdcurrency() {
		return this.idcurrency;
	}

	public void setIdcurrency(int idcurrency) {
		this.idcurrency = idcurrency;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<CcyConversion> getCcyConversions1() {
		return this.ccyConversions1;
	}

	public void setCcyConversions1(Set<CcyConversion> ccyConversions1) {
		this.ccyConversions1 = ccyConversions1;
	}
	
	public Set<CcyConversion> getCcyConversions2() {
		return this.ccyConversions2;
	}

	public void setCcyConversions2(Set<CcyConversion> ccyConversions2) {
		this.ccyConversions2 = ccyConversions2;
	}
	
	public Set<CreditEntry> getCreditMasters() {
		return this.creditMasters;
	}

	public void setCreditMasters(Set<CreditEntry> creditMasters) {
		this.creditMasters = creditMasters;
	}
	
	public Set<DebitEntry> getDebitMasters() {
		return this.debitMasters;
	}

	public void setDebitMasters(Set<DebitEntry> debitMasters) {
		this.debitMasters = debitMasters;
	}
	
}