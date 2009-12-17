package com.nanosim.client.rpc;

import com.nanosim.model.Budget;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface TransferServiceAsync {
	void insertBudget(String credit, String id, String purpose, AsyncCallback<Integer> callback);
	
	void getGroupNames(AsyncCallback<String[]> callback);

}
