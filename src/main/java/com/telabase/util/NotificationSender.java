package com.telabase.util;

import java.util.List;
import java.util.logging.Logger;

import com.telabase.ds.entity.EmRequest;
import com.telabase.security.dao.UserDAO;
import com.telabase.security.entity.User;
import com.telabase.ws.controller.EmRequestController;

public class NotificationSender {
	
	private final static Logger logger = Logger.getLogger(NotificationSender.class.getName());

	public void toCallCenter(boolean alert, String msg) {
		// query all call centers user
		UserDAO userDAO = new UserDAO();

		List<User> callcenters = userDAO.findUserByAppRole("CallCenter");
		// send notification to call center
		PushService push = new PushService();
		try {
			for (User cc : callcenters) {

				push.send(cc.getDeviceTokenKey(), alert, msg);

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void toEmCenterUser(long id, String msg) {
		UserDAO userDAO = new UserDAO();
		PushService pushServe = new PushService();
		logger.info("toEmCenterUser() "+id); 
		try {
			List<User> users = userDAO.findUserByReferObjectIdAndAppRole(Long.toString(id), "EmCenter");
			for (User user : users) {
				logger.info("send msg to "+user.getDeviceTokenKey()); 
				pushServe.send(user.getDeviceTokenKey(), msg);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void toEmDriverUser(long id, String msg) {
		UserDAO userDAO = new UserDAO();
		PushService pushServe = new PushService();
		logger.info("toEmDriverUser() "+id); 
		try {
			User user = userDAO.findById(id);
			if (user!=null) {
				logger.info("send msg to "+user.getDeviceTokenKey()); 
				pushServe.send(user.getDeviceTokenKey(), msg);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
