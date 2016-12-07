package com.shtyka.web.command;

import com.shtyka.entity.Menu;
import com.shtyka.services.exceptions.ServiceException;
import com.shtyka.services.serviceImpl.MenuServiceImpl;
import com.shtyka.web.commandFactory.SessionRequestContent;
import com.shtyka.web.webManager.ConfigurationManager;

import java.util.List;

import static com.shtyka.web.command.ClientCommand.currentPage;
import static com.shtyka.web.command.ClientCommand.recordsPerPage;

public class ClientPageSortCommand implements ActionCommand {
	private Integer minPrice;
	private Integer maxPrice;
	private Integer minWeight;
	private Integer maxWeight;

	public String execute(SessionRequestContent requestContent) throws ServiceException {

		if (!requestContent.getParameter("minPrice")[0].isEmpty()) {
			minPrice = Integer.valueOf(requestContent.getParameter("minPrice")[0]);
			requestContent.setAttribute("minPrice", minPrice);
		} else {
			minPrice = 0;
		}
		if (!requestContent.getParameter("maxPrice")[0].isEmpty()) {
			maxPrice = Integer.valueOf(requestContent.getParameter("maxPrice")[0]);
			requestContent.setAttribute("maxPrice", maxPrice);
		}
		if (!requestContent.getParameter("minWeight")[0].isEmpty()) {
			minWeight = Integer.valueOf(requestContent.getParameter("minWeight")[0]);
			requestContent.setAttribute("minWeight", minWeight);
		}else {
			minWeight = 0;
		}
		if (!requestContent.getParameter("maxWeight")[0].isEmpty()) {
			maxWeight = Integer.valueOf(requestContent.getParameter("maxWeight")[0]);
			requestContent.setAttribute("maxWeight", maxWeight);
		}

		List<Menu> menus;


		if(minWeight == null && maxWeight ==null){
			menus = MenuServiceImpl.getMenuServiceImpl().findAll(recordsPerPage, currentPage, minPrice, maxPrice, minWeight, maxWeight);
//			for(Menu menu:menus) {
//			if (menu.getPrice() > maxPrice) {
//				maxPrice = menu.getPrice();
//			}
//			}
		}else{
			menus = MenuServiceImpl.getMenuServiceImpl().findAll(recordsPerPage, currentPage, minPrice, maxPrice, minWeight, maxWeight);
			}
        Integer numberOfPages = MenuServiceImpl.getMenuServiceImpl().getNumberPageWithFilter(minPrice, maxPrice, minWeight, maxWeight);

        requestContent.setAttribute("currentPage", currentPage);
        requestContent.setAttribute("numberOfPages", numberOfPages);
		requestContent.setAttribute("recordsPerPage", recordsPerPage);
        requestContent.setAttribute("menus", menus);


		return ConfigurationManager.getProperty("path.page.main");
	}
}