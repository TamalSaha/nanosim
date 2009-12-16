package com.nanosim.model;

import java.io.Serializable;

public class Person implements Serializable {

	private static final long serialVersionUID = -779653421827193669L;

	private int personId;

	private String name;

	private int groupId;

	public int getPersonId() {
		return personId;
	}

	public void setPersonId(int personId) {
		this.personId = personId;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return name.toLowerCase() + "@nanosim.com";
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
}
