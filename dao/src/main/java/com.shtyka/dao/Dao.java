package com.shtyka.dao;

import com.shtyka.dao.exceptions.DaoException;

import java.io.Serializable;
import java.util.List;

public interface Dao<T> {

    void saveOrUpdate(T t) throws DaoException;
    T get(Serializable id) throws DaoException;
    void delete(Serializable id) throws DaoException;
    List<T> findAll() throws DaoException;
}
