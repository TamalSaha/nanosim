package com.nanosim.server.rpc;

import java.util.List;

import com.nanosim.client.rpc.RiskCertificateService;
import com.nanosim.model.RiskCertificate;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class RiskCertificateServiceImpl extends RemoteServiceServlet implements RiskCertificateService {

	@Override
	public List<RiskCertificate> getRiskReductionOptions() {
		// TODO Auto-generated method stub
		return null;
	}
}
