package com.nanosim.server;

import java.util.List;

import com.nanosim.client.rpc.GroupService;
import com.nanosim.dao.GroupDAO;
import com.nanosim.model.Group;
import com.nanosim.model.GroupType;
import com.nanosim.model.Person;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class GroupServiceImpl extends RemoteServiceServlet implements
		GroupService {

	private static final long serialVersionUID = 1599752535430264095L;

	@Override
	public Group getGroup(int groupId) {
		try {
			GroupDAO groupDao = new GroupDAO();
			return groupDao.getGroupById(groupId);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public GroupType getGroupType(int groupTypeId) {
		try {
			GroupDAO groupDao = new GroupDAO();
			return groupDao.getGroupTypeById(groupTypeId);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public int updateObjective(String obj, int groupId) {
		try {
			GroupDAO groupDao = new GroupDAO();
			return groupDao.updateObjective(obj, groupId);
		} catch (Exception e) {
			return -1;
		}
	}

	@Override
	public List<Group> getGroups() {
		try {
			GroupDAO groupDao = new GroupDAO();
			return groupDao.getGroups();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<Person> getMembers(int groupId) {
		try {
			GroupDAO groupDao = new GroupDAO();
			return groupDao.getMembers(groupId);
		} catch (Exception e) {
			return null;
		}
	}
}
