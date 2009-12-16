package com.nanosim.dao;

import java.sql.ResultSet;

import com.nanosim.model.Group;
import com.nanosim.model.GroupType;
import com.nanosim.util.ISqlHelper;

public class GroupDAO {

	ISqlHelper sqlHelper = ISqlHelper.Factory.getInstance();

	public Group getGroupById(int groupId) {
		ResultSet rs = null;
		try {
			rs = sqlHelper.executeQuery(String.format(
					"select * from groups where group_id = %s", groupId));
			Group g = null;
			if (rs.next()) {
				g = new Group();
				g.setGroupId(groupId);
				g.setName(rs.getString("name"));
				g.setGroupTypeId(rs.getInt("group_type_id"));
				g.setObjective(rs.getString("objective"));
				g.setBudget(rs.getDouble("budget"));
			}
			return g;
		} catch (Exception e) {
			return null;
		} finally {
			if (rs != null)
				sqlHelper.close();
		}
	}

	public GroupType getGroupTypeById(int groupTypeId) {
		ResultSet rs = null;
		try {
			rs = sqlHelper.executeQuery(String.format(
					"select * from group_types where group_type_id = %s",
					groupTypeId));
			GroupType g = null;
			if (rs.next()) {
				g = new GroupType();
				g.setGroupTypeId(groupTypeId);
				g.setName(rs.getString("name"));
				g.setHasBusiness(rs.getString("has_business").equals("y"));
				g.setHasCertificates(rs.getString("has_certificates").equals(
						"y"));
				g.setHasFacilities(rs.getString("has_facilities").equals("y"));
				g.setHasReview(rs.getString("has_review").equals("y"));
				g.setNewspaper(rs.getString("newspaper").equals("y"));
				g.setResearchAvailability(rs.getString("research_availability")
						.equals("y"));
				g.setViewAllBudgets(rs.getString("view_all_budgets")
						.equals("y"));

				/* Profile Section */
				g.setHasProfile(rs.getString("has_profile").equals("y"));
				g.setHasProfileMission(rs.getString("has_profile_mission")
						.equals("y"));

				/* Patent Section */
				g.setHasPatents(rs.getString("has_patents").equals("y"));
				g.setHasPatentsGroupPatents(rs.getString(
						"has_patents_group_patents").equals("y"));
				g.setHasPatentsAllApproved(rs.getString(
						"has_patents_all_approved").equals("y"));
				g.setHasPatentsNewSubmission(rs.getString(
						"has_patents_new_submission").equals("y"));
				g.setHasPatentsApproveSubmission(rs.getString(
						"has_patents_approve_submission").equals("y"));
				
				/* Research Section */
				g.setHasResearch(rs.getString("has_research").equals("y"));
				g.setHasResearchCompleted(rs.getString("has_research_completed").equals("y"));
				g.setHasResearchIncomplete(rs.getString("has_research_incomplete").equals("y"));
				g.setHasResearchAllCurrent(rs.getString("has_research_allcurrent").equals("y"));
			}
			return g;
		} catch (Exception e) {
			return null;
		} finally {
			if (rs != null)
				sqlHelper.close();
		}
	}

	public int updateObjective(String obj) {
		try {
			int retVal = sqlHelper.executeUpdate(
					"update groups set objective = ?", obj == null ? "" : obj);
			return retVal;
		} catch (Exception e) {
			return -1;
		}
	}
}
