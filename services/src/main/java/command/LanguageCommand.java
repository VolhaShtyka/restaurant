package command;
import resource.ConfigurationManager;
import resource.LanguageBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.Locale;
public class LanguageCommand implements ActionCommand {		

	public String execute(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		String page = null;
		HttpSession session = request.getSession(true);
		Locale.setDefault(new Locale(request.getParameter("language").toLowerCase(), request.getParameter("language").toUpperCase()));
		LanguageBundle.addLanguage(request.getParameter("language")+".properties", request);		
		session.setAttribute("language", request.getParameter("language"));
		page = ConfigurationManager.getProperty("path.page.login");		
		return page;
	}
}