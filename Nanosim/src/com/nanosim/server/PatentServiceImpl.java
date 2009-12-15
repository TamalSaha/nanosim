package com.nanosim.server;

import java.util.List;

import com.nanosim.client.rpc.PatentService;
import com.nanosim.dao.PatentDAO;
import com.nanosim.model.Patent;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class PatentServiceImpl extends RemoteServiceServlet implements
		PatentService {

	private static final long serialVersionUID = 4663921301204293875L;

	@Override
	public List<Patent> getGroupPatents(long groupId) {
		try {
			PatentDAO patentDao = new PatentDAO();
			return patentDao.getGroupPatents(groupId);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<Patent> getApprovedPatents() {
		try {
			PatentDAO patentDao = new PatentDAO();
			return patentDao.getApprovedPatents();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<Patent> getNewPatents() {
		try {
			PatentDAO patentDao = new PatentDAO();
			return patentDao.getNewPatents();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public int submitPatent(Patent item) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int approvePatent(Patent item) {
		// TODO Auto-generated method stub
		return 0;
	}
}
