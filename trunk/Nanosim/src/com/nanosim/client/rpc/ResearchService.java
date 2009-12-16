package com.nanosim.client.rpc;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.nanosim.model.Research;
import com.nanosim.model.ResearchType;

@RemoteServiceRelativePath("ResearchService")
public interface ResearchService extends RemoteService {
	/**
	 * Utility class for simplifying access to the instance of async service.
	 */
	public static class Util {
		private static ResearchServiceAsync instance;
		public static ResearchServiceAsync getInstance(){
			if (instance == null) {
				instance = GWT.create(ResearchService.class);
			}
			return instance;
		}
	}
	
	List<Research> getCompletedResearch(int groupId);
	List<Research> getIncompleteResearch(int groupId);
	List<Research> getAllCurrentResearch();
	List<ResearchType> getResearchTypeTitles(int groupId);
	Research getResearchItem(int researchId);
	List<ResearchType> getPossibleResearch(int groupId);
}
