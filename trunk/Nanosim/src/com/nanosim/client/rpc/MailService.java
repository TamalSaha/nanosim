package com.nanosim.client.rpc;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.nanosim.model.Mail;

@RemoteServiceRelativePath("MailService")
public interface MailService extends RemoteService {
	/**
	 * Utility class for simplifying access to the instance of async service.
	 */
	public static class Util {
		private static MailServiceAsync instance;
		public static MailServiceAsync getInstance(){
			if (instance == null) {
				instance = GWT.create(MailService.class);
			}
			return instance;
		}
	}
	
	List<Mail> getMail(long groupId, String type);
	
	int sendMail(Mail item);
}
