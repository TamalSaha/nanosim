package com.nanosim.server;

import java.util.List;

import com.nanosim.client.rpc.ResearchService;
import com.nanosim.dao.ReserachDAO;
import com.nanosim.model.ResearchType;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class ResearchServiceImpl extends RemoteServiceServlet implements
		ResearchService {

	private static final long serialVersionUID = -2609545942427200512L;

	@Override
	public List<ResearchType> getCompletedResearch(long groupId) {
		try {
			ReserachDAO reserachDao = new ReserachDAO();
			return reserachDao.getCompletedResearch(groupId);
		} catch (Exception e) {
			return null;
		}
	}
}
