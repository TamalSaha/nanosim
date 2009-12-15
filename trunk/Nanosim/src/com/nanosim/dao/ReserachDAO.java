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

	public List<Research> getCompletedResearch(long groupId) {
		List<Research> research = new ArrayList<Research>();
		ResultSet rs = null;
		try {
			rs = sqlHelper
					.executeQuery(
							"SELECT A.*, B.title FROM research A inner join research_types B on A.research_type_id = B.research_type_id WHERE A.group_id = ? AND A.owns_research = 'y' ORDER BY submitted DESC",
							groupId);

			Research r = null;
			while (rs.next()) {
				r = new Research();

				r.setResearchId(rs.getLong("research_id"));
				r.setTitle(rs.getString("title"));

				/*
				 * private float cost; private String failed; private String
				 * failedMessage; private int failedTimeLeft; private int
				 * groupId; private String ownsResearch; private String
				 * patented; private String researchProposal; private String
				 * researchSources; private int researchTypeId; private Date
				 * submitted; private int timeLeft;
				 */
				// r.setn
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
