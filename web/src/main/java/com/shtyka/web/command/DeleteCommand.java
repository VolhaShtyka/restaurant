package com.shtyka.web.command;

import com.shtyka.entity.User;
import com.shtyka.services.exceptions.ServiceException;
import com.shtyka.services.serviceImpl.UserServiceImpl;
import com.shtyka.web.commandFactory.SessionRequestContent;
import com.shtyka.web.webManager.ConfigurationManager;

import java.io.Serializable;

public class DeleteCommand implements ActionCommand {

	public String execute(SessionRequestContent requestContent) throws ServiceException {
		Serializable id = (Serializable) requestContent.getAttribute("userid");
		User administatortDAO= UserServiceImpl.getUserServiceImpl().get(id);
		UserServiceImpl.getUserServiceImpl().delete(administatortDAO.getId());
		requestContent.removeAttribute("users");
		requestContent.removeAttribute("orders");
		return ConfigurationManager.getProperty("path.page.admin");
	}
}