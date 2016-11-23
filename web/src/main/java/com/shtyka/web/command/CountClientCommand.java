package com.shtyka.web.command;

import com.shtyka.entity.Order;
import com.shtyka.services.exceptions.ServiceException;
import com.shtyka.services.serviceImpl.OrderServiceImpl;
import com.shtyka.web.commandFactory.SessionRequestContent;
import com.shtyka.web.webManager.ConfigurationManager;

import java.util.List;

public class CountClientCommand implements ActionCommand {

	public String execute(SessionRequestContent requestContent) throws ServiceException {
//		order.update(StatusMeal.CHEKED.name());
		List<Order> orders = OrderServiceImpl.getOrderServiceImpl().findAll();
		requestContent.setAttribute("sum", "0");
		return ConfigurationManager.getProperty("path.page.main");
	}
}