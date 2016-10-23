package command;
import com.shtyka.entity.User;
import com.shtyka.jdbc.DataSource;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
class LoginLogic {
	private final static String adminOrUser = "SELECT client_name, password, role_name FROM client INNER JOIN role ON client.role_id=role.role_id";

	public static String checkLoginAdmin(String enterLogin, String enterPassword) throws SQLException {
		String loginStatus = "guest";
		List<User> clients = new ArrayList<User>();
		Connection cn = null;
		Statement st = null;
		try {
			cn = DataSource.getInstance().getConnection();
			st = cn.createStatement();
			ResultSet resultSetAdminAndUser = st.executeQuery(adminOrUser);	
			/*
			 * check the password and login role 
			 * from the database to the similarity
			 * with the data entered by md5
			 */
			while (resultSetAdminAndUser.next()) {
				User client = new User();
				client.setName(resultSetAdminAndUser.getString("client_name"));
				client.setPassword(LoginMd5.md5Custom(resultSetAdminAndUser.getString("password")));
				client.setRoleName(resultSetAdminAndUser.getString("role_name"));
				clients.add(client);
			}
		} catch (SQLException e) {
			System.out.println("Request or table failed.");
		} catch (IOException e) {
			System.out.println("IOException e: ClientDAO");
		} catch (PropertyVetoException e) {
			System.out.println("PropertyVetoException e: ClientDAO");
		} finally {
			System.out.print("");
		}
		for (User client : clients) {
			String userName = "user";
			String administrator = "administrator";
			if (client.getName().equals(enterLogin)
					&& client.getPassword().equals(LoginMd5.md5Custom(enterPassword))
					&& client.getRoleName().equals(administrator)) {
				loginStatus = administrator;
			} else if (client.getName().equals(enterLogin)
					&& client.getPassword().equals(LoginMd5.md5Custom(enterPassword))
					&& client.getRoleName().equals(userName)) {
				loginStatus = userName;
			}
		}
		return loginStatus;
	}
}