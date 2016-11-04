package com.shtyka.entity;

import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private Integer roleId;
    private String password;
    private String name;
    private Integer tableNumber;
    //private String roleName;
    public User(){}

//    public User(String name, String password, String roleName){
//        this.password = password;
//        this.name=name;
//        this.roleName = roleName;
//    }
//
//    public User(String password, Integer id, String name, int tableNumber, String roleName){
//        this.password = password;
//        this.id = id;
//        this.name=name;
//        this.tableNumber=tableNumber;
//        this.roleName = roleName;
//    }
public Integer getId() {
    return id;
}

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(Integer numberTable) {
        this.tableNumber = numberTable;
    }
    @Override
    public String toString(){
        return name + " " + tableNumber;
    }
    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        if (!name.equals(user.name)) return false;
        if (!password.equals(user.password)) return false;
        if (!id.equals(user.id)) return false;
        if (!tableNumber.equals(user.tableNumber)) return false;
        //if (!roleName.equals(user.roleName)) return false;
        if (!roleId.equals(user.roleId)) return false;
        return true;
    }
    @Override
    public int hashCode(){
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + tableNumber.hashCode();
        //result = 31 * result + roleName.hashCode();
        result = 31 * result + roleId.hashCode();
        return result;
    }

    public void setRoleName(String roleName) {
        //this.roleName = roleName;
    }
    public String getRoleName() {
        return "administrator";
    }
}
