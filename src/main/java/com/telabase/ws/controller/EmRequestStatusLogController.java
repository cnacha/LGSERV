
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

import com.google.api.services.storagetransfer.v1.model.Status;
import com.google.gson.JsonElement;
	import com.telabase.ds.dao.*;
	import com.telabase.ds.entity.*;
	import com.telabase.ws.model.WSResponse;

	@Controller
	public class EmRequestStatusLogController {
		
		private final static Logger logger = Logger.getLogger(EmRequestStatusLogController.class.getName());
		
		@RequestMapping(value = "/api/emrequeststatuslog/list", method = RequestMethod.GET)
		@ResponseBody
		public List<EmRequestStatusLog> list(HttpServletRequest request) {EmRequestStatusLogDAO serve = new EmRequestStatusLogDAO();
			return serve.list();
		}
		
		@RequestMapping(value = "/api/emrequeststatuslog/save", method = RequestMethod.POST)
		@ResponseBody
		public WSResponse save(@RequestBody EmRequestStatusLog o, HttpServletRequest request) {

			EmRequestStatusLogDAO serve = new EmRequestStatusLogDAO();
			try {
					if(o.getLogDate() == null)
						o.setLogDate(new Date());
					serve.save(o);
					// save status to emrequest
					EmRequestDAO rDAO = new EmRequestDAO();
					EmRequest emreq = rDAO.findById(o.getEmRequestId());
					emreq.setStatus(o.getStatus());
					rDAO.save(emreq);
					// save status to patient
					PatientDAO pDAO = new PatientDAO();
					
					if(o.getStatus().equalsIgnoreCase("closed")) {
						emreq.getPatient().setCurrentStatus("normal");
						
						// calculate average time
						List<EmRequestStatusLog> logs = serve.findEmRequestStatusLogByEmRequestId(o.getEmRequestId());
						Date assignTime = null;
						Date pickupTime = null;
						Date atpatientTime = null;
						Date deliverTime = null;
						
						for(EmRequestStatusLog log: logs) {
							if(assignTime== null && "assigned".equalsIgnoreCase(log.getStatus())) {
								assignTime = log.getLogDate();
							} 
							if(pickupTime==null && "pickingup".equalsIgnoreCase(log.getStatus())) {
									pickupTime = log.getLogDate();
							}  
							
							if(atpatientTime==null && "atpatient".equalsIgnoreCase(log.getStatus())) {
								atpatientTime = log.getLogDate();
							} 
							if(deliverTime==null && "delivered".equalsIgnoreCase(log.getStatus())) {
								deliverTime = log.getLogDate();
							}
						}
					//	logger.info("assignTime "+assignTime);
					//	logger.info("pickupTime "+pickupTime);
					//	logger.info("pickupTime "+atpatientTime);
					//	logger.info("deliverTime "+deliverTime);
						double responseTimeMin = ((pickupTime.getTime() - assignTime.getTime()) / 1000) ;
						double pickUpTimeMin = ((atpatientTime.getTime() - pickupTime.getTime()) / 1000) ;
						double deliverTimeMin = ((deliverTime.getTime() - atpatientTime.getTime()) / 1000) ;
						logger.info("assignTime "+assignTime.getTime());
						logger.info("pickupTime "+pickupTime.getTime());
						logger.info("deliverTime "+deliverTime.getTime());
						logger.info("atpatientTime "+atpatientTime.getTime());
						logger.info("responseTimeMin "+responseTimeMin);
						logger.info("pickUpTimeMin "+pickUpTimeMin);
						logger.info("deliverTimeMin "+deliverTimeMin);
						EmCenterDAO centerDAO = new EmCenterDAO();
						EmCenter center = centerDAO.findById(emreq.getEmCenterId());
						
						if(center.getAvgResponseRate() != 0)
							center.setAvgResponseRate((center.getAvgResponseRate() + responseTimeMin)/2);
						else 
							center.setAvgResponseRate(responseTimeMin);
						
						if(center.getAvgPickupMinsTime() !=0)
							center.setAvgPickupMinsTime((center.getAvgPickupMinsTime() + pickUpTimeMin)/2);
						else
							center.setAvgPickupMinsTime(pickUpTimeMin);
						
						if(center.getAvgDeliveryMinsTime() != 0)
							center.setAvgDeliveryMinsTime((center.getAvgDeliveryMinsTime() + deliverTimeMin)/2);
						else
							center.setAvgDeliveryMinsTime(deliverTimeMin);
						
						centerDAO.save(center);
						
					} else if(RequestStatus.getStatusIndex(o.getStatus())<4){
						emreq.getPatient().setCurrentStatus("alert");
						
					}
					else 
						emreq.getPatient().setCurrentStatus("beware");
				
					pDAO.save(emreq.getPatient());
					
					if(RequestStatus.getStatusIndex(o.getStatus())<=1){
						emreq.setEmCenterId(0);
						rDAO.save(emreq);
					}
					
			} catch (Exception e) {
				e.printStackTrace();
				return new WSResponse(""+o.getId(), WSResponse.STATUS_FAIL);
			}
			return new WSResponse(""+o.getId(), WSResponse.STATUS_SUCCESS);
		}
		
		
		
		@RequestMapping(value = "/api/emrequeststatuslog/get", method = RequestMethod.GET)
		@ResponseBody
		public EmRequestStatusLog findById(HttpServletRequest request) {

			EmRequestStatusLogDAO serve = new EmRequestStatusLogDAO();
			EmRequestStatusLog o = serve.findById(Long.parseLong(request.getParameter("id")));
			return o;
		}
		
		@RequestMapping(value = "/api/emrequeststatuslog/delete", method = RequestMethod.GET)
		@ResponseBody
		public WSResponse delete(HttpServletRequest request) {

			EmRequestStatusLogDAO serve = new EmRequestStatusLogDAO();
			try {
				EmRequestStatusLog o = new EmRequestStatusLog();
				o.setId(Long.parseLong(request.getParameter("id")));
				serve.delete(o);
				
			} catch (Exception e) {
				e.printStackTrace();
				return new WSResponse(null, WSResponse.STATUS_FAIL);
			}
			return new WSResponse(null, WSResponse.STATUS_SUCCESS);
		}
		
		@RequestMapping(value = "/admin/emrequeststatuslog/batch/save", method = RequestMethod.POST)
		@ResponseBody
		public List<WSResponse> saveAsBatch(@RequestBody EmRequestStatusLog[] list, HttpServletRequest request){
			List<WSResponse> resList = new ArrayList<WSResponse>();
			String result;
			for (EmRequestStatusLog d : list) {
				
				resList.add(this.save(d,request));
			}

			return resList;
		}
		@RequestMapping(value = "/api/emrequest/emrequeststatuslog/list", method = RequestMethod.GET)
		@ResponseBody
		public List<EmRequestStatusLog> listByEmRequest(HttpServletRequest request) {
		
			long id = Long.parseLong(request.getParameter("id"));
			EmRequestStatusLogDAO dao = new EmRequestStatusLogDAO();
			List<EmRequestStatusLog> rs = dao.findEmRequestStatusLogByEmRequestId(id);

			return rs;
		}

	
	}
	
	
	