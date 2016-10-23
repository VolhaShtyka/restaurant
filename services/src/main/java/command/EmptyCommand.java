package command;
import serviceManager.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
public class EmptyCommand implements ActionCommand{

	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
        return ConfigurationManager.getProperty("path.page.login");
	}
}