package command;

import com.shtyka.dao.daoIlml.OrderDaoImpl;
import com.shtyka.dao.exceptions.DaoException;
import com.shtyka.entity.Order;
import com.shtyka.entity.StatusMeal;
import serviceManager.ConfigurationManager;
import serviceManager.MessageManager;
import commandFactory.SessionRequestContent;

import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
public class ClearCommand implements ActionCommand {

	public String execute(SessionRequestContent requestContent) throws SQLException, DaoException {
		String page;
		ResourceBundle property = ResourceBundle.getBundle(Locale.getDefault().getLanguage().toUpperCase(), Locale.getDefault());
		OrderDaoImpl order = new OrderDaoImpl();
		List<Order> orders = order.findAll();
		/*
		 * we can clear all orders,
		 * except those which are now being prepared
		 */
        for (Order order1 : orders) {
            if (order1.getStatusOrder().equals(property.getString(StatusMeal.COOKING.name()))) {
				requestContent.setAttribute("errorCookingMessage", MessageManager.getProperty("message.cookingerror"));
                page = ConfigurationManager.getProperty("path.page.main");
            } else {
                order.delete(order1);
            }
        }
		orders = order.findAll();
		requestContent.setAttribute("orders", orders);
		page = ConfigurationManager.getProperty("path.page.main");
		return page;
	}
}