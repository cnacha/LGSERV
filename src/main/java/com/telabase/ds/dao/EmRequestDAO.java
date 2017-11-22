
package com.telabase.ds.dao;

import java.util.List;
import com.google.appengine.api.datastore.KeyFactory;
import com.googlecode.objectify.ObjectifyService;
import com.telabase.ds.entity.*;

public class EmRequestDAO {
	
	public int count() {
		return ObjectifyService.ofy()
		          .load()
		          .type(EmRequest.class) 
		          .count();
	}
	
	public List<EmRequest> list(){
		List<EmRequest> results = ObjectifyService.ofy()
		          .load()
		          .type(EmRequest.class)
		          .list();

		return results;
	}
	
	public void save(EmRequest o) {
		if(o.getId() == 0)
			o.setId(ObjectifyService.ofy().factory().allocateId(EmRequest.class).getId());
		ObjectifyService.ofy().save().entity(o).now();

	}
	
	public EmRequest findById(long id){
		return ObjectifyService.ofy().load().type(EmRequest.class).id(id).now();
	}

	public void delete(EmRequest o) {
		ObjectifyService.ofy().delete().entity(o).now();
	}
	
	
	public List<EmRequest> findEmRequestByPatientId(long patientId){
		List<EmRequest> results = ObjectifyService.ofy()
		          .load()
		          .type(EmRequest.class)
		          .filter("patientId", patientId)
		          .list();

		return results;
	}
	
	public List<EmRequest> findEmRequestByStatus(String status){
		List<EmRequest> results = ObjectifyService.ofy()
		          .load()
		          .type(EmRequest.class)
		          .filter("status", status)
		          .list();

		return results;
	}
	
	public List<EmRequest> findEmRequestByOpenStatus(){
		List<EmRequest> results = ObjectifyService.ofy()
		          .load()
		          .type(EmRequest.class)
		          .filter("status != ", RequestStatus.STATUS[6])
		          .list();

		return results;
	}
	
	public List<EmRequest> findLatestEmRequest(int number){
		List<EmRequest> results = ObjectifyService.ofy()
		          .load()
		          .type(EmRequest.class)
		          .order("-submitDate")
		          .limit(number)
		          .list();
	
		return results;
	}
	
	public List<EmRequest> listLatestEmRequest(int start,int end){
		List<EmRequest> results = ObjectifyService.ofy()
		          .load()
		          .type(EmRequest.class)
		          .order("-submitDate")
		          .limit(end-start).offset(start)
		          .list();
	
		return results;
	}
	
	public List<EmRequest> findLatestEmRequestByPatientId(long patientId){
		List<EmRequest> results = ObjectifyService.ofy()
		          .load()
		          .type(EmRequest.class)
		          .filter("patientId", patientId)
		          .order("-submitDate")
		          .limit(1)
		          .list();
	
		return results;
	}
	
	
	public List<EmRequest> findEmRequestByEmCenterId(long emCenterId){
		List<EmRequest> results = ObjectifyService.ofy()
		          .load()
		          .type(EmRequest.class)
		          .filter("emCenterId", emCenterId)
		          .list();

		return results;
	}
	
	public List<EmRequest> findOpenEmRequestByDriverId(long driverId){
		List<EmRequest> results = ObjectifyService.ofy()
		          .load()
		          .type(EmRequest.class)
		          .filter("emDriverId", driverId)
		          .filter("status != ", RequestStatus.STATUS[6])
		          .list();

		return results;
	}
	
	public List<EmRequest> findOpenEmRequestByEmCenterId(long emCenterId){
		List<EmRequest> results = ObjectifyService.ofy()
		          .load()
		          .type(EmRequest.class)
		          .filter("emCenterId", emCenterId)
		          .filter("status != ", RequestStatus.STATUS[6])
		          .list();

		return results;
	}
	
	
	public List<EmRequest> findEmRequestByEmCenterIdAndStatus(long emCenterId, String status){
		List<EmRequest> results = ObjectifyService.ofy()
		          .load()
		          .type(EmRequest.class)
		          .filter("emCenterId", emCenterId)
		          .filter("status", status)
		          .list();

		return results;
	}
		
}
	