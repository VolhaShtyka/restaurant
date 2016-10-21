package com.shtyka.dao;

import com.shtyka.entity.Menu;

import java.sql.SQLException;
import java.util.List;

public interface MenuDao extends Dao <Menu>{
    List<Menu> findAll();
    Menu findByLogin(String login);
    int countOrder(MenuDao t);
    boolean deleteById(int id);
    Menu findById(int id);
    boolean delete(MenuDao entity);
    boolean create(Menu entity) throws SQLException;
    List<Menu> update(Menu entity);
}
