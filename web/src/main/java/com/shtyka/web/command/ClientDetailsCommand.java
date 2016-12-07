package com.shtyka.web.command;

import com.shtyka.entity.Order;
import com.shtyka.entity.User;
import com.shtyka.services.exceptions.ServiceException;
import com.shtyka.services.serviceImpl.OrderServiceImpl;
import com.shtyka.services.serviceImpl.UserServiceImpl;
import com.shtyka.web.commandFactory.SessionRequestContent;
import com.shtyka.web.webManager.ConfigurationManager;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;


public class ClientDetailsCommand implements ActionCommand {

	public String execute(SessionRequestContent requestContent) {
		final Logger log = Logger.getLogger(ClientDetailsCommand.class);
		int sum = 0;
		try {
			List<User> users = UserServiceImpl.getUserServiceImpl().findAll();
            List<User> clients = new ArrayList<>();
			for (User user:users) {
				if (user.getRoleId() !=1){
					clients.add(user);
				}
			}

			User user = UserServiceImpl.getUserServiceImpl().findByLogin(requestContent.getParameter("user")[0]);
			List<Order> orders = OrderServiceImpl.getOrderServiceImpl().findClientOrder(user.getId());
			for (int i = 0; i < orders.size(); i++) {
				sum += UserServiceImpl.getUserServiceImpl().countOrder(user);
			}
			requestContent.setAttribute("users", clients);
			requestContent.setAttribute("orders", orders);
			requestContent.setAttribute("sum", sum);
			requestContent.setAttribute("userid", user.getId());
			if (OrderCommand.comments != null) {
				requestContent.setAttribute("comment", OrderCommand.comments);
			}
		}catch (ServiceException e){
			log.info("Error Database.");
			return ConfigurationManager.getProperty("path.page.errorDatabase");
//			request.setAttribute("errorDatabase", MessageManager.getProperty("message.errorDatabase"));
		}
		return ConfigurationManager.getProperty("path.page.client");
	}
}