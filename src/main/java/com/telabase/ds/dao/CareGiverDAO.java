
package com.telabase.ds.dao;

import java.util.List;
import com.google.appengine.api.datastore.KeyFactory;
import com.googlecode.objectify.ObjectifyService;
import com.telabase.ds.entity.*;

public class CareGiverDAO {
	
	public int count() {
		return ObjectifyService.ofy()
		          .load()
		          .type(CareGiver.class) 
		          
		          .count();
	}
	
	public List<CareGiver> list(){
		List<CareGiver> results = ObjectifyService.ofy()
		          .load()
		          .type(CareGiver.class) 
		          .limit(10)
		          .list();

		return results;
	}
	
	public List<CareGiver> list(int start,int end) {

		List<CareGiver> results = ObjectifyService.ofy()
		          .load()
		          .type(CareGiver.class)
		          .limit(end-start).offset(start)
		          .list();

		return results;

	}
	
	public void save(CareGiver o) {
		if(o.getId() == 0)
			o.setId(ObjectifyService.ofy().factory().allocateId(CareGiver.class).getId());
		ObjectifyService.ofy().save().entity(o).now();

	}
	
	public CareGiver findById(long id){
		return ObjectifyService.ofy().load().type(CareGiver.class).id(id).now();
	}

	public void delete(CareGiver o) {
		ObjectifyService.ofy().delete().entity(o).now();
	}
	
	public List<CareGiver> searchByFirstname(String keyword) {
			List<CareGiver> results = ObjectifyService.ofy()
					  .load()
					  .type(CareGiver.class)
					  .filter("firstname >=", keyword).filter("firstname <", keyword + "\uFFFD")
					  .list();

			return results;
		}
		
}
	