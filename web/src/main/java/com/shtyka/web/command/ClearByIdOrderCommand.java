package com.shtyka.web.command;
import com.shtyka.entity.Order;
import com.shtyka.services.exceptions.ServiceException;
import com.shtyka.services.serviceImpl.OrderServiceImpl;
import com.shtyka.web.commandFactory.SessionRequestContent;
import com.shtyka.web.webManager.ConfigurationManager;

import java.util.List;
public class ClearByIdOrderCommand implements ActionCommand {

	public String execute(SessionRequestContent requestContent) {
		try {
			List<Order> orders = OrderServiceImpl.getOrderServiceImpl().findAll();
			requestContent.setAttribute("orders", orders);
		}catch (ServiceException e){

		}
		return ConfigurationManager.getProperty("path.page.main");
	}
}