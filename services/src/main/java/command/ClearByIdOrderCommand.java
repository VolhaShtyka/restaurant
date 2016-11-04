package command;
import com.shtyka.dao.daoIlml.OrderDaoImpl;
import com.shtyka.entity.Order;
import serviceManager.ConfigurationManager;
import commandFactory.SessionRequestContent;

import java.sql.SQLException;
import java.util.List;
public class ClearByIdOrderCommand implements ActionCommand {

	public String execute(SessionRequestContent requestContent) throws SQLException {
		String page;
		OrderDaoImpl order = new OrderDaoImpl();
		List<Order> orders = order.findAll();
		requestContent.setAttribute("orders", orders);
		page = ConfigurationManager.getProperty("path.page.main");
		return page;
	}
}