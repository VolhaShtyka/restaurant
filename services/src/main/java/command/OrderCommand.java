package command;

import com.shtyka.dao.daoIlml.MenuDaoImpl;
import com.shtyka.dao.daoIlml.OrderDaoImpl;
import com.shtyka.dao.daoIlml.UserDaoImpl;
import com.shtyka.entity.Order;
import resource.ConfigurationManager;
import resource.MessageManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;
public class OrderCommand implements ActionCommand {
	public static String comments = "-";
	/*
	 * team for the formation of the order
	 *  based on the menu and pulling comments 
	 */

	public String execute(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		String page = null;
		int sum = 0;
		HttpSession session = request.getSession(true);
		if (request.getParameter("menuForOrder") == null) {
			request.setAttribute("errorChooseCheked", MessageManager.getProperty("message.chooseerror"));
			page = ConfigurationManager.getProperty("path.page.main");
		} else {
			String menu = request.getParameter("menuForOrder");
			String[] poiskId = menu.split(" ");
			int menuId = Integer.valueOf(poiskId[0]);
			MenuDaoImpl menudao = new MenuDaoImpl();
			OrderDaoImpl orderdao = new OrderDaoImpl();
			UserDaoImpl clientdao = new UserDaoImpl();
			Order orderClient = orderdao.createByMenu(menudao.findEntityById(menuId),
					clientdao.findByLogin((String) session.getAttribute("user")));
			List<Order> orders = orderdao
					.findOrderClient(clientdao.findByLogin((String) session.getAttribute("user")).getId());
			for (int i = 0; i < orders.size(); i++) {
				sum = +clientdao.countOrder(clientdao.findByLogin((String) session.getAttribute("user")));
			}
			if (orderClient.getOrderId() != 0) {
				orders.add(orderClient);
			}
			session.setAttribute("sum", sum);
			session.setAttribute("orders", orders);
			comments += request.getParameter("comment") + " ";
		}
		page = ConfigurationManager.getProperty("path.page.main");
		return page;
	}
}