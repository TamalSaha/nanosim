package com.nanosim.client.rpc;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.nanosim.model.Nanopost;
import com.nanosim.model.NanopostComment;

public interface NanopostServiceAsync {

	void getPosts(AsyncCallback<List<Nanopost>> callback);
	void getComments(int nanopostId, AsyncCallback<List<NanopostComment>> callback);
	void sendComment(NanopostComment item, AsyncCallback<Integer> callback);
	void sendPost(Nanopost item, AsyncCallback<Integer> callback);
	void deletePost(Nanopost item, AsyncCallback<Integer> asyncCallback);
	void editPost(Nanopost item, AsyncCallback<Integer> asyncCallback);
}
