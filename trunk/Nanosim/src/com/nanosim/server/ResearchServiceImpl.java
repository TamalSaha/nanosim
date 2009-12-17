package com.nanosim.server;

import java.util.ArrayList;
import java.util.List;

import com.nanosim.client.rpc.ResearchService;
import com.nanosim.dao.BudgetDAO;
import com.nanosim.dao.FacilityDAO;
import com.nanosim.dao.FacilityUserDAO;
import com.nanosim.dao.PatentDAO;
import com.nanosim.dao.ReserachDAO;
import com.nanosim.model.Research;
import com.nanosim.model.ResearchType;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class ResearchServiceImpl extends RemoteServiceServlet implements
		ResearchService {

	private static final long serialVersionUID = -2609545942427200512L;

	@Override
	public List<Research> getCompletedResearch(int groupId) {
		try {
			ReserachDAO reserachDao = new ReserachDAO();
			return reserachDao.getCompletedResearch(groupId);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<Research> getIncompleteResearch(int groupId) {
		try {
			ReserachDAO reserachDao = new ReserachDAO();
			return reserachDao.getIncompleteResearch(groupId);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<Research> getAllCurrentResearch() {
		try {
			ReserachDAO reserachDao = new ReserachDAO();
			return reserachDao.getAllCurrentResearch();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<ResearchType> getResearchTypeTitles(int groupId) {
		try {
			ReserachDAO reserachDao = new ReserachDAO();
			return reserachDao.getResearchTypeTitles(groupId);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Research getResearchItem(int researchId) {
		try {
			ReserachDAO reserachDao = new ReserachDAO();
			return reserachDao.getResearchItem(researchId);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<ResearchType> getPossibleResearch(int groupId) {
		try {
			List<ResearchType> possibleResearch = new ArrayList<ResearchType>();
			ReserachDAO reserachDao = new ReserachDAO();
			List<ResearchType> availableResearch = reserachDao
					.getAvailableResearch();
			int hasRequirements = 1;
			String andParents;
			String orParents;
			for (ResearchType researchType : availableResearch) {
				andParents = researchType.getAndParents();
				orParents = researchType.getOrParents();

				if (orParents != null && !orParents.trim().equals("")) {
					String[] researchList = orParents.split(",");
					hasRequirements = 0;
					for (String parent : researchList) {
						if (parent != null && !parent.trim().equals("")) {
							if (reserachDao.getDependentResearch(groupId,
									Integer.parseInt(parent))) {
								hasRequirements = 1;
								break;
							}
						}
					}
				} else if (andParents != null && !andParents.trim().equals("")) {
					String[] researchList = andParents.split(",");
					hasRequirements = 1;
					for (String parent : researchList) {
						if (parent != null && !parent.trim().equals("")) {
							if (!reserachDao.getDependentResearch(groupId,
									Integer.parseInt(parent))) {
								hasRequirements = 0;
								break;
							}
						}
					}
				}
				if (hasRequirements == 1) {
					possibleResearch.add(researchType);
				}
			}
			return possibleResearch;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public String submitResearchProposal(Research r) {
		try {
			ReserachDAO reserachDao = new ReserachDAO();
			PatentDAO patentDao = new PatentDAO();
			FacilityDAO facilityDAO = new FacilityDAO();
			FacilityUserDAO facilityUserDAO = new FacilityUserDAO();
			BudgetDAO budgetDAO = new BudgetDAO();

			// Checks if group already has reaserach
			if (reserachDao.alreadyHasResearch(r.getGroupId(), r
					.getResearchTypeId()))
				return "Your group already has access to or has started that research.";

			// Checks if there is a patent on the research
			if (patentDao.alreadyHasPatent(r.getResearchTypeId()))
				return "That research has been patented.";

			ResearchType researchType = reserachDao.getResearchTopic(r
					.getResearchTypeId());

			// check if group has facility rerequisites for research
			// **EDIT HERE FOR CORE COMPETENCY
			boolean hasRequirements = true;
			String strFacilityReq = researchType.getFacilityReq();
			if (strFacilityReq != null && !strFacilityReq.trim().equals("")) {
				String[] facilityList = strFacilityReq.split(",");
				for (String strFacilityTypeId : facilityList) {
					if (strFacilityTypeId != null
							&& !strFacilityTypeId.trim().equals("")) {
						if (!facilityDAO.hasFacility(r.getGroupId(), Integer
								.parseInt(strFacilityTypeId))) {
							if (!facilityUserDAO.hasFacility(r.getGroupId(),
									Integer.parseInt(strFacilityTypeId))) {
								hasRequirements = false;
							}
						}
					}
				}
				if (hasRequirements) {
					return "Your group does not have the facilities to research $title.";
				}
			}
			hasRequirements = reserachDao.checkLevelRequirement(r
					.getResearchTypeId(), r.getGroupId());
			if (!hasRequirements) {
				return "Your group does not meet the level requirements to research $title.";
			}
			if (researchType.getLevel() == 1)
				researchType.setDuration(researchType.getDuration()
						+ reserachDao.getTimeLeft(r.getGroupId()));
			else
				r.setTimeLeft(0);

			// $duration = $duration + getTimeReduction($research_type_id,
			// $group_id);
			reserachDao.insertResearch(r);
			budgetDAO.insertBudget(r.getCost(), r.getGroupId(), "Purpose: "
					+ researchType.getTitle());

			return "";
		} catch (Exception e) {
			return e.getMessage();
		}
	}
}