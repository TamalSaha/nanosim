package com.nanosim.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import sun.security.krb5.internal.PAEncTSEnc;

import com.nanosim.model.Patent;
import com.nanosim.model.Person;
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
			if (rs.next()) {
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
					.executeQuery("SELECT * FROM patents LEFT JOIN groups ON patents.group_id=groups.group_id ORDER BY submitted DESC");
			Patent p = null;
			if (rs.next()) {
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
			if (rs.next()) {
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
			}
		} catch (Exception e) {
		} finally {
			if (rs != null)
				sqlHelper.close();
		}
		return patents;
	}
}
