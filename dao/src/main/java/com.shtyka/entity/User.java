package com.shtyka.entity;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@GenericGenerator(name = "PK", strategy = "increment")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "PK")
    public Integer getId() {
        return id;
    }
    private Integer id;


    @Column (name = "role_id")
    public Integer getRoleId() {
        return roleId;
    }
    private Integer roleId;


    @Column (name = "password")
    public String getPassword() { return password;}
    private String password;

    @Column (name = "user_name")
    public String getName() {
        return name;
    }
    private String name;

    @Column (name = "table_number")
    public Integer getTableNumber() {
        return tableNumber;
    }
    private Integer tableNumber;


    public User(){}
    public User(int i, int i1, int i2, String michel, int i3, List<Menu> menu){}

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


    public void setId(Integer id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
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


}
