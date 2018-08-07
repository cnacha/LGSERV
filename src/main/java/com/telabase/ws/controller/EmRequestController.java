
package com.telabase.ws.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.google.gson.JsonElement;
import com.telabase.ds.dao.*;
import com.telabase.ds.entity.*;
import com.telabase.security.dao.UserDAO;
import com.telabase.security.entity.User;
import com.telabase.util.NotificationSender;
import com.telabase.util.PushService;
import com.telabase.ws.model.DataList;
import com.telabase.ws.model.WSResponse;

@Controller
public class EmRequestController {

	private final static Logger logger = Logger.getLogger(EmRequestController.class.getName());

	@RequestMapping(value = "/api/emrequest/list", method = RequestMethod.GET)
	@ResponseBody
	public List<EmRequest> list(HttpServletRequest request) {
		EmRequestDAO serve = new EmRequestDAO();
		return serve.list();
	}
	
	@RequestMapping(value = "/api/emrequest/datatable/list", method = RequestMethod.GET)
	@ResponseBody
	public DataList listDatatable(HttpServletRequest request) {

		String sStart = request.getParameter("start");
		String sLength = request.getParameter("length");
		List<EmRequest> rs = null;
		EmRequestDAO serve = new EmRequestDAO();
		DataList ds = new DataList();
		if(sStart!=null && sLength!=null){
			int start = Integer.parseInt(sStart);
			int length = Integer.parseInt(sLength);
				rs = serve.listLatestEmRequest(start,  start+length);
				ds.setRecordsFiltered(serve.count());
				ds.setRecordsTotal(rs.size());
			
		}else{
			ds.setRecordsTotal(serve.count());
		}
		ds.setData(rs);
		
		return ds;
	}

	@RequestMapping(value = "/api/emrequest/status/query", method = RequestMethod.GET)
	@ResponseBody
	public List<EmRequest> queryByStatus(HttpServletRequest request) {
		EmRequestDAO serve = new EmRequestDAO();
		return serve.findEmRequestByStatus(request.getParameter("status"));
	}

	@RequestMapping(value = "/api/emrequest/status/open/query", method = RequestMethod.GET)
	@ResponseBody
	public List<EmRequest> queryByOpenStatus(HttpServletRequest request) {
		EmRequestDAO serve = new EmRequestDAO();
		return serve.findEmRequestByOpenStatus();
	}

	@RequestMapping(value = "/api/emrequest/status/push", method = RequestMethod.GET)
	@ResponseBody
	public WSResponse push(HttpServletRequest request) {
		EmRequestDAO serve = new EmRequestDAO();
		EmRequest o = serve.findById(Long.parseLong(request.getParameter("id")));
		try {
			
			EmRequestStatusLog log = new EmRequestStatusLog();
			log.setEmRequestId(o.getId());
			log.setLogDate(new Date());
			log.setStatus(RequestStatus.getNextStatus(o.getStatus()));
			log.setNote(request.getParameter("note"));

			EmRequestStatusLogController c = new EmRequestStatusLogController();
			c.save(log, request);
		} catch (Exception e) {
			e.printStackTrace();
			return new WSResponse("" + o.getId(), WSResponse.STATUS_FAIL);
		}

		return new WSResponse("" + o.getId(), WSResponse.STATUS_SUCCESS);
	}
	
	@RequestMapping(value = "/api/emrequest/status/respond", method = RequestMethod.GET)
	@ResponseBody
	public synchronized WSResponse acceptcall(HttpServletRequest request) {
		EmRequestDAO serve = new EmRequestDAO();
		EmRequest o = serve.findById(Long.parseLong(request.getParameter("id")));
		// check if already responded 
		if(o.getStatus().equals(RequestStatus.STATUS[1])) {
			return new WSResponse("" + o.getId(), WSResponse.STATUS_SUCCESS);
		}
		try {
			
			EmRequestStatusLog log = new EmRequestStatusLog();
			log.setEmRequestId(o.getId());
			log.setLogDate(new Date());
			log.setStatus(RequestStatus.STATUS[1]);

			EmRequestStatusLogController c = new EmRequestStatusLogController();
			c.save(log, request);
			
			// set call center user
			o.setCallCenterUser(request.getParameter("user"));
			serve.save(o);
		} catch (Exception e) {
			e.printStackTrace();
			return new WSResponse("" + o.getId(), WSResponse.STATUS_FAIL);
		}

		return new WSResponse("" + o.getId(), WSResponse.STATUS_SUCCESS);
	}
	
	@RequestMapping(value = "/api/emrequest/status/push/list", method = RequestMethod.GET)
	@ResponseBody
	public String[] getPushStatus(HttpServletRequest request) {
		String status = request.getParameter("status");
		List<String> statusList = new ArrayList<String>();
		while(!status.equals("")) {
			status = RequestStatus.getNextStatus(status);
			if(!"".equals(status))
				statusList.add(status);
		}
		return statusList.toArray(new String[statusList.size()]);
	}
	
	@RequestMapping(value = "/api/emrequest/status/pull/list", method = RequestMethod.GET)
	@ResponseBody
	public String[] getPullStatus(HttpServletRequest request) {
		String status = request.getParameter("status");
		List<String> statusList = new ArrayList<String>();
		while(!status.equals("")) {
			status = RequestStatus.getPrevStatus(status);
			if(!"".equals(status))
				statusList.add(status);
		}
		return statusList.toArray(new String[statusList.size()]);
	}
	
	
	@RequestMapping(value = "/api/emrequest/status/pull", method = RequestMethod.GET)
	@ResponseBody
	public WSResponse pull(HttpServletRequest request) {
		EmRequestDAO serve = new EmRequestDAO();
		EmRequest o = serve.findById(Long.parseLong(request.getParameter("id")));
		try {
			
			EmRequestStatusLog log = new EmRequestStatusLog();
			log.setEmRequestId(o.getId());
			log.setLogDate(new Date());
			log.setStatus(RequestStatus.getPrevStatus(o.getStatus()));
			log.setNote(request.getParameter("note"));

			EmRequestStatusLogController c = new EmRequestStatusLogController();
			c.save(log, request);
		} catch (Exception e) {
			e.printStackTrace();
			return new WSResponse("" + o.getId(), WSResponse.STATUS_FAIL);
		}

		return new WSResponse("" + o.getId(), WSResponse.STATUS_SUCCESS);

	}

	@RequestMapping(value = "/api/emrequest/save", method = RequestMethod.POST)
	@ResponseBody
	public WSResponse save(@RequestBody EmRequest o, HttpServletRequest request) {

		EmRequestDAO serve = new EmRequestDAO();
		if (o.getSubmitDate() == null)
			o.setSubmitDate(new Date());
		try {
			serve.save(o);

			if (o.getId() != 0) {
				// create status log
				EmRequestStatusLog log = new EmRequestStatusLog();
				log.setEmRequestId(o.getId());
				log.setLogDate(new Date());
				log.setStatus(RequestStatus.STATUS[0]);
				EmRequestStatusLogController c = new EmRequestStatusLogController();
				c.save(log, request);
				
				NotificationSender sender = new NotificationSender();
				sender.toCallCenter(true,"SOS Emergency Request By " + o.getPatient().getFirstname()
						+ " " + o.getPatient().getLastname());
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new WSResponse("" + o.getId(), WSResponse.STATUS_FAIL);
		}
		return new WSResponse("" + o.getId(), WSResponse.STATUS_SUCCESS);
	}
	
	@RequestMapping(value = "/api/emrequest/call", method = RequestMethod.GET)
	@ResponseBody
	public  WSResponse call(HttpServletRequest request) {
		PatientDAO patientDAO = new PatientDAO();
		Patient o = patientDAO.searchByPhone(request.getParameter("phone"));
		if(o == null)
			return new WSResponse("no patient found for "+request.getParameter("phone"), WSResponse.STATUS_FAIL);
		
		EmRequestDAO serve = new EmRequestDAO();
		EmRequest req = new EmRequest();
		req.setPatientId(o.getId());
		List<EmRequest> list = serve.findLatestEmRequestByPatientId(o.getId());
		if(list.size()>0) {
			EmRequest last = list.get(0);
			Date now = new Date();
			if (last.getSubmitDate()!=null && (now.getTime() - last.getSubmitDate().getTime() > 5*60*1000)) {
			   logger.info("create new request from call for "+o.getId());
			   return this.save(req, request);
			} else {
				logger.info(" request is already created from call for "+o.getId());
			 	return new WSResponse("request has been created id: " + last.getId(), WSResponse.STATUS_FAIL);
			}
		} else {
			return this.save(req, request);
		}
		
		
	}
	
	@RequestMapping(value = "/api/emrequest/assign", method = RequestMethod.POST)
	@ResponseBody
	public WSResponse assign(@RequestBody EmRequest o, HttpServletRequest request) {

		EmRequestDAO serve = new EmRequestDAO();
		try {
			serve.save(o);

			// create status log
			EmRequestStatusLog log = new EmRequestStatusLog();
			log.setEmRequestId(o.getId());
			log.setLogDate(new Date());
			
			// assign to driver
			if(o.getEmDriverId() != 0) {
				log.setStatus(RequestStatus.STATUS[3]);
				NotificationSender sender = new NotificationSender();
				sender.toEmDriverUser(o.getEmDriverId(), "คุณได้รับงานบริการฉุกเฉิน ของ คนไข้  "+o.getPatient().getFirstname()+" "+o.getPatient().getLastname());
			} else
				
			// assign to emcenter
			if(o.getEmCenterId() != 0) {
				log.setStatus(RequestStatus.STATUS[2]);
				NotificationSender sender = new NotificationSender();
				sender.toEmCenterUser(o.getEmCenterId(), "คุณได้รับงานบริการฉุกเฉิน ของ คนไข้  "+o.getPatient().getFirstname()+" "+o.getPatient().getLastname());
			}
			
			log.setNote(o.getNote());
			EmRequestStatusLogController c = new EmRequestStatusLogController();
			c.save(log, request);

		} catch (Exception e) {
			e.printStackTrace();
			return new WSResponse("" + o.getId(), WSResponse.STATUS_FAIL);
		}
		return new WSResponse("" + o.getId(), WSResponse.STATUS_SUCCESS);
	}
	
	
	@RequestMapping(value = "/api/emrequest/setloc", method = RequestMethod.GET)
	@ResponseBody
	public WSResponse setloc( HttpServletRequest request) {
		EmRequestDAO dao = new EmRequestDAO();
		EmRequest req = null;
		try {
			req = dao.findById(Long.parseLong(request.getParameter("id")));
			req.setLocationDetails(request.getParameter("location"));
			
			dao.save(req);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new WSResponse("", WSResponse.STATUS_FAIL);
		}
		return new WSResponse("", WSResponse.STATUS_SUCCESS);
	}


	@RequestMapping(value = "/api/emrequest/get", method = RequestMethod.GET)
	@ResponseBody
	public EmRequest findById(HttpServletRequest request) {

		EmRequestDAO serve = new EmRequestDAO();
		EmRequest o = serve.findById(Long.parseLong(request.getParameter("id")));
		return o;
	}

	@RequestMapping(value = "/api/emrequest/delete", method = RequestMethod.GET)
	@ResponseBody
	public WSResponse delete(HttpServletRequest request) {

		EmRequestDAO serve = new EmRequestDAO();
		try {
			EmRequest o = new EmRequest();
			o.setId(Long.parseLong(request.getParameter("id")));
			serve.delete(o);

		} catch (Exception e) {
			e.printStackTrace();
			return new WSResponse(null, WSResponse.STATUS_FAIL);
		}
		return new WSResponse(null, WSResponse.STATUS_SUCCESS);
	}

	@RequestMapping(value = "/admin/emrequest/batch/save", method = RequestMethod.POST)
	@ResponseBody
	public List<WSResponse> saveAsBatch(@RequestBody EmRequest[] list, HttpServletRequest request) {
		List<WSResponse> resList = new ArrayList<WSResponse>();
		String result;
		for (EmRequest d : list) {

			resList.add(this.save(d, request));
		}

		return resList;
	}

	@RequestMapping(value = "/api/emrequest/patient/list", method = RequestMethod.GET)
	@ResponseBody
	public List<EmRequest> listByPatient(HttpServletRequest request) {

		long id = Long.parseLong(request.getParameter("id"));
		EmRequestDAO dao = new EmRequestDAO();
		List<EmRequest> rs = dao.findEmRequestByPatientId(id);

		return rs;
	}
	
	@RequestMapping(value = "/api/emrequest/patient/latest", method = RequestMethod.GET)
	@ResponseBody
	public EmRequest getLatestRequestByPatient(HttpServletRequest request) {

		long id = Long.parseLong(request.getParameter("id"));
		EmRequestDAO dao = new EmRequestDAO();
		List<EmRequest> rs = dao.findLatestEmRequestByPatientId(id);
		if(rs!=null)
			return rs.get(0);
		else
			return null;
	}


	@RequestMapping(value = "/api/emrequest/emcenter/list", method = RequestMethod.GET)
	@ResponseBody
	public List<EmRequest> listByEmCenter(HttpServletRequest request) {

		long id = Long.parseLong(request.getParameter("id"));
		EmRequestDAO dao = new EmRequestDAO();
		List<EmRequest> rs = dao.findEmRequestByEmCenterId(id);

		return rs;
	}
	
	@RequestMapping(value = "/api/emrequest/emdriver/status/open/query", method = RequestMethod.GET)
	@ResponseBody
	public List<EmRequest> listByEmDriver(HttpServletRequest request) {

		long id = Long.parseLong(request.getParameter("id"));
		EmRequestDAO dao = new EmRequestDAO();
		List<EmRequest> rs = dao.findOpenEmRequestByDriverId(id);

		return rs;
	}

	@RequestMapping(value = "/api/emrequest/emcenter/status/open/query", method = RequestMethod.GET)
	@ResponseBody
	public List<EmRequest> listByEmCenterAndOpenStatus(HttpServletRequest request) {

		long id = Long.parseLong(request.getParameter("id"));
		EmRequestDAO dao = new EmRequestDAO();
		List<EmRequest> rs = dao.findOpenEmRequestByEmCenterId(id);

		return rs;
	}
	

	@RequestMapping(value = "/api/emrequest/emcenter/status/query", method = RequestMethod.GET)
	@ResponseBody
	public List<EmRequest> listByEmCenterAndStatus(HttpServletRequest request) {

		long id = Long.parseLong(request.getParameter("id"));
		EmRequestDAO dao = new EmRequestDAO();
		List<EmRequest> rs = dao.findEmRequestByEmCenterIdAndStatus(id, request.getParameter("status"));

		return rs;
	}

}
