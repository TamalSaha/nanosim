package com.nanosim.dao;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import com.nanosim.model.Budget;
import com.nanosim.util.ISqlHelper;

public class BudgetDAO {

	ISqlHelper sqlHelper = ISqlHelper.Factory.getInstance();

	/***************************************************************************
	Methods to access budget table
	 ***************************************************************************/

	public int insertBudget(int amount, int groupId, String purpose) {
		ResultSet rs = null;
		try {
			int retVal = -1;
			rs= sqlHelper.executeQuery("SELECT budget AS current_budget FROM groups WHERE group_id= ?", groupId);
			rs.first();

			float total = rs.getInt("current_budget") + (float)amount;
			System.out.println("In budgetDAO: " + amount);

			//			retVal = sqlHelper
			//			.executeUpdate(
			//					"INSERT INTO budgets(credit, group_id, total, purpose) VALUES (?,?,?,?)", (float)amount, groupId, total, purpose);

			retVal = sqlHelper
			.executeUpdate(
					"UPDATE groups SET budget = ? where group_id= ?", total, groupId);
			//			//sqlHelper.setAutoCommit(true);

			return retVal;
		} catch (Exception e) {
			return -1;
		}
		//		finally {
		//		if (rs != null)
		//			sqlHelper.close();
		//		}
	}

	public List<Budget> getTransactions(long groupId) {
		List<Budget> budget = new ArrayList<Budget>();
		ResultSet rs = null;
		String DATE_FORMAT = "MMM dd HH:mm";
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);

		try {
			rs = sqlHelper
			.executeQuery(
					"SELECT * FROM budgets WHERE group_id = ? ORDER BY time DESC",
					groupId);

			Budget b = null;
			while (rs.next()) {
				b = new Budget();
				b.setBudgetId(rs.getInt("budget_id"));
				String s = sdf.format(rs.getTimestamp("time")); 
				b.setTime(s);
				b.setCredit(rs.getDouble("credit"));
				b.setTotal(rs.getDouble("total"));
				b.setGroupId(rs.getInt("group_id"));
				b.setPurpose(rs.getString("purpose"));
				budget.add(b);
			}
		} catch (Exception e) {
		} finally {
			if (rs != null)
				sqlHelper.close();
		}
		return budget;
	}
}