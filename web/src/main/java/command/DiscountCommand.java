package command;
import command.commandFactory.SessionRequestContent;
import webManager.ConfigurationManager;
public class DiscountCommand implements ActionCommand {	

	public String execute(SessionRequestContent requestContent) {
		double sumOrder = Integer.parseInt((String) requestContent.getRequestAttributes("sum"));
		requestContent.setAttribute("sum", sumOrder);
		return  ConfigurationManager.getProperty("path.page.admin");
	}
}