package com.nanosim.model;

import java.io.Serializable;

public class GroupType implements Serializable {

	private static final long serialVersionUID = 2892477664295981025L;

	// public final String GOVERNMENT = "Government";
	//	
	// public final String FUNDING_AGENCY = "Fuding Agency";
	//	
	// public final String PRIVATE_LAB = "Private Labratory";
	// public final String PUBLIC_LAB = "Public Labratory";
	//	
	// public final String REVIEW = "Review";
	// public final String Newspaper = "Newspaper";
	// public final String RISK_REDUCTION = "Risk Reduction";

	private int groupTypeId;
	private String name;
	private boolean hasBusiness;
	private boolean hasCertificates;
	private boolean hasFacilities;
	private boolean hasReview;
	private boolean newspaper;
	private boolean researchAvailability;
	private boolean viewAllBudgets;

	public GroupType() {
	}

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

	public boolean getHasBusiness() {
		return this.hasBusiness;
	}

	public void setHasBusiness(boolean hasBusiness) {
		this.hasBusiness = hasBusiness;
	}

	public boolean getHasCertificates() {
		return this.hasCertificates;
	}

	public void setHasCertificates(boolean hasCertificates) {
		this.hasCertificates = hasCertificates;
	}

	public boolean getHasFacilities() {
		return this.hasFacilities;
	}

	public void setHasFacilities(boolean hasFacilities) {
		this.hasFacilities = hasFacilities;
	}

	public boolean getHasReview() {
		return this.hasReview;
	}

	public void setHasReview(boolean hasReview) {
		this.hasReview = hasReview;
	}

	public boolean getNewspaper() {
		return this.newspaper;
	}

	public void setNewspaper(boolean newspaper) {
		this.newspaper = newspaper;
	}

	public boolean getResearchAvailability() {
		return this.researchAvailability;
	}

	public void setResearchAvailability(boolean researchAvailability) {
		this.researchAvailability = researchAvailability;
	}

	public boolean getViewAllBudgets() {
		return this.viewAllBudgets;
	}

	public void setViewAllBudgets(boolean viewAllBudgets) {
		this.viewAllBudgets = viewAllBudgets;
	}

	/*
	 * Profile Section
	 */

	public void setHasProfile(boolean hasProfile) {
		this.hasProfile = hasProfile;
	}

	public boolean getHasProfile() {
		return hasProfile;
	}

	public void setHasProfileMission(boolean hasProfileMission) {
		this.hasProfileMission = hasProfileMission;
	}

	public boolean getHasProfileMission() {
		return hasProfileMission;
	}

	private boolean hasProfile;
	private boolean hasProfileMission;

	/*
	 * Patent Section
	 */

	public boolean getHasPatents() {
		return this.hasPatents;
	}

	public void setHasPatents(boolean hasPatents) {
		this.hasPatents = hasPatents;
	}

	public void setHasPatentsGroupPatents(boolean hasPatentsGroupPatents) {
		this.hasPatentsGroupPatents = hasPatentsGroupPatents;
	}

	public boolean getHasPatentsGroupPatents() {
		return hasPatentsGroupPatents;
	}

	public void setHasPatentsAllApproved(boolean hasPatentsAllApproved) {
		this.hasPatentsAllApproved = hasPatentsAllApproved;
	}

	public boolean getHasPatentsAllApproved() {
		return hasPatentsAllApproved;
	}

	public void setHasPatentsNewSubmission(boolean hasPatentsNewSubmission) {
		this.hasPatentsNewSubmission = hasPatentsNewSubmission;
	}

	public boolean getHasPatentsNewSubmission() {
		return hasPatentsNewSubmission;
	}

	public void setHasPatentsApproveSubmission(
			boolean hasPatentsApproveSubmission) {
		this.hasPatentsApproveSubmission = hasPatentsApproveSubmission;
	}

	public boolean getHasPatentsApproveSubmission() {
		return hasPatentsApproveSubmission;
	}

	private boolean hasPatents;
	private boolean hasPatentsGroupPatents;
	private boolean hasPatentsAllApproved;
	private boolean hasPatentsNewSubmission;
	private boolean hasPatentsApproveSubmission;

	/*
	 * Research Section
	 */

	public boolean getHasResearch() {
		return this.hasResearch;
	}

	public void setHasResearch(boolean hasResearch) {
		this.hasResearch = hasResearch;
	}

	public void setHasResearchCompleted(boolean hasResearchCompleted) {
		this.hasResearchCompleted = hasResearchCompleted;
	}

	public boolean getHasResearchCompleted() {
		return hasResearchCompleted;
	}

	public void setHasResearchIncomplete(boolean hasResearchIncomplete) {
		this.hasResearchIncomplete = hasResearchIncomplete;
	}

	public boolean getHasResearchIncomplete() {
		return hasResearchIncomplete;
	}

	public void setHasResearchAllCurrent(boolean hasResearchAllCurrent) {
		this.hasResearchAllCurrent = hasResearchAllCurrent;
	}

	public boolean getHasResearchAllCurrent() {
		return hasResearchAllCurrent;
	}

	private boolean hasResearch;
	private boolean hasResearchCompleted;
	private boolean hasResearchIncomplete;
	private boolean hasResearchAllCurrent;
}