package com.nanosim.client.rpc;

import java.util.List;

import com.nanosim.model.Patent;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface PatentServiceAsync {
	void getGroupPatents(int groupId, AsyncCallback<List<Patent>> callback);
	void getApprovedPatents(AsyncCallback<List<Patent>> callback);
	void getNewPatents(AsyncCallback<List<Patent>> callback);
	
	void submitPatent(Patent item, AsyncCallback<Integer> callback);
	void approvePatent(Patent item, AsyncCallback<Integer> callback);
}
