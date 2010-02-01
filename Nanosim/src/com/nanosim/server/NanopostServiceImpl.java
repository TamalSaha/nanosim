package com.nanosim.server;

import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.nanosim.client.rpc.NanopostService;
import com.nanosim.dao.NanopostDAO;
import com.nanosim.model.Nanopost;
import com.nanosim.model.NanopostComment;

public class NanopostServiceImpl extends RemoteServiceServlet implements NanopostService {

	private static final long serialVersionUID = 1176131854561730908L;
	
	@Override
	public List<Nanopost> getNews() {
		try {
			NanopostDAO newsDao = new NanopostDAO();
			return newsDao.getNews();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<NanopostComment> getComments(int nanopostId) {
		try {
			NanopostDAO newsDao = new NanopostDAO();
			return newsDao.getComments(nanopostId);
		} catch (Exception e) {
			return null;
		}
	}
}
