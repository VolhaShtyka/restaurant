package com.shtyka.dao;

import com.shtyka.entity.Menu;

import java.sql.SQLException;
import java.util.List;

public interface MenuDao extends Dao <Menu>{
    List<Menu> findAll() throws SQLException;
    //Menu findById(int id); удалить
    Menu read(int id) throws SQLException;
    boolean delete(Menu entity);
    boolean create(Menu entity);
    List<Menu> update(Menu entity);
}
