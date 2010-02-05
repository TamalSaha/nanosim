package com.nanosim.client.rpc;

import java.util.List;

import com.nanosim.model.Group;
import com.nanosim.model.GroupType;
import com.nanosim.model.Person;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface GroupServiceAsync {
	void getGroup(int groupId, AsyncCallback<Group> callback);
	void getGroups(AsyncCallback<List<Group>> callback);
	void getGroupType(int groupTypeId, AsyncCallback<GroupType> callback);
	void updateObjective(String obj, int groupId,
			AsyncCallback<Integer> callback);
	void getMembers(int groupId, AsyncCallback<List<Person>> callback);
}
