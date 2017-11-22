
package com.telabase.ds.dao;

import java.util.List;
import com.google.appengine.api.datastore.KeyFactory;
import com.googlecode.objectify.ObjectifyService;
import com.telabase.ds.entity.*;

public class EmRequestStatusLogDAO {
	
	public int count() {
		return ObjectifyService.ofy()
		          .load()
		          .type(EmRequestStatusLog.class) 
		          .count();
	}
	
	public List<EmRequestStatusLog> list(){
		List<EmRequestStatusLog> results = ObjectifyService.ofy()
		          .load()
		          .type(EmRequestStatusLog.class) 
		          .list();

		return results;
	}
	
	public void save(EmRequestStatusLog o) {
		if(o.getId() == 0)
			o.setId(ObjectifyService.ofy().factory().allocateId(EmRequestStatusLog.class).getId());
		ObjectifyService.ofy().save().entity(o).now();

	}
	
	public EmRequestStatusLog findById(long id){
		return ObjectifyService.ofy().load().type(EmRequestStatusLog.class).id(id).now();
	}

	public void delete(EmRequestStatusLog o) {
		ObjectifyService.ofy().delete().entity(o).now();
	}
	
	public List<EmRequestStatusLog> findEmRequestStatusLogByEmRequestId(long emRequestId){
		List<EmRequestStatusLog> results = ObjectifyService.ofy()
		          .load()
		          .type(EmRequestStatusLog.class)
		          .filter("emRequestId", emRequestId)
		          .order("-logDate")
		          .list();

		return results;
	}
	
	public List<EmRequestStatusLog> findCurrentStatusByEmRequestId(long emRequestId){
		List<EmRequestStatusLog> results = ObjectifyService.ofy()
		          .load()
		          .type(EmRequestStatusLog.class)
		          .filter("emRequestId", emRequestId)
		          .order("-logDate")
		          .limit(1)
		          .list();

		return results;
	}
		
}
	