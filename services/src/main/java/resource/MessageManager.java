package resource;
import java.util.ResourceBundle;
public class MessageManager {
	private final static ResourceBundle resourceBundle = ResourceBundle.getBundle("C:/Users/home/IdeaProjects/restaurant/services/src/main/java/properties.messages");
	private MessageManager() {}
	public static String getProperty(String key) {
		return resourceBundle.getString(key);
	}
}