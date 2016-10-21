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
public class CountCommand implements ActionCommand {	

	public String execute(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		String page = null;
		ResourceBundle property = ResourceBundle.getBundle("properties/" + Locale.getDefault().getLanguage().toUpperCase(), Locale.getDefault());
		HttpSession session = request.getSession(true);
		UserDaoImpl clientdao = new UserDaoImpl();
		List <User> clients = clientdao.findAll();
		OrderDaoImpl order = new OrderDaoImpl();
		List <Order> orders = order.findOrderClient(clients.get(0).getId());
		/*
		 * find the right client and
		 * if the order has not yet paid the
		 * amount you want to deduce pay
		 */
		for (int i = 0; i < orders.size(); i++) {
			if (orders.get(i).getStatusOrder().equals(property.getString(StatusMeal.CHEKED.name()))) {
				request.setAttribute("errorCookingCheked", MessageManager.getProperty("message.errorCookingCheked"));
				page = ConfigurationManager.getProperty("path.page.admin");
			} else {
				order.update(StatusMeal.CHEKED.name());	
			}
		}			
		orders = order.findOrderClient(clients.get(0).getId());	
		session.setAttribute("orders", orders);	
		session.setAttribute("sum", "");
		page = ConfigurationManager.getProperty("path.page.admin");
		return page;
	}
}