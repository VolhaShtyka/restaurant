package command;
import serviceManager.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
public class CreateNewMenuCommand implements ActionCommand {

	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException {
        return ConfigurationManager.getProperty("path.page.menu");
	}
}