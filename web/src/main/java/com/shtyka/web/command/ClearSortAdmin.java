package com.shtyka.web.command;

import com.shtyka.dao.exceptions.DaoException;
import com.shtyka.entity.User;
import com.shtyka.services.exceptions.ServiceException;
import com.shtyka.services.serviceImpl.MenuServiceImpl;
import com.shtyka.services.serviceImpl.UserServiceImpl;
import com.shtyka.web.commandFactory.SessionRequestContent;
import com.shtyka.web.webManager.ConfigurationManager;

import java.util.ArrayList;
import java.util.List;

import static com.shtyka.web.command.ClientCommand.recordsPerPage;

public class ClearSortAdmin implements ActionCommand {

	public String execute(SessionRequestContent requestContent) throws DaoException, ServiceException {
		List<User> users = UserServiceImpl.getUserServiceImpl().findAll();
		List<User> clients = new ArrayList<>(30);
		for (User user : users) {
			if (user.getRoleId() != 1) {
				clients.add(user);
			}
		}
        int numberOfPages = MenuServiceImpl.getMenuServiceImpl().getNumberOfPages(recordsPerPage);
        requestContent.setAttribute("numberOfPages", numberOfPages);
		requestContent.setAttribute("users", clients);
		return ConfigurationManager.getProperty("path.page.admin");
	}
}