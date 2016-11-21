package command;
import command.commandFactory.SessionRequestContent;
import webManager.ConfigurationManager;
public class CreateNewMenuCommand implements ActionCommand {

	public String execute(SessionRequestContent requestContent) {
        return ConfigurationManager.getProperty("path.page.menu");
	}
}