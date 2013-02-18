package com.mooneyserver.account.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the ccy_conversion database table.
 * 
 */
@Entity
@Table(name="ccy_conversion")
public class CcyConversion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idccy_conversion")
	private int idccyConversion;

	private BigDecimal rate;

	//bi-directional many-to-one association to Currency
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ccy1")
	private Currency currency1;

	//bi-directional many-to-one association to Currency
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ccy2")
	private Currency currency2;

    public CcyConversion() {
    }

	public int getIdccyConversion() {
		return this.idccyConversion;
	}

	public void setIdccyConversion(int idccyConversion) {
		this.idccyConversion = idccyConversion;
	}

	public BigDecimal getRate() {
		return this.rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public Currency getCurrency1() {
		return this.currency1;
	}

	public void setCurrency1(Currency currency1) {
		this.currency1 = currency1;
	}
	
	public Currency getCurrency2() {
		return this.currency2;
	}

	public void setCurrency2(Currency currency2) {
		this.currency2 = currency2;
	}
	
}