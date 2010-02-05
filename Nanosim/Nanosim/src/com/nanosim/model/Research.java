package com.nanosim.model;

import java.io.Serializable;

public class Research implements Serializable {
	
	private static final long serialVersionUID = 6149569353449122019L;

	private int researchId;

	private String title;
	private float cost;
	private String failed;
	private String failedMessage;
	private int failedTimeLeft;
	private int groupId;
	private String groupName;
	private String ownsResearch;
	private String patented;
	private String researchProposal;
	private String researchSources;
	private int researchTypeId;
	private String researchTypeTitle;
	private String submitted;
	private int timeLeft;
	private String riskReducion;

	public Research() {
	}

	public int getResearchId() {
		return this.researchId;
	}

	public void setResearchId(int researchId) {
		this.researchId = researchId;
	}

	public float getCost() {
		return this.cost;
	}

	public void setCost(float cost) {
		this.cost = cost;
	}

	public String getFailed() {
		return this.failed;
	}

	public void setFailed(String failed) {
		this.failed = failed;
	}

	public String getFailedMessage() {
		return this.failedMessage;
	}

	public void setFailedMessage(String failedMessage) {
		this.failedMessage = failedMessage;
	}

	public int getFailedTimeLeft() {
		return this.failedTimeLeft;
	}

	public void setFailedTimeLeft(int failedTimeLeft) {
		this.failedTimeLeft = failedTimeLeft;
	}

	public int getGroupId() {
		return this.groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public String getOwnsResearch() {
		return this.ownsResearch;
	}

	public void setOwnsResearch(String ownsResearch) {
		this.ownsResearch = ownsResearch;
	}

	public String getPatented() {
		return this.patented;
	}

	public void setPatented(String patented) {
		this.patented = patented;
	}

	public String getResearchProposal() {
		return this.researchProposal;
	}

	public void setResearchProposal(String researchProposal) {
		this.researchProposal = researchProposal;
	}

	public String getResearchSources() {
		return this.researchSources;
	}

	public void setResearchSources(String researchSources) {
		this.researchSources = researchSources;
	}

	public int getResearchTypeId() {
		return this.researchTypeId;
	}

	public void setResearchTypeId(int researchTypeId) {
		this.researchTypeId = researchTypeId;
	}

	public String getSubmitted() {
		return this.submitted;
	}

	public void setSubmitted(String submitted) {
		this.submitted = submitted;
	}

	public int getTimeLeft() {
		return this.timeLeft;
	}

	public void setTimeLeft(int timeLeft) {
		this.timeLeft = timeLeft;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setResearchTypeTitle(String researchTypeTitle) {
		this.researchTypeTitle = researchTypeTitle;
	}

	public String getResearchTypeTitle() {
		return researchTypeTitle;
	}

	public void setRiskReducion(String riskReducion) {
		this.riskReducion = riskReducion;
	}

	public String getRiskReducion() {
		return riskReducion;
	}
}