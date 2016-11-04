package command;

import com.shtyka.dao.daoIlml.OrderDaoImpl;
import com.shtyka.entity.Order;
import commandFactory.SessionRequestContent;
import serviceManager.ConfigurationManager;

import java.sql.SQLException;
import java.util.List;

public class CountClientCommand implements ActionCommand {

	public String execute(SessionRequestContent requestContent) throws SQLException {
		String page;
		//HttpSession session = request.getSession();
		OrderDaoImpl order = new OrderDaoImpl();
//		order.update(StatusMeal.CHEKED.name());
		List<Order> orders = order.findAll();
		requestContent.setAttribute("sum", "0");
		requestContent.setAttribute("orders", orders);
		page = ConfigurationManager.getProperty("path.page.main");
		return page;
	}
}