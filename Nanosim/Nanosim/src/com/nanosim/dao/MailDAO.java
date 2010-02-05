package com.nanosim.dao;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import com.nanosim.model.Mail;
import com.nanosim.util.ISqlHelper;

public class MailDAO {
	ISqlHelper sqlHelper = ISqlHelper.Factory.getInstance();

	/***************************************************************************
	Method to access mail table
	***************************************************************************/
	
	//Gets group's mail (received or sent depending on the type)
	public List<Mail> getMail(long groupId, String type) {
		List<Mail> mail = new ArrayList<Mail>();
		ResultSet rs = null;
		String DATE_FORMAT = "MMM dd HH:mm";
	    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
	    
		try {
			if (type.equals("inbox")){
				rs = sqlHelper
				.executeQuery(
						"SELECT M . * , F.name from_name, T.name to_name FROM mail M INNER JOIN groups F ON M.from_group = F.group_id INNER JOIN groups T ON M.to_group = T.group_id WHERE M.to_group = ? ORDER BY sent DESC",
						groupId);
			}
			else if (type.equals("sent")){
				rs = sqlHelper
				.executeQuery(
						"SELECT M . * , F.name from_name, T.name to_name FROM mail M INNER JOIN groups F ON M.from_group = F.group_id INNER JOIN groups T ON M.to_group = T.group_id WHERE M.from_group = ? ORDER BY sent DESC",
						groupId);
			}
			Mail m = null;
			while (rs.next()) {
				m = new Mail();
				m.setMailId(rs.getInt("mail_id"));
				m.setToGroup(rs.getInt("to_group"));
				m.setToGroupName(rs.getString("to_name"));
				m.setFromGroup(rs.getInt("from_group"));
				m.setFromGroupName(rs.getString("from_name"));
				m.setSubject(rs.getString("subject"));
				m.setMessage(rs.getString("message"));
				String s = sdf.format(rs.getTimestamp("sent")); 
				m.setSent(s);
				m.setUnread(rs.getString("unread"));
				mail.add(m);
			}
		} catch (Exception e) {
		} finally {
			if (rs != null)
				sqlHelper.close();
		}
		return mail;
	}
	
	/***************************************************************************
	Method to modify mail table
	***************************************************************************/
	
	//Sends mail
	public int sendMail(Mail item) {
		try {
			int retVal = sqlHelper
			.executeUpdate(
					"INSERT INTO mail (to_group, from_group, subject, message, sent, unread) VALUES (?, ?, ?, ?, NOW(), ?)",
					item.getToGroup(), item.getFromGroup(), item.getSubject(), item.getMessage(), "y");
			return retVal;
		} catch (Exception e) {
			return -1;
		}
	}
	
	//Updates the Unread flag
	public int updateUnread(Mail item) {
		try {
			int retVal = sqlHelper.executeUpdate(
					"UPDATE mail SET unread = ? WHERE mail_id = ?", item.getUnread(), item.getMailId());
			return retVal;
		} catch (Exception e) {
			return -1;
		}
	}
}