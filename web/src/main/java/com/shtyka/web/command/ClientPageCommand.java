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

        recordsPerPage = 4;
		if (requestContent.getParameter("currentPage")[0] != null) {
			currentPage = Integer.valueOf(requestContent.getParameter("currentPage")[0]);
		} else {
			currentPage = 1;
		}
        List<Menu> menus;

        Integer minPrice = null;
        Integer  maxPrice= null;
        Integer minWeight= null;
        Integer maxWeight= null;
        if(requestContent.getAttribute("minPrice")!=null){
            minPrice = Integer.valueOf((Integer) requestContent.getAttribute("minPrice"));
        }
        if(requestContent.getAttribute("maxPrice")!=null){
            maxPrice = Integer.valueOf((Integer)requestContent.getAttribute("maxPrice"));
        }
        if(requestContent.getAttribute("minWeight")!=null){
            minWeight =  Integer.valueOf((Integer)requestContent.getAttribute("minWeight"));
        }
        if(requestContent.getAttribute("maxWeight")!=null){

            maxWeight = Integer.valueOf((Integer)requestContent.getAttribute("maxWeight"));
        }
        int numberOfPages;
        if(minPrice != null || maxPrice != null || minWeight != null || maxWeight != null) {
            menus = MenuServiceImpl.getMenuServiceImpl().findAll(recordsPerPage, currentPage, minPrice, maxPrice, minWeight, maxWeight);
            numberOfPages = MenuServiceImpl.getMenuServiceImpl().getNumberPageWithFilter(minPrice, maxPrice, minWeight, maxWeight);
        }else {
            menus = MenuServiceImpl.getMenuServiceImpl().findAll(recordsPerPage, currentPage);
            numberOfPages = MenuServiceImpl.getMenuServiceImpl().getNumberOfPages(recordsPerPage);
        }

		requestContent.setAttribute("numberOfPages", numberOfPages);
		requestContent.setAttribute("currentPage", currentPage);
		requestContent.setAttribute("recordsPerPage", recordsPerPage);
        requestContent.setAttribute("menus", menus);

		return ConfigurationManager.getProperty("path.page.main");
	}
}