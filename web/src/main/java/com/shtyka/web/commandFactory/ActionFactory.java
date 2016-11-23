package com.shtyka.web.commandFactory;

import com.shtyka.web.command.EmptyCommand;
import com.shtyka.web.command.ActionCommand;
import com.shtyka.web.commandEnum.CommandEnum;
import com.shtyka.web.webManager.MessageManager;

public class ActionFactory {
	public ActionCommand defineCommand(SessionRequestContent requestContent) {
		ActionCommand current = new EmptyCommand();
		String action;
		try {
			action = requestContent.getParameter("command")[0];
			CommandEnum currentEnum = CommandEnum.valueOf(action.toUpperCase());
			current = currentEnum.getCurrentCommand();
		}catch (NullPointerException e){
			return current;

		} catch (IllegalArgumentException e) {
			requestContent.setAttribute("wrongAction", MessageManager.getProperty("message.wrongaction"));
		}
		return current;
	}
}