package command;
import resource.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
public class AllowanceCommand implements ActionCommand {	

	public String execute(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		String page = null;
		HttpSession session = request.getSession();
		double sumOrder = Integer.parseInt((String) session.getAttribute("sum"));
		request.setAttribute("sum", sumOrder/100*105);
		page = ConfigurationManager.getProperty("path.page.admin");
		return page;
	}
}