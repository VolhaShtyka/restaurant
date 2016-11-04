package command;

import com.shtyka.dao.daoIlml.MenuDaoImpl;
import com.shtyka.dao.daoIlml.OrderDaoImpl;
import com.shtyka.dao.daoIlml.UserDaoImpl;
import com.shtyka.entity.Order;
import commandFactory.SessionRequestContent;
import serviceManager.ConfigurationManager;
import serviceManager.MessageManager;

import java.sql.SQLException;
import java.util.List;

public class OrderCommand implements ActionCommand {
	public static String comments = "-";
	/*
	 * team for the formation of the order
	 *  based on the menu and pulling comments 
	 */

	public String execute(SessionRequestContent requestContent) throws SQLException {
		String page = null;
		int sum = 0;
		//HttpSession session = request.getSession(true);
		if (requestContent.getParameter("menuForOrder") == null) {
			requestContent.setAttribute("errorChooseCheked", MessageManager.getProperty("message.chooseerror"));
			page = ConfigurationManager.getProperty("path.page.main");
		} else {
			String menu = requestContent.getParameter("menuForOrder")[0];
			String[] poiskId = menu.split(" ");
			int menuId = Integer.valueOf(poiskId[0]);
			MenuDaoImpl menudao = new MenuDaoImpl();
			OrderDaoImpl orderdao = new OrderDaoImpl();
			UserDaoImpl clientdao = new UserDaoImpl();
			Order orderClient = orderdao.createByMenu(menudao.findEntityById(menuId),
					clientdao.findByLogin((String) requestContent.getAttribute("user")));
			List<Order> orders = orderdao
					.findOrderClient(clientdao.findByLogin((String) requestContent.getAttribute("user")).getId());
			for (int i = 0; i < orders.size(); i++) {
				sum = +clientdao.countOrder(clientdao.findByLogin((String) requestContent.getAttribute("user")));
			}
			if (orderClient.getOrderId() != 0) {
				orders.add(orderClient);
			}
			requestContent.setAttribute("sum", sum);
			requestContent.setAttribute("orders", orders);
			comments += requestContent.getParameter("comment") + " ";
		}
		page = ConfigurationManager.getProperty("path.page.main");
		return page;
	}
}