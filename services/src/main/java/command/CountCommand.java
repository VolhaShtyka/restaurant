package command;

import com.shtyka.dao.daoIlml.OrderDaoImpl;
import com.shtyka.dao.daoIlml.UserDaoImpl;
import com.shtyka.dao.exceptions.DaoException;
import com.shtyka.entity.Order;
import com.shtyka.entity.StatusMeal;
import com.shtyka.entity.User;
import commandFactory.SessionRequestContent;
import serviceManager.ConfigurationManager;
import serviceManager.MessageManager;

import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class CountCommand implements ActionCommand {

	public String execute(SessionRequestContent requestContent) throws SQLException, DaoException {
		String page;
		ResourceBundle property = ResourceBundle.getBundle(Locale.getDefault().getLanguage().toUpperCase(), Locale.getDefault());
		//HttpSession session = request.getSession(true);
		UserDaoImpl clientdao = new UserDaoImpl();
		List <User> clients = clientdao.findAll();
		OrderDaoImpl order = new OrderDaoImpl();
		List <Order> orders = order.findOrderClient(clients.get(0).getId());
		/*
		 * find the right client and
		 * if the order has not yet paid the
		 * amount you want to deduce pay
		 */
        for (Order order1 : orders) {
            if (order1.getStatusOrder().equals(property.getString(StatusMeal.CHEKED.name()))) {
				requestContent.setAttribute("errorCookingCheked", MessageManager.getProperty("message.errorCookingCheked"));
                page = ConfigurationManager.getProperty("path.page.admin");
            } else {
				order1.setStatusOrder(StatusMeal.CHEKED.name());
                order.saveOrUpdate(order1);
            }
        }
		orders = order.findOrderClient(clients.get(0).getId());
		requestContent.setAttribute("orders", orders);
		requestContent.setAttribute("sum", "");
		page = ConfigurationManager.getProperty("path.page.admin");
		return page;
	}
}