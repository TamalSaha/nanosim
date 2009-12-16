package com.nanosim.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.nanosim.model.Research;
import com.nanosim.model.RiskCertificate;
import com.nanosim.model.RiskCertificate;
import com.nanosim.util.ISqlHelper;

public class RiskCertificateDAO {

	ISqlHelper sqlHelper = ISqlHelper.Factory.getInstance();

	public List<RiskCertificate> getRiskReductionOptions() {
		List<RiskCertificate> risks = new ArrayList<RiskCertificate>();
		ResultSet rs = null;
		try {
			rs = sqlHelper
					.executeQuery("Select A.certificate_id, B.certificate_type_id, A.group_id, C.name group_name, A.cost, risk_reduction, title FROM risk_certificates A INNER JOIN risk_certificate_types B ON A.certificate_type_id = B.certificate_type_id INNER JOIN groups C ON A.group_id = C.group_id WHERE time_left < 1 order by A.cost");

			RiskCertificate r = null;
			while (rs.next()) {
				r = new RiskCertificate();

				r.setCertificateId(rs.getInt("certificate_id"));
				r.setGroupId(rs.getInt("group_id"));
				r.setGroupName(rs.getString("group_name"));
				r.setCost(rs.getInt("cost"));
				r.setCertificateTypeId(rs.getInt("certificate_type_id"));
				r.setRiskReduction(rs.getInt("risk_reduction"));
				r.setTitle(rs.getString("title"));

				risks.add(r);
			}
		} catch (Exception e) {
		} finally {
			if (rs != null)
				sqlHelper.close();
		}
		return risks;
	}
}
