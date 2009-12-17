package com.nanosim.server;

import com.nanosim.client.rpc.TransferService;
import com.nanosim.dao.BudgetDAO;
import com.nanosim.model.Budget;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class TransferServiceImpl extends RemoteServiceServlet implements
		TransferService {

	private static final long serialVersionUID = 164797187055616290L;

	@Override
	public int insertBudget(String credit, String id, String purpose) {
		try {
			BudgetDAO budgetDao = new BudgetDAO();
			return budgetDao.insertBudget(Double.parseDouble(credit), Integer.parseInt(id), purpose);
		} catch (Exception e) {
			System.out.println("In TransferServiceImpl "+ e.toString());
			return -1;
		}
	}

	@Override
	public String[] getGroupNames() {
		// TODO Auto-generated method stub
		return null;
	}


}