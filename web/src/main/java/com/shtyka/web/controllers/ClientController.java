package com.shtyka.web.controllers;

import com.shtyka.entity.Menu;
import com.shtyka.entity.Order;
import com.shtyka.entity.StatusMeal;
import com.shtyka.entity.User;
import com.shtyka.services.MenuService;
import com.shtyka.services.OrderService;
import com.shtyka.services.UserService;
import com.shtyka.services.exceptions.ServiceException;
import com.shtyka.web.webManager.ConfigurationManager;
import com.shtyka.web.webManager.MessageManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/clients")
@SessionAttributes("userSession")
public class ClientController {

	@Autowired
	private UserService userService;

	@Autowired
	private MenuService menuService;


	@Autowired
	private OrderService orderService;

	@ModelAttribute("userSession")
	public User createUser() {
		return new User();
	}

	@RequestMapping(value = "/clearbyidorder", method = RequestMethod.GET)
	public String clearByIdOrder(Model model) throws ServiceException {
		try {
			List<Order> orders = orderService.findAll();
			model.addAttribute("orders", orders);
		} catch (ServiceException e) {

		}
		return ConfigurationManager.getProperty("path.page.main");
	}


	@RequestMapping(value = "/clear", method = RequestMethod.GET)
	public String clearOrder(Model model) throws ServiceException {
		List<Order> orders = orderService.findAll();

		for (Order order1 : orders) {
			if (order1.getStatusOrder().toLowerCase().equals(StatusMeal.COOKING.name().toLowerCase())) {
				model.addAttribute("errorCookingMessage", MessageManager.getProperty("message.cookingerror"));
			} else {
				orderService.delete(order1.getOrderId());
			}
		}
		orders = orderService.findAll();
		model.addAttribute("orders", orders);
		model.addAttribute("sum", 0);
		return "client/resultClient";


	}

	@RequestMapping(value = "/clearSort", method = RequestMethod.GET)
	public String clearSort(Model model) throws ServiceException {
		int currentPage = 1;
		int recordsPerPage = 4;
		List<Menu> menus = menuService.findAll(recordsPerPage, currentPage);
		int numberOfPages = menuService.getNumberOfPages(recordsPerPage);
		model.addAttribute("numberOfPages", numberOfPages);
		model.addAttribute("menus", menus);
		model.addAttribute("minPrice", null);
		model.addAttribute("maxPrice", null);
		model.addAttribute("minWeight", null);
		model.addAttribute("maxWeight", null);
		return "client/resultClient";
	}


	@RequestMapping(value = "/client", method = RequestMethod.GET)
	public String getClientPage(ModelMap model,
								@ModelAttribute("login") String login, Locale locale,
								HttpSession httpSession,
								@ModelAttribute("userSession") User userSession,
								@RequestParam(value = "currentPage", defaultValue = "1", required = false) int currentPage,
								@RequestParam(value = "recordsPerPage", defaultValue = "4", required = false) int recordsPerPage) throws ServiceException {
		int sum = 0;
		int numberOfPages = menuService.getNumberOfPages(recordsPerPage);
		User user = (User) userService.findByLogin(login);
		if (user == null) {
			user = (User) userService.findByLogin(userSession.getName());
		}
		model.addAttribute(new User());
		List<Menu> menus = menuService.findAll(recordsPerPage, currentPage);
		List<Menu> menusForOrder = menuService.findAll();
		Locale.setDefault(locale);
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy hh:mm", Locale.getDefault());
		List<Order> orders = orderService.findClientOrder(user.getId());
		for (Order order : orders) {
			for (Menu aMenusForOrder : menusForOrder) {
				if (order.getMenuId().equals(aMenusForOrder.getMenuId())) {
					List<Menu> menuList = new ArrayList<>();
					menuList.add(aMenusForOrder);
					order.setMenus(menuList);
					sum += aMenusForOrder.getPrice();
				}
			}
		}
		model.addAttribute("minPrice", null);
		model.addAttribute("maxPrice", null);
		model.addAttribute("minWeight", null);
		model.addAttribute("maxWeight", null);
		httpSession.setAttribute("numberOfPages", numberOfPages);
		httpSession.setAttribute("currentPage", currentPage);
		httpSession.setAttribute("recordsPerPage", recordsPerPage);
		model.addAttribute("user", user.getName());
		model.addAttribute("userSession", user);
		httpSession.setAttribute("table", user.getTableNumber());
		httpSession.setAttribute("time", sdf.format(c.getTime()));
		httpSession.setAttribute("menus", menus);
		httpSession.setAttribute("orders", orders);
		httpSession.setAttribute("sum", sum);
		return "client/resultClient";

	}

	@RequestMapping(value = "/sort", method = RequestMethod.GET)
	public String getClientPageWithSort(Model model, @RequestParam(value = "currentPage", defaultValue = "1", required = false) int currentPage,
										@RequestParam(value = "minPrice", defaultValue = "0", required = false) Integer minPrice,
										@RequestParam(value = "maxPrice", required = false) Integer maxPrice,
										@RequestParam(value = "minWeight", defaultValue = "0", required = false) Integer minWeight,
										@RequestParam(value = "maxWeight", required = false) Integer maxWeight,
										@RequestParam(value = "recordsPerPage", defaultValue = "4", required = false) int recordsPerPage) throws ServiceException {
		List<Menu> menus = new ArrayList<>();
		int numberOfPages;
		if(maxPrice == null){
			maxPrice = 0;
			for (Menu menu:menus){
				if(menu.getPrice()>maxPrice){
					maxPrice = menu.getPrice();
				}
			}
		}
		if(maxWeight == null){
			maxWeight = 0;
			for (Menu menu:menus){
				if(menu.getWeight()>maxWeight){
					maxWeight = menu.getWeight();
				}
			}
		}
		if (minPrice != null || maxPrice != null || minWeight != null || maxWeight != null) {
			menus = menuService.findAll(recordsPerPage, currentPage, minPrice, maxPrice, minWeight, maxWeight);
			numberOfPages = menuService.getNumberPageWithFilter(minPrice, maxPrice, minWeight, maxWeight);
		} else {
			menus = menuService.findAll(recordsPerPage, currentPage);
			numberOfPages = menuService.getNumberOfPages(recordsPerPage);
		}
		model.addAttribute("numberOfPages", numberOfPages);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("recordsPerPage", recordsPerPage);
		model.addAttribute("menus", menus);
		return "client/resultClient";
	}

	@RequestMapping(value = "/cooking", method = RequestMethod.GET)
	public String setCooking(ModelMap model) throws ServiceException {
		String page;
		List<Order> orders = orderService.findAll();
		//just change the order status
		for (Order order1 : orders) {
			if (order1.getStatusOrder().equals(StatusMeal.NOREADY.name())) {
				order1.setStatusOrder(StatusMeal.COOKING.name());
				orderService.saveOrUpdate(order1);
			} else {
				model.addAttribute("errorCookingMessage", MessageManager.getProperty("message.cookingerror"));
				page = "client/resultClient";
			}
		}
		orders = orderService.findAll();
		model.addAttribute("orders", orders);
		page = "client/resultClient";
		return page;
	}


	@RequestMapping(value = "/countClient", method = RequestMethod.GET)
	public String getClientsOrder(Model model) throws ServiceException {
		List<Order> orders = orderService.findAll();
		for (Order order : orders) {
			order.setStatusOrder(StatusMeal.CHEKED.name());
			orderService.saveOrUpdate(order);
		}
		List<Menu> menus = menuService.findAll();
		for (int i = 0; i < orders.size(); i++) {
			for (int j = 0; j < menus.size(); j++) {
				if (orders.get(i).getMenuId().equals(menus.get(j).getMenuId())) {
					List<Menu> menuList = new ArrayList<>();
					menuList.add(menus.get(j));
					orders.get(i).setMenus(menuList);
				}
			}
		}
		model.addAttribute("orders", orders);
		model.addAttribute("sum", "0");
		return "client/resultClient";
	}


	@RequestMapping(value = "/order", method = RequestMethod.GET)
	public String getOrders(ModelMap model, @RequestParam(value = "menuForOrder") String menuForOrder,
							@RequestParam(value = "comment") String comments,
							@ModelAttribute("userSession") User user) throws ServiceException {
		int sum = 0;
		if (menuForOrder == null) {
			model.addAttribute("errorChooseCheked", MessageManager.getProperty("message.chooseerror"));
			// page = ConfigurationManager.getProperty("path.page.main");
		} else {
			String[] findId = menuForOrder.split(" ");
			Integer menuId = Integer.valueOf(findId[0]);
			orderService.createByMenu((Menu) menuService.findEntityById(menuId),
					(User) userService.findByLogin(user.getName()));
			List<Order> orders = orderService
					.findClientOrder(((User) userService.findByLogin(user.getName())).getId());
			for (int i = 0; i < orders.size(); i++) {
				sum = +userService.countOrder(userService.findByLogin(user.getName()));
			}

			List<Menu> menus = menuService.findAll();
			for (int i = 0; i < orders.size(); i++) {
				for (int j = 0; j < menus.size(); j++) {
					if (orders.get(i).getMenuId().equals(menus.get(j).getMenuId())) {
						List<Menu> menuList = new ArrayList<>();
						menuList.add(menus.get(j));
						orders.get(i).setMenus(menuList);
					}
				}
			}

			model.addAttribute("user", user.getName());
			model.addAttribute("sum", sum);
			model.addAttribute("orders", orders);
			//           comments += requestContent.getParameter("comment") + " ";
		}
		return "client/resultClient";
	}

	@RequestMapping(value = "/sorting", method = RequestMethod.GET)
	public String setSortMenu(Model model, @RequestParam(value = "sortPriceOrWeight") int sort,
							  @RequestParam(value = "menus") List<Menu> menus,
							  @ModelAttribute("user") String userName) throws ServiceException {
		menus = menuService.sortingMenu(menus, sort);
		model.addAttribute("menus", menus);
		return "client/resultClient";

	}
}
