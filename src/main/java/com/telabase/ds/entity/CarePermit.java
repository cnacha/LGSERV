
package com.telabase.ds.entity;

import java.util.Date;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.telabase.ds.dao.CareGiverDAO;
import com.telabase.ds.dao.PatientDAO;
import com.telabase.json.formatter.*;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class CarePermit {

	@Id
	private long id;

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
		
	// Generated code for attribute: patient
	@Index
	private long patientId;
	public long getPatientId() {
		return patientId;
	}
	public void setPatientId(long param) {
		this.patientId = param;
	}
	    
	// Generated code for attribute: careGiver
	@Index
	private long careGiverId;
	public long getCareGiverId() {
		return careGiverId;
	}
	public void setCareGiverId(long param) {
		this.careGiverId = param;
	}
	
	public CareGiver getCareGiver() {
		CareGiverDAO cdao = new CareGiverDAO();
		return cdao.findById(this.careGiverId);
	}
	
	public Patient getPatient() {
		PatientDAO pdao = new PatientDAO();
		return pdao.findById(this.patientId);
	}
	
	    
}
	