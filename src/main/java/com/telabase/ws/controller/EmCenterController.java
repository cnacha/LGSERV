
	package com.telabase.ws.controller;

	import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
	import java.util.List;
	import java.util.logging.Logger;

	import javax.servlet.http.HttpServletRequest;

	import org.springframework.stereotype.Controller;
	import org.springframework.web.bind.annotation.RequestBody;
	import org.springframework.web.bind.annotation.RequestMapping;
	import org.springframework.web.bind.annotation.RequestMethod;
	import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.telabase.ds.dao.*;
	import com.telabase.ds.entity.*;
import com.telabase.security.dao.UserDAO;
import com.telabase.security.entity.User;
import com.telabase.util.EmCenterDistanceComparator;
import com.telabase.util.LocationUtil;
import com.telabase.util.SortUtil;
import com.telabase.util.StringUtil;
import com.telabase.ws.model.DataList;
import com.telabase.ws.model.WSResponse;

	@Controller
	public class EmCenterController {
		
		private final static Logger logger = Logger.getLogger(EmCenterController.class.getName());
		
		@RequestMapping(value = "/api/emcenter/list", method = RequestMethod.GET)
		@ResponseBody
		public List<EmCenter> list(HttpServletRequest request) {EmCenterDAO serve = new EmCenterDAO();
			return serve.list();
		}
		
		@RequestMapping(value = "/api/emcenter/datatable/list", method = RequestMethod.GET)
		@ResponseBody
		public DataList listDatatable(HttpServletRequest request) {

			String sStart = request.getParameter("start");
			String sLength = request.getParameter("length");
			List<EmCenter> rs = null;
			EmCenterDAO serve = new EmCenterDAO();
			DataList ds = new DataList();
			if(sStart!=null && sLength!=null){
				int start = Integer.parseInt(sStart);
				int length = Integer.parseInt(sLength);
					rs = serve.list(start,  start+length);
					ds.setRecordsFiltered(serve.count());
					ds.setRecordsTotal(rs.size());
				
			}else{
				ds.setRecordsTotal(serve.count());
			}
			ds.setData(rs);
			
			return ds;
		}
		
		@RequestMapping(value = "/api/emcenter/query", method = RequestMethod.GET)
		@ResponseBody
		public List<EmCenter> query(HttpServletRequest request) {EmCenterDAO serve = new EmCenterDAO();
			if(request.getParameter("keyword")==null)
				return null;
			return serve.searchByName(request.getParameter("keyword"));
		}
		
		@RequestMapping(value = "/api/emcenter/distance/query", method = RequestMethod.GET)
		@ResponseBody
		public List<EmCenter> queryByDistance(HttpServletRequest request) {EmCenterDAO serve = new EmCenterDAO();
			double latitude = Double.parseDouble(request.getParameter("lat"));
			double longtitude = Double.parseDouble(request.getParameter("long"));
			List<EmCenter> rs = serve.list();
			double distance;
			for(EmCenter ec: rs) {
				distance = LocationUtil.getInstance().distance(latitude, ec.getLocLat(), longtitude, ec.getLocLong(), 0, 0);
				ec.setDistance(distance);
			}
			
			rs.sort(new EmCenterDistanceComparator());
			return rs.subList(0,rs.size()<4?rs.size():4);
		}
		
		@RequestMapping(value = "/api/emcenter/save", method = RequestMethod.POST)
		@ResponseBody
		public WSResponse save(@RequestBody EmCenter o, HttpServletRequest request) {
			EmCenterDAO serve = new EmCenterDAO();
			try {
					if(o.getSecurityCode() == null || "".equals(o.getSecurityCode()) ) {
						o.setSecurityCode(StringUtil.randomNumber(10));
					}
					serve.save(o);
			} catch (Exception e) {
				e.printStackTrace();
				return new WSResponse(""+o.getId(), WSResponse.STATUS_FAIL);
			}
			return new WSResponse(""+o.getId(), WSResponse.STATUS_SUCCESS);
		}
		
		@RequestMapping(value = "/api/emcenter/setloc", method = RequestMethod.POST)
		@ResponseBody
		public WSResponse setLocation(@RequestBody EmCenter o, HttpServletRequest request) {
			EmCenterDAO serve = new EmCenterDAO();
			try {
					EmCenter center = serve.findById(o.getId());
					center.setLocLat(o.getLocLat());
					center.setLocLong(o.getLocLong());
					serve.save(center);
			} catch (Exception e) {
				e.printStackTrace();
				return new WSResponse(""+o.getId(), WSResponse.STATUS_FAIL);
			}
			return new WSResponse(""+o.getId(), WSResponse.STATUS_SUCCESS);
		}
		
		@RequestMapping(value = "/api/emcenter/get", method = RequestMethod.GET)
		@ResponseBody
		public EmCenter findById(HttpServletRequest request) {

			EmCenterDAO serve = new EmCenterDAO();
			EmCenter o = serve.findById(Long.parseLong(request.getParameter("id")));
			return o;
		}
		
		@RequestMapping(value = "/public/emcenter/securityCode/query", method = RequestMethod.GET)
		@ResponseBody
		public List<EmCenter> queryBySecurityCode(HttpServletRequest request) {
			EmCenterDAO serve = new EmCenterDAO();
			if (request.getParameter("code") == null)
				return null;
			return serve.searchBySecurityCode(request.getParameter("code"));
		}
		
		@RequestMapping(value = "/api/emcenter/emdriver/list", method = RequestMethod.GET)
		@ResponseBody
		public List<User> getEmDrivers(HttpServletRequest request) {
			UserDAO serve = new UserDAO();
			return serve.findUserByReferObjectIdAndAppRole(request.getParameter("id"), "EmDriver");
		}
		
		@RequestMapping(value = "/api/emcenter/callcenter/list", method = RequestMethod.GET)
		@ResponseBody
		public List<User> getEmCenters(HttpServletRequest request) {
			UserDAO serve = new UserDAO();
			return serve.findUserByReferObjectIdAndAppRole(request.getParameter("id"), "EmCenter");
		}

		
		@RequestMapping(value = "/api/emcenter/delete", method = RequestMethod.GET)
		@ResponseBody
		public WSResponse delete(HttpServletRequest request) {

			EmCenterDAO serve = new EmCenterDAO();
			try {
				EmCenter o = new EmCenter();
				o.setId(Long.parseLong(request.getParameter("id")));
				serve.delete(o);
				
			} catch (Exception e) {
				e.printStackTrace();
				return new WSResponse(null, WSResponse.STATUS_FAIL);
			}
			return new WSResponse(null, WSResponse.STATUS_SUCCESS);
		}
		
		
		
		@RequestMapping(value = "/admin/emcenter/batch/save", method = RequestMethod.POST)
		@ResponseBody
		public List<WSResponse> saveAsBatch(@RequestBody EmCenter[] list, HttpServletRequest request){
			List<WSResponse> resList = new ArrayList<WSResponse>();
			String result;
			for (EmCenter d : list) {
				
				resList.add(this.save(d,request));
			}

			return resList;
		}
		
		
		
	}
	
	
	