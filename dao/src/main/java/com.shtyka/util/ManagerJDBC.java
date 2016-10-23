package com.shtyka.util;

import java.util.ResourceBundle;

public class ManagerJDBC {
    private final static ResourceBundle resourceBundle = ResourceBundle.getBundle("confDB");
    private ManagerJDBC() {}
    public static String getProperty(String key) {
        return resourceBundle.getString(key);
    }
}
