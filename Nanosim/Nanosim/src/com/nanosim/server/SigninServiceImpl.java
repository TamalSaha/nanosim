package com.nanosim.server;

import com.nanosim.client.rpc.SigninService;
import com.nanosim.dao.PersonDAO;
import com.nanosim.model.Person;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class SigninServiceImpl extends RemoteServiceServlet implements
		SigninService {

	private static final long serialVersionUID = 4809883712768767861L;

	@Override
	public Person signin(String userName, String password) {
		try {
			PersonDAO personDao = new PersonDAO();
			return personDao.signin(userName, password);
		} catch (Exception e) {
			return null;
		}
	}
	
	public int changePassword(int personId, String oldPassword, String newPassword) {
		try {
			PersonDAO personDao = new PersonDAO();
			return personDao.changePassword(personId, oldPassword, newPassword);
		} catch (Exception e) {
			return -1;
		}
	}
	
	public int sendNotes(int personId, String notes) {
		try {
			PersonDAO personDao = new PersonDAO();
			return personDao.sendNotes(personId, notes);
		} catch (Exception e) {
			return -1;
		}
	}
}
