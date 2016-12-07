package com.shtyka.web.webManager;

import com.shtyka.web.commandFactory.SessionRequestContent;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;
public class LanguageBundle {
    public static void addLanguage(String fileURL, SessionRequestContent requestContent){
		Properties properties = new Properties();
		InputStream in = LanguageBundle.class.getClassLoader().getResourceAsStream(fileURL);
		try {
			InputStreamReader reader = new InputStreamReader(in, "UTF-8");
			properties.load(in);
			reader.close();
			in.close();
		} catch (IOException e) {
			System.out.print("Language not found");
		}
		for(Object key : properties.keySet()) {
			String value = properties.getProperty((String)key);
			requestContent.setAttribute((String)key, value);
		}
	}
}