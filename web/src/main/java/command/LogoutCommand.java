package command;

import command.commandFactory.SessionRequestContent;
import webManager.ConfigurationManager;
public class LogoutCommand implements ActionCommand{

	public String execute(SessionRequestContent requestContent) {
		requestContent.invalidate();
//		Locale.setDefault(new Locale("ru", "RU"));
		return ConfigurationManager.getProperty("path.page.index");
	}
}