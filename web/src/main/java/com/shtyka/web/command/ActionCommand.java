package com.shtyka.web.command;
import com.shtyka.dao.exceptions.DaoException;
import com.shtyka.services.exceptions.ServiceException;
import com.shtyka.web.commandFactory.SessionRequestContent;

import java.sql.SQLException;

/*
 * Interface commands, which will be overridden 
 * by all the teams in the servlet
*/
public interface ActionCommand {
	String execute(SessionRequestContent requestContent) throws DaoException, ServiceException, SQLException;
}