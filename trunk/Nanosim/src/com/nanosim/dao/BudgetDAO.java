package com.nanosim.dao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.nanosim.util.ISqlHelper;

public class BudgetDAO {

	ISqlHelper sqlHelper = ISqlHelper.Factory.getInstance();

	public int insertBudget(double d, int id) {
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	        Date date = new Date();
	        String now = dateFormat.format(date);
			int retVal = sqlHelper
					.executeUpdate("INSERT Budgets SET time = ? AND credit = ? AND id = ", now, d, id);
			return retVal == -1 ? null : 1;
		} catch (Exception e) {
			System.out.println("In budgetDAO: "+ e.toString());
			return -1;
		}
	}
}