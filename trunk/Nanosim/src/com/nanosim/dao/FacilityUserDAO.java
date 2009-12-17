package com.nanosim.dao;

import java.sql.ResultSet;

import com.nanosim.util.ISqlHelper;

public class FacilityUserDAO {

	ISqlHelper sqlHelper = ISqlHelper.Factory.getInstance();

	public boolean hasFacility(int groupId, int facilityId) {
		ResultSet rs = null;
		try {
			rs = sqlHelper
					.executeQuery(
							"SELECT count(*) result_count FROM facility_users WHERE group_id = ? AND facility_id = ?",
							groupId, facilityId);
			if (rs.next()) {
				return rs.getInt("result_count") > 0;
			}
			return false;
		} catch (Exception e) {
			return false;
		} finally {
			if (rs != null)
				sqlHelper.close();
		}
	}
}
