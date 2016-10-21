package com.shtyka.entity;

public class User {
    private int id;
    private String password;
    private int roleId;
    private String name;
    private int tableNumber;
    private String roleName;
    public User(){};
    public User(String name, String password, String roleName){
        this.password = password;
        this.name=name;
        this.roleName = roleName;
    }

    public User(String password, int id, int roleId, String name, int tableNumber, String roleName){
        this.password = password;
        this.roleId = roleId;
        this.id = id;
        this.name=name;
        this.tableNumber=tableNumber;
        this.roleName = roleName;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getTableNumber() {
        return tableNumber;
    }
    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public int getRoleId() {
        return roleId;
    }
    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
    public String getRoleName() {
        return roleName;
    }
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
    @Override
    public String toString(){
        return name + " " + tableNumber;
    }
}
