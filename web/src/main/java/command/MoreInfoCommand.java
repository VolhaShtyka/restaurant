package command;
import command.commandFactory.SessionRequestContent;
import webManager.ConfigurationManager;
public class MoreInfoCommand implements ActionCommand {

	public String execute(SessionRequestContent requestContent) {
		return ConfigurationManager.getProperty("path.page.more");
	}
}