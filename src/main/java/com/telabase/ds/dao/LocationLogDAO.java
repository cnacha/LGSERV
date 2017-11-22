
package com.telabase.ds.dao;

import java.util.List;
import com.google.appengine.api.datastore.KeyFactory;
import com.googlecode.objectify.ObjectifyService;
import com.telabase.ds.entity.*;

public class LocationLogDAO {
	
	public int count() {
		return ObjectifyService.ofy()
		          .load()
		          .type(LocationLog.class) 
		          .count();
	}
	
	public List<LocationLog> list(){
		List<LocationLog> results = ObjectifyService.ofy()
		          .load()
		          .type(LocationLog.class) 
		          .list();

		return results;
	}
	
	public void save(LocationLog o) {
		if(o.getId() == 0)
			o.setId(ObjectifyService.ofy().factory().allocateId(LocationLog.class).getId());
		ObjectifyService.ofy().save().entity(o).now();

	}
	
	public LocationLog findById(long id){
		return ObjectifyService.ofy().load().type(LocationLog.class).id(id).now();
	}

	public void delete(LocationLog o) {
		ObjectifyService.ofy().delete().entity(o).now();
	}
	
	public List<LocationLog> findLocationLogByPatientId(long patientId){
		List<LocationLog> results = ObjectifyService.ofy()
		          .load()
		          .type(LocationLog.class)
		          .filter("patientId", patientId)
		          .limit(5)
		          .order("-logDate")
		          .list();

		return results;
	}
		
}
	