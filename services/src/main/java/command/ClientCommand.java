package command;

import com.shtyka.dao.daoIlml.MenuDaoImpl;
import com.shtyka.dao.daoIlml.OrderDaoImpl;
import com.shtyka.dao.daoIlml.UserDaoImpl;
import com.shtyka.entity.User;
import com.shtyka.entity.Order;
import com.shtyka.entity.Menu;

import resource.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
public class ClientCommand implements ActionCommand {

	public String execute(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		int sum = 0;		
		HttpSession session = request.getSession(true);
		UserDaoImpl clientdao = new UserDaoImpl();
		User clientOne = clientdao.findByLogin(request.getParameter("login"));
		MenuDaoImpl menudao = new MenuDaoImpl();
		List<Menu> menus = menudao.findAll();			
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy hh:mm", Locale.getDefault());
		OrderDaoImpl order = new OrderDaoImpl();
		List<Order> orders = order.findOrderClient(clientOne.getId());
		for (int i = 0; i < orders.size(); i++) {
			sum =+ clientdao.countOrder(clientOne);
		}		
		session.setAttribute("user", clientOne.getName());
		session.setAttribute("table", clientOne.getTableNumber());
		session.setAttribute("time", sdf.format(c.getTime()));			
		session.setAttribute("menus", menus);
		session.setAttribute("orders", orders);
		session.setAttribute("sum", sum);
		return ConfigurationManager.getProperty("path.page.main");
	}
}