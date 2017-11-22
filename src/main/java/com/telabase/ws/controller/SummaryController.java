package com.telabase.ws.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonObject;
import com.telabase.ds.dao.CareGiverDAO;
import com.telabase.ds.dao.EmCenterDAO;
import com.telabase.ds.dao.EmRequestDAO;
import com.telabase.ds.dao.PatientDAO;
import com.telabase.ds.entity.EmRequest;
import com.telabase.ds.entity.RequestStatus;

@Controller
public class SummaryController {

	@RequestMapping(value = "/api/summary/entity/count", method = RequestMethod.GET)
	@ResponseBody
	public String summaryEntityCount(HttpServletRequest request) {
		PatientDAO pDAO = new PatientDAO();
		int pCount = pDAO.count();
		CareGiverDAO cDAO = new CareGiverDAO();
		int cCount = cDAO.count();
		EmCenterDAO eDAO = new EmCenterDAO();
		int eCount = eDAO.count();
		
		JsonObject obj = new JsonObject();
		obj.addProperty("patient", pCount);
		obj.addProperty("caregiver", cCount);
		obj.addProperty("emcenter", eCount);
		
		return obj.toString();
		
	}
	
	@RequestMapping(value = "/api/summary/emrequest/status/count", method = RequestMethod.GET)
	@ResponseBody
	public String summaryRequestStatusCount(HttpServletRequest request) {
		EmRequestDAO dao = new EmRequestDAO();
		JsonObject obj = new JsonObject();
		List rs;
		for(String status: RequestStatus.STATUS) {
			
			rs = dao.findEmRequestByStatus(status);
			if(rs!=null) {
				obj.addProperty(status, rs.size());
			} else
				obj.addProperty(status, 0);
		}
		
		
		return obj.toString();
		
	}
	
	
	@RequestMapping(value = "/api/summary/emrequest/latest", method = RequestMethod.GET)
	@ResponseBody
	public List<EmRequest> summaryLatestRequest(HttpServletRequest request) {
		int number = Integer.parseInt(request.getParameter("number"));
		EmRequestDAO dao = new EmRequestDAO();
		return dao.findLatestEmRequest(number);
	}
	
}
