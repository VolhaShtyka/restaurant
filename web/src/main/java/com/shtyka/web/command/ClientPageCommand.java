package com.shtyka.web.command;

import com.shtyka.entity.Menu;
import com.shtyka.services.exceptions.ServiceException;
import com.shtyka.services.serviceImpl.MenuServiceImpl;
import com.shtyka.web.commandFactory.SessionRequestContent;
import com.shtyka.web.webManager.ConfigurationManager;

import java.util.List;

public class ClientPageCommand implements ActionCommand {
	private int currentPage;
	private int recordsPerPage;

	public String execute(SessionRequestContent requestContent) throws ServiceException {

        recordsPerPage = 2;
		if (requestContent.getParameter("currentPage")[0] != null) {
			currentPage = Integer.valueOf(requestContent.getParameter("currentPage")[0]);
		} else {
			currentPage = 1;
		}
		int numberOfPages = MenuServiceImpl.getMenuServiceImpl().getNumberOfPages(recordsPerPage);
        List<Menu> menus = MenuServiceImpl.getMenuServiceImpl().findAll(recordsPerPage, currentPage);
		requestContent.setAttribute("numberOfPages", numberOfPages);
		requestContent.setAttribute("currentPage", currentPage);
		requestContent.setAttribute("recordsPerPage", recordsPerPage);
        requestContent.setAttribute("menus", menus);

		return ConfigurationManager.getProperty("path.page.main");
	}
}