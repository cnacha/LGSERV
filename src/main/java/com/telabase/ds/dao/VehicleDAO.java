
package com.telabase.ds.dao;

import java.util.List;
import com.google.appengine.api.datastore.KeyFactory;
import com.googlecode.objectify.ObjectifyService;
import com.telabase.ds.entity.*;

public class VehicleDAO {
	
	public int count() {
		return ObjectifyService.ofy()
		          .load()
		          .type(Vehicle.class) 
		          .count();
	}
	
	public List<Vehicle> list(){
		List<Vehicle> results = ObjectifyService.ofy()
		          .load()
		          .type(Vehicle.class) 
		          .list();

		return results;
	}
	
	public void save(Vehicle o) {
		if(o.getId() == 0)
			o.setId(ObjectifyService.ofy().factory().allocateId(Vehicle.class).getId());
		ObjectifyService.ofy().save().entity(o).now();

	}
	
	public Vehicle findById(long id){
		return ObjectifyService.ofy().load().type(Vehicle.class).id(id).now();
	}

	public void delete(Vehicle o) {
		ObjectifyService.ofy().delete().entity(o).now();
	}
	
	public List<Vehicle> searchByNumber(String keyword) {
			List<Vehicle> results = ObjectifyService.ofy()
					  .load()
					  .type(Vehicle.class)
					  .filter("number >=", keyword).filter("number <", keyword + "\uFFFD")
					  .list();

			return results;
		}
		public List<Vehicle> findVehicleByEmCenterId(long emCenterId){
		List<Vehicle> results = ObjectifyService.ofy()
		          .load()
		          .type(Vehicle.class)
		          .filter("emCenterId", emCenterId)
		          .list();

		return results;
	}
		
}
	