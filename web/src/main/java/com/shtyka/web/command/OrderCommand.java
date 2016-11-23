package com.shtyka.web.command;

import com.shtyka.entity.Order;
import com.shtyka.services.exceptions.ServiceException;
import com.shtyka.services.serviceImpl.MenuServiceImpl;
import com.shtyka.services.serviceImpl.OrderServiceImpl;
import com.shtyka.services.serviceImpl.UserServiceImpl;
import com.shtyka.web.commandFactory.SessionRequestContent;
import com.shtyka.web.webManager.ConfigurationManager;
import com.shtyka.web.webManager.MessageManager;

import java.util.List;

public class OrderCommand implements ActionCommand {
    public static String comments = "-";
    private String userName = "user";

	/*
     * team for the formation of the order
	 *  based on the menu and pulling comments 
	 */

    public String execute(SessionRequestContent requestContent) throws ServiceException {
        String page = null;
        int sum = 0;
        if (requestContent.getParameter("menuForOrder") == null) {
            requestContent.setAttribute("errorChooseCheked", MessageManager.getProperty("message.chooseerror"));
            page = ConfigurationManager.getProperty("path.page.main");
        } else {
            String menu = requestContent.getParameter("menuForOrder")[0];
            String[] poiskId = menu.split(" ");
            int menuId = Integer.valueOf(poiskId[0]);
            MenuServiceImpl menudao = MenuServiceImpl.getMenuServiceImpl();
            OrderServiceImpl orderdao = OrderServiceImpl.getOrderServiceImpl();
            UserServiceImpl clientdao = UserServiceImpl.getUserServiceImpl();


            orderdao.createByMenu(menudao.findEntityById(menuId),
                    clientdao.findByLogin((String) requestContent.getAttribute(userName)));


            List<Order> orders = orderdao
                    .findClientOrder(clientdao.findByLogin((String) requestContent.getAttribute(userName)).getId());


            for (int i = 0; i < orders.size(); i++) {
                sum = +clientdao.countOrder(clientdao.findByLogin((String) requestContent.getAttribute(userName)));
            }
            requestContent.setAttribute("sum", sum);
            requestContent.setAttribute("orders", orders);
            comments += requestContent.getParameter("comment") + " ";
            page = ConfigurationManager.getProperty("path.page.main");
        }
        return page;
    }
}