package com.nanosim.client.rpc;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.nanosim.model.Nanopost;
import com.nanosim.model.NanopostComment;

public interface NanopostServiceAsync {

	void getNews(AsyncCallback<List<Nanopost>> callback);
	void getComments(int nanopostId, AsyncCallback<List<NanopostComment>> callback);
}
