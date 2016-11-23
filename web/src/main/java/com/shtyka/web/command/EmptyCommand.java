package com.shtyka.web.command;
import com.shtyka.web.commandFactory.SessionRequestContent;
import com.shtyka.web.webManager.ConfigurationManager;

public class EmptyCommand implements ActionCommand {

	public String execute(SessionRequestContent requestContent) {
        return ConfigurationManager.getProperty("path.page.login");
	}
}