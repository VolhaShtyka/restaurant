package com.shtyka.web.webManager;


import org.springframework.ui.ModelMap;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class LanguageBundle {
    public static ModelMap addLanguage(String fileURL, ModelMap modelMap){
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
			modelMap.addAttribute((String)key, value);
		}
		return modelMap;
	}
}