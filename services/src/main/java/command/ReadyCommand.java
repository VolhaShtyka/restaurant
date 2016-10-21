package command;

import com.shtyka.dao.daoIlml.OrderDaoImpl;
import com.shtyka.dao.daoIlml.UserDaoImpl;
import com.shtyka.entity.Order;
import com.shtyka.entity.StatusMeal;
import com.shtyka.entity.User;
import resource.ConfigurationManager;
import resource.MessageManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
public class ReadyCommand implements ActionCommand {

	public String execute(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		String page = null;
		HttpSession session = request.getSession(true);
		ResourceBundle property = ResourceBundle
				.getBundle(Locale.getDefault().getLanguage().toUpperCase(), Locale.getDefault());
		UserDaoImpl clientdao = new UserDaoImpl();
		List<User> clients = clientdao.findAll();
		OrderDaoImpl order = new OrderDaoImpl();
		List<Order> orders = order.findAll();
		for (int i = 0; i < orders.size(); i++) {
			if (orders.get(i).getStatusOrder().equals(property.getString(StatusMeal.CHEKED.name()))) {
				request.setAttribute("errorCookingMessage", MessageManager.getProperty("message.cookingerror"));
				page = ConfigurationManager.getProperty("path.page.main");
			} else {
				order.update(StatusMeal.READY.name());
			}
		}
		orders = order.findOrderClient(clients.get(0).getId());
		session.setAttribute("orders", orders);
		page = ConfigurationManager.getProperty("path.page.admin");
		return page;
	}
}