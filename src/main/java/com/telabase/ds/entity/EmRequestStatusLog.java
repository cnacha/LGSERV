
package com.telabase.ds.entity;

import java.util.Date;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.telabase.json.formatter.*;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmRequestStatusLog {

	@Id
	private long id;

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
		
	// Generated code for attribute: status
	private String status;
	public String getStatus() {
		return status;
	}
	public void setStatus(String param) {
		this.status = param;
	}
	
	// Generated code for attribute: note
	private String note;
	public String getNote() {
		return note;
	}
	public void setNote(String param) {
		this.note = param;
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
	
	// Generated code for attribute: emRequest
	@Index
	private long emRequestId;
	public long getEmRequestId() {
		return emRequestId;
	}
	public void setEmRequestId(long param) {
		this.emRequestId = param;
	}
	    
}
	