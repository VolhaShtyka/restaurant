package com.shtyka.dao;

import com.shtyka.entity.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDao extends Dao <User>{
    boolean create(User entity);
    User read(int id);
    List<User> update(User entity) throws SQLException;
    boolean delete(int id);
}
