
package com.telabase.ds.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.telabase.ds.dao.CareGiverDAO;
import com.telabase.ds.dao.CarePermitDAO;
import com.telabase.ds.dao.PatientDAO;
import com.telabase.json.formatter.*;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class CareGiver {

	@Id
	private long id;

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
		
	// Generated code for attribute: firstname
	@Index
	private String firstname;
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String param) {
		this.firstname = param;
	}
	
	// Generated code for attribute: lastname
	private String lastname;
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String param) {
		this.lastname = param;
	}
	
	// Generated code for attribute: address
	private String address;
	public String getAddress() {
		return address;
	}
	public void setAddress(String param) {
		this.address = param;
	}
	
	// Manual Added
	private String phone;
	private String email;

	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public List<Patient> getPatients(){
		CarePermitDAO cdao = new CarePermitDAO();
		List<CarePermit> permits = cdao.findCarePermitByCareGiverId(this.id);
		List<Patient> patients = new ArrayList<Patient>();
		Patient p =null;
		for(CarePermit cp: permits) {
			p = cp.getPatient();
			if(p!=null)
				patients.add(p);
		}
		return patients;
		
	}
}
	