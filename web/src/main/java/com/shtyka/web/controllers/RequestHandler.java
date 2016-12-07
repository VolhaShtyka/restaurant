package com.shtyka.web.controllers;

import com.shtyka.dao.exceptions.DaoException;
import com.shtyka.services.exceptions.ServiceException;
import com.shtyka.web.command.ActionCommand;
import com.shtyka.web.commandFactory.ActionFactory;
import com.shtyka.web.commandFactory.SessionRequestContent;
import com.shtyka.web.webManager.ConfigurationManager;
import com.shtyka.web.webManager.LanguageBundle;
import com.shtyka.web.webManager.MessageManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class RequestHandler {
    protected static void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, DaoException, ServiceException {
        request.setCharacterEncoding("UTF-8");
        String page;

        SessionRequestContent requestContent = new SessionRequestContent();
        requestContent.extractValues(request);
        ActionFactory client = new ActionFactory();
        ActionCommand command = client.defineCommand(requestContent);
        page = command.execute(requestContent);
        if (page != null) {
            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(page);
            if (requestContent.getAttribute("language") == null) {
                requestContent.setAttribute("language", "RU");
                LanguageBundle.addLanguage("RU.properties", requestContent);
            }
            dispatcher.forward(request, response);
        } else {
            page = ConfigurationManager.getProperty("path.page.index");
            requestContent.setAttribute("nullPage", MessageManager.getProperty("message.nullpage"));
            response.sendRedirect(request.getContextPath() + page);
        }
    }
}
