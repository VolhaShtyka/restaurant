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
    public String getValueEnum() {
        propert = ResourceBundle.getBundle("RU", Locale.getDefault());
        return propert.getString(valueEnum);
    }
    public void setValueEnum(String valueEnum) {
        this.valueEnum = valueEnum;
    }
}