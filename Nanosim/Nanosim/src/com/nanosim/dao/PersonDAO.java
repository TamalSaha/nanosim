package com.nanosim.dao;

import com.nanosim.model.Person;
import com.nanosim.util.ISqlHelper;
import java.sql.ResultSet;

public class PersonDAO {

	ISqlHelper sqlHelper = ISqlHelper.Factory.getInstance();

	/***************************************************************************
	Method to access persons table
	***************************************************************************/
	
	//Access control
	public Person signin(String userName, String password) {
		ResultSet rs = null;
		try {
			rs = sqlHelper
			.executeQuery(
					"SELECT * FROM persons WHERE name = ? and password=?",
					userName, password);
			Person p = null;
			if (rs.next()) {
				p = new Person();
				p.setPersonId(rs.getInt("person_id"));
				p.setName(rs.getString("name"));
				p.setGroupId(rs.getInt("group_id"));
				p.setNotes(rs.getString("notes"));
			}
			return p;
		} catch (Exception e) {
			return null;
		} finally {
			if (rs != null)
				sqlHelper.close();
		}
	}

	/***************************************************************************
	Methods to access persons table
	***************************************************************************/
	
	//Change user's password
	public int changePassword(int personId, String oldPassword, String newPassword) {
		ResultSet rs = null;
		try {
			int retVal = -1;
			rs = sqlHelper
			.executeQuery(
					"SELECT * FROM persons WHERE person_id = ?", personId);

			if (rs.next() && rs.getString("password").equals(oldPassword))
			{
				retVal = sqlHelper		
				.executeUpdate(
						"UPDATE persons SET password = ? WHERE person_id = ?", newPassword, personId);
			}

			return retVal;
		} catch (Exception e) {
			return -1;
		}
	}

	public int sendNotes(int personId, String notes) {
		try {
			int retVal = sqlHelper.executeUpdate(
					"UPDATE persons SET notes = ? WHERE person_id = ?" , notes == null ? "" : notes, personId);
			return retVal;
		} catch (Exception e) {
			return -1;
		}
	}
}