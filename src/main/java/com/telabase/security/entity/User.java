package com.telabase.security.entity;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.telabase.ds.dao.CareGiverDAO;
import com.telabase.ds.dao.EmCenterDAO;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements Serializable{
	
	@Id
	private long id;
	
	@Index
	private String username;
	@Index
	private String password;
	private String firstname;
	private String lastname;
	private String role;
	private int status;
	
	
	@Index
	private String deviceTokenKey;
	
	@Index
	private String authorizationKey;
	
	@Index 
	private String appRole;
	
	@Index 
	private long appReferObjectId;
	
	private String email;
	private String phone;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getDeviceTokenKey() {
		return deviceTokenKey;
	}
	public void setDeviceTokenKey(String deviceTokenKey) {
		this.deviceTokenKey = deviceTokenKey;
	}
	public String getAuthorizationKey() {
		return authorizationKey;
	}
	public void setAuthorizationKey(String authorizationKey) {
		this.authorizationKey = authorizationKey;
	}
	public String getAppRole() {
		return appRole;
	}
	public void setAppRole(String appRole) {
		this.appRole = appRole;
	}
	public long getAppReferObjectId() {
		return appReferObjectId;
	}
	public void setAppReferObjectId(long appReferObjectId) {
		this.appReferObjectId = appReferObjectId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Object getReferenceObject() {
		if("CareGiver".equalsIgnoreCase(this.appRole)) {
			CareGiverDAO dao = new CareGiverDAO();
			return dao.findById(this.appReferObjectId);
			
		} else if("EMcenter".equalsIgnoreCase(this.appRole) || "EMdriver".equalsIgnoreCase(this.appRole)) {
			EmCenterDAO dao = new EmCenterDAO();
			return dao.findById(this.appReferObjectId);
			
		} else
			return null;
	}
	
	
	

}
