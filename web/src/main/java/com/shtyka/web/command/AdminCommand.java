package com.shtyka.web.command;

import com.shtyka.entity.User;
import com.shtyka.services.exceptions.ServiceException;
import com.shtyka.services.serviceImpl.UserServiceImpl;
import com.shtyka.web.commandFactory.SessionRequestContent;
import com.shtyka.web.webManager.ConfigurationManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Controller
public class AdminCommand implements ActionCommand {
    public static int currentPage = 1;
    public static int recordsPerPage = 4;
    public static int numberOfPages = 1;


    @Autowired
    private UserServiceImpl userService;

    public String execute(SessionRequestContent requestContent) {
        final Logger log = Logger.getLogger(AdminCommand.class);

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
            requestContent.setAttribute("nameAdmin", admin);
            requestContent.setAttribute("numberOfPages", numberOfPages);
            requestContent.setAttribute("currentPage", currentPage);
            requestContent.setAttribute("recordsPerPage", recordsPerPage);
            requestContent.setAttribute("users", clients);
            requestContent.setAttribute("sum", sumClient);


        } catch (ServiceException e) {
            log.info("Error Database.");
            return ConfigurationManager.getProperty("path.page.errorDatabase");
//			request.setAttribute("errorDatabase", MessageManager.getProperty("message.errorDatabase"));
        }
        return ConfigurationManager.getProperty("path.page.admin");
    }
}