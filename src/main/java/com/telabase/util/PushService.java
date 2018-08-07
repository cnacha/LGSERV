package com.telabase.util;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Logger;

import org.json.JSONObject;

public class PushService {
	
	private final static Logger logger = Logger.getLogger(PushService.class.getName());


	// Method to send Notifications from server to client end.

	public final static String AUTH_KEY_FCM = "AAAA_jGSWzs:APA91bF1Q6wsra6Kf6CCX8b1jj8ZTeQwLCYdbvmCc3csH6nRrsQJel2V1_NDqXQFlFJy3e5iGOLBZmhgxw4XE36NL97r1Q6GvOuvHO2PLKMid8PbNQt3tFXIJyaMjPNiZy0ZDoc1S8y_";
	public final static String API_URL_FCM = "https://fcm.googleapis.com/fcm/send";
	
	public final static String AUTH_KEY_IONIC = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJqdGkiOiI2YzJkOTNmOC05N2ZiLTQ5YjEtOWRhMS1hZDZjODNhZGQwMTkifQ.WXrPN2Y-sDh08Yj9simCKFKbb5hfQJJgcZEr7x_XND4";
	public final static String API_URL_IONIC = "https://api.ionic.io/push/notifications";

	// userDeviceIdKey is the device id you will query from your database

	public boolean send(String deviceTokenKey, String body) throws Exception {
		
		return sendFCM(deviceTokenKey,false, body);
		//return sendIONIC(deviceTokenKey, body);
	}
	
public boolean send(String deviceTokenKey,boolean alarm,  String body) throws Exception {
		
		return sendFCM(deviceTokenKey,alarm, body);
		//return sendIONIC(deviceTokenKey, body);
	}
	
	
	private boolean sendIONIC(String deviceTokenKey, String body) throws Exception {

		String authKey = AUTH_KEY_IONIC;
		String FMCurl = API_URL_IONIC;
		try {
			URL url = new URL(FMCurl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			conn.setUseCaches(false);
			conn.setDoInput(true);
			conn.setDoOutput(true);

			conn.setRequestMethod("POST");
			conn.setRequestProperty("Authorization", "Bearer " + authKey);
			conn.setRequestProperty("Content-Type", "application/json");

			JSONObject json = new JSONObject();
			json.put("tokens", new String[]{deviceTokenKey.trim()});
			json.put("profile", "Life Guard"); 
			JSONObject notification = new JSONObject();
			notification.put("message", body);
			json.put("notification", notification);

			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			wr.write(json.toString());
			wr.flush();
			conn.getInputStream();
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}
	
	private boolean sendFCM(String deviceTokenKey,boolean alarm, String body) throws Exception {

		String authKey = AUTH_KEY_FCM;
		String FMCurl = API_URL_FCM;
		
		logger.info("sending push notification to "+deviceTokenKey+" alarm: "+alarm);
		if(deviceTokenKey == null)
			return false;
		
		

		try {
			URL url = new URL(FMCurl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			conn.setUseCaches(false);
			conn.setDoInput(true);
			conn.setDoOutput(true);

			conn.setRequestMethod("POST");
			conn.setRequestProperty("Authorization", "key=" + authKey);
			conn.setRequestProperty("Content-Type", "application/json");

			JSONObject json = new JSONObject();
			ArrayList<String> recepients = new ArrayList<String>();
			recepients.add(deviceTokenKey.trim());
			json.put("registration_ids", recepients.toArray());
			JSONObject info = new JSONObject();
			info.put("title", alarm?"Lifeguard Alert":"Lifeguard Message"); 
			info.put("body", body);
			info.put("sound", "default");
			info.put("badge", "0");
			
			if(alarm) {
				info.put("content-available","1");
				info.put("force-start","1");
				json.put("data", info);
			} else {
				json.put("notification", info);
			}
			
			

			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			wr.write(json.toString());
			wr.flush();
			conn.getInputStream();
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	public static void main(String[] args) {
		try { 
			PushService fcm = new PushService();
			fcm.send(
					"fSTWevBNUUA:APA91bFMidgHCPdcHpi6WUs4pkO1-Yh-QtQE92i7McCxRv9nmJ2LPLoKxYAl7r05Hd5izJmIsh1tqTuu8dha9wq2VqMofjwchEgPWryM4hMMItWLbVcTRW0a8hebhmXPJUd4C6UpsNL_",
					false,
					"Hello Test from GAE");
			System.out.println("Success");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
