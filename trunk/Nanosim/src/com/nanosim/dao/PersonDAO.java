package com.nanosim.dao;

import com.nanosim.model.Person;
import com.nanosim.util.ISqlHelper;
import java.sql.ResultSet;

public class PersonDAO {

	ISqlHelper sqlHelper = ISqlHelper.Factory.getInstance();

	public Person signin(String userName, String password) {
		ResultSet rs = null;
		try {
			rs = sqlHelper
					.executeQuery(
							"select * from persons where name = ? and password=?",
							userName, password);
			Person p = null;
			if (rs.next()) {
				p = new Person();
				p.setPersonId(rs.getInt("person_id"));
				p.setName(rs.getString("name"));
				p.setGroupId(rs.getInt("group_id"));
			}
			return p;
		} catch (Exception e) {
			return null;
		} finally {
			if (rs != null)
				sqlHelper.close();
		}
	}
}