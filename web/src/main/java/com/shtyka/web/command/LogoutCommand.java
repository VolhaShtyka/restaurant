package com.shtyka.web.command;

import com.shtyka.web.commandFactory.SessionRequestContent;
import com.shtyka.web.webManager.ConfigurationManager;
import com.shtyka.web.webManager.LanguageBundle;

import java.util.Locale;

public class LogoutCommand implements ActionCommand{

	public String execute(SessionRequestContent requestContent) {
        requestContent.setSessionAttributes(null);
        requestContent.setAttribute("language", "RU");
        Locale.setDefault(new Locale ("ru", "RU"));
        LanguageBundle.addLanguage("RU.properties", requestContent);
//        requestContent.invalidate();
//        requestContent.insertRequestAttributes("language", "RU");
//        LanguageBundle.addLanguage("RU.properties", requestContent);

 //       requestContent.insertRequestAttributes("language", "RU");
//		Locale.setDefault(new Locale("ru", "RU"));
		return ConfigurationManager.getProperty("path.page.index");
	}
}