package command;
import commandFactory.SessionRequestContent;
import serviceManager.ConfigurationManager;

public class EmptyCommand implements ActionCommand {

	public String execute(SessionRequestContent requestContent) {
        return ConfigurationManager.getProperty("path.page.login");
	}
}