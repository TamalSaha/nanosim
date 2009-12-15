package com.nanosim.client.rpc;

import java.util.List;

import com.nanosim.model.Research;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ResearchServiceAsync {
	void getCompletedResearch(long groupId, AsyncCallback<List<Research>> callback);
}
