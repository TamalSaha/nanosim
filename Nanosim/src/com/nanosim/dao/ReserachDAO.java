package com.nanosim.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.nanosim.model.Research;
import com.nanosim.model.ResearchType;
import com.nanosim.util.ISqlHelper;

public class ReserachDAO {

	ISqlHelper sqlHelper = ISqlHelper.Factory.getInstance();

	public List<ResearchType> getResearchTypeTitles(int groupId) {
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

	public List<Research> getCompletedResearch(int groupId) {
		List<Research> research = new ArrayList<Research>();
		ResultSet rs = null;
		try {
			rs = sqlHelper
					.executeQuery(
							"SELECT A.*, B.name group_name, C.title, (Select count(*) from hires_aid where hiring_group = A.group_id AND research_type_id = A.research_type_id) hires_aid_count FROM research A INNER JOIN groups B ON A.group_id = B.group_id inner join research_types C ON A.research_type_id = C.research_type_id WHERE A.group_id = ? and A.owns_research = 'y' ORDER BY B.name ASC",
							groupId);

			Research r = null;
			while (rs.next()) {
				r = new Research();

				r.setResearchId(rs.getInt("research_id"));
				r.setSubmitted(rs.getDate("submitted"));
				r.setGroupId(rs.getInt("group_id"));
				r.setGroupName(rs.getString("group_name"));
				r.setResearchTypeId(rs.getInt("research_type_id"));
				r.setResearchTypeTitle(rs.getString("title"));
				r.setTitle(rs.getString("title"));
				r.setRiskReducion(rs.getInt("hires_aid_count") <= 0 ? "No"
						: "Yes");
				r.setResearchProposal(rs.getString("research_proposal"));

				research.add(r);
			}
		} catch (Exception e) {
		} finally {
			if (rs != null)
				sqlHelper.close();
		}
		return research;
	}

	public List<Research> getIncompleteResearch(int groupId) {
		List<Research> research = new ArrayList<Research>();
		ResultSet rs = null;
		try {
			rs = sqlHelper
					.executeQuery(
							"SELECT A.*, B.title, (Select count(*) from hires_aid where hiring_group = A.group_id AND research_type_id = A.research_type_id) hires_aid_count FROM research A inner join research_types B ON A.research_type_id = B.research_type_id WHERE group_id = ? AND owns_research = 'n' ORDER BY submitted DESC",
							groupId);

			Research r = null;
			while (rs.next()) {
				r = new Research();

				r.setResearchId(rs.getInt("research_id"));
				r.setSubmitted(rs.getDate("submitted"));
				r.setGroupId(rs.getInt("group_id"));
				r.setGroupName(rs.getString("group_name"));
				r.setResearchTypeId(rs.getInt("research_type_id"));
				r.setResearchTypeTitle(rs.getString("title"));
				r.setTitle(rs.getString("title"));
				r.setRiskReducion(rs.getInt("hires_aid_count") <= 0 ? "No"
						: "Yes");
				r.setResearchProposal(rs.getString("research_proposal"));

				research.add(r);
			}
		} catch (Exception e) {
		} finally {
			if (rs != null)
				sqlHelper.close();
		}
		return research;
	}

	public List<Research> getAllCurrentResearch() {
		List<Research> research = new ArrayList<Research>();
		ResultSet rs = null;
		try {
			rs = sqlHelper
					.executeQuery("SELECT A.*, B.name group_name, C.title, (Select count(*) from hires_aid where hiring_group = A.group_id AND research_type_id = A.research_type_id) hires_aid_count FROM research A INNER JOIN groups B ON A.group_id = B.group_id inner join research_types C ON A.research_type_id = C.research_type_id WHERE A.owns_research = 'y' ORDER BY B.name");

			Research r = null;
			while (rs.next()) {
				r = new Research();

				r.setResearchId(rs.getInt("research_id"));
				r.setSubmitted(rs.getDate("submitted"));
				r.setGroupId(rs.getInt("group_id"));
				r.setGroupName(rs.getString("group_name"));
				r.setResearchTypeId(rs.getInt("research_type_id"));
				r.setResearchTypeTitle(rs.getString("title"));
				r.setTitle(rs.getString("title"));
				r.setRiskReducion(rs.getInt("hires_aid_count") <= 0 ? "No"
						: "Yes");
				r.setResearchProposal(rs.getString("research_proposal"));

				research.add(r);
			}
		} catch (Exception e) {
		} finally {
			if (rs != null)
				sqlHelper.close();
		}
		return research;
	}

	public Research getResearchItem(int researchId) {
		ResultSet rs = null;
		try {
			rs = sqlHelper
					.executeQuery(
							"SELECT A.*, B.title, C.name group_name FROM research A inner join research_types B on A.research_type_id = B.research_type_id inner join groups C on  A.group_id = C.group_id WHERE research_id = ? order by C.name",
							researchId);

			Research r = null;
			if (rs.next()) {
				r = new Research();

				r.setResearchId(rs.getInt("research_id"));
				r.setResearchTypeTitle(rs.getString("title"));
				r.setGroupName(rs.getString("group_name"));
				r.setSubmitted(rs.getDate("submitted"));
				r.setResearchProposal(rs.getString("research_proposal"));

				// private String title;
				// private float cost;
				// private String failed;
				// private String failedMessage;
				// private int failedTimeLeft;
				// private int groupId;
				// private String groupName;
				// private String ownsResearch;
				// private String patented;
				// private String researchProposal;
				// private String researchSources;
				// private int researchTypeId;
				// private String researchTypeTitle;
				// private Date submitted;
				// private int timeLeft;

			}
			return r;
		} catch (Exception e) {
			return null;
		} finally {
			if (rs != null)
				sqlHelper.close();
		}
	}

	public List<ResearchType> getAvailableResearch() {
		List<ResearchType> research = new ArrayList<ResearchType>();
		ResultSet rs = null;
		try {
			rs = sqlHelper
					.executeQuery("SELECT * FROM research_types ORDER BY title ASC");

			ResearchType r = null;
			while (rs.next()) {
				r = new ResearchType();

				r.setResearchTypeId(rs.getInt("research_type_id"));
				r.setTitle(rs.getString("title"));
				r.setAndParents(rs.getString("and_parents"));
				r.setOrParents(rs.getString("or_parents"));
				r.setCost(rs.getInt("cost"));

				research.add(r);
			}
		} catch (Exception e) {
		} finally {
			if (rs != null)
				sqlHelper.close();
		}
		return research;
	}

	public boolean getDependentResearch(int groupId, int parentId) {
		ResultSet rs = null;
		try {
			rs = sqlHelper
					.executeQuery(
							"SELECT count(*) result_count FROM research WHERE group_id = ? AND research_type_id = ? AND owns_research = 'y'",
							groupId, parentId);
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

	public boolean alreadyHasResearch(int groupId, int researchTypeId) {
		ResultSet rs = null;
		try {
			rs = sqlHelper
					.executeQuery(
							"SELECT count(*) result_count FROM research WHERE group_id = ? AND research_type_id = ? AND failed = 'n'",
							groupId, researchTypeId);
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

	public ResearchType getResearchTopic(int researchTypeId) {
		ResultSet rs = null;
		try {
			rs = sqlHelper.executeQuery(
					"SELECT * FROM research_types WHERE research_type_id = ?",
					researchTypeId);

			ResearchType r = null;
			if (rs.next()) {
				r = new ResearchType();

				r.setResearchTypeId(rs.getInt("research_type_id"));
				r.setTitle(rs.getString("title"));
				r.setAndParents(rs.getString("and_parents"));
				r.setOrParents(rs.getString("or_parents"));
				r.setCost(rs.getInt("cost"));
				r.setDuration(rs.getInt("duration"));
				r.setFacilityReq(rs.getString("facility_req"));
				r.setLevel(rs.getInt("level"));
			}
			return r;
		} catch (Exception e) {
			return null;
		} finally {
			if (rs != null)
				sqlHelper.close();
		}
	}

	public int insertResearch(Research r) {
		try {
			int retVal = sqlHelper
					.executeUpdate(
							"INSERT INTO research (group_id, research_type_id, submitted, time_left, cost, research_proposal, research_sources) VALUES (?, ?, NOW(), ?, ?, ?, ?)",
							r.getGroupId(), r.getResearchTypeId(), r
									.getTimeLeft(), r.getCost(), r
									.getResearchProposal(), r
									.getResearchSources());
			return retVal == -1 ? null : 1;
		} catch (Exception e) {
			return -1;
		}
	}

	public int getResearchLevelCount(int groupId, int researchLevel) {
		ResultSet rs = null;
		try {
			rs = sqlHelper
					.executeQuery(
							"SELECT COUNT(*) result_count FROM research INNER JOIN research_types ON research.research_type_id = research_types.research_type_id WHERE research_types.level = ? AND research.group_id = ? and research.owns_research = 'y'",
							researchLevel, groupId);
			if (rs.next()) {
				return rs.getInt("result_count");
			}
			return 0;
		} catch (Exception e) {
			return 0;
		} finally {
			if (rs != null)
				sqlHelper.close();
		}
	}

	public boolean checkLevelRequirement(int rid, int groupId) {
		boolean meetsReq = true;
		int researchLevel = getResearchTopic(rid).getLevel();
		if (researchLevel == 2) {
			int levelOnes = getResearchLevelCount(1, groupId);
			if (levelOnes < 2)
				meetsReq = false;
		}
		if (researchLevel == 3) {
			int levelOnes = getResearchLevelCount(1, groupId);
			int levelTwos = getResearchLevelCount(2, groupId);
			if (levelOnes < 4 || levelTwos < 2)
				meetsReq = false;
		}
		return meetsReq;
	}

	public int getTimeLeft(int groupId) {
		ResultSet rs = null;
		try {
			rs = sqlHelper
					.executeQuery(
							"SELECT SUM(time_left) result_sum FROM research WHERE group_id = $group_id AND time_left > 0",
							groupId);
			if (rs.next()) {
				return rs.getInt("result_sum");
			}
			return 0;
		} catch (Exception e) {
			return 0;
		} finally {
			if (rs != null)
				sqlHelper.close();
		}
	}
}