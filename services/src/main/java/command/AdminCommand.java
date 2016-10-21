package command;

import com.shtyka.dao.daoIlml.OrderDaoImpl;
import com.shtyka.dao.daoIlml.UserDaoImpl;
import com.shtyka.entity.Order;
import com.shtyka.entity.User;
import resource.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

public class AdminCommand implements ActionCommand {

	public String execute(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		int sum = 0;
		HttpSession session = request.getSession(true);
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
		session.setAttribute("users", clients);
		session.setAttribute("orders", orders);
		session.setAttribute("sum", sum);		
		session.setAttribute("userid", clients.get(clients.size()-1).getId());
		if(OrderCommand.comments != null){
		session.setAttribute("comment", OrderCommand.comments);	
		}
		return ConfigurationManager.getProperty("path.page.admin");
	}
}