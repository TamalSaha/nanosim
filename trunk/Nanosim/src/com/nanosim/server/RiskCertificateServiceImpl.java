package com.nanosim.server;

import java.util.List;

import com.nanosim.client.rpc.RiskCertificateService;
import com.nanosim.dao.RiskCertificateDAO;
import com.nanosim.model.RiskCertificate;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class RiskCertificateServiceImpl extends RemoteServiceServlet implements
		RiskCertificateService {

	private static final long serialVersionUID = 1408969226158672754L;

	@Override
	public List<RiskCertificate> getRiskReductionOptions() {
		try {
			RiskCertificateDAO riskDAO = new RiskCertificateDAO();
			return riskDAO.getRiskReductionOptions();
		} catch (Exception e) {
			return null;
		}
	}
}
