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
							"SELECT M . * , F.name from_name, T.name to_name FROM mail M INNER JOIN groups F ON M.from_group = F.group_id INNER JOIN groups T ON M.to_group = T.group_id WHERE M.to_group = ? ORDER BY sent DESC",
							groupId);
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
	
	public int sendMail(Mail item) {
		try {
			int retVal = sqlHelper
					.executeUpdate(
							"INSERT INTO mail (mail_id, to_group, from_group, subject, message, sent, unread) VALUES (?, ?, ?, ?, ?, NOW(), ?)",
							item.getMailId(), item.getToGroup(), item.getFromGroup(), item.getSubject(),
							item.getMessage(), "y");
			return retVal;
		} catch (Exception e) {
			return -1;
		}
	}
}