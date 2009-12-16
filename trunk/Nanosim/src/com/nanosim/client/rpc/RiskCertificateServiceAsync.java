package com.nanosim.client.rpc;

import java.util.List;

import com.nanosim.model.RiskCertificate;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface RiskCertificateServiceAsync {
	void getRiskReductionOptions(AsyncCallback<List<RiskCertificate>> callback);
}
