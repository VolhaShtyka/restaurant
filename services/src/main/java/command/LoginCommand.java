package command;
import serviceManager.ConfigurationManager;
import serviceManager.MessageManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
public class LoginCommand implements ActionCommand {
	private static final String PARAM_NAME_LOGIN = "login";
	private static final String PARAM_NAME_PASSWORD = "password";

	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException {
		String amdinProfile = "administrator";
		String userProfile = "user";
		String page;
		String login = request.getParameter(PARAM_NAME_LOGIN);
		String password = request.getParameter(PARAM_NAME_PASSWORD);
		if (LoginLogic.checkLoginAdmin(login, password).equals(amdinProfile)) {
			AdminCommand admin = new AdminCommand();
			admin.execute(request, response);			
			page = ConfigurationManager.getProperty("path.page.admin");
		} else if (LoginLogic.checkLoginAdmin(login, password).equals(userProfile)) {
			ClientCommand client = new ClientCommand();
			client.execute(request, response);	
			page = ConfigurationManager.getProperty("path.page.main");
		} else {
			request.setAttribute("errorLoginPasswordMessage", MessageManager.getProperty("message.loginerror"));
			page = ConfigurationManager.getProperty("path.page.login");
		}
		return page;
	}
}