package serviceManager;

import commandFactory.SessionRequestContent;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;
public class LanguageBundle {
    public static void addLanguage(String fileURL, SessionRequestContent requestContent){
		Properties properties = new Properties();
		InputStream in = LanguageBundle.class.getClassLoader().getResourceAsStream(fileURL);
        String resBundle = Locale.getDefault().getLanguage().toUpperCase();
		try {
//			InputStreamReader reader = new InputStreamReader(in, "UTF-8");
			properties.load(in);
//			reader.close();
	//		in.close();
		} catch (IOException e) {
			System.out.print("Language not found");
		}
		for(Object key : properties.keySet()) {
			String value = properties.getProperty((String)key);
			requestContent.setAttribute((String)key, value);
		}
	}
}