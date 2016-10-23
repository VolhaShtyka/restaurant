package com.shtyka.util;

import java.util.ResourceBundle;

public class ManagerSQL {
    private final static ResourceBundle resourceBundle = ResourceBundle.getBundle("inquirySQL");

    public ManagerSQL() {
    }

    public static String getProperty(String key) {
        return resourceBundle.getString(key);
    }
}
