package com.mooneyserver.account.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


/**
 * The persistent class for the payment_type database table.
 * 
 */
@Entity
@Table(name="payment_type")
public class PaymentType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idpayment_type")
	private int idpaymentType;

	private String name;

	//bi-directional many-to-one association to CreditMaster
	@OneToMany(mappedBy="paymentTypeBean")
	private Set<CreditMaster> creditMasters;

	//bi-directional many-to-one association to DebitMaster
	@OneToMany(mappedBy="paymentTypeBean")
	private Set<DebitMaster> debitMasters;

    public PaymentType() {
    }

	public int getIdpaymentType() {
		return this.idpaymentType;
	}

	public void setIdpaymentType(int idpaymentType) {
		this.idpaymentType = idpaymentType;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
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