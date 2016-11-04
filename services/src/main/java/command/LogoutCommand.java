package command;
import commandFactory.SessionRequestContent;
import serviceManager.ConfigurationManager;

import java.util.Locale;
public class LogoutCommand implements ActionCommand{

	public String execute(SessionRequestContent requestContent) {
		String page = ConfigurationManager.getProperty("path.page.index");
		requestContent.invalidate();
		Locale.setDefault(new Locale("ru", "RU"));
		return page;
	}
}