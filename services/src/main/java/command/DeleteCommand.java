package command;
import com.shtyka.dao.daoIlml.UserDaoImpl;
import serviceManager.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
public class DeleteCommand implements ActionCommand {	

	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		String page;
		HttpSession session = request.getSession(true);		
		UserDaoImpl administatortDAO= new UserDaoImpl();
		administatortDAO.delete(Integer.parseInt((String) session.getAttribute("userid")));
		session.removeAttribute("users");
		session.removeAttribute("orders");
		page = ConfigurationManager.getProperty("path.page.admin");
		return page;
	}
}