package com.shtyka.web.command;

import com.shtyka.dao.daoImpl.MenuDaoImpl;
import com.shtyka.dao.exceptions.DaoException;
import com.shtyka.entity.Menu;
import com.shtyka.web.commandFactory.SessionRequestContent;
import com.shtyka.web.webManager.ConfigurationManager;
public class NewMenuCommand implements ActionCommand {	

	public String execute(SessionRequestContent requestContent) throws DaoException {
		Menu menu = new Menu();
		//menu.setMealName(requestContent.getParameter("mealNameNewEN")+"-"+requestContent.getParameter("mealNameNewRU"));
		menu.setMealName(requestContent.getParameter("mealNameNewEN")[0]);
		menu.setPrice(Integer.parseInt(requestContent.getParameter("priceNew")[0]));
		menu.setWeight(Integer.parseInt(requestContent.getParameter("weightNew")[0]));
		MenuDaoImpl menuNew = new MenuDaoImpl();
		menuNew.saveOrUpdate(menu);
		return ConfigurationManager.getProperty("path.page.admin");
	}
}