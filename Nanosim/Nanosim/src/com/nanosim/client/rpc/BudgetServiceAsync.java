package com.nanosim.client.rpc;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.nanosim.model.Budget;

public interface BudgetServiceAsync {
	
	void insertBudget(int amount, int groupId, String purpose,
			AsyncCallback<Integer> callback);
	void getTransactions(int groupId, AsyncCallback<List<Budget>> asyncCallback);
}
