package com.shtyka.web.command;
import com.shtyka.web.commandFactory.SessionRequestContent;
import com.shtyka.web.webManager.ConfigurationManager;
import com.shtyka.web.webManager.LanguageBundle;

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