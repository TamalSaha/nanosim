package com.nanosim.server;

import java.util.List;

import com.nanosim.client.rpc.MailService;
import com.nanosim.dao.MailDAO;
import com.nanosim.model.Mail;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class MailServiceImpl extends RemoteServiceServlet implements MailService {

	private static final long serialVersionUID = -8255516572529415733L;

	@Override
	public List<Mail> getMail(long groupId, String type) {
		try {
			MailDAO mailDao = new MailDAO();
			return mailDao.getMail(groupId, type);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public int sendMail(Mail item) {
		try {
			MailDAO mailDao = new MailDAO();
			return mailDao.sendMail(item);
		} catch (Exception e) {
			return -1;
		}
	}
	
	@Override
	public int updateUnread(Mail item) {
		try {
			MailDAO mailDao = new MailDAO();
			return mailDao.updateUnread(item);
		} catch (Exception e) {
			return -1;
		}
	}
}