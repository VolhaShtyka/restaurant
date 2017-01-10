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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/admins")
public class AdminController {

	@Autowired
	private UserService userService;

	@Autowired
	private MenuService menuService;

	@Autowired
	private OrderService orderService;

	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String getAdminPage(Model model, HttpSession session) {
		int currentPage = 1;
		int recordsPerPage = 4;
		int numberOfPages = 1;
		try {
			List<Integer> sumClient = new ArrayList<>();
			List<User> users = userService.findAll();
			List<User> clients = new ArrayList<>();
			User admin = new User();
			for (User user : users) {
				if (user.getRoleId() != 1) {
					clients.add(user);
				} else {
					admin = user;
				}
			}
			for (User user : clients) {
				int sum = userService.countOrder(user);
				for (int i = 0; i <= user.getId(); i++) {
					sumClient.add(null);
				}
				sumClient.set(user.getId(), sum);
			}
			model.addAttribute("nameAdmin", admin);
			model.addAttribute("numberOfPages", numberOfPages);
			model.addAttribute("currentPage", currentPage);
			model.addAttribute("recordsPerPage", recordsPerPage);
			model.addAttribute("users", clients);
			session.setAttribute("sum", sumClient);
		} catch (ServiceException e) {
			return ConfigurationManager.getProperty("path.page.errorDatabase");
		}
		return "admins/resultAdmin";
	}


	@RequestMapping(value = "/adminFilter", method = RequestMethod.GET)
	public String getAdminFilterOfClients(Model model, HttpSession session,
										  @RequestParam(value = "currentPage", defaultValue = "1", required = false) int currentPage,
										  @RequestParam(value = "recordsPerPage", defaultValue = "4", required = false) int recordsPerPage,
										  @RequestParam(value = "upOrDown") String parameterSort) throws ServiceException {
		Integer minFilterPrice;
		Integer maxFilterPrice;
		Integer minFilterTableNumber;
		Integer maxFilterTableNumber;

		if (session.getAttribute("minPrice") == null){
			minFilterPrice = 0;
		}else{
			minFilterPrice = (Integer) session.getAttribute("minPrice");
		}

		if (session.getAttribute("maxPrice") == null) {
			maxFilterPrice = 1000;
		}else{
			maxFilterPrice =  (Integer)session.getAttribute("maxPrice");
		}

		if (session.getAttribute("minTableNumber") == null){
			minFilterTableNumber = 0;
		}else{
			minFilterTableNumber =  (Integer)session.getAttribute("minTableNumber");
		}


		if (session.getAttribute("maxTableNumber") == null) {
			maxFilterTableNumber = 1000; //magic number
		}else{
			maxFilterTableNumber =  (Integer)session.getAttribute("maxTableNumber");
		}
		List<User> clients = userService.findAll(recordsPerPage, currentPage, minFilterPrice, maxFilterPrice, minFilterTableNumber, maxFilterTableNumber, parameterSort);
		List<User> users = new ArrayList<>();
		for (User user : clients) {
			if (user.getRoleId() != 1) {
				users.add(user);
			}
		}
		model.addAttribute("users", users);
		session.setAttribute("minPrice", minFilterPrice);
		session.setAttribute("maxPrice", maxFilterPrice);
		session.setAttribute("minTableNumber", minFilterTableNumber);
		session.setAttribute("maxTableNumber", maxFilterTableNumber);
		return "admins/resultAdmin";
	}


	@RequestMapping(value = "/allowance", method = RequestMethod.GET)
	public String getAllowance(ModelMap model,HttpSession session) {
		int sum = (Integer)session.getAttribute("sum");
		double sumOrder = sum / 100 * 105;
		model.addAttribute("sum", sumOrder);
		return "resultAdminDetails";
	}


	@RequestMapping(value = "/clearSortAdmin", method = RequestMethod.GET)
	public String clearSort(Model model) throws ServiceException {
		int recordsPerPage = 1;
		List<User> users = userService.findAll();
		List<User> clients = new ArrayList<>(30);
		for (User user : users) {
			if (user.getRoleId() != 1) {
				clients.add(user);
			}
		}
		int numberOfPages = menuService.getNumberOfPages(recordsPerPage);
		model.addAttribute("numberOfPages", numberOfPages);
		model.addAttribute("users", clients);
		return "admins/resultAdmin";
	}

	@RequestMapping(value = "/clientDetails", method = RequestMethod.GET)
	public String getDetailsAboutClient(ModelMap model, @RequestParam(value = "user") String userName,
										@ModelAttribute("comment") String comment,
										HttpSession session) throws ServiceException {
		int sum = 0;
		try {
			User user = (User) userService.findByLogin(userName);
			List<Order> orders = orderService.findClientOrder(user.getId());
			for (int i = 0; i < orders.size(); i++) {
				sum += userService.countOrder(user);
			}
			session.setAttribute("user", user.getName());
			model.addAttribute("orders", orders);
			session.setAttribute("sum", sum);
			model.addAttribute("userid", user.getId());
			if (comment != null) {
				model.addAttribute("comment", comment);
			}
		} catch (ServiceException e) {
			model.addAttribute("errorCookingMessage", MessageManager.getProperty("message.cookingerror"));
			return "resultAdminDetails";
		}
		return "resultAdminDetails";
	}


	@RequestMapping(value = "/filterClients", method = RequestMethod.GET)
	public String getClientPageWithFilter(ModelMap model, @RequestParam(value = "minPrice", defaultValue = "0", required = false) Integer minFilterPrice,
										  @RequestParam(value = "maxPrice", defaultValue = "1000", required = false) Integer maxFilterPrice,
										  @RequestParam(value = "minTableNumber", defaultValue = "0", required = false) Integer minFilterTableNumber,
										  @RequestParam(value = "maxTableNumber", defaultValue = "1000", required = false) Integer maxFilterTableNumber,
										  @RequestParam(value = "currentPage", defaultValue = "1", required = false) int currentPage,
										  @RequestParam(value = "recordsPerPage", defaultValue = "4", required = false) int recordsPerPage,
										  HttpSession session) throws ServiceException {
		int numberOfPages = userService.getNumberPageWithFilter(minFilterPrice, maxFilterPrice, minFilterTableNumber, maxFilterTableNumber);
		List<User> clients = userService.findAll(recordsPerPage, currentPage, minFilterPrice, maxFilterPrice, minFilterTableNumber, maxFilterTableNumber, "");
		List<User> users = new ArrayList<>();
		for (User user : clients) {
			if (user.getRoleId() != 1) {
				users.add(user);
			}
		}
		model.addAttribute("users", users);
		session.setAttribute("minPrice", minFilterPrice);
		session.setAttribute("maxPrice", maxFilterPrice);
		session.setAttribute("minTableNumber", minFilterTableNumber);
		session.setAttribute("maxTableNumber", maxFilterTableNumber);
		return "admins/resultAdmin";
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String deleteClient(HttpSession session, @ModelAttribute("user") User user) throws ServiceException {
		userService.delete(user.getId());
		session.removeAttribute("user");
		return "admins/resultAdmin";
	}


	@RequestMapping(value = "/ready", method = RequestMethod.GET)
	public String setReadyOrder(Model model) throws ServiceException {
		String page = null;
		List<User> clients = userService.findAll();
		List<Order> orders = orderService.findAll();
		for (Order order1 : orders) {
			if (order1.getStatusOrder().equals(StatusMeal.CHEKED.name())) {
				model.addAttribute("errorCookingMessage", MessageManager.getProperty("message.cookingerror"));
				page = "resultAdminDetails";
			} else {
				order1.setStatusOrder(StatusMeal.READY.name());
				orderService.saveOrUpdate(order1);
			}
		}
		orders = orderService.findClientOrder(clients.get(0).getId());
		model.addAttribute("orders", orders);
		return page;
	}

	@RequestMapping(value = "/count", method = RequestMethod.GET)
	public String getOrdersSum(ModelMap model) throws ServiceException {
		List<User> clients = userService.findAll();
		List<Order> orders = orderService.findClientOrder(clients.get(0).getId());

		for (Order order1 : orders) {
			if (order1.getStatusOrder().equals(StatusMeal.CHEKED.name())) {
				model.addAttribute("errorCookingCheked", MessageManager.getProperty("message.errorCookingCheked"));
			} else {
				order1.setStatusOrder(StatusMeal.CHEKED.name());
				orderService.saveOrUpdate(order1);
			}
		}
		orders = orderService.findClientOrder(clients.get(0).getId());
		model.addAttribute("orders", orders);
		return "admins/resultAdmin";
	}

	@RequestMapping(value = "/newMenu", method = RequestMethod.GET)
	public String createNewMenu( @RequestParam(value = "mealNameNewRU", required = false) String mealNameNewRU,
								 @RequestParam(value = "mealNameNewEN", required = false) String mealNameNewEN,
								 @RequestParam(value = "priceNew", required = false) Integer priceNew,
								 @RequestParam(value = "weightNew", required = false) Integer weightNew) throws ServiceException {
		if(mealNameNewEN != null && mealNameNewRU != null && priceNew != null && weightNew != null){
			Menu menu = new Menu();
			menu.setNameen(mealNameNewEN);
			menu.setMealName(mealNameNewRU);
			menu.setWeight(weightNew);
			menu.setPrice(priceNew);
			menuService.saveOrUpdate(menu);
		}

		return "createMenu";
	}

	@RequestMapping(value = "/discount", method = RequestMethod.GET)
	public String getDiscount(ModelMap model, HttpSession session) {
		int sum = (Integer) session.getAttribute("sum");
		double sumOrder = sum / 100 * 85;
		model.addAttribute("sum", sumOrder);
		return "resultAdminDetails";
	}

	@RequestMapping(value = "/moreInfo", method = RequestMethod.GET)
	public String getMoreInfo() {
		return "moreInfo";
	}
}
