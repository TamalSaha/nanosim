package com.nanosim.client.rpc;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface BudgetServiceAsync {
	
	void insertBudget(int amount, int groupId, String purpose,
			AsyncCallback<Integer> callback);

}
