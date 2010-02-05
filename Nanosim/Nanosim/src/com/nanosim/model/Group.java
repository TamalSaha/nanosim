package com.nanosim.model;

import java.io.Serializable;

public class Group implements Serializable {

	private static final long serialVersionUID = -8449529016272321612L;

	private int groupId;
	private double budget;
	// private int classId;
	private int groupTypeId;
	private String name;
	private String objective;

	public Group() {
	}

	public int getGroupId() {
		return this.groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public double getBudget() {
		return this.budget;
	}

	public void setBudget(double budget) {
		this.budget = budget;
	}

	// public int getClassId() {
	// return this.classId;
	// }
	//
	// public void setClassId(int classId) {
	// this.classId = classId;
	// }

	public int getGroupTypeId() {
		return this.groupTypeId;
	}

	public void setGroupTypeId(int groupTypeId) {
		this.groupTypeId = groupTypeId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getObjective() {
		return this.objective;
	}

	public void setObjective(String objective) {
		this.objective = objective;
	}
}