package com.shtyka.web.controllers;

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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;


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
    public String getAdminPage(Model model) {
        int currentPage = 1;
        int recordsPerPage = 4;
        int numberOfPages = 1;
 //       final Logger log = Logger.getLogger(AdminCommand.class);
        try {
            List<Integer> sumClient = new ArrayList<>();
            List<User> users = userService.findAll();
            List<User> clients = new ArrayList<>(30);
            User admin = new User();
            for (User user : users) {
                if (user.getRoleId() != 1) {
                    clients.add(user);
                }else {
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
            model.addAttribute("sum", sumClient);
        } catch (ServiceException e) {
        //    log.info("Error Database.");
            return ConfigurationManager.getProperty("path.page.errorDatabase");
//			request.setAttribute("errorDatabase", MessageManager.getProperty("message.errorDatabase"));
        }
        return "admin/resultAdmin";
    }


    @RequestMapping(value = "/adminFilter", method = RequestMethod.GET)
    public String getAdminFilterOfClients(Model model, @ModelAttribute("currentPage") int currentPage,
                                          @RequestParam(value = "minPrice") Integer minFilterPrice,
                                          @RequestParam(value = "maxPrice") Integer maxFilterPrice,
                                          @RequestParam(value = "minTableNumber") Integer minFilterTableNumber,
                                          @RequestParam(value = "maxTableNumber") Integer maxFilterTableNumber,
                                          @RequestParam(value = "upPrice") String parameterSort) throws ServiceException {
       int recordsPerPage = 4;
        int numberOfPages;
//        Integer maxFilterPrice = (Integer) model("maxPrice");
//        Integer minFilterPrice = (Integer) requestContent.getAttribute("minPrice");
//        Integer maxFilterTableNumber = (Integer) requestContent.getAttribute("maxTableNumber");
//        Integer minFilterTableNumber= (Integer) requestContent.getAttribute("minTableNumber");
//        String parameterSort = requestContent.getParameter("upPrice")[0];
        if (minFilterPrice == null){
            minFilterPrice = 0;
        }
        if (minFilterTableNumber == null){
            minFilterTableNumber = 0;
        }

        recordsPerPage = 4;
//        if (requestContent.getParameter("currentPage")[0] != null) {
//            currentPage = Integer.valueOf(requestContent.getParameter("currentPage")[0]);
//        } else {
        currentPage = 1;
        //      }
        numberOfPages = userService.getNumberPageWithFilter(minFilterPrice, maxFilterPrice, minFilterTableNumber, maxFilterTableNumber);
        List<User> clients = userService.findAll(recordsPerPage, currentPage, minFilterPrice, maxFilterPrice, minFilterTableNumber, maxFilterTableNumber, parameterSort);
        List<User> users = new ArrayList<>();
        for (User user:clients) {
            if (user.getRoleId() !=1){
                users.add(user);
            }
        }
        model.addAttribute("users", users);
        model.addAttribute("minPrice", minFilterPrice);
        model.addAttribute("maxPrice", maxFilterPrice);
        model.addAttribute("minTableNumber", minFilterTableNumber);
        model.addAttribute("maxTableNumber", maxFilterTableNumber);

        return ConfigurationManager.getProperty("path.page.admin");
    }


    @RequestMapping(value = "/allowance", method = RequestMethod.GET)
    public ModelMap getAllowance(ModelMap model, @RequestParam(value = "sum") double sumOrder) {
		sumOrder = sumOrder/100*105;
        model.addAttribute("sum", sumOrder);
        return model;
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
        int numberOfPages =menuService.getNumberOfPages(recordsPerPage);
        model.addAttribute("numberOfPages", numberOfPages);
        model.addAttribute("users", clients);
        return ConfigurationManager.getProperty("path.page.admin");
    }

    @RequestMapping(value = "/clientDetails", method = RequestMethod.GET)
    public String getDetailsAboutClient(ModelMap model, @RequestParam(value = "user") String userName,
                                        @ModelAttribute("comment") String comment) throws ServiceException {
//        final Logger log = Logger.getLogger(ClientDetailsCommand.class);
        int sum = 0;
        try {
            List<User> users = userService.findAll();
            List<User> clients = new ArrayList<>();
            for (User user:users) {
                if (user.getRoleId() !=1){
                    clients.add(user);
                }
            }
            User user = (User) userService.findByLogin(userName);
            List<Order> orders = orderService.findClientOrder(user.getId());
            for (int i = 0; i < orders.size(); i++) {
                sum += userService.countOrder(user);
            }
            model.addAttribute("users", user);
            model.addAttribute("orders", orders);
            model.addAttribute("sum", sum);
            model.addAttribute("userid", user.getId());
            if (comment != null) {
                model.addAttribute("comment", comment);
            }
        }catch (ServiceException e){
            //log.info("Error Database.");
            return ConfigurationManager.getProperty("path.page.errorDatabase");
//			request.setAttribute("errorDatabase", MessageManager.getProperty("message.errorDatabase"));
        }
        return "admin/resultAdminDetails";
    }


    @RequestMapping(value = "/filterClients", method = RequestMethod.GET)
    public String getClientPageWithFilter(Model model, @RequestParam(value = "minFilterPrice") Integer minFilterPrice,
                                          @RequestParam(value = "maxFilterPrice") Integer maxFilterPrice,
                                          @RequestParam(value = "minTableNumber") Integer maxFilterTableNumber,
                                          @RequestParam(value = "maxTableNumber") Integer minFilterTableNumber) throws ServiceException {
        int currentPage = 1;
        int recordsPerPage = 4;
        int numberOfPages;
//        Integer maxFilterPrice = Integer.valueOf(requestContent.getParameter("maxPrice")[0]);
//        Integer minFilterPrice = Integer.valueOf(requestContent.getParameter("minPrice")[0]);
//        Integer maxFilterTableNumber = Integer.valueOf(requestContent.getParameter("maxTableNumber")[0]);
//        Integer minFilterTableNumber= Integer.valueOf(requestContent.getParameter("minTableNumber")[0]);
//		Integer ASC = (Integer) requestContent.getAttribute("maxPrice");
//      Integer DESC = (Integer) requestContent.getAttribute("maxPrice");
        recordsPerPage = 4;
//        if (requestContent.getParameter("currentPage")[0] != null) {
//            currentPage = Integer.valueOf(requestContent.getParameter("currentPage")[0]);
//        } else {
        currentPage = 1;
        //      }
        numberOfPages = userService.getNumberPageWithFilter(minFilterPrice, maxFilterPrice, minFilterTableNumber, maxFilterTableNumber);
        List<User> clients = userService.findAll(recordsPerPage, currentPage, minFilterPrice, maxFilterPrice, minFilterTableNumber, maxFilterTableNumber, null);
        List<User> users = new ArrayList<>();
        for (User user:clients) {
            if (user.getRoleId() !=1){
                users.add(user);
            }
        }
        model.addAttribute("users", users);
//			requestContent.setAttribute("minPrice", minFilterPrice);
//			requestContent.setAttribute("maxPrice", maxFilterPrice);
//			requestContent.setAttribute("minTableNumber", minFilterTableNumber);
//			requestContent.setAttribute("maxTableNumber", maxFilterTableNumber);

        return "admin/resultAdmin";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String deleteClient(Model model) throws ServiceException {
        Serializable id = (Serializable) model.addAttribute("userid");
        User administatortDAO= (User) userService.get(id);
        userService.delete(administatortDAO.getId());
//        model..removeAttribute("users");
//        requestContent.removeAttribute("orders");
        return "admin/resultAdmin";
    }


    @RequestMapping(value = "/ready", method = RequestMethod.GET)
    public String setReadyOrder(Model model) throws ServiceException {
        String page = null;
        List<User> clients = userService.findAll();
        List<Order> orders = orderService.findAll();
        for (Order order1 : orders) {
            if (order1.getStatusOrder().equals(StatusMeal.CHEKED.name())) {
                model.addAttribute("errorCookingMessage", MessageManager.getProperty("message.cookingerror"));
                page = "admin/resultAdminDetails";
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
    public ModelMap getOrdersSum(ModelMap model) throws ServiceException {
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
        return model;
    }

    @RequestMapping(value = "/newMenu", method = RequestMethod.GET)
    public String createNewMenu() {
        return "admin/createMenu";
    }

    @RequestMapping(value = "/discount", method = RequestMethod.GET)
    public ModelMap getDiscount(ModelMap model, @ModelAttribute("sum")  double sumOrder) {
		sumOrder = sumOrder/100*85;
        model.addAttribute("sum", sumOrder);
        return model;
    }

    @RequestMapping(value = "/moreInfo", method = RequestMethod.GET)
    public String getMoreInfo() {
        return "admin/moreInfo";
    }
}
