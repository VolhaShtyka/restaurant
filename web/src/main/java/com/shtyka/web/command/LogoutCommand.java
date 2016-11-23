package com.shtyka.web.command;

import com.shtyka.web.commandFactory.SessionRequestContent;
import com.shtyka.web.webManager.ConfigurationManager;

public class LogoutCommand implements ActionCommand{

	public String execute(SessionRequestContent requestContent) {
		requestContent.invalidate();
//		Locale.setDefault(new Locale("ru", "RU"));
		return ConfigurationManager.getProperty("path.page.index");
	}
}