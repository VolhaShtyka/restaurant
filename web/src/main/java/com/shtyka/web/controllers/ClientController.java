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
import com.shtyka.web.webManager.LanguageBundle;
import com.shtyka.web.webManager.MessageManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

@Controller
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private UserService userService;

    @Autowired
    private MenuService menuService;


    @Autowired
    private OrderService orderService;


    @RequestMapping(value = "/clearbyidorder", method = RequestMethod.GET)
    public String clearByIdOrder(Model model) throws ServiceException {
        try {
            List<Order> orders = orderService.findAll();
            model.addAttribute("orders", orders);
        }catch (ServiceException e){

        }
        return ConfigurationManager.getProperty("path.page.main");
    }


    @RequestMapping(value = "/clear", method = RequestMethod.GET)
    public String clearOrder(Model model) throws ServiceException {
    ResourceBundle property = ResourceBundle.getBundle(Locale.getDefault().getLanguage().toUpperCase(), Locale.getDefault());
    List<Order> orders = orderService.findAll();

        for (Order order1 : orders) {
        if (order1.getStatusOrder().equals(property.getString(StatusMeal.COOKING.name()))) {
            model.addAttribute("errorCookingMessage", MessageManager.getProperty("message.cookingerror"));
        } else {
            orderService.delete(order1.getOrderId());
        }
    }
    orders = orderService.findAll();
        model.addAttribute("orders", orders);
        model.addAttribute("sum", 0);
		return ConfigurationManager.getProperty("path.page.main");


}
    @RequestMapping(value = "/clearsort", method = RequestMethod.GET)
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
        return ConfigurationManager.getProperty("path.page.main");
    }


    @RequestMapping(value = "/client", method = RequestMethod.GET)
    public String getClientPage(ModelMap model,
                                @ModelAttribute("login") String login,
								@ModelAttribute("language") String language) throws ServiceException {
		model = LanguageBundle.addLanguage(language, model);
        int sum = 0;
        int currentPage = 1;
        int recordsPerPage = 4;
        int numberOfPages = menuService.getNumberOfPages(recordsPerPage);
        User user = (User) userService.findByLogin(login);
        List<Menu> menus = menuService.findAll(recordsPerPage, currentPage);
        List <Menu> menusForOrder = menuService.findAll();
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy hh:mm", Locale.getDefault());
        List <Order> orders = orderService.findClientOrder(user.getId());
        for (int i = 0; i < orders.size(); i++) {
            for (int j = 0; j < menusForOrder.size(); j++) {
                if(orders.get(i).getMenuId().equals(menusForOrder.get(j).getMenuId())) {
                    sum += menus.get(i).getPrice();
                }
            }
        }

        model.addAttribute("minPrice", null);
        model.addAttribute("maxPrice", null);
        model.addAttribute("minWeight", null);
        model.addAttribute("maxWeight", null);
        model.addAttribute("numberOfPages", numberOfPages);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("recordsPerPage", recordsPerPage);
        model.addAttribute("user", user.getName());
        model.addAttribute("table", user.getTableNumber());
        model.addAttribute("time", sdf.format(c.getTime()));
        model.addAttribute("menus", menus);
        model.addAttribute("orders", orders);
        model.addAttribute("sum", sum);
        return "resultClient";

    }

    @RequestMapping(value = "/sort", method = RequestMethod.GET)
    public String getClientPageWithSort(Model model, @RequestParam(value = "login") Integer currentPage,
                                @RequestParam(value = "minPrice") Integer minPrice,
                                @RequestParam(value = "maxPrice") Integer maxPrice,
                                @RequestParam(value = "minWeight") Integer minWeight,
                                @RequestParam(value = "maxWeight") Integer maxWeight) throws ServiceException {
        int recordsPerPage;
        recordsPerPage = 4;
        if (currentPage == null) {
            currentPage = 1;
        }
//        } else {
//            currentPage = 1;
//        }
        List<Menu> menus;

//        Integer minPrice = null;
//        Integer  maxPrice= null;
//        Integer minWeight= null;
//        Integer maxWeight= null;
//        if(requestContent.getAttribute("minPrice")!=null){
//            minPrice = Integer.valueOf((Integer) requestContent.getAttribute("minPrice"));
//        }
//        if(requestContent.getAttribute("maxPrice")!=null){
//            maxPrice = Integer.valueOf((Integer)requestContent.getAttribute("maxPrice"));
//        }
//        if(requestContent.getAttribute("minWeight")!=null){
//            minWeight =  Integer.valueOf((Integer)requestContent.getAttribute("minWeight"));
//        }
//        if(requestContent.getAttribute("maxWeight")!=null){
//
//            maxWeight = Integer.valueOf((Integer)requestContent.getAttribute("maxWeight"));
//        }
        int numberOfPages;
        if(minPrice != null || maxPrice != null || minWeight != null || maxWeight != null) {
            menus = menuService.findAll(recordsPerPage, currentPage, minPrice, maxPrice, minWeight, maxWeight);
            numberOfPages = menuService.getNumberPageWithFilter(minPrice, maxPrice, minWeight, maxWeight);
        }else {
            menus = menuService.findAll(recordsPerPage, currentPage);
            numberOfPages = menuService.getNumberOfPages(recordsPerPage);
        }

        model.addAttribute("numberOfPages", numberOfPages);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("recordsPerPage", recordsPerPage);
        model.addAttribute("menus", menus);

        return ConfigurationManager.getProperty("path.page.main");
    }

    @RequestMapping(value = "/cooking", method = RequestMethod.GET)
    public String setCooking(Model model){
        String page;
        ResourceBundle property = ResourceBundle.getBundle(Locale.getDefault().getLanguage().toUpperCase(), Locale.getDefault());
//		OrderDaoImpl order = new OrderDaoImpl();
//		List<Order> orders = order.findAll();
        //just change the order status
//        for (Order order1 : orders) {
//            if (order1.getStatusOrder().equals(property.getString(StatusMeal.NOREADY.name()))) {
//				order1.setStatusOrder(StatusMeal.COOKING.name());
//                order.saveOrUpdate(order1);
//            } else {
//				requestContent.setAttribute("errorCookingMessage", MessageManager.getProperty("message.cookingerror"));
//                page = ConfigurationManager.getProperty("path.page.main");
//            }
//        }
//		orders = order.findAll();
//		requestContent.setAttribute("orders", orders);
        page = ConfigurationManager.getProperty("path.page.main");
        return page;
    }


    @RequestMapping(value = "/countclient", method = RequestMethod.GET)
    public String getClientsOrder(Model model) throws ServiceException {
 //       order.update(StatusMeal.CHEKED.name());
        List<Order> orders = orderService.findAll();
        model.addAttribute("sum", "0");
        return ConfigurationManager.getProperty("path.page.main");
    }

    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public String getOrdersSum(Model model) throws ServiceException {
        ResourceBundle property = ResourceBundle.getBundle(Locale.getDefault().getLanguage().toUpperCase(), Locale.getDefault());
        List <User> clients = userService.findAll();
        List <Order> orders = orderService.findClientOrder(clients.get(0).getId());

        for (Order order1 : orders) {
            if (order1.getStatusOrder().equals(property.getString(StatusMeal.CHEKED.name()))) {
                model.addAttribute("errorCookingCheked", MessageManager.getProperty("message.errorCookingCheked"));
            } else {
                order1.setStatusOrder(StatusMeal.CHEKED.name());
                orderService.saveOrUpdate(order1);
            }
        }
        orders = orderService.findClientOrder(clients.get(0).getId());
        model.addAttribute("orders", orders);
        model.addAttribute("sum", "");
        return ConfigurationManager.getProperty("path.page.admin");
    }

    @RequestMapping(value = "/order", method = RequestMethod.GET)
    public String getOrders(Model model, @RequestParam(value = "menuForOrder") String menuForOrder,
                            @RequestParam(value = "comment") String comments,
                            @ModelAttribute("user") String userName) throws ServiceException {
        String page = null;
        int sum = 0;
        if (menuForOrder == null) {
            model.addAttribute("errorChooseCheked", MessageManager.getProperty("message.chooseerror"));
            page = ConfigurationManager.getProperty("path.page.main");
        } else {
            String[] poiskId = menuForOrder.split(" ");
            Integer menuId = Integer.valueOf(poiskId[0]);
            orderService.createByMenu((Menu) menuService.findEntityById(menuId),
                    (User) userService.findByLogin(userName));
            List<Order> orders = orderService
                    .findClientOrder( ((User) userService.findByLogin(userName)).getId());


            for (int i = 0; i < orders.size(); i++) {
                sum = +userService.countOrder(userService.findByLogin(userName));
            }
            model.addAttribute("sum", sum);
            model.addAttribute("orders", orders);
 //           comments += requestContent.getParameter("comment") + " ";
            page = ConfigurationManager.getProperty("path.page.main");
        }
        return page;
    }

    @RequestMapping(value = "/sorting", method = RequestMethod.GET)
    public String setSortMenu(Model model, @RequestParam(value = "sortPriceOrWeight") int sort,
                            @RequestParam(value = "menus") List<Menu> menus,
                            @ModelAttribute("user") String userName) throws ServiceException {
        menus = menuService.sortingMenu(menus, sort);
        model.addAttribute("menus", menus);
        return ConfigurationManager.getProperty("path.page.main");

    }
}
