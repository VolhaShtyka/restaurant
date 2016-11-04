package command;

import com.shtyka.dao.daoIlml.UserDaoImpl;
import com.shtyka.dao.exceptions.DaoException;
import com.shtyka.entity.User;
import commandFactory.SessionRequestContent;
import serviceManager.ConfigurationManager;

import java.io.Serializable;

public class DeleteCommand implements ActionCommand {

	public String execute(SessionRequestContent requestContent) throws DaoException {
		String page;
		UserDaoImpl trolollo = new UserDaoImpl ();
		Serializable id = (Serializable) requestContent.getAttribute("userid");
		User administatortDAO= trolollo.get(id);
		trolollo.delete(administatortDAO);
		requestContent.removeAttribute("users");
		requestContent.removeAttribute("orders");
		page = ConfigurationManager.getProperty("path.page.admin");
		return page;
	}
}