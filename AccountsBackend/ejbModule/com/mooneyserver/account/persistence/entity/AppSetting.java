package com.mooneyserver.account.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the app_setting database table.
 * 
 */
@Entity
@Table(name="app_setting")
public class AppSetting implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

    @Temporal( TemporalType.TIMESTAMP)
	private Date inserted;

    @Column(name="key_name")
	private String key;

    @Temporal( TemporalType.TIMESTAMP)
	private Date updated;

	private String value;

    public AppSetting() {
    }

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getInserted() {
		return this.inserted;
	}

	public void setInserted(Date inserted) {
		this.inserted = inserted;
	}

	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Date getUpdated() {
		return this.updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}