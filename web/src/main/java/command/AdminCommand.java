package command;

import com.shtyka.entity.Order;
import com.shtyka.entity.User;
import com.shtyka.services.exceptions.ServiceException;
import com.shtyka.services.serviceImpl.OrderServiceImpl;
import com.shtyka.services.serviceImpl.UserServiceImpl;
import command.commandFactory.SessionRequestContent;
import org.apache.log4j.Logger;
import webManager.ConfigurationManager;

import java.util.List;


public class AdminCommand implements ActionCommand {

	public String execute(SessionRequestContent requestContent) {
		final Logger log = Logger.getLogger(AdminCommand.class);
		int sum = 0;
		try {
			List<User> clients = UserServiceImpl.getUserServiceImpl().findAll();
			User user = UserServiceImpl.getUserServiceImpl().findByLogin(requestContent.getParameter("login")[0]);
			List<Order> orders = OrderServiceImpl.getOrderServiceImpl().findOrderClient(user.getId());
			for (int i = 0; i < orders.size(); i++) {
				sum = + UserServiceImpl.getUserServiceImpl().countOrder(user);
			}
			requestContent.setAttribute("users", clients);
			requestContent.setAttribute("orders", orders);
			requestContent.setAttribute("sum", sum);
			requestContent.setAttribute("userid", user.getId());
			if (OrderCommand.comments != null) {
				requestContent.setAttribute("comment", OrderCommand.comments);
			}
		}catch (ServiceException e){
			log.info("Error Database.");
			return ConfigurationManager.getProperty("path.page.errorDatabase");
//			request.setAttribute("errorDatabase", MessageManager.getProperty("message.errorDatabase"));
		}
		return ConfigurationManager.getProperty("path.page.admin");
	}
}