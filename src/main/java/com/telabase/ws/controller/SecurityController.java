package com.telabase.ws.controller;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.telabase.ds.dao.CareGiverDAO;
import com.telabase.ds.dao.EmCenterDAO;
import com.telabase.ds.entity.CareGiver;
import com.telabase.ds.entity.EmCenter;
import com.telabase.security.SecurityManager;
import com.telabase.security.dao.UserDAO;
import com.telabase.security.entity.User;
import com.telabase.util.SecurityUtil;
import com.telabase.util.StringUtil;
import com.telabase.ws.model.WSResponse;

@Controller
public class SecurityController {

	private SecurityManager securityManager = new SecurityManager();
	private final static Logger logger = Logger.getLogger(SecurityController.class .getName()); 

	@RequestMapping(value = "/api/security/logout", method = RequestMethod.GET)
	public @ResponseBody WSResponse logout(HttpServletRequest request, HttpServletResponse response) {
		request.getSession().setAttribute(SecurityManager.USER_DATA_SESSION_ATTRIBUTE, null);
		securityManager.destroyAuthority(request);
		// reset device token
		UserDAO userService = new UserDAO();
		User user = userService.findUserByName(request.getParameter("username"));
		if(user != null) {
			user.setDeviceTokenKey("");
			user.setAuthorizationKey("");
			userService.save(user);
		}
		return new WSResponse(null, WSResponse.STATUS_SUCCESS);
	}

	@RequestMapping(value = "/api/security/login", method = RequestMethod.POST)
	@ResponseBody
	public User login(HttpServletRequest request,@RequestBody User loginBean) {
		logger.info("login: " + loginBean.getUsername() + " " + loginBean.getPassword());
		try {
			SecurityUtil secureUtil = new SecurityUtil();
			UserDAO userService = new UserDAO();
			User validUser = userService.findUser(loginBean.getUsername(), secureUtil.encrypt(loginBean.getPassword()));
			logger.info("found user: "+validUser.getUsername());
			logger.info(validUser.getAppRole()+"<=>"+loginBean.getAppRole());
			if ("".equals(loginBean.getAppRole()) || (validUser != null && validUser.getAppRole().equals(loginBean.getAppRole())) ) {
				logger.info("Login Successful");
				securityManager.initAuthority(request, validUser);
				return validUser;
			} else {
				return null;
			}
			

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	@RequestMapping(value = "/api/user/save", method = RequestMethod.POST)
	@ResponseBody
	public WSResponse saveUser(HttpServletRequest request,@RequestBody User user) {
		logger.info("save user: " + user.getUsername());
		try {

			UserDAO userService = new UserDAO();
			User validUser = userService.findUserByName(user.getUsername());
			validUser.setFirstname(user.getFirstname());
			validUser.setLastname(user.getLastname());
			validUser.setPhone(user.getPhone());
			validUser.setEmail(user.getEmail());
			userService.save(validUser);		
			
		} catch (Exception e) {
			e.printStackTrace();
			return new WSResponse(null, WSResponse.STATUS_FAIL);
		}

		return new WSResponse(null, WSResponse.STATUS_SUCCESS);
	}
	
	@RequestMapping(value = "/api/user/changePassword", method = RequestMethod.POST)
	@ResponseBody
	public WSResponse changePassword(HttpServletRequest request,@RequestBody User user) {
		logger.info("change pw user: " + user.getUsername());
		try {

			UserDAO userService = new UserDAO();
			User validUser = userService.findUserByName(user.getUsername());
			// encrypt password
			SecurityUtil secureUtil = new SecurityUtil();
			validUser.setPassword(secureUtil.encrypt(user.getPassword()));
			userService.save(validUser);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new WSResponse(null, WSResponse.STATUS_FAIL);
		}

		return new WSResponse(null, WSResponse.STATUS_SUCCESS);
	}
	
	@RequestMapping(value = "/api/security/resetPassword", method = RequestMethod.POST)
	@ResponseBody
	public WSResponse resetPassword(HttpServletRequest request,@RequestBody User user) {
		logger.info("change pw user: " + user.getUsername());
		try {

			UserDAO userService = new UserDAO();
			User validUser = userService.findUserByName(user.getUsername());
			if(validUser== null)
				return new WSResponse("Wrong Username", WSResponse.STATUS_FAIL);
			else if(!validUser.getEmail().equalsIgnoreCase(user.getEmail()))
				return new WSResponse("Wrong Email", WSResponse.STATUS_FAIL);
			
			// encrypt password
			String password = StringUtil.randomString(7);
			SecurityUtil secureUtil = new SecurityUtil();
			validUser.setPassword(secureUtil.encrypt(password));
			userService.save(validUser);
			
			validUser.setPassword(password);
			EmailController emailcon = new EmailController();
			emailcon.sendResetPasswordEmail(validUser);
		} catch (Exception e) {
			e.printStackTrace();
			return new WSResponse(null, WSResponse.STATUS_FAIL);
		}

		return new WSResponse(null, WSResponse.STATUS_SUCCESS);
	}
	
	
	@RequestMapping(value = "/api/security/register", method = RequestMethod.POST)
	@ResponseBody
	public WSResponse register(@RequestBody User user, HttpServletRequest request) {
		logger.info("register " + user.getPassword());

		UserDAO userService = new UserDAO();
		try {
			if(user.getUsername() == null || user.getUsername().equals(""))
				return new WSResponse(WSResponse.STATUS_FAIL, "Username must contain proper text");
			// Check Duplicate user
			if (userService.checkDuplicateUsername(user.getUsername())) {
				return new WSResponse(WSResponse.STATUS_FAIL_SECURITY_DUPLICATE_USER, "Duplicate Username");
			}

			// create care giver profile along with user
			if(user.getAppRole().equalsIgnoreCase("CareGiver")) {
				CareGiver cg = new CareGiver();
				cg.setFirstname(user.getFirstname());
				cg.setLastname(user.getLastname());
				cg.setEmail(user.getEmail());
				cg.setPhone(user.getPhone());
				CareGiverDAO dao = new CareGiverDAO();
				dao.save(cg);
				user.setAppRole("CareGiver");
				user.setAppReferObjectId(cg.getId());
		
			// create emergency center profile along with user
			}
			/**
			else if(user.getAppRole().equalsIgnoreCase("EmCenter")) {
				EmCenter em = new EmCenter();
				em.setName(user.getFirstname());
				em.setDescription(user.getLastname());
				em.setPhone(user.getPhone());
				EmCenterDAO dao = new EmCenterDAO();
				dao.save(em);
				user.setAppRole("EmCenter");
			
				user.setAppReferObjectId(em.getId());
			}
			**/
			
			user.setRole(SecurityManager.ROLE_USER);
			user.setStatus(1);
			// encrypt password
			SecurityUtil secureUtil = new SecurityUtil();
			String password = user.getPassword();
			user.setPassword(secureUtil.encrypt(user.getPassword()));
			userService.save(user);
			
			EmailController emailcon = new EmailController();
			user.setPassword(password);
			emailcon.sendUserRegistrationEmail(user);
			
			return new WSResponse(null, WSResponse.STATUS_SUCCESS);


		} catch (Exception e) {
			e.printStackTrace();
			return new WSResponse(WSResponse.STATUS_FAIL, "Error during registration");
		}

	}
	
	@RequestMapping(value = "/api/security/pushtoken/save", method = RequestMethod.GET)
	@ResponseBody
	public WSResponse updateDeviceTokenKeyOnSpecificUser(HttpServletRequest request) {

		long userId = Long.parseLong(request.getParameter("userId"));
		String tokenKey = request.getParameter("tokenKey");

		try {
			UserDAO dao = new UserDAO();
			User user = dao.findById(userId);
			user.setDeviceTokenKey(tokenKey);
			dao.save(user);
		} catch (Exception e) {
			e.printStackTrace();
			return new WSResponse(WSResponse.STATUS_FAIL, e.getMessage());
		}

		return new WSResponse(null, WSResponse.STATUS_SUCCESS);
	}


	
}
