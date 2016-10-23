package command;

import com.shtyka.dao.daoIlml.MenuDaoImpl;
import com.shtyka.entity.Menu;
import serviceManager.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
public class NewMenuCommand implements ActionCommand {	

	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException {		
		String page = null;
		Menu menu = new Menu();
		menu.setMealName(request.getParameter("mealNameNewEN")+"-"+request.getParameter("mealNameNewRU"));
		menu.setPrice(Integer.parseInt(request.getParameter("priceNew")));
		menu.setWeight(Integer.parseInt(request.getParameter("weightNew")));
		page = ConfigurationManager.getProperty("path.page.admin");
		MenuDaoImpl menuNew = new MenuDaoImpl();
		menuNew.create(menu);
		return page;
	}
}