package com.nanosim.client.rpc;

import java.util.List;

import com.nanosim.model.Mail;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface MailServiceAsync {
	void getMail(long groupId, AsyncCallback<List<Mail>> callback);
}
