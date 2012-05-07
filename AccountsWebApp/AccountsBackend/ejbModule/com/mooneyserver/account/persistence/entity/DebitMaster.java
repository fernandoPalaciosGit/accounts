package com.mooneyserver.account.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.math.BigDecimal;


/**
 * The persistent class for the debit_master database table.
 * 
 */
@Entity
@Table(name="debit_master")
public class DebitMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="iddebit_master")
	private int iddebitMaster;

	@Column(name="insert_time")
	private Timestamp insertTime;

	@Column(name="payment_ammount")
	private BigDecimal paymentAmmount;

	//bi-directional many-to-one association to BalanceSheet
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="balance_sheet")
	private BalanceSheet balanceSheetBean;

	//bi-directional many-to-one association to PaymentType
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="payment_type")
	private PaymentType paymentTypeBean;

	//bi-directional many-to-one association to Currency
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="currency")
	private Currency currencyBean;

    public DebitMaster() {
    }

	public int getIddebitMaster() {
		return this.iddebitMaster;
	}

	public void setIddebitMaster(int iddebitMaster) {
		this.iddebitMaster = iddebitMaster;
	}

	public Timestamp getInsertTime() {
		return this.insertTime;
	}

	public void setInsertTime(Timestamp insertTime) {
		this.insertTime = insertTime;
	}

	public BigDecimal getPaymentAmmount() {
		return this.paymentAmmount;
	}

	public void setPaymentAmmount(BigDecimal paymentAmmount) {
		this.paymentAmmount = paymentAmmount;
	}

	public BalanceSheet getBalanceSheetBean() {
		return this.balanceSheetBean;
	}

	public void setBalanceSheetBean(BalanceSheet balanceSheetBean) {
		this.balanceSheetBean = balanceSheetBean;
	}
	
	public PaymentType getPaymentTypeBean() {
		return this.paymentTypeBean;
	}

	public void setPaymentTypeBean(PaymentType paymentTypeBean) {
		this.paymentTypeBean = paymentTypeBean;
	}
	
	public Currency getCurrencyBean() {
		return this.currencyBean;
	}

	public void setCurrencyBean(Currency currencyBean) {
		this.currencyBean = currencyBean;
	}
	
}