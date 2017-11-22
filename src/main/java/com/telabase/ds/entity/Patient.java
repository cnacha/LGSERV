
package com.telabase.ds.entity;

import java.util.Date;
import java.util.List;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.telabase.ds.dao.EmRequestDAO;
import com.telabase.json.formatter.*;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Patient {

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
	
	// Generated code for attribute: birthday
	@JsonDeserialize(using = DateDeserializer.class)
	@JsonSerialize(using = DateSerializer.class)
	private Date birthday;
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date param) {
		this.birthday = param;
	}
	
	// Generated code for attribute: photo
	private String photoFilePath;
	public String getPhotoFilePath() {
		return photoFilePath;
	}
	public void setPhotoFilePath(String param) {
		this.photoFilePath = param;
	}
	
	public String getPhotoFileURI() {
		return "/public/patient/photo/download.do?id="+this.id+"&timestamp="+(new Date()).getTime();
	}
	
	// Generated code for attribute: address
	private String address;
	public String getAddress() {
		return address;
	}
	public void setAddress(String param) {
		this.address = param;
	}
	
	// Generated code for attribute: disease
	private String disease;
	public String getDisease() {
		return disease;
	}
	public void setDisease(String param) {
		this.disease = param;
	}
	
	// Generated code for attribute: homeLat
	private double homeLat;
	public double getHomeLat() {
		return homeLat;
	}
	public void setHomeLat(double param) {
		this.homeLat = param;
	}
	
	// Generated code for attribute: homeLong
	private double homeLong;
	public double getHomeLong() {
		return homeLong;
	}
	public void setHomeLong(double param) {
		this.homeLong = param;
	}
	
	private double warnDistance;

	public double getWarnDistance() {
		return warnDistance;
	}
	public void setWarnDistance(double warnDistance) {
		this.warnDistance = warnDistance;
	}
	
	private String watchNumber;
	
	public String getWatchNumber() {
		return watchNumber;
	}
	public void setWatchNumber(String watchNumber) {
		this.watchNumber = watchNumber;
	}

	@Index
	private String phone;

	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Index
	private String securityCode;

	public String getSecurityCode() {
		return securityCode;
	}
	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}
	
	private String currentStatus = "normal";
	public String getCurrentStatus() {
		return this.currentStatus;
	}
	
	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}
	
	public int getAge() {
		Date now = new Date();
		long timeBetween = now.getTime() - birthday.getTime();
		double yearsBetween = timeBetween / 3.156e+10;
		int age = (int) Math.floor(yearsBetween);
		 return age;
	}
	
	private String gender;

	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	

}
	