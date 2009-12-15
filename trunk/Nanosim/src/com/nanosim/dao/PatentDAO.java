package com.nanosim.dao;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.nanosim.model.Patent;
import com.nanosim.util.ISqlHelper;

public class PatentDAO {

	ISqlHelper sqlHelper = ISqlHelper.Factory.getInstance();

	public List<Patent> getGroupPatents(long groupId) {
		List<Patent> patents = new ArrayList<Patent>();
		ResultSet rs = null;
		try {
			rs = sqlHelper
					.executeQuery(
							"SELECT * FROM patents WHERE group_id = ? ORDER BY submitted DESC",
							groupId);
			Patent p = null;
			while (rs.next()) {
				p = new Patent();

				/*
				 * `patent_id` `class_id` `group_id` `research_type_id`
				 * `proposal` `submitted` `approved` `response`
				 */
				p.setPatentId(rs.getLong("patent_id"));
				p.setGroupId(rs.getLong("group_id"));
				p.setResearchTypeId(rs.getInt("research_type_id"));
				p.setProposal(rs.getString("proposal"));
				p.setSubmitted(rs.getDate("submitted"));
				p.setApproved(rs.getString("approved"));
				p.setResponse(rs.getString("response"));
				patents.add(p);
			}
		} catch (Exception e) {
		} finally {
			if (rs != null)
				sqlHelper.close();
		}
		return patents;
	}

	public List<Patent> getApprovedPatents() {
		List<Patent> patents = new ArrayList<Patent>();
		ResultSet rs = null;
		try {
			rs = sqlHelper
					.executeQuery("SELECT A.*, B.name groupName, C.title research_title FROM patents A inner join groups B ON A.group_id = B.group_id inner join research_types C ON A.research_type_id = C.research_type_id ORDER BY submitted DESC");
			Patent p = null;
			while (rs.next()) {
				p = new Patent();

				/*
				 * `patent_id` `class_id` `group_id` `research_type_id`
				 * `proposal` `submitted` `approved` `response`
				 */
				p.setPatentId(rs.getLong("patent_id"));
				p.setGroupId(rs.getLong("group_id"));
				p.setGroupName(rs.getString("groupName"));
				p.setResearchTypeId(rs.getInt("research_type_id"));
				p.setResearchTitle(rs.getString("research_title"));
				p.setProposal(rs.getString("proposal"));
				p.setSubmitted(rs.getDate("submitted"));
				p.setApproved(rs.getString("approved"));
				p.setResponse(rs.getString("response"));
				patents.add(p);
			}
		} catch (Exception e) {
		} finally {
			if (rs != null)
				sqlHelper.close();
		}
		return patents;
	}

	public List<Patent> getNewPatents() {
		List<Patent> patents = new ArrayList<Patent>();
		ResultSet rs = null;
		try {
			rs = sqlHelper
					.executeQuery("SELECT * FROM patents LEFT JOIN groups ON patents.group_id=groups.group_id ORDER BY submitted DESC LIMIT 10");
			Patent p = null;
			while (rs.next()) {
				p = new Patent();

				/*
				 * `patent_id` `class_id` `group_id` `research_type_id`
				 * `proposal` `submitted` `approved` `response`
				 */
				p.setPatentId(rs.getLong("patent_id"));
				p.setGroupId(rs.getLong("group_id"));
				p.setResearchTypeId(rs.getInt("research_type_id"));
				p.setProposal(rs.getString("proposal"));
				p.setSubmitted(rs.getDate("submitted"));
				p.setApproved(rs.getString("approved"));
				p.setResponse(rs.getString("response"));
				patents.add(p);
			}
		} catch (Exception e) {
		} finally {
			if (rs != null)
				sqlHelper.close();
		}
		return patents;
	}

	public int submitPatent(Patent item) {
		try {
			int retVal = sqlHelper
					.executeUpdate(
							"INSERT INTO patents (group_id, class_id, research_type_id, proposal, submitted) VALUES (?, ?, ?, ?, NOW())",
							item.getGroupId(), item.getClassId(), item
									.getResearchTypeId(), item.getProposal(),
							item.getSubmitted());
			return retVal;
		} catch (Exception e) {
			return -1;
		}
	}

	public int approvePatent(Patent item) {
		try {
			int retVal = sqlHelper
					.executeUpdate(
							"UPDATE patents SET approved = ?, response = ? WHERE patent_id = ?",
							item.getApproved(), item.getResponse(), item
									.getPatentId());
			return retVal;
		} catch (Exception e) {
			return -1;
		}
	}

}
