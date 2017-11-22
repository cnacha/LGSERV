package com.telabase.ds.entity;

public class RequestStatus {

	public static final String[] STATUS = {
	                                       "calling",
	                                       "responded",
	                                       "assigned",
	                                       "pickingup",
	                                       "atpatient",
	                                       "delivered",
	                                       "closed"

										};
	
	
	public static int getStatusIndex(String s) {
		for(int i=0; i <STATUS.length; i++) {
			if(STATUS[i].equalsIgnoreCase(s))
				return i;
		}
		 return -1;
	}
	
	public static String getNextStatus(String s) {
		if(getStatusIndex(s)+1 < STATUS.length)
			return STATUS[getStatusIndex(s)+1];
		else 
			return "";
	}
	
	public static String getPrevStatus(String s) {
		if(getStatusIndex(s)-1 >= 0)
			return STATUS[getStatusIndex(s)-1];
		else 
			return "";
	}
}
