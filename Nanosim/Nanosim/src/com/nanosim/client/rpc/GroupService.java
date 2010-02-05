package com.nanosim.client.rpc;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.nanosim.model.Group;
import com.nanosim.model.GroupType;
import com.nanosim.model.Person;

@RemoteServiceRelativePath("GroupService")
public interface GroupService extends RemoteService {
	/**
	 * Utility class for simplifying access to the instance of async service.
	 */
	public static class Util {
		private static GroupServiceAsync instance;
		public static GroupServiceAsync getInstance(){
			if (instance == null) {
				instance = GWT.create(GroupService.class);
			}
			return instance;
		}
	}
	
	Group getGroup(int groupId);
	List<Person> getMembers(int groupId);
	List<Group> getGroups();
	GroupType getGroupType(int groupTypeId);
	int updateObjective(String obj, int groupId);
}
