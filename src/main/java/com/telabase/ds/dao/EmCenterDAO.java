
package com.telabase.ds.dao;

import java.util.List;
import com.google.appengine.api.datastore.KeyFactory;
import com.googlecode.objectify.ObjectifyService;
import com.telabase.ds.entity.*;

public class EmCenterDAO {

	public int count() {
		return ObjectifyService.ofy().load().type(EmCenter.class).count();
	}

	public List<EmCenter> list() {
		List<EmCenter> results = ObjectifyService.ofy().load().type(EmCenter.class).list();

		return results;
	}

	public List<EmCenter> list(int start, int end) {

		List<EmCenter> results = ObjectifyService.ofy().load().type(EmCenter.class).limit(end - start).offset(start)
				.list();

		return results;

	}

	public void save(EmCenter o) {
		if (o.getId() == 0)
			o.setId(ObjectifyService.ofy().factory().allocateId(EmCenter.class).getId());
		ObjectifyService.ofy().save().entity(o).now();

	}

	public EmCenter findById(long id) {
		return ObjectifyService.ofy().load().type(EmCenter.class).id(id).now();
	}

	public void delete(EmCenter o) {
		ObjectifyService.ofy().delete().entity(o).now();
	}

	public List<EmCenter> searchByName(String keyword) {
		List<EmCenter> results = ObjectifyService.ofy().load().type(EmCenter.class).filter("name >=", keyword)
				.filter("name <", keyword + "\uFFFD").list();

		return results;
	}

	public List<EmCenter> searchBySecurityCode(String code) {
		List<EmCenter> results = ObjectifyService.ofy().load().type(EmCenter.class).filter("securityCode", code).list();

		return results;
	}

}
