package command;
import commandFactory.SessionRequestContent;
import serviceManager.ConfigurationManager;
import serviceManager.LanguageBundle;

import java.sql.SQLException;
import java.util.Locale;
public class LanguageCommand implements ActionCommand {		

	public String execute(SessionRequestContent requestContent) throws SQLException {
		String page;
		Locale.setDefault(new Locale(requestContent.getParameter("language")[0].toLowerCase(), requestContent.getParameter("language")[0].toUpperCase()));
		String lang = requestContent.getParameter("language")[0].concat(".properties");
		LanguageBundle.addLanguage(lang, requestContent);
		requestContent.setAttribute("language", requestContent.getParameter("language"));
		page = ConfigurationManager.getProperty("path.page.login");		
		return page;
	}
}