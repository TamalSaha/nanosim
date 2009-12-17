package com.nanosim.dao;

import java.sql.ResultSet;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.nanosim.model.Budget;
import com.nanosim.util.ISqlHelper;

public class BudgetDAO {

	ISqlHelper sqlHelper = ISqlHelper.Factory.getInstance();
	
	public String[] getGroupNames(){
		String result[];
		String resultAll = ""; 
		try {
			ResultSet names = sqlHelper.executeQuery("select name from group_types");
			while(!names.last())
				resultAll.concat(names.next()+"!");
			result = resultAll.split("!");
			return result;
		} catch (Exception e) {
			System.out.println("In budgetDAO: " + e.toString());
			return null;
		}
	}

	public int insertBudget(double d, int id, String purpose) {
		try {
			sqlHelper.setAutoCommit(false);
			ResultSet credit = sqlHelper.executeQuery("select budget AS current_budget from groups where group_id= ?", id);
			credit.first();
			float total = credit.getFloat("current_budget") + (float)d;
			int retVal = sqlHelper
					.executeUpdate(
							"insert into Budgets(credit, group_id, total, purpose) values(?,?,?,?)",	d, id,total, purpose);
			retVal += sqlHelper
			.executeUpdate(
					"UPDATE groups SET budget = ? where group_id= ?",	total, id);
			sqlHelper.setAutoCommit(true);

			return retVal == -1 ? null : 1;
		} catch (Exception e) {
//			try {
//				System.err.print("Transaction is being ");
//				System.err.println("rolled back");
//				sqlHelper.rollback();
//			} catch(SQLException excep) {
//				System.err.print("SQLException: ");
//				System.err.println(excep.getMessage());
//			}
			System.out.println("In budgetDAO: " + e.toString());
			return -1;
		}
	}
}