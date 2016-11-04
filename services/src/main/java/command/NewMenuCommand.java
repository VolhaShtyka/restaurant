package command;

import com.shtyka.dao.daoIlml.MenuDaoImpl;
import com.shtyka.dao.exceptions.DaoException;
import com.shtyka.entity.Menu;
import commandFactory.SessionRequestContent;
import serviceManager.ConfigurationManager;

import java.sql.SQLException;
public class NewMenuCommand implements ActionCommand {	

	public String execute(SessionRequestContent requestContent) throws SQLException, DaoException {
		String page = null;
		Menu menu = new Menu();
		//menu.setMealName(requestContent.getParameter("mealNameNewEN")+"-"+requestContent.getParameter("mealNameNewRU"));
		menu.setMealName(requestContent.getParameter("mealNameNewEN")[0]);
		menu.setPrice(Integer.parseInt(requestContent.getParameter("priceNew")[0]));
		menu.setWeight(Integer.parseInt(requestContent.getParameter("weightNew")[0]));
		page = ConfigurationManager.getProperty("path.page.admin");
		MenuDaoImpl menuNew = new MenuDaoImpl();
		menuNew.saveOrUpdate(menu);
		return page;
	}
}