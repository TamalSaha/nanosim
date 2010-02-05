package com.nanosim.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.nanosim.model.Group;
import com.nanosim.model.GroupType;
import com.nanosim.model.Person;
import com.nanosim.util.ISqlHelper;

public class GroupDAO {

	ISqlHelper sqlHelper = ISqlHelper.Factory.getInstance();

	/***************************************************************************
	Methods to access groups, group_type or person tables
	***************************************************************************/
	
	//Get a group by using its groupId
	public Group getGroupById(int groupId) {
		ResultSet rs = null;
		try {
			rs = sqlHelper.executeQuery(String.format(
					"SELECT * FROM groups WHERE group_id = %s", groupId));
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

	//Get a group type by using its groupId
	public GroupType getGroupTypeById(int groupTypeId) {
		ResultSet rs = null;
		try {
			rs = sqlHelper.executeQuery(String.format(
					"SELECT * FROM group_types WHERE group_type_id = %s", groupTypeId));
			GroupType g = null;
			if (rs.next()) {
				g = new GroupType();
				g.setGroupTypeId(groupTypeId);
				g.setName(rs.getString("name"));
				g.setHasBusiness(rs.getString("has_business").equals("y"));
				g.setHasCertificates(rs.getString("has_certificates").equals("y"));
				g.setHasFacilities(rs.getString("has_facilities").equals("y"));
				g.setHasReview(rs.getString("has_review").equals("y"));
				g.setNewspaper(rs.getString("newspaper").equals("y"));
				g.setResearchAvailability(rs.getString("research_availability").equals("y"));
				g.setViewAllBudgets(rs.getString("view_all_budgets").equals("y"));

				/* Profile Section */
				g.setHasProfile(rs.getString("has_profile").equals("y"));
				g.setHasProfileMission(rs.getString("has_profile_mission").equals("y"));

				/* Patent Section */
				g.setHasPatents(rs.getString("has_patents").equals("y"));
				g.setHasPatentsGroupPatents(rs.getString("has_patents_group_patents").equals("y"));
				g.setHasPatentsAllApproved(rs.getString("has_patents_all_approved").equals("y"));
				g.setHasPatentsNewSubmission(rs.getString("has_patents_new_submission").equals("y"));
				g.setHasPatentsApproveSubmission(rs.getString("has_patents_approve_submission").equals("y"));
				
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

	//Get all Groups
	public List<Group> getGroups() {
		List<Group> groups = new ArrayList<Group>();
		ResultSet rs = null;
		try {
			rs = sqlHelper.executeQuery(String.format(
					"SELECT * FROM groups"));
			Group g = null;
			while (rs.next()) {
				g = new Group();
				g.setGroupId(rs.getInt("group_id"));
				g.setName(rs.getString("name"));
				groups.add(g);
			}
			return groups;
		} catch (Exception e) {
			return null;
		} finally {
			if (rs != null)
				sqlHelper.close();
		}
	}
	
	//Get group's members by using the group_id
	public List<Person> getMembers(int groupId) {
		List<Person> members = new ArrayList<Person>();
		ResultSet rs = null;
		try {
			rs = sqlHelper.executeQuery(String.format(
					"SELECT * FROM persons WHERE group_id = %s", groupId));
			Person m = null;
			while (rs.next()) {
				m = new Person();
				m.setPersonId(rs.getInt("person_id"));
				m.setName(rs.getString("name"));
				members.add(m);
			}
			return members;
		} catch (Exception e) {
			return null;
		} finally {
			if (rs != null)
				sqlHelper.close();
		}
	}	
	
	/***************************************************************************
	Method to modify groups table
	***************************************************************************/
	
	//Update mission objective
	public int updateObjective(String obj, int groupId) {
		try {
			int retVal = sqlHelper.executeUpdate(
					"UPDATE groups SET objective = ? WHERE group_id = ?" , obj == null ? "" : obj, groupId);
			return retVal;
		} catch (Exception e) {
			return -1;
		}
	}
}
