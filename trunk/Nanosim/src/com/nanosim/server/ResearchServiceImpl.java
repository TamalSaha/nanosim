package com.nanosim.server;

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
}
