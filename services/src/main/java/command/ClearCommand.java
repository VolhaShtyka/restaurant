package command;

import com.shtyka.dao.daoIlml.OrderDaoImpl;
import com.shtyka.entity.Order;
import com.shtyka.entity.StatusMeal;
import serviceManager.ConfigurationManager;
import serviceManager.MessageManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
public class ClearCommand implements ActionCommand {

	public String execute(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		String page;
		ResourceBundle property = ResourceBundle.getBundle(Locale.getDefault().getLanguage().toUpperCase(), Locale.getDefault());
		HttpSession session = request.getSession();
		OrderDaoImpl order = new OrderDaoImpl();
		List<Order> orders = order.findAll();
		/*
		 * we can clear all orders,
		 * except those which are now being prepared
		 */
        for (Order order1 : orders) {
            if (order1.getStatusOrder().equals(property.getString(StatusMeal.COOKING.name()))) {
                request.setAttribute("errorCookingMessage", MessageManager.getProperty("message.cookingerror"));
                page = ConfigurationManager.getProperty("path.page.main");
            } else {
                order.delete(order1.getOrderId());
            }
        }
		orders = order.findAll();
		session.setAttribute("orders", orders);
		page = ConfigurationManager.getProperty("path.page.main");
		return page;
	}
}