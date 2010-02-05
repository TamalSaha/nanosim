package com.nanosim.server;

import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.nanosim.client.rpc.BudgetService;
import com.nanosim.dao.BudgetDAO;
import com.nanosim.model.Budget;

public class BudgetServiceImpl extends RemoteServiceServlet implements BudgetService {

	private static final long serialVersionUID = 1L;

	@Override
	public int insertBudget(int amount, int groupId, String purpose) {
		try {
			BudgetDAO budgetDao = new BudgetDAO();
			return budgetDao.insertBudget(amount, groupId, purpose);
		} catch (Exception e) {
			return -1;
		}
	}

	@Override
	public List<Budget> getTransactions(int groupId) {
		try {
			BudgetDAO budgetDao = new BudgetDAO();
			return budgetDao.getTransactions(groupId);
		} catch (Exception e) {
			return null;
		}
	}
}
