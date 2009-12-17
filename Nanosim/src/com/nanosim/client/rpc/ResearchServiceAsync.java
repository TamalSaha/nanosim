package com.nanosim.client.rpc;

import java.util.List;

import com.nanosim.model.Research;
import com.nanosim.model.ResearchType;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ResearchServiceAsync {
	void getCompletedResearch(int groupId, AsyncCallback<List<Research>> callback);
	void getIncompleteResearch(int groupId, AsyncCallback<List<Research>> callback);
	void getAllCurrentResearch(AsyncCallback<List<Research>> callback);
	void getResearchTypeTitles(int groupId, AsyncCallback<List<ResearchType>> callback);
	void getResearchItem(int researchId, AsyncCallback<Research> callback);
	void getPossibleResearch(int groupId, AsyncCallback<List<ResearchType>> callback);
	void submitResearchProposal(Research r, AsyncCallback<String> callback);
}
