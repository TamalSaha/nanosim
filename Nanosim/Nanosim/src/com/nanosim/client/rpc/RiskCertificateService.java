package com.nanosim.client.rpc;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.nanosim.model.RiskCertificate;

@RemoteServiceRelativePath("RiskCertificateService")
public interface RiskCertificateService extends RemoteService {
	/**
	 * Utility class for simplifying access to the instance of async service.
	 */
	public static class Util {
		private static RiskCertificateServiceAsync instance;
		public static RiskCertificateServiceAsync getInstance(){
			if (instance == null) {
				instance = GWT.create(RiskCertificateService.class);
			}
			return instance;
		}
	}
	
	
	List<RiskCertificate> getRiskReductionOptions();
}
