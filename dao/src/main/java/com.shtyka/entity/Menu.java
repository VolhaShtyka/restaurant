package com.shtyka.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Locale;

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

    @Column(name = "nameen")
    public String getNameen() {
        return nameen;
    }
    private String nameen;

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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "menu_id", insertable = false, updatable = false)
    public Order getOrder() {
        return order;
    }
    private Order order;

    public Menu() {
    }

    public Menu(Long menuId, String mealName, Integer price, Integer weight) {
        this.menuId = menuId;
        this.mealName = mealName;
        this.price = price;
        this.weight = weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Menu)) return false;
        Menu menu = (Menu) o;
        if (!mealName.equals(menu.mealName)) return false;
        if (!nameen.equals(menu.nameen)) return false;
        if (!menuId.equals(menu.menuId)) return false;
        if (!price.equals(menu.price)) return false;
        if (!weight.equals(menu.weight)) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = menuId.hashCode();
        result = 31 * result + mealName.hashCode();
		result = 31 * result + nameen.hashCode();
        result = 31 * result + price.hashCode();
        result = 31 * result + weight.hashCode();
        return result;
    }

    @Override
    public String toString() {
        if(("ru").equalsIgnoreCase(Locale.getDefault().getLanguage())) {
            return menuId + " " + mealName + " " + price + " ";
        }else{
            return menuId + " " + nameen + " " + price + " ";
        }
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }
    public void setMealName(String mealName) {
        this.mealName = mealName;
    }
    public void setNameen(String nameen) {
        this.nameen = nameen;
    }
    public void setPrice(Integer price) {
        this.price = price;
    }
    public void setWeight(Integer weight) {
        this.weight = weight;
    }
    public void setOrder(Order order) {
        this.order = order;
    }
}
