package com.shtyka.entity;

public class Menu {
    private int menuId;
    private String mealName;
    private int price;
    private int weight;
    public Menu(){}
    public Menu(int menuId, String mealName, int price, int weight){
        this.menuId = menuId;
        this.mealName=mealName;
        this.price = price;
        this.weight=weight;
    }
    @Override
    public String toString(){
        return menuId+" " +  mealName + " " + price + " ";
    }
    public int getMenuId() {
        return menuId;
    }
    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }
    public String getMealName() {
        return mealName;
    }
    public void setMealName(String mealName) {
        this.mealName = mealName;
    }
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public int getWeight() {
        return weight;
    }
    public void setWeight(int weight) {
        this.weight = weight;
    }
}
