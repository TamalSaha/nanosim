package com.nanosim.client.rpc;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.nanosim.model.Patent;

@RemoteServiceRelativePath("PatentService")
public interface PatentService extends RemoteService {
	/**
	 * Utility class for simplifying access to the instance of async service.
	 */
	public static class Util {
		private static PatentServiceAsync instance;

		public static PatentServiceAsync getInstance() {
			if (instance == null) {
				instance = GWT.create(PatentService.class);
			}
			return instance;
		}
	}

	List<Patent> getGroupPatents(long groupId);
	List<Patent> getApprovedPatents();
	List<Patent> getNewPatents();
}
