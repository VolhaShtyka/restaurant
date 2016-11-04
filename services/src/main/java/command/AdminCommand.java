package command;

import com.shtyka.dao.daoIlml.OrderDaoImpl;
import com.shtyka.dao.daoIlml.UserDaoImpl;
import com.shtyka.entity.Order;
import com.shtyka.entity.User;
import commandFactory.SessionRequestContent;
import serviceManager.ConfigurationManager;

import java.sql.SQLException;
import java.util.List;

public class AdminCommand implements ActionCommand {

	public String execute(SessionRequestContent requestContent) throws SQLException {
		int sum = 0;
		UserDaoImpl clientdao = new UserDaoImpl();
		/*
		 * create customer, orders and find it
		 * all in assigning attributes
		 * Since the client is a code shortened 
		 * by 2 loop lines , and taken one client
		 */
		List<User> clients = clientdao.findAll();
		OrderDaoImpl order = new OrderDaoImpl();
		List<Order> orders = order.findOrderClient(clients.get(0).getId());
		for (int i = 0; i < orders.size(); i++) {
			sum = +clientdao.countOrder(clients.get(0));
		}
		requestContent.setAttribute("users", clients);
		requestContent.setAttribute("orders", orders);
		requestContent.setAttribute("sum", sum);
		requestContent.setAttribute("userid", clients.get(clients.size()-1).getId());
		if(OrderCommand.comments != null){
			requestContent.setAttribute("comment", OrderCommand.comments);
		}
		return ConfigurationManager.getProperty("path.page.admin");
	}
}