package command;
import serviceManager.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
public class AllowanceCommand implements ActionCommand {	

	public String execute(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		String page;
		HttpSession session = request.getSession();
		//double sumOrder = Integer.parseInt((String) session.getAttribute("sum"));
		request.setAttribute("sum", 100*105);
		page = ConfigurationManager.getProperty("path.page.admin");
		return page;
	}
}