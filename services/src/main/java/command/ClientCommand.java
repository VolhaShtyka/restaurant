package command;

import com.shtyka.dao.daoIlml.MenuDaoImpl;
import com.shtyka.dao.daoIlml.OrderDaoImpl;
import com.shtyka.dao.daoIlml.UserDaoImpl;
import com.shtyka.entity.Menu;
import com.shtyka.entity.Order;
import com.shtyka.entity.User;
import commandFactory.SessionRequestContent;
import serviceManager.ConfigurationManager;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ClientCommand implements ActionCommand {

	public String execute(SessionRequestContent requestContent) throws SQLException {
		int sum = 0;		
		//HttpSession session = request.getSession(true);
		UserDaoImpl clientdao = new UserDaoImpl();
		User clientOne = clientdao.findByLogin(requestContent.getParameter("login")[0]);
		MenuDaoImpl menudao = new MenuDaoImpl();
		List<Menu> menus = menudao.findAll();			
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy hh:mm", Locale.getDefault());
		OrderDaoImpl order = new OrderDaoImpl();
		List<Order> orders = order.findOrderClient(clientOne.getId());
		for (int i = 0; i < orders.size(); i++) {
			sum =+ clientdao.countOrder(clientOne);
		}
		requestContent.setAttribute("user", clientOne.getName());
		requestContent.setAttribute("table", clientOne.getTableNumber());
		requestContent.setAttribute("time", sdf.format(c.getTime()));
		requestContent.setAttribute("menus", menus);
		requestContent.setAttribute("orders", orders);
		requestContent.setAttribute("sum", sum);
		return ConfigurationManager.getProperty("path.page.main");
	}
}