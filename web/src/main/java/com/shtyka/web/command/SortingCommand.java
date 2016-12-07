package com.shtyka.web.command;

import com.shtyka.entity.Menu;
import com.shtyka.services.exceptions.ServiceException;
import com.shtyka.services.serviceImpl.MenuServiceImpl;
import com.shtyka.web.commandFactory.SessionRequestContent;
import com.shtyka.web.webManager.ConfigurationManager;

import java.util.List;

public class SortingCommand implements ActionCommand {

	public String execute(SessionRequestContent requestContent) throws ServiceException {

        int sort = Integer.valueOf(requestContent.getParameter("sortPriceOrWeight")[0]);
        List<Menu> menus = (List<Menu>) requestContent.getAttribute("menus");
        menus = MenuServiceImpl.getMenuServiceImpl().sortingMenu(menus, sort);

        requestContent.setAttribute("menus", menus);



		return ConfigurationManager.getProperty("path.page.main");
	}
}