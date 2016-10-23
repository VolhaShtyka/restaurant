package com.shtyka.dao;

import java.sql.SQLException;
import java.util.List;

interface Dao<T> {

    boolean create(T entity);
    T read(int id) throws SQLException;
    List<T> update(T entity) throws SQLException;
    boolean delete(int id);
}
