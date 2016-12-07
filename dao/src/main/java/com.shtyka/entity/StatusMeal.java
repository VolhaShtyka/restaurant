package com.shtyka.entity;

import java.util.Locale;
import java.util.ResourceBundle;

public enum StatusMeal {
    NOREADY("NOREADY"), COOKING("COOKING"), READY("READY"), CHEKED("CHEKED");
    private String valueEnum;
    public static ResourceBundle propert = ResourceBundle.getBundle("RU", Locale.getDefault());
    StatusMeal(String valueEnum) {
        this.setValueEnum(valueEnum);
    }
    public static String getValueEnum(String valueEnum) {
        if(("ru").equalsIgnoreCase(Locale.getDefault().getLanguage())) {
            propert = ResourceBundle.getBundle("RU", Locale.getDefault());
            return propert.getString(valueEnum);
        }else {
            propert = ResourceBundle.getBundle("EN", Locale.getDefault());
            return propert.getString(valueEnum);
        }
    }
    private void setValueEnum(String valueEnum) {
        this.valueEnum = valueEnum;
    }
}