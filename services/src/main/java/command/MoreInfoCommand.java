package command;
import commandFactory.SessionRequestContent;
import serviceManager.ConfigurationManager;

import java.sql.SQLException;
public class MoreInfoCommand implements ActionCommand {

	public String execute(SessionRequestContent requestContent) throws SQLException {
		return ConfigurationManager.getProperty("path.page.more");
	}
}