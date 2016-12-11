package com.shtyka.web.controllers;

import com.shtyka.services.UserService;
import com.shtyka.services.exceptions.ServiceException;
import com.shtyka.web.webManager.MessageManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index() {
		return "index";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String getLogin(ModelMap model,
						   @RequestParam(value = "login", defaultValue = "") String login,
						   @RequestParam(value = "password", defaultValue = "") String password,
						   RedirectAttributes redirectAttributes) throws ServiceException {
		String amdinProfile = "administrator";
		String userProfile = "user";
		String page;
		String status = userService.checkLoginAdmin(login, password);
		redirectAttributes.addFlashAttribute("login", login);
		if (status.equals(amdinProfile)) {
			page = "redirect:admins/admin";
		} else if (status.equals(userProfile)) {
			page = "redirect:clients/client";
		} else {
			model.addAttribute("errorLoginPasswordMessage", MessageManager.getProperty("message.loginerror"));
			page = "login";
		}
		return page;
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(SessionStatus status) throws ServiceException {
		status.setComplete();
		return "index";
	}
}
