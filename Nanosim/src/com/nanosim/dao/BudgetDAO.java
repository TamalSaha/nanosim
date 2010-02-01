package com.nanosim.dao;

import java.sql.ResultSet;
//import java.sql.Time;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.Date;

//import com.nanosim.model.Budget;
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
			//sqlHelper.setAutoCommit(false);
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
}