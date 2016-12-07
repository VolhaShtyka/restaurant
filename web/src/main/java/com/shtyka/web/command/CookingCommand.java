package com.shtyka.web.command;

import com.shtyka.dao.exceptions.DaoException;
import com.shtyka.web.commandFactory.SessionRequestContent;
import com.shtyka.web.webManager.ConfigurationManager;

import java.util.Locale;
import java.util.ResourceBundle;
public class CookingCommand implements ActionCommand {

	public String execute(SessionRequestContent requestContent) throws DaoException {
		String page;
		ResourceBundle property = ResourceBundle.getBundle(Locale.getDefault().getLanguage().toUpperCase(), Locale.getDefault());
//		OrderDaoImpl order = new OrderDaoImpl();
//		List<Order> orders = order.findAll();
		//just change the order status
//        for (Order order1 : orders) {
//            if (order1.getStatusOrder().equals(property.getString(StatusMeal.NOREADY.name()))) {
//				order1.setStatusOrder(StatusMeal.COOKING.name());
//                order.saveOrUpdate(order1);
//            } else {
//				requestContent.setAttribute("errorCookingMessage", MessageManager.getProperty("message.cookingerror"));
//                page = ConfigurationManager.getProperty("path.page.main");
//            }
//        }
//		orders = order.findAll();
//		requestContent.setAttribute("orders", orders);
		page = ConfigurationManager.getProperty("path.page.main");
		return page;
	}
}