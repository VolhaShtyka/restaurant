package com.shtyka.entity;

import java.io.Serializable;

public class Menu implements Serializable {
    private Integer menuId;
    private String mealName;
    private Integer price;
    private Integer weight;
    public Menu(){}
    public Menu(Integer menuId, String mealName, Integer price, Integer weight){
        this.menuId = menuId;
        this.mealName=mealName;
        this.price = price;
        this.weight=weight;
    }
    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if (!(o instanceof Menu)) return false;
        Menu menu = (Menu) o;
        if (!mealName.equals(menu.mealName)) return false;
        if (!menuId.equals(menu.menuId)) return false;
        if (!price.equals(menu.price)) return false;
        if (!weight.equals(menu.weight)) return false;
        return true;
    }
    @Override
    public int hashCode(){
        int result = menuId.hashCode();
        result = 31 * result + mealName.hashCode();
        result = 31 * result + price.hashCode();
        result = 31 * result + weight.hashCode();
        return result;
    }
    @Override
    public String toString(){
        return menuId+" " +  mealName + " " + price + " ";
    }
    public Integer getMenuId() {
        return menuId;
    }
    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }
    public String getMealName() {
        return mealName;
    }
    public void setMealName(String mealName) {
        this.mealName = mealName;
    }
    public Integer getPrice() {
        return price;
    }
    public void setPrice(Integer price) {
        this.price = price;
    }
    public Integer getWeight() {
        return weight;
    }
    public void setWeight(Integer weight) {
        this.weight = weight;
    }
}
