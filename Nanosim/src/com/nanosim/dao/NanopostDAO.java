package com.nanosim.dao;

import java.sql.ResultSet;
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
	
	public List<Nanopost> getNews() {
		List<Nanopost> news = new ArrayList<Nanopost>();
		ResultSet rs = null;
		try {
			rs = sqlHelper
				.executeQuery("SELECT * FROM nanopost ORDER BY post_date DESC");
			Nanopost n = null;
			while (rs.next()) {
				n = new Nanopost();
				n.setNanopostId(rs.getInt("nanopost_id"));
				n.setBody(rs.getString("body"));
				n.setClassId(rs.getInt("class_id"));
				n.setPostDate(rs.getDate("post_date"));
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
				n.setPostDate(rs.getDate("post_date"));
				
				newsComments.add(n);
			}
		} catch (Exception e) {
		} finally {
			if (rs != null)
				sqlHelper.close();
		} 
		return newsComments;
	}

//	public int sendNews(Nanopost item) {
//		try {
//			int retVal = sqlHelper
//			.executeUpdate(
//					"INSERT INTO mail (to_group, from_group, subject, message, sent, unread) VALUES (?, ?, ?, ?, NOW(), ?)",
//					item.getToGroup(), item.getFromGroup(), item
//					.getSubject(), item.getMessage(), "y");
//			return retVal;
//		} catch (Exception e) {
//			return -1;
//		}
//	}
//	
//	public int updateUnread(Mail item) {
//		try {
//			int retVal = sqlHelper.executeUpdate(
//					"UPDATE mail SET unread = ? WHERE mail_id = ?", item.getUnread(), item.getMailId());
//			return retVal;
//		} catch (Exception e) {
//			return -1;
//		}
//	}
}