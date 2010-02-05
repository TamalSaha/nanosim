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
	public List<Nanopost> getPosts() {
		try {
			NanopostDAO newsDao = new NanopostDAO();
			return newsDao.getPosts();
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
	
	@Override
	public int sendComment(NanopostComment item) {
		try {
			NanopostDAO nanopostDao = new NanopostDAO();
			return nanopostDao.sendComment(item);
		} catch (Exception e) {
			return -1;
		}
	}

	@Override
	public int sendPost(Nanopost item) {
		try {
			NanopostDAO nanopostDao = new NanopostDAO();
			return nanopostDao.sendPost(item);
		} catch (Exception e) {
			return -1;
		}
	}
	
	@Override
	public int deletePost(Nanopost item) {
		try {
			NanopostDAO nanopostDao = new NanopostDAO();
			return nanopostDao.deletePost(item);
		} catch (Exception e) {
			return -1;
		}
	}

	@Override
	public int editPost(Nanopost item) {
		try {
			NanopostDAO nanopostDao = new NanopostDAO();
			return nanopostDao.editPost(item);
		} catch (Exception e) {
			return -1;
		}
	}
}
