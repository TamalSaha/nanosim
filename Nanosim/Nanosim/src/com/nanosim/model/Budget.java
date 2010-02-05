package com.nanosim.model;

import java.io.Serializable;

public class Budget implements Serializable {

	private static final long serialVersionUID = 6710322197663774769L;

	private int budgetId;
	private String time;
	private double credit;
	private double total;
	private int groupId;
	private String purpose;
	
	public Budget() {
	}

	public Budget(String t, double c) {
		this.time = t;
		this.credit = c;
	}

	public int getBudgetId() {
		return this.budgetId;
	}

	public void setBudgetId(int budgetId) {
		this.budgetId = budgetId;
	}

	public String getTime() {
		return this.time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
	public double getCredit() {
		return this.credit;
	}

	public void setCredit(double credit) {
		this.credit = credit;
	}
	
	public double getTotal() {
		return this.total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public int getGroupId() {
		return this.groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public String getPurpose() {
		return this.purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
}