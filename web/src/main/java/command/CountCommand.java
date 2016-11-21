package command;

import com.shtyka.dao.exceptions.DaoException;
import com.shtyka.entity.Order;
import com.shtyka.entity.StatusMeal;
import com.shtyka.entity.User;
import com.shtyka.services.exceptions.ServiceException;
import com.shtyka.services.serviceImpl.OrderServiceImpl;
import com.shtyka.services.serviceImpl.UserServiceImpl;
import command.commandFactory.SessionRequestContent;
import webManager.ConfigurationManager;
import webManager.MessageManager;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class CountCommand implements ActionCommand {

	public String execute(SessionRequestContent requestContent) throws DaoException, ServiceException {
		ResourceBundle property = ResourceBundle.getBundle(Locale.getDefault().getLanguage().toUpperCase(), Locale.getDefault());
		List <User> clients = UserServiceImpl.getUserServiceImpl().findAll();
		OrderServiceImpl order = new OrderServiceImpl();
		List <Order> orders = OrderServiceImpl.getOrderServiceImpl().findOrderClient(clients.get(0).getId());

        for (Order order1 : orders) {
            if (order1.getStatusOrder().equals(property.getString(StatusMeal.CHEKED.name()))) {
				requestContent.setAttribute("errorCookingCheked", MessageManager.getProperty("message.errorCookingCheked"));
            } else {
				order1.setStatusOrder(StatusMeal.CHEKED.name());
                order.saveOrUpdate(order1);
            }
        }
		orders = order.findOrderClient(clients.get(0).getId());
		requestContent.setAttribute("orders", orders);
		requestContent.setAttribute("sum", "");
		return ConfigurationManager.getProperty("path.page.admin");
	}
}