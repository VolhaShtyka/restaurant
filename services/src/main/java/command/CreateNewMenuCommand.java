package command;
import commandFactory.SessionRequestContent;
import serviceManager.ConfigurationManager;

import java.sql.SQLException;
public class CreateNewMenuCommand implements ActionCommand {

	public String execute(SessionRequestContent requestContent) throws SQLException {
        return ConfigurationManager.getProperty("path.page.menu");
	}
}