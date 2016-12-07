package com.shtyka.web.command;

import com.shtyka.dao.exceptions.DaoException;
import com.shtyka.entity.Menu;
import com.shtyka.services.exceptions.ServiceException;
import com.shtyka.services.serviceImpl.MenuServiceImpl;
import com.shtyka.web.commandFactory.SessionRequestContent;
import com.shtyka.web.webManager.ConfigurationManager;

import java.util.List;

import static com.shtyka.web.command.ClientCommand.currentPage;
import static com.shtyka.web.command.ClientCommand.recordsPerPage;

public class ClearSort implements ActionCommand {

	public String execute(SessionRequestContent requestContent) throws DaoException, ServiceException {
		List<Menu> menus = MenuServiceImpl.getMenuServiceImpl().findAll(recordsPerPage, currentPage);

        int numberOfPages = MenuServiceImpl.getMenuServiceImpl().getNumberOfPages(recordsPerPage);
        requestContent.setAttribute("numberOfPages", numberOfPages);
		requestContent.setAttribute("menus", menus);
		requestContent.setAttribute("minPrice", null);
		requestContent.setAttribute("maxPrice", null);
		requestContent.setAttribute("minWeight", null);
		requestContent.setAttribute("maxWeight", null);
		return ConfigurationManager.getProperty("path.page.main");
	}
}