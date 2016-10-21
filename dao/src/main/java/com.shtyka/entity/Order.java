package com.shtyka.entity;

public class Order {
    private int orderId;
    private int clientId;
    private int menuId;
    private String statusOrder;
    public Order(){}
    public Order(String statusOrder, int orderId, int clientId, int menuId){
        this.orderId = orderId;
        this.statusOrder=statusOrder;
        this.clientId = clientId;
        this.menuId = menuId;
    }
    public int getOrderId() {
        return orderId;
    }
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
    public int getClientId() {
        return clientId;
    }
    public void setClientId(int clientId) {
        this.clientId = clientId;
    }
    public String getStatusOrder() {
        return statusOrder;
    }
    public void setStatusOrder(String statusOrder) {
        this.statusOrder = statusOrder;
    }
    public int getMenuId() {
        return menuId;
    }
    public void setMenuId(Menu menu) {
        this.menuId = menu.getMenuId();
    }
    @Override
    public String toString(){
        return orderId + " " + statusOrder;
    }
}
