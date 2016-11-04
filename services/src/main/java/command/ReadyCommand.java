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
public class ReadyCommand implements ActionCommand {

	public String execute(SessionRequestContent requestContent) throws SQLException, DaoException {
		String page = null;
		ResourceBundle property = ResourceBundle
				.getBundle(Locale.getDefault().getLanguage().toUpperCase(), Locale.getDefault());
		UserDaoImpl clientdao = new UserDaoImpl();
		List<User> clients = clientdao.findAll();
		OrderDaoImpl order = new OrderDaoImpl();
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
		orders = order.findOrderClient(clients.get(0).getId());
		requestContent.setAttribute("orders", orders);
		page = ConfigurationManager.getProperty("path.page.admin");
		return page;
	}
}