package com.shtyka.web.command;
import com.shtyka.web.commandFactory.SessionRequestContent;
import com.shtyka.web.webManager.ConfigurationManager;
public class DiscountCommand implements ActionCommand {	

	public String execute(SessionRequestContent requestContent) {
		double sumOrder = Integer.parseInt((String) requestContent.getRequestAttributes("sum"));
		requestContent.setAttribute("sum", sumOrder);
		return  ConfigurationManager.getProperty("path.page.admin");
	}
}