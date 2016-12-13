package com.shtyka.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.context.annotation.Lazy;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "users")
//@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@GenericGenerator(name = "PK", strategy = "increment")
@Lazy(value = false)
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

    public User(String password, Integer id, String name, int tableNumber, Integer roleId){
        this.password = password;
        this.id = id;
        this.name=name;
        this.tableNumber=tableNumber;
        this.roleId = roleId;
    }


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
        return " " + name;
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
        if (!roleId.equals(user.roleId)) return false;
        return true;
    }
    @Override
    public int hashCode(){
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + tableNumber.hashCode();
        result = 31 * result + roleId.hashCode();
        return result;
    }


}
