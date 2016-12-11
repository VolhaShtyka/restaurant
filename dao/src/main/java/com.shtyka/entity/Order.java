package com.shtyka.entity;

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Locale;

@Entity
@Table(name = "orders")
@GenericGenerator(name = "PK", strategy = "increment")
public class Order implements Serializable {
    private static final long serialVersionUID = 3L;

    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "PK")
    public Integer getOrderId() {
        return orderId;
    }

    private Integer orderId;

    @Column(name = "user_id")
    public Integer getClientId() {
        return clientId;
    }

    private Integer clientId;

    @Column(name = "menu_id")
    public Long getMenuId() {
        return menuId;
    }

    private Long menuId;

    @Column(name = "status_order")
    public String getStatusOrder() {
        return statusOrder;
    }

    private String statusOrder;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order", cascade = CascadeType.PERSIST)
	public List<Menu> getMenus() {
        return menus;
    }
    private List<Menu> menus;

    public Order() {}

    public Order(String statusOrder, Integer orderId, Integer clientId, Long menuId, List<Menu> menu) {
        this.orderId = orderId;
        this.statusOrder = statusOrder;
        this.clientId = clientId;
        this.menuId = menuId;
        this.menus = menu;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Menu)) return false;
        Order order = (Order) o;
        if (!menuId.equals(order.getMenuId())) return false;
        if (!clientId.equals(order.getClientId())) return false;
        if (!statusOrder.equals(order.getStatusOrder())) return false;
        if (!menus.equals(order.getMenus())) return false;
        if (!orderId.equals(order.getOrderId())) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = orderId.hashCode();
        result = 31 * result + menuId.hashCode();
        result = 31 * result + clientId.hashCode();
        result = 31 * result + statusOrder.hashCode();
        result = 31 * result + menus.hashCode();
        return result;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public void setStatusOrder(String statusOrder) {
        this.statusOrder = statusOrder;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }


    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }


    @Override
    public String toString() {

        if (!menus.isEmpty()) {
            if(("ru").equalsIgnoreCase(Locale.getDefault().getLanguage())) {
                return orderId + " " + menus.get(0).getMealName() + " " + StatusMeal.getValueEnum(statusOrder);
            }else{return orderId + " " + menus.get(0).getNameen() + " " +  StatusMeal.getValueEnum(statusOrder);
            }
        }else{
            return orderId + " " + statusOrder;
        }
    }
}
