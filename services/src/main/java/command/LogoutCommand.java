package command;
import resource.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Locale;
public class LogoutCommand implements ActionCommand{

	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		String page = ConfigurationManager.getProperty("path.page.index");
		HttpSession session = request.getSession();
		Locale.setDefault(new Locale("ru", "RU"));
		session.invalidate();		
		return page;
	}
}