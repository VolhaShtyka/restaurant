package command;
import command.commandFactory.SessionRequestContent;
import webManager.ConfigurationManager;
public class AllowanceCommand implements ActionCommand {	

	public String execute(SessionRequestContent requestContent) {
		double sumOrder = Integer.parseInt((String) requestContent.getAttribute("sum"));
		requestContent.setAttribute("sum", sumOrder);
		return ConfigurationManager.getProperty("path.page.admin");
	}
}