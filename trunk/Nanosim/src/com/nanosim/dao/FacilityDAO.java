package com.nanosim.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.nanosim.model.Research;
import com.nanosim.model.ResearchType;
import com.nanosim.util.ISqlHelper;

public class FacilityDAO {

	ISqlHelper sqlHelper = ISqlHelper.Factory.getInstance();

	public boolean hasFacility(int groupId, int facilityTypeId) {
		ResultSet rs = null;
		try {
			rs = sqlHelper
					.executeQuery(
							"SELECT count(*) result_count FROM facilities WHERE group_id = ? AND facility_type_id = ? AND time_left <=0 AND failed='n'",
							groupId, facilityTypeId);
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
