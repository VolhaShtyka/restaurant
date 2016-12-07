package com.shtyka.web.command;

import com.shtyka.entity.User;
import com.shtyka.services.exceptions.ServiceException;
import com.shtyka.services.serviceImpl.UserServiceImpl;
import com.shtyka.web.commandFactory.SessionRequestContent;
import com.shtyka.web.webManager.ConfigurationManager;

import java.util.ArrayList;
import java.util.List;


public class AdminFilterCommand implements ActionCommand {
    public static int currentPage = 1;
    public static int recordsPerPage = 4;
    int numberOfPages;
	public String execute(SessionRequestContent requestContent) throws ServiceException {
		Integer maxFilterPrice = (Integer) requestContent.getAttribute("maxPrice");
		Integer minFilterPrice = (Integer) requestContent.getAttribute("minPrice");
		Integer maxFilterTableNumber = (Integer) requestContent.getAttribute("maxTableNumber");
		Integer minFilterTableNumber= (Integer) requestContent.getAttribute("minTableNumber");
        String parameterSort = requestContent.getParameter("upPrice")[0];
        if (minFilterPrice == null){
            minFilterPrice = 0;
        }
        if (minFilterTableNumber == null){
            minFilterTableNumber = 0;
        }

        recordsPerPage = 4;
//        if (requestContent.getParameter("currentPage")[0] != null) {
//            currentPage = Integer.valueOf(requestContent.getParameter("currentPage")[0]);
//        } else {
            currentPage = 1;
  //      }
        numberOfPages = UserServiceImpl.getUserServiceImpl().getNumberPageWithFilter(minFilterPrice, maxFilterPrice, minFilterTableNumber, maxFilterTableNumber);
        List<User> clients = UserServiceImpl.getUserServiceImpl().findAll(recordsPerPage, currentPage, minFilterPrice, maxFilterPrice, minFilterTableNumber, maxFilterTableNumber, parameterSort);
		List<User> users = new ArrayList<>();
		for (User user:clients) {
			if (user.getRoleId() !=1){
				users.add(user);
			}
		}
        System.out.println(requestContent.getParameter("upPrice")[0]);
        requestContent.setAttribute("users", users);
			requestContent.setAttribute("minPrice", minFilterPrice);
			requestContent.setAttribute("maxPrice", maxFilterPrice);
			requestContent.setAttribute("minTableNumber", minFilterTableNumber);
			requestContent.setAttribute("maxTableNumber", maxFilterTableNumber);

		return ConfigurationManager.getProperty("path.page.admin");
	}
}