package com.shtyka.web.command;

import com.shtyka.dao.exceptions.DaoException;
import com.shtyka.entity.Order;
import com.shtyka.entity.StatusMeal;
import com.shtyka.services.exceptions.ServiceException;
import com.shtyka.services.serviceImpl.OrderServiceImpl;
import com.shtyka.web.commandFactory.SessionRequestContent;
import com.shtyka.web.webManager.ConfigurationManager;
import com.shtyka.web.webManager.MessageManager;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
public class ClearCommand implements ActionCommand {

	public String execute(SessionRequestContent requestContent) throws DaoException, ServiceException {
		ResourceBundle property = ResourceBundle.getBundle(Locale.getDefault().getLanguage().toUpperCase(), Locale.getDefault());
		OrderServiceImpl order = OrderServiceImpl.getOrderServiceImpl();
		List<Order> orders = OrderServiceImpl.getOrderServiceImpl().findAll();

        for (Order order1 : orders) {
            if (order1.getStatusOrder().equals(property.getString(StatusMeal.COOKING.name()))) {
				requestContent.setAttribute("errorCookingMessage", MessageManager.getProperty("message.cookingerror"));
            } else {
                order.delete(order1.getOrderId());
            }
        }

		orders = order.findAll();
		requestContent.setAttribute("orders", orders);
        requestContent.setAttribute("sum", 0);
		return ConfigurationManager.getProperty("path.page.main");
	}
}