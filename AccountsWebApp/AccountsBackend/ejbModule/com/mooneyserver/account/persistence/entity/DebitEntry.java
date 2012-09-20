package com.mooneyserver.account.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;

import com.mooneyserver.account.persistence.service.accounts.IDebitCredit;

import java.sql.Timestamp;
import java.util.Date;
import java.math.BigDecimal;


/**
 * The persistent class for the debit_master database table.
 * 
 */
@Entity
@Table(name="debit_master")
public class DebitEntry implements Serializable, IDebitCredit {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="iddebit_master")
	private int iddebitMaster;
	
	@Column(name="monthly")
	private boolean monthlyDebit;

	@Column(name="insert_time")
	private Timestamp insertTime;

	@Column(name="payment_ammount")
	private BigDecimal paymentAmmount;

	//bi-directional many-to-one association to BalanceSheet
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="balance_sheet")
	private BalanceSheet balanceSheetBean;

	//bi-directional many-to-one association to PaymentType
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="payment_type")
	private PaymentType paymentTypeBean;

	//bi-directional many-to-one association to Currency
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="currency")
	private Currency currencyBean;

	@Column(name="description")
	private String description;
	
    public DebitEntry() {
    }

	public int getIddebitMaster() {
		return this.iddebitMaster;
	}
	public void setIddebitMaster(int iddebitMaster) {
		this.iddebitMaster = iddebitMaster;
	}
	
	public boolean isMonthly() {
		return monthlyDebit;
	}
	public void setMonthly(boolean bool) {
		monthlyDebit = bool;
	}

	public Date getInsertTime() {
		return new Date(insertTime.getTime());
	}
	public void setInsertTime(Date date) {
		insertTime = new Timestamp(date.getTime());
	}

	public BigDecimal getPaymentAmmount() {
		return this.paymentAmmount;
	}
	public void setPaymentAmmount(BigDecimal paymentAmmount) {
		this.paymentAmmount = paymentAmmount;
	}

	public BalanceSheet getBalanceSheet() {
		return this.balanceSheetBean;
	}
	public void setBalanceSheet(BalanceSheet balanceSheetBean) {
		this.balanceSheetBean = balanceSheetBean;
	}
	
	public PaymentType getPaymentType() {
		return this.paymentTypeBean;
	}
	public void setPaymentType(PaymentType paymentTypeBean) {
		this.paymentTypeBean = paymentTypeBean;
	}
	
	public Currency getCurrency() {
		return this.currencyBean;
	}
	public void setCurrency(Currency currencyBean) {
		this.currencyBean = currencyBean;
	}
	
	
	public String getDescription() {
		return description == null ? "" : description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}