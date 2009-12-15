package com.nanosim.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.nanosim.model.Mail;
import com.nanosim.util.ISqlHelper;

public class MailDAO {
	ISqlHelper sqlHelper = ISqlHelper.Factory.getInstance();
	
	public List<Mail> getMail(long groupId) {
		List<Mail> mail = new ArrayList<Mail>();
		ResultSet rs = null;
		try {
			rs = sqlHelper
					.executeQuery(
							"SELECT * FROM mail WHERE to_group = ? ORDER BY sent DESC",
							groupId);
			Mail m = null;
			if (rs.next()) {
				m = new Mail();
				m.setMailId(rs.getInt("mail_id"));
				m.setToGroup(rs.getInt("to_group"));
				m.setFromGroup(rs.getInt("to_group"));
				m.setSubject(rs.getString("subject"));
				m.setMessage(rs.getString("message"));
				m.setSent(rs.getDate("sent"));
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
}
