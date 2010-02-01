package com.nanosim.model;

import java.io.Serializable;
import java.util.Date;

public class NanopostComment implements Serializable {

	private static final long serialVersionUID = -1530825851907368436L;

	private int nanopostCommentId;
	private int nanopostId;
	private String comment;
	private String writer;
	private Date postDate;

	public NanopostComment() {
	}

	public int getNanopostCommentId() {
		return this.nanopostCommentId;
	}

	public void setNanopostCommentId(int nanopostCommentId) {
		this.nanopostCommentId = nanopostCommentId;
	}

	public int getNanopostId() {
		return this.nanopostId;
	}

	public void setNanopostId(int nanopostId) {
		this.nanopostId = nanopostId;
	}

	public String getWriter() {
		return this.writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getPostDate() {
		return this.postDate;
	}

	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}
}