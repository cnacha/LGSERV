
package com.telabase.ds.dao;

import java.util.List;
import com.google.appengine.api.datastore.KeyFactory;
import com.googlecode.objectify.ObjectifyService;
import com.telabase.ds.entity.*;
import com.telabase.util.StringUtil;

public class PatientDAO {
	
	public int count() {
		return ObjectifyService.ofy()
		          .load()
		          .type(Patient.class) 
		          .count();
	}
	
	public List<Patient> list(){
		List<Patient> results = ObjectifyService.ofy()
		          .load()
		          .type(Patient.class) 
		          .list();

		return results;
	}
	
	public List<Patient> list(int start,int end) {

		List<Patient> results = ObjectifyService.ofy()
		          .load()
		          .type(Patient.class)
		          .limit(end-start).offset(start)
		          .list();

		return results;

	}
	
	public void save(Patient o) {
		if(o.getId() == 0) {
			// add new patient
			o.setId(ObjectifyService.ofy().factory().allocateId(Patient.class).getId());
			// generate security code used for adding to care giver
			o.setSecurityCode(StringUtil.randomNumber(10));
		}
		ObjectifyService.ofy().save().entity(o).now();

	}
	
	public Patient findById(long id){
		return ObjectifyService.ofy().load().type(Patient.class).id(id).now();
	}

	public void delete(Patient o) {
		ObjectifyService.ofy().delete().entity(o).now();
	}
	
	public List<Patient> searchByFirstname(String keyword) {
			List<Patient> results = ObjectifyService.ofy()
					  .load()
					  .type(Patient.class)
					  .filter("firstname >=", keyword).filter("firstname <", keyword + "\uFFFD")
					  .list();

			return results;
		}
		
	public List<Patient> searchBySecurityCode(String code) {
		List<Patient> results = ObjectifyService.ofy()
				  .load()
				  .type(Patient.class)
				  .filter("securityCode", code)
				  .list();

		return results;
	}
	
	public Patient searchByPhone(String ph) {
		List<Patient> results = ObjectifyService.ofy()
				  .load()
				  .type(Patient.class)
				  .filter("phone", ph)
				  .list();
		if(results!=null && results.size()>0)
			return results.get(0);
		else
			return null;
	}
}
	