package com.shtyka.web.command;

import com.shtyka.dao.exceptions.DaoException;
import com.shtyka.services.exceptions.ServiceException;
import com.shtyka.services.serviceImpl.UserServiceImpl;
import com.shtyka.web.commandFactory.SessionRequestContent;
import com.shtyka.web.webManager.ConfigurationManager;
import com.shtyka.web.webManager.MessageManager;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Controller
public class LoginCommand implements ActionCommand {
	private static final String PARAM_NAME_LOGIN = "login";
	private static final String PARAM_NAME_PASSWORD = "password";

    @Autowired
    private UserServiceImpl userService;

	public String execute(SessionRequestContent requestContent) throws ServiceException, DaoException {
		String amdinProfile = "administrator";
		String userProfile = "user";
		String page;
		String login = requestContent.getParameter(PARAM_NAME_LOGIN)[0];
		String password = requestContent.getParameter(PARAM_NAME_PASSWORD)[0];

		String status = userService.checkLoginAdmin(login, password);
		if (status.equals(amdinProfile)) {
			AdminCommand admin = new AdminCommand();
			admin.execute(requestContent);
			page = ConfigurationManager.getProperty("path.page.admin");
		} else if (status.equals(userProfile)) {
			ClientCommand client = new ClientCommand();
			client.execute(requestContent);
			page = ConfigurationManager.getProperty("path.page.main");
		} else {
			requestContent.setAttribute("errorLoginPasswordMessage", MessageManager.getProperty("message.loginerror"));
			page = ConfigurationManager.getProperty("path.page.login");
		}
		return page;
	}
}