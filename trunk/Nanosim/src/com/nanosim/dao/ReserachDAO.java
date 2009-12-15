package com.nanosim.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.nanosim.model.Research;
import com.nanosim.model.ResearchType;
import com.nanosim.util.ISqlHelper;

public class ReserachDAO {

	ISqlHelper sqlHelper = ISqlHelper.Factory.getInstance();

	public List<ResearchType> getCompletedResearch(long groupId) {
		List<ResearchType> research = new ArrayList<ResearchType>();
		ResultSet rs = null;
		try {
			rs = sqlHelper
					.executeQuery(
							"SELECT distinct B.* FROM research A inner join research_types B on A.research_type_id = B.research_type_id WHERE A.group_id = ? AND A.owns_research = 'y' ORDER BY submitted DESC",
							groupId);

			ResearchType r = null;
			while (rs.next()) {
				r = new ResearchType();

				r.setResearchTypeId(rs.getInt("research_type_id"));
				r.setTitle(rs.getString("title"));

				research.add(r);
			}
		} catch (Exception e) {
		} finally {
			if (rs != null)
				sqlHelper.close();
		}
		return research;
	}
}
