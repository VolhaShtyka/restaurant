package com.shtyka.util;
import java.util.ResourceBundle;
public class ManagerHQL {
    private final static ResourceBundle resourceBundle = ResourceBundle.getBundle("inquiryHQL");
    public ManagerHQL() {}
    public static String getProperty(String key) {
        return resourceBundle.getString(key);
    }
}
