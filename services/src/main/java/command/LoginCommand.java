package command;
import commandFactory.SessionRequestContent;
import serviceManager.ConfigurationManager;
import serviceManager.MessageManager;

import java.sql.SQLException;

public class LoginCommand implements ActionCommand {
	private static final String PARAM_NAME_LOGIN = "login";
	private static final String PARAM_NAME_PASSWORD = "password";

	public String execute(SessionRequestContent requestContent) throws SQLException {
		String amdinProfile = "administrator";
		String userProfile = "user";
		String page;
		String login = requestContent.getParameter(PARAM_NAME_LOGIN)[0];
		String password = requestContent.getParameter(PARAM_NAME_PASSWORD)[0];
		if (LoginLogic.checkLoginAdmin(login, password).equals(amdinProfile)) {
			AdminCommand admin = new AdminCommand();
			admin.execute(requestContent);
			page = ConfigurationManager.getProperty("path.page.admin");
		} else if (LoginLogic.checkLoginAdmin(login, password).equals(userProfile)) {
			ClientCommand client = new ClientCommand();
			client.execute(requestContent);
			page = ConfigurationManager.getProperty("path.page.main");
		} else {
			requestContent.setAttribute("errorLoginPasswordMessage", MessageManager.getProperty("message.loginerror"));
			page = ConfigurationManager.getProperty("path.page.login");
		}
		return page;
	}
}