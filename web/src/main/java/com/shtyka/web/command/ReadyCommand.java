package com.shtyka.web.command;

import com.shtyka.dao.exceptions.DaoException;
import com.shtyka.entity.Order;
import com.shtyka.entity.StatusMeal;
import com.shtyka.entity.User;
import com.shtyka.services.exceptions.ServiceException;
import com.shtyka.services.serviceImpl.OrderServiceImpl;
import com.shtyka.services.serviceImpl.UserServiceImpl;
import com.shtyka.web.commandFactory.SessionRequestContent;
import com.shtyka.web.webManager.ConfigurationManager;
import com.shtyka.web.webManager.MessageManager;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
public class ReadyCommand implements ActionCommand {

	public String execute(SessionRequestContent requestContent) throws DaoException, ServiceException {
		String page = null;
		ResourceBundle property = ResourceBundle
				.getBundle(Locale.getDefault().getLanguage().toUpperCase(), Locale.getDefault());
		List<User> clients = UserServiceImpl.getUserServiceImpl().findAll();
		OrderServiceImpl order = new OrderServiceImpl();
		List<Order> orders = order.findAll();
        for (Order order1 : orders) {
            if (order1.getStatusOrder().equals(property.getString(StatusMeal.CHEKED.name()))) {
				requestContent.setAttribute("errorCookingMessage", MessageManager.getProperty("message.cookingerror"));
                page = ConfigurationManager.getProperty("path.page.main");
            } else {
				order1.setStatusOrder(StatusMeal.READY.name());
                order.saveOrUpdate(order1);
            }
        }
		orders = order.findClientOrder(clients.get(0).getId());
		requestContent.setAttribute("orders", orders);
		return page;
	}
}