package com.telabase.ws.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.telabase.security.dao.UserDAO;
import com.telabase.security.entity.User;

@Controller
public class PresetController {
	@RequestMapping(value = "/preset/createAdmin", method = RequestMethod.GET)
	@ResponseBody
	public String userTester() {

		try {

			UserDAO userService1 = new UserDAO();
			User user1 = new User();
			user1.setFirstname("Administrator");
			user1.setLastname("Administrator");
			user1.setPassword("admin");
			user1.setUsername("admin");
			user1.setRole("Admin");
			user1.setStatus(1);
			user1.setAuthorizationKey("AAABBBCCC");
			userService1.save(user1);

		} catch (Exception e) {
			e.printStackTrace();
			return "-1";
		}

		return "1";
	}
}
