package controllers;

import com.shtyka.dao.exceptions.DaoException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

//@WebServlet("/ServletRestoraut")
public class ServletRestoraut extends HttpServlet {
    private static final long serialVersionUID = 1L;


    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            RequestHandler.processRequest(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            RequestHandler.processRequest(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }
//	private void processRequest(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException, SQLException {
//		request.setCharacterEncoding("UTF-8");
//		String page;
//		//define came from jsp command and call processing method
//		ActionFactory client = new ActionFactory();
//		ActionCommand command = client.defineCommand(request);
//		page = command.execute(request, response);
//		if (page != null) {
//			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
//			if (request.getSession().getAttribute("language") == null) {
//				request.getSession().setAttribute("language", "RU");
//				LanguageBundle.addLanguage("RU.properties", request);
//			}
//			dispatcher.forward(request, response);
//		} else {
//			page = ConfigurationManager.getProperty("path.page.index");
//			request.getSession().setAttribute("nullPage", MessageManager.getProperty("message.nullpage"));
//			response.sendRedirect(request.getContextPath() + page);
//		}
//	}
}