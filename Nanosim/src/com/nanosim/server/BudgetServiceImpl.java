package com.nanosim.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.nanosim.client.rpc.BudgetService;
import com.nanosim.dao.BudgetDAO;

public class BudgetServiceImpl extends RemoteServiceServlet implements BudgetService {

	private static final long serialVersionUID = 1L;

	@Override
	public int insertBudget(int amount, int groupId, String purpose) {
		try {
			BudgetDAO budgetDao = new BudgetDAO();
			return budgetDao.insertBudget(amount, groupId, purpose);
		} catch (Exception e) {
			//System.out.println("In TransferServiceImpl "+ e.toString());
			return -1;
		}
	}
}
