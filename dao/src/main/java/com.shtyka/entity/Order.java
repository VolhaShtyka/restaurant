package com.shtyka.entity;

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
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

    @Formula("order_id*2")
    public Integer getTotalOrder() { return totalOrder; }
    private Integer totalOrder;

    public Order(){}
    public Order(String statusOrder, Integer orderId, Integer clientId, Long menuId){
        this.orderId = orderId;
        this.statusOrder=statusOrder;
        this.clientId = clientId;
        this.menuId = menuId;
        //this.totalOrder = totalOrder;
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
    @Override
    public String toString(){
        return orderId + " " + statusOrder;
    }

    public void setTotalOrder(Integer totalOrder) {
        this.totalOrder = totalOrder;
    }
}
