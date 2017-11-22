
package com.telabase.ds.entity;

import java.util.Date;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.telabase.ds.dao.PatientDAO;
import com.telabase.json.formatter.*;
import com.telabase.util.LocationUtil;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class LocationLog {

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
	    
	// Generated code for attribute: locLat
	private double locLat;
	public double getLocLat() {
		return locLat;
	}
	public void setLocLat(double param) {
		this.locLat = param;
	}
	
	// Generated code for attribute: locLong
	private double locLong;
	public double getLocLong() {
		return locLong;
	}
	public void setLocLong(double param) {
		this.locLong = param;
	}
	
	// Generated code for attribute: logDate
	@JsonDeserialize(using = DateTimeDeserializer.class)
	@JsonSerialize(using = DateTimeSerializer.class)
	@Index
	private Date logDate;
	public Date getLogDate() {
		return logDate;
	}
	public void setLogDate(Date param) {
		this.logDate = param;
	}
	
	public double getDistanceFromCenter() {
		PatientDAO dao = new PatientDAO();
		Patient p = dao.findById(this.patientId);
		return  LocationUtil.getInstance().distance(p.getHomeLat(), this.locLat, p.getHomeLong(), this.locLong, 0, 0);
	}
	
	
	
	
}
	