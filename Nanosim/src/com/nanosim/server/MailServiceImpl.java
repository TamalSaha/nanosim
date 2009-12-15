package com.nanosim.server;

import java.util.List;

import com.nanosim.client.rpc.MailService;
import com.nanosim.dao.MailDAO;
import com.nanosim.model.Mail;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class MailServiceImpl extends RemoteServiceServlet implements MailService {

	private static final long serialVersionUID = -8255516572529415733L;

	@Override
	public List<Mail> getMail(long groupId) {
		try {
			MailDAO mailDao = new MailDAO();
			return mailDao.getMail(groupId);
		} catch (Exception e) {
			return null;
		}
	}
}