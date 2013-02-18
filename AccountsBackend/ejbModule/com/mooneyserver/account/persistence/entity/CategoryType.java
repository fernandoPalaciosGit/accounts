package com.mooneyserver.account.persistence.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.mooneyserver.account.persistence.service.accounts.IPaymentCategorization;

@Entity
@Table(name="payment_category")
@NamedQueries({
	@NamedQuery(name = "accounts.schema.CategoryType.findByBalSheet", 
			query = "SELECT c FROM CategoryType c where c.owningSheet = :balanceSheet")
})
public class CategoryType implements Serializable, IPaymentCategorization {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="category_id")
	private int id;
	
	@Column(name="category_name")
	private String name;
	
	@OneToMany(mappedBy="category", fetch=FetchType.LAZY)
	private Set<PaymentType> paymentTypes;
	
	@ManyToOne
	@JoinColumn(name="bal_sheet")
	private BalanceSheet owningSheet;
	
	@Column(name="is_credit")
	private boolean credit;
	
	public CategoryType() {}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Set<PaymentType> getPaymentTypes() {
		return paymentTypes;
	}
	public void setPaymentTypes(Set<PaymentType> paymentTypes) {
		this.paymentTypes = paymentTypes;
	}
	
	public BalanceSheet getOwningSheet() {
		return owningSheet;
	}
	public void setOwningSheet(BalanceSheet balSheet) {
		this.owningSheet = balSheet;
	}
	
	public boolean isCredit() {
		return credit;
	}
	public void setCredit(boolean credit) {
		this.credit = credit;
	}
}