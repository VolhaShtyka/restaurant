package command;
import command.commandFactory.SessionRequestContent;
import webManager.ConfigurationManager;
import webManager.LanguageBundle;

import java.util.Locale;
public class LanguageCommand implements ActionCommand {		

	public String execute(SessionRequestContent requestContent) {
		Locale.setDefault(new Locale(requestContent.getParameter("language")[0].toLowerCase(), requestContent.getParameter("language")[0].toUpperCase()));
		String lang = requestContent.getParameter("language")[0].concat(".properties");
		LanguageBundle.addLanguage(lang, requestContent);
		requestContent.setAttribute("language", requestContent.getParameter("language"));
		return ConfigurationManager.getProperty("path.page.login");
	}
}