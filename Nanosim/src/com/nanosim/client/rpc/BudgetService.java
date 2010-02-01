package com.nanosim.client.rpc;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("BudgetService")
public interface BudgetService extends RemoteService {
	/**
	 * Utility class for simplifying access to the instance of async service.
	 */
	public static class Util {
		private static BudgetServiceAsync instance;
		public static BudgetServiceAsync getInstance(){
			if (instance == null) {
				instance = GWT.create(BudgetService.class);
			}
			return instance;
		}
	}
	
	int insertBudget(int amount, int groupId, String purpose);

}