package com.shtyka.web.command;

import com.shtyka.dao.exceptions.DaoException;
import com.shtyka.entity.Menu;
import com.shtyka.entity.Order;
import com.shtyka.entity.User;
import com.shtyka.services.exceptions.ServiceException;
import com.shtyka.services.serviceImpl.MenuServiceImpl;
import com.shtyka.services.serviceImpl.OrderServiceImpl;
import com.shtyka.services.serviceImpl.UserServiceImpl;
import com.shtyka.web.commandFactory.SessionRequestContent;
import com.shtyka.web.webManager.ConfigurationManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ClientCommand implements ActionCommand {
	public static int sum = 0;
	public static int currentPage = 1;
	public static int recordsPerPage = 2;

	public String execute(SessionRequestContent requestContent) throws DaoException, ServiceException {

		int numberOfPages = MenuServiceImpl.getMenuServiceImpl().getNumberOfPages(recordsPerPage);
		User user = UserServiceImpl.getUserServiceImpl().findByLogin(requestContent.getParameter("login")[0]);
		MenuServiceImpl menuService = new MenuServiceImpl();
		List<Menu> menus = menuService.findAll(recordsPerPage, currentPage);
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy hh:mm", Locale.getDefault());

		List <Order> orders = OrderServiceImpl.getOrderServiceImpl().findClientOrder(user.getId());
		for (int i = 0; i < orders.size(); i++) {
			sum = orders.get(i).getTotalOrder();
		}
		requestContent.setAttribute("numberOfPages", numberOfPages);
		requestContent.setAttribute("currentPage", currentPage);
		requestContent.setAttribute("recordsPerPage", recordsPerPage);
		requestContent.setAttribute("user", user.getName());
		requestContent.setAttribute("table", user.getTableNumber());
		requestContent.setAttribute("time", sdf.format(c.getTime()));
		requestContent.setAttribute("menus", menus);
		requestContent.setAttribute("orders", orders);
		requestContent.setAttribute("sum", sum);
		return ConfigurationManager.getProperty("path.page.main");
	}
}