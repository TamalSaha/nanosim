package com.nanosim.model;

import java.io.Serializable;
import java.util.Date;

public class Patent implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2292634940221943566L;
	
	private long patentId;
	private String approved;
	private int classId;
	private long groupId;
	private String groupName;
	private String proposal;
	private int researchTypeId;
	private String researchTypeTitle;
	private String response;
	private Date submitted;

	public Patent() {
	}

	public long getPatentId() {
		return this.patentId;
	}

	public void setPatentId(long patentId) {
		this.patentId = patentId;
	}

	public String getApproved() {
		return this.approved;
	}

	public void setApproved(String approved) {
		this.approved = approved;
	}

	public int getClassId() {
		return this.classId;
	}

	public void setClassId(int classId) {
		this.classId = classId;
	}

	public long getGroupId() {
		return this.groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	public String getProposal() {
		return this.proposal;
	}

	public void setProposal(String proposal) {
		this.proposal = proposal;
	}

	public int getResearchTypeId() {
		return this.researchTypeId;
	}

	public void setResearchTypeId(int researchTypeId) {
		this.researchTypeId = researchTypeId;
	}

	public String getResponse() {
		return this.response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public Date getSubmitted() {
		return this.submitted;
	}

	public void setSubmitted(Date submitted) {
		this.submitted = submitted;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setResearchTitle(String researchTypeTitle) {
		this.researchTypeTitle = researchTypeTitle;
	}

	public String getResearchTitle() {
		return researchTypeTitle;
	}
}