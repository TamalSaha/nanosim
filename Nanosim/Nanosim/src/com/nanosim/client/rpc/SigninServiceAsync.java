package com.nanosim.client.rpc;

import com.nanosim.model.Person;
import com.google.gwt.user.client.rpc.AsyncCallback;


public interface SigninServiceAsync {
	void signin(String userName, String password, AsyncCallback<Person> callback);
	void changePassword(int personId, String newPassword,
			String oldPassword, AsyncCallback<Integer> callback);
	void sendNotes(int personId, String notes,
			AsyncCallback<Integer> asyncCallback);
}
