package com.shtyka.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "menuru")
@GenericGenerator(name = "PK", strategy = "increment")
public class Menu implements Serializable {
    private static final long serialVersionUID = 2L;

    @Id
    @Column(name = "menu_id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "PK")
    public Long getMenuId() {
        return menuId;
    }
    private Long menuId;

    @Column(name = "meal_name")
    public String getMealName() {
        return mealName;
    }
    private String mealName;

    @Column(name = "price")
    public Integer getPrice() {
        return price;
    }
    private Integer price;

    @Column(name = "weight")
    public Integer getWeight() {
        return weight;
    }
    private Integer weight;

    @ManyToMany(mappedBy = "menus")
    public List<User> getUsers() {
        return users;
    }
    private List<User> users;

    public Menu(){}
    public Menu(Long menuId, String mealName, Integer price, Integer weight){
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

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }
    public void setMealName(String mealName) { this.mealName = mealName; }
    public void setPrice(Integer price) {
        this.price = price;
    }
    public void setWeight(Integer weight) {
        this.weight = weight;
    }




    public void setUsers(List<User> users) {
        this.users = users;
    }
}
