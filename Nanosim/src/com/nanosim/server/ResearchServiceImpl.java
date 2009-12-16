package com.nanosim.server;

import java.util.ArrayList;
import java.util.List;

import com.nanosim.client.rpc.ResearchService;
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
				hasRequirements = 1;
				andParents = researchType.getAndParents();
				orParents = researchType.getOrParents();

				if (orParents != null && !orParents.trim().equals("")) {
					String[] researchList = orParents.split(",");
					hasRequirements = 0;
					for (String parent : researchList) {
						if (parent != null && !parent.trim().equals("")) {
							if (reserachDao.getDependentResearch(groupId,
									Integer.parseInt(parent)))
								hasRequirements = 1;
						}
					}
				} else if (andParents != null && !andParents.trim().equals("")) {
					String[] researchList = andParents.split(",");
					hasRequirements = 1;
					for (String parent : researchList) {
						if (parent != null && !parent.trim().equals("")) {
							if (!reserachDao.getDependentResearch(groupId,
									Integer.parseInt(parent)))
								hasRequirements = 0;
						}
					}
				}
				if (hasRequirements == 1) {
					possibleResearch.add(researchType);
				}
			}
			return null;
		} catch (Exception e) {
			return null;
		}
	}
}