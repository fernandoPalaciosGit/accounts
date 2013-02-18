package com.mooneyserver.account.persistence.service.accounts;

import java.math.BigDecimal;
import java.util.Date;

import com.mooneyserver.account.persistence.entity.BalanceSheet;
import com.mooneyserver.account.persistence.entity.PaymentType;

public interface IDebitCredit {
	
	public boolean isMonthly();
	public void setMonthly(boolean bool);
	
	public Date getInsertTime();
	public void setInsertTime(Date date);

	public BigDecimal getPaymentAmmount();
	public void setPaymentAmmount(BigDecimal ammount);
	
	public BalanceSheet getBalanceSheet();
	public void setBalanceSheet(BalanceSheet sheet);
	
	public PaymentType getPaymentType();
	public void setPaymentType(PaymentType type);
	
	public String getDescription();
	public void setDescription(String desc);
}
