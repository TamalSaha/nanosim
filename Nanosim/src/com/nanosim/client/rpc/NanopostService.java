package com.nanosim.client.rpc;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.nanosim.model.Nanopost;
import com.nanosim.model.NanopostComment;

@RemoteServiceRelativePath("NanopostService")
public interface NanopostService extends RemoteService {
	/**
	 * Utility class for simplifying access to the instance of async service.
	 */
	public static class Util {
		private static NanopostServiceAsync instance;
		public static NanopostServiceAsync getInstance(){
			if (instance == null) {
				instance = GWT.create(NanopostService.class);
			}
			return instance;
		}
	}

	List<Nanopost> getNews();
	List<NanopostComment> getComments(int nanopostId);
}
