
package com.telabase.ds.entity;

import java.util.Date;
import java.util.List;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Ignore;
import com.googlecode.objectify.annotation.Index;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.telabase.ds.dao.EmRequestStatusLogDAO;
import com.telabase.ds.dao.PatientDAO;
import com.telabase.json.formatter.*;
import com.telabase.security.dao.UserDAO;
import com.telabase.security.entity.User;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmRequest {
	
	@Id
	private long id;

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
		
	// Generated code for attribute: submitDate
	@JsonDeserialize(using = DateTimeDeserializer.class)
	@JsonSerialize(using = DateTimeSerializer.class)
	@Index
	private Date submitDate;
	public Date getSubmitDate() {
		return submitDate;
	}
	public void setSubmitDate(Date param) {
		this.submitDate = param;
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
	    
	// Generated code for attribute: emCenter
	@Index
	private long emCenterId;
	public long getEmCenterId() {
		return emCenterId;
	}
	public void setEmCenterId(long param) {
		this.emCenterId = param;
	}
	
	@Index
	private long emDriverId;


	public long getEmDriverId() {
		return emDriverId;
	}
	public void setEmDriverId(long emDriverId) {
		this.emDriverId = emDriverId;
	}
	// Manual Code
	public Patient getPatient() {
		PatientDAO dao = new PatientDAO();
		return dao.findById(this.patientId);
	}
	
	public User getDriver() {
		if(emDriverId != 0) {
			UserDAO dao = new UserDAO();
			return dao.findById(emDriverId);
		}
		return null;
	}

	@Index
	private String status;

	public String getStatus() {
		
			return this.status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	private String locationDetails;

	public String getLocationDetails() {
		return locationDetails;
	}
	public void setLocationDetails(String locationDetails) {
		this.locationDetails = locationDetails;
	}
	
	@Ignore
	private String note;

	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
	private String callCenterUser;

	public String getCallCenterUser() {
		return callCenterUser;
	}
	public void setCallCenterUser(String callCenterUser) {
		this.callCenterUser = callCenterUser;
	}
	
	
}
	