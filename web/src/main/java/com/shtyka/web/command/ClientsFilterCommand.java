package com.shtyka.web.command;

import com.shtyka.entity.User;
import com.shtyka.services.exceptions.ServiceException;
import com.shtyka.services.serviceImpl.UserServiceImpl;
import com.shtyka.web.commandFactory.SessionRequestContent;
import com.shtyka.web.webManager.ConfigurationManager;

import java.util.ArrayList;
import java.util.List;


public class ClientsFilterCommand implements ActionCommand {
    public static int currentPage = 1;
    public static int recordsPerPage = 4;
    int numberOfPages;
	public String execute(SessionRequestContent requestContent) throws ServiceException {
		Integer maxFilterPrice = Integer.valueOf(requestContent.getParameter("maxPrice")[0]);
		Integer minFilterPrice = Integer.valueOf(requestContent.getParameter("minPrice")[0]);
		Integer maxFilterTableNumber = Integer.valueOf(requestContent.getParameter("maxTableNumber")[0]);
		Integer minFilterTableNumber= Integer.valueOf(requestContent.getParameter("minTableNumber")[0]);
//		Integer ASC = (Integer) requestContent.getAttribute("maxPrice");
//      Integer DESC = (Integer) requestContent.getAttribute("maxPrice");
        recordsPerPage = 4;
//        if (requestContent.getParameter("currentPage")[0] != null) {
//            currentPage = Integer.valueOf(requestContent.getParameter("currentPage")[0]);
//        } else {
            currentPage = 1;
  //      }
        numberOfPages = UserServiceImpl.getUserServiceImpl().getNumberPageWithFilter(minFilterPrice, maxFilterPrice, minFilterTableNumber, maxFilterTableNumber);
        List<User> clients = UserServiceImpl.getUserServiceImpl().findAll(recordsPerPage, currentPage, minFilterPrice, maxFilterPrice, minFilterTableNumber, maxFilterTableNumber, null);
		List<User> users = new ArrayList<>();
		for (User user:clients) {
			if (user.getRoleId() !=1){
				users.add(user);
			}
		}
			requestContent.setAttribute("users", users);
//			requestContent.setAttribute("minPrice", minFilterPrice);
//			requestContent.setAttribute("maxPrice", maxFilterPrice);
//			requestContent.setAttribute("minTableNumber", minFilterTableNumber);
//			requestContent.setAttribute("maxTableNumber", maxFilterTableNumber);

		return ConfigurationManager.getProperty("path.page.admin");
	}
}