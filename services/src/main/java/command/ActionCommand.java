package command;
import com.shtyka.dao.exceptions.DaoException;
import commandFactory.SessionRequestContent;

import java.sql.SQLException;

/*
 * Interface commands, which will be overridden 
 * by all the teams in the servlet
*/
public interface ActionCommand {
	String execute(SessionRequestContent requestContent) throws SQLException, DaoException;
}