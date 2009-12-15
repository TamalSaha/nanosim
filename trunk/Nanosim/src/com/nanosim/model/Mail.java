package com.nanosim.model;

import java.io.Serializable;

import java.util.Date;

public class Mail implements Serializable {

	private static final long serialVersionUID = 1662363742565681819L;
	
	private int mailId;
	private int toGroup;	
	private String toGroupName;	
	private int fromGroup;
	private String fromGroupName;
	private String subject;
	private String message;
	private Date sent;
	private String unread;

	public Mail() {
	}

	public int getMailId() {
		return this.mailId;
	}

	public void setMailId(int mailId) {
		this.mailId = mailId;
	}

	public int getToGroup() {
		return this.toGroup;
	}

	public void setToGroup(int toGroup) {
		this.toGroup = toGroup;
	}
	
	public int getFromGroup() {
		return this.fromGroup;
	}

	public void setFromGroup(int fromGroup) {
		this.fromGroup = fromGroup;
	}
	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
		
	public Date getSent() {
		return this.sent;
	}

	public void setSent(Date sent) {
		this.sent = sent;
	}

	public String getUnread() {
		return this.unread;
	}

	public void setUnread(String unread) {
		this.unread = unread;
	}

	public void setToGroupName(String toGroupName) {
		this.toGroupName = toGroupName;
	}

	public String getToGroupName() {
		return toGroupName;
	}

	public void setFromGroupName(String fromGroupName) {
		this.fromGroupName = fromGroupName;
	}

	public String getFromGroupName() {
		return fromGroupName;
	}
}