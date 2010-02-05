package com.nanosim.dao;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import com.nanosim.model.Nanopost;
import com.nanosim.model.NanopostComment;
import com.nanosim.util.ISqlHelper;

public class NanopostDAO {

	ISqlHelper sqlHelper = ISqlHelper.Factory.getInstance();

	/***************************************************************************
	Methods to access nanopost and nanopost_comment tables
	 ***************************************************************************/

	public List<Nanopost> getPosts() {
		List<Nanopost> news = new ArrayList<Nanopost>();
		ResultSet rs = null;
		String DATE_FORMAT = "MMM dd HH:mm";
	    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
	    
		try {
			rs = sqlHelper
			.executeQuery("SELECT * FROM nanopost ORDER BY post_date DESC");
			Nanopost n = null;
			while (rs.next()) {
				n = new Nanopost();
				n.setNanopostId(rs.getInt("nanopost_id"));
				n.setBody(rs.getString("body"));
				n.setClassId(rs.getInt("class_id"));
				String s = sdf.format(rs.getTimestamp("post_date")); 
				n.setPostDate(s);
				n.setSubject(rs.getString("subject"));
				n.setWriter(rs.getString("writer"));
				news.add(n);
			}
		} catch (Exception e) {
		} finally {
			if (rs != null)
				sqlHelper.close();
		} 
		return news;
	}

	public List<NanopostComment> getComments(int nanopostId) {
		List<NanopostComment> newsComments = new ArrayList<NanopostComment>();
		ResultSet rs = null;
		String DATE_FORMAT = "MMM dd HH:mm";
	    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
	    
		try {
			rs = sqlHelper
			.executeQuery("SELECT * FROM nanopost_comments WHERE nanopost_id = ? ORDER BY post_date ASC", nanopostId);
			NanopostComment n = null;
			while (rs.next()) {
				n = new NanopostComment();
				n.setNanopostCommentId(rs.getInt("nanopost_comment_id"));
				n.setNanopostId(rs.getInt("nanopost_id"));
				n.setWriter(rs.getString("writer"));
				n.setComment(rs.getString("comment"));
				String s = sdf.format(rs.getTimestamp("post_date")); 
				n.setPostDate(s);

				newsComments.add(n);
			}
		} catch (Exception e) {
		} finally {
			if (rs != null)
				sqlHelper.close();
		} 
		return newsComments;
	}

	public int sendComment(NanopostComment item) {
		try {
			int retVal = sqlHelper
			.executeUpdate(
					"INSERT INTO nanopost_comments (nanopost_id, writer, comment, post_date) VALUES (?, ?, ?, NOW())",
					item.getNanopostId(), item.getWriter(), item.getComment());
			return retVal;
		} catch (Exception e) {
			return -1;
		}
	}

	public int sendPost(Nanopost item) {
		try {
			int retVal = sqlHelper
			.executeUpdate(
					"INSERT INTO nanopost (class_id, writer, subject, body, post_date) VALUES (?, ?, ?, ?, NOW())",
					item.getClassId(), item.getWriter(), item.getSubject(), item.getBody());
			return retVal;
		} catch (Exception e) {
			return -1;
		}
	}

	public int deletePost(Nanopost item) {
		try {
			int retVal = sqlHelper
			.executeUpdate(
					"DELETE FROM nanopost WHERE nanopost_id = ?",
					item.getNanopostId());
			return retVal;
		} catch (Exception e) {
			return -1;
		}
	}
	
	public int editPost(Nanopost item) {
		try {
			int retVal = sqlHelper
			.executeUpdate(
					"UPDATE nanopost SET subject = ?, body = ?, post_date = NOW() WHERE nanopost_id = ?",
					item.getSubject(), item.getBody(), item.getNanopostId());
			return retVal;
		} catch (Exception e) {
			return -1;
		}
	}
}