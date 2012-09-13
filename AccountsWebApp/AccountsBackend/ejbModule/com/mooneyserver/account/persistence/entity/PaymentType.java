package com.mooneyserver.account.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;

import com.mooneyserver.account.persistence.service.accounts.IPaymentCategorization;


/**
 * The persistent class for the payment_type database table.
 * 
 */
@Entity
@Table(name="payment_type")
@NamedQueries({
	@NamedQuery(name = "accounts.schema.PaymentType.findByategory", 
			query = "SELECT p FROM PaymentType p where p.category = :category")
})
public class PaymentType implements Serializable, IPaymentCategorization {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idpayment_type")
	private int idpaymentType;

	@Column(name="payment_name")
	private String name;
	
	@ManyToOne
	@JoinColumn(name="payment_category")
	private CategoryType category;
	

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
	
	public CategoryType getCategory() {
		return this.category;
	}
	public void setCategory(CategoryType category) {
		this.category = category;
	}
}