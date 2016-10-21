package command;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
/*
 * Interface commands, which will be overridden 
 * by all the teams in the servlet
*/
public interface ActionCommand {
	String execute(HttpServletRequest request,
                   HttpServletResponse response) throws SQLException;
}