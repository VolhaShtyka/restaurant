package command;

import com.shtyka.entity.User;
import com.shtyka.services.exceptions.ServiceException;
import com.shtyka.services.serviceImpl.UserServiceImpl;
import command.commandFactory.SessionRequestContent;
import webManager.ConfigurationManager;

import java.io.Serializable;

public class DeleteCommand implements ActionCommand {

	public String execute(SessionRequestContent requestContent) throws ServiceException {
		Serializable id = (Serializable) requestContent.getAttribute("userid");
		User administatortDAO= UserServiceImpl.getUserServiceImpl().get(id);
		UserServiceImpl.getUserServiceImpl().delete(administatortDAO);
		requestContent.removeAttribute("users");
		requestContent.removeAttribute("orders");
		return ConfigurationManager.getProperty("path.page.admin");
	}
}