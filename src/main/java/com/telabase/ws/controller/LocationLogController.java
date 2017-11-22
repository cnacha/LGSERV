
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
import com.telabase.ws.model.WSResponse;

	@Controller
	public class LocationLogController {
		
		private final static Logger logger = Logger.getLogger(LocationLogController.class.getName());
		
		@RequestMapping(value = "/api/locationlog/list", method = RequestMethod.GET)
		@ResponseBody
		public List<LocationLog> list(HttpServletRequest request) {LocationLogDAO serve = new LocationLogDAO();
			return serve.list();
		}
		
		@RequestMapping(value = "/api/locationlog/save", method = RequestMethod.POST)
		@ResponseBody
		public WSResponse save(@RequestBody LocationLog o, HttpServletRequest request) {

			LocationLogDAO serve = new LocationLogDAO();
			try {
					o.setLogDate(new Date());
					serve.save(o);
					
					// check distance from center
					PatientDAO pDao = new PatientDAO();
					Patient p = pDao.findById(o.getPatientId());
					if(o.getDistanceFromCenter() > p.getWarnDistance()) {
						//find last alert
						DistanceAlertDAO daDAO = new DistanceAlertDAO();
						DistanceAlert lastAlert = daDAO.findLastDistanceAlertByPatientId(o.getPatientId());
						// check if last alert is within 3 hours
						if(lastAlert == null || ((new Date()).getTime() - lastAlert.getLogDate().getTime())> 3*60*60*1000) {
						
							// create distance alert
							DistanceAlert alert = new DistanceAlert();
							alert.setDistance(o.getDistanceFromCenter());
							alert.setPatientId(o.getPatientId());
							alert.setLogDate(new Date());
							alert.setStatus("1");
							daDAO.save(alert);
							
							// send notification to caregiver
							UserDAO uDAO = new UserDAO();
							CarePermitDAO cpDAO = new CarePermitDAO();
							List<CarePermit> rs = cpDAO.findCarePermitByPatientId(o.getPatientId());
							User u;
							PushService pushServ = new PushService();
							String msg = "คุณ "+p.getFirstname()+" ออกนอกบริเวณที่กำหนดไว้ เป็นระยะทาง "+o.getDistanceFromCenter()+" เมตร";
							for(CarePermit permit: rs) {
								u = uDAO.findUserByReferenceObjectId(permit.getCareGiverId());
								if(u!=null && u.getDeviceTokenKey() !=null && !"".equals(u.getDeviceTokenKey())) {
									pushServ.send(u.getDeviceTokenKey(), msg);
								}
							}
							
							// send notification to callcenter
							NotificationSender sender = new NotificationSender();
							sender.toCallCenter(false, msg);
						}
					}
					
			} catch (Exception e) {
				e.printStackTrace();
				return new WSResponse(""+o.getId(), WSResponse.STATUS_FAIL);
			}
			return new WSResponse(""+o.getId(), WSResponse.STATUS_SUCCESS);
		}
		
		@RequestMapping(value = "/api/locationlog/get", method = RequestMethod.GET)
		@ResponseBody
		public LocationLog findById(HttpServletRequest request) {

			LocationLogDAO serve = new LocationLogDAO();
			LocationLog o = serve.findById(Long.parseLong(request.getParameter("id")));
			return o;
		}
		
		@RequestMapping(value = "/api/locationlog/delete", method = RequestMethod.GET)
		@ResponseBody
		public WSResponse delete(HttpServletRequest request) {

			LocationLogDAO serve = new LocationLogDAO();
			try {
				LocationLog o = new LocationLog();
				o.setId(Long.parseLong(request.getParameter("id")));
				serve.delete(o);
				
			} catch (Exception e) {
				e.printStackTrace();
				return new WSResponse(null, WSResponse.STATUS_FAIL);
			}
			return new WSResponse(null, WSResponse.STATUS_SUCCESS);
		}
		
		@RequestMapping(value = "/admin/locationlog/batch/save", method = RequestMethod.POST)
		@ResponseBody
		public List<WSResponse> saveAsBatch(@RequestBody LocationLog[] list, HttpServletRequest request){
			List<WSResponse> resList = new ArrayList<WSResponse>();
			String result;
			for (LocationLog d : list) {
				
				resList.add(this.save(d,request));
			}

			return resList;
		}
		@RequestMapping(value = "/api/patient/locationlog/list", method = RequestMethod.GET)
		@ResponseBody
		public List<LocationLog> listByPatient(HttpServletRequest request) {
		
			long id = Long.parseLong(request.getParameter("id"));
			LocationLogDAO dao = new LocationLogDAO();
			List<LocationLog> rs = dao.findLocationLogByPatientId(id);

			return rs;
		}

	
	}
	
	
	