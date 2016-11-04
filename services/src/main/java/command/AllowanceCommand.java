package command;
import serviceManager.ConfigurationManager;
import commandFactory.SessionRequestContent;

import java.sql.SQLException;
public class AllowanceCommand implements ActionCommand {	

	public String execute(SessionRequestContent requestContent) throws SQLException {
		String page;
		//double sumOrder = Integer.parseInt((String) session.getAttribute("sum"));
		requestContent.setAttribute("sum", 100*105);
		page = ConfigurationManager.getProperty("path.page.admin");
		return page;
	}
}