
package com.telabase.ws.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.telabase.ds.dao.*;
import com.telabase.ds.entity.*;
import com.telabase.ws.model.DataList;
import com.telabase.ws.model.WSResponse;

@Controller
public class CareGiverController {

	private final static Logger logger = Logger.getLogger(CareGiverController.class.getName());

	@RequestMapping(value = "/api/caregiver/list", method = RequestMethod.GET)
	@ResponseBody
	public List<CareGiver> list(HttpServletRequest request) {
		CareGiverDAO serve = new CareGiverDAO();
		return serve.list();
	}

	@RequestMapping(value = "/api/caregiver/datatable/list", method = RequestMethod.GET)
	@ResponseBody
	public DataList listDatatable(HttpServletRequest request) {

		String sStart = request.getParameter("start");
		String sLength = request.getParameter("length");
		List<CareGiver> rs = null;
		CareGiverDAO serve = new CareGiverDAO();
		DataList ds = new DataList();
		if (sStart != null && sLength != null) {
			int start = Integer.parseInt(sStart);
			int length = Integer.parseInt(sLength);
			rs = serve.list(start, start + length);
			ds.setRecordsFiltered(serve.count());
			ds.setRecordsTotal(rs.size());

		} else {
			ds.setRecordsTotal(serve.count());
		}
		ds.setData(rs);

		return ds;
	}

	@RequestMapping(value = "/api/caregiver/query", method = RequestMethod.GET)
	@ResponseBody
	public List<CareGiver> query(HttpServletRequest request) {
		CareGiverDAO serve = new CareGiverDAO();
		if (request.getParameter("keyword") == null)
			return null;
		return serve.searchByFirstname(request.getParameter("keyword"));
	}

	@RequestMapping(value = "/api/caregiver/save", method = RequestMethod.POST)
	@ResponseBody
	public WSResponse save(@RequestBody CareGiver o, HttpServletRequest request) {

		CareGiverDAO serve = new CareGiverDAO();
		try {
			serve.save(o);
		} catch (Exception e) {
			e.printStackTrace();
			return new WSResponse("" + o.getId(), WSResponse.STATUS_FAIL);
		}
		return new WSResponse("" + o.getId(), WSResponse.STATUS_SUCCESS);
	}

	@RequestMapping(value = "/api/caregiver/get", method = RequestMethod.GET)
	@ResponseBody
	public CareGiver findById(HttpServletRequest request) {

		CareGiverDAO serve = new CareGiverDAO();
		CareGiver o = serve.findById(Long.parseLong(request.getParameter("id")));
		return o;
	}

	@RequestMapping(value = "/api/caregiver/delete", method = RequestMethod.GET)
	@ResponseBody
	public WSResponse delete(HttpServletRequest request) {

		CareGiverDAO serve = new CareGiverDAO();
		try {
			CareGiver o = new CareGiver();
			o.setId(Long.parseLong(request.getParameter("id")));
			serve.delete(o);

		} catch (Exception e) {
			e.printStackTrace();
			return new WSResponse(null, WSResponse.STATUS_FAIL);
		}
		return new WSResponse(null, WSResponse.STATUS_SUCCESS);
	}

	@RequestMapping(value = "/admin/caregiver/batch/save", method = RequestMethod.POST)
	@ResponseBody
	public List<WSResponse> saveAsBatch(@RequestBody CareGiver[] list, HttpServletRequest request) {
		List<WSResponse> resList = new ArrayList<WSResponse>();
		String result;
		for (CareGiver d : list) {

			resList.add(this.save(d, request));
		}

		return resList;
	}

	// Manual added code
	@RequestMapping(value = "/api/caregiver/patient/list", method = RequestMethod.GET)
	@ResponseBody
	public List<CareGiver> listByPatient(HttpServletRequest request) {

		long id = Long.parseLong(request.getParameter("id"));
		CarePermitDAO dao = new CarePermitDAO();
		CareGiverDAO cDAO = new CareGiverDAO();
		List<CarePermit> rs = dao.findCarePermitByPatientId(id);
		List<CareGiver> cList = new ArrayList<CareGiver>();
		CareGiver c;
		for (CarePermit permit : rs) {
			c = cDAO.findById(permit.getCareGiverId());
			if (c != null)
				cList.add(c);
		}
		return cList;
	}

	// Manual added code
	@RequestMapping(value = "/api/caregiver/patient/delete", method = RequestMethod.GET)
	@ResponseBody
	public WSResponse purgePermit(HttpServletRequest request) {

		long careGiverId = Long.parseLong(request.getParameter("careGiverId"));
		long patientId = Long.parseLong(request.getParameter("patientId"));
		try {
			CarePermitDAO dao = new CarePermitDAO();

			List<CarePermit> rs = dao.findCarePermitByPatientIdAndCareGiverId(patientId, careGiverId);
			for (CarePermit permit : rs) {
				dao.delete(permit);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new WSResponse(null, WSResponse.STATUS_FAIL);
		}

		return new WSResponse(null, WSResponse.STATUS_SUCCESS);
	}

}
