
package com.telabase.ds.entity;

import java.util.Date;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Ignore;
import com.googlecode.objectify.annotation.Index;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.telabase.json.formatter.*;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmCenter {

	@Id
	private long id;

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
		
	// Generated code for attribute: name
	@Index
	private String name;
	public String getName() {
		return name;
	}
	public void setName(String param) {
		this.name = param;
	}
	
	// Generated code for attribute: description
	private String description;
	public String getDescription() {
		return description;
	}
	public void setDescription(String param) {
		this.description = param;
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
	
	private String phone;

	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
	private double avgResponseRate;
	private double avgPickupMinsTime;
	private double avgDeliveryMinsTime;

	public double getAvgResponseRate() {
		return avgResponseRate;
	}
	public void setAvgResponseRate(double avgResponse) {
		this.avgResponseRate = avgResponse;
	}
	public double getAvgPickupMinsTime() {
		return avgPickupMinsTime;
	}
	public void setAvgPickupMinsTime(double avgPickupMinsTime) {
		this.avgPickupMinsTime = avgPickupMinsTime;
	}
	public double getAvgDeliveryMinsTime() {
		return avgDeliveryMinsTime;
	}
	public void setAvgDeliveryMinsTime(double avgDeliveryMinsTime) {
		this.avgDeliveryMinsTime = avgDeliveryMinsTime;
	}
	
	@Ignore 
	private double distance;

	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
	
	@Index
	private String securityCode;

	public String getSecurityCode() {
		return securityCode;
	}
	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}
	
	
	
	
	

}
	