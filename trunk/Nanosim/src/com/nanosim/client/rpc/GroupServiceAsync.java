package com.nanosim.client.rpc;

import java.util.List;

import com.nanosim.model.Group;
import com.nanosim.model.GroupType;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface GroupServiceAsync {
	void getGroup(int groupId, AsyncCallback<Group> callback);
	void getGroups(AsyncCallback<List<Group>> callback);
	void getGroupType(int groupTypeId, AsyncCallback<GroupType> callback);
	void updateObjective(String obj, AsyncCallback<Integer> callback);
}
