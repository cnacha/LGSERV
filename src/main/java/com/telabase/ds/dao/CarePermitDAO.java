
package com.telabase.ds.dao;

import java.util.List;
import com.google.appengine.api.datastore.KeyFactory;
import com.googlecode.objectify.ObjectifyService;
import com.telabase.ds.entity.*;

public class CarePermitDAO {
	
	public int count() {
		return ObjectifyService.ofy()
		          .load()
		          .type(CarePermit.class) 
		          .count();
	}
	
	public List<CarePermit> list(){
		List<CarePermit> results = ObjectifyService.ofy()
		          .load()
		          .type(CarePermit.class) 
		          .list();

		return results;
	}
	
	public void save(CarePermit o) {
		if(o.getId() == 0)
			o.setId(ObjectifyService.ofy().factory().allocateId(CarePermit.class).getId());
		ObjectifyService.ofy().save().entity(o).now();

	}
	
	public CarePermit findById(long id){
		return ObjectifyService.ofy().load().type(CarePermit.class).id(id).now();
	}

	public void delete(CarePermit o) {
		ObjectifyService.ofy().delete().entity(o).now();
	}
	
	public List<CarePermit> findCarePermitByPatientId(long patientId){
		List<CarePermit> results = ObjectifyService.ofy()
		          .load()
		          .type(CarePermit.class)
		          .filter("patientId", patientId)
		          .list();

		return results;
	}
	
	public List<CarePermit> findCarePermitByPatientIdAndCareGiverId(long patientId, long careGiverId){
		List<CarePermit> results = ObjectifyService.ofy()
		          .load()
		          .type(CarePermit.class)
		          .filter("patientId", patientId)
		          .filter("careGiverId", careGiverId)
		          .list();

		return results;
	}
	
	public List<CarePermit> findCarePermitByCareGiverId(long careGiverId){
		List<CarePermit> results = ObjectifyService.ofy()
		          .load()
		          .type(CarePermit.class)
		          .filter("careGiverId", careGiverId)
		          .list();

		return results;
	}
		
}
	