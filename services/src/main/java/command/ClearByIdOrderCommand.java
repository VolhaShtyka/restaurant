package command;
import com.shtyka.dao.daoIlml.OrderDaoImpl;
import com.shtyka.entity.Order;
import serviceManager.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;
public class ClearByIdOrderCommand implements ActionCommand {

	public String execute(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		String page;
		HttpSession session = request.getSession();
		OrderDaoImpl order = new OrderDaoImpl();
		List<Order> orders = order.findAll();
		session.setAttribute("orders", orders);
		page = ConfigurationManager.getProperty("path.page.main");
		return page;
	}
}