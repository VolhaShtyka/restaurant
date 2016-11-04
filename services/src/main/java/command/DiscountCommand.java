package command;
import commandFactory.SessionRequestContent;
import serviceManager.ConfigurationManager;

import java.sql.SQLException;
public class DiscountCommand implements ActionCommand {	

	public String execute(SessionRequestContent requestContent) throws SQLException {
		String page;
		double sumOrder = 100;
				//Integer.parseInt((String) requestContent.getRequestAttributes("sum"));
		requestContent.setAttribute("sum", sumOrder);
		page = ConfigurationManager.getProperty("path.page.admin");
		return page;
	}
}