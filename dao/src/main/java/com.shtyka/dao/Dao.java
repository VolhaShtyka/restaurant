package com.shtyka.dao;

import java.sql.SQLException;
import java.util.List;

public interface Dao<T> {

    boolean create(T entity) throws SQLException;
    T read(int id);
    List<T> update(T entity);
    boolean delete(T entity);

}
