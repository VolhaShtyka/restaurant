package com.shtyka.dao;

import com.shtyka.dao.exceptions.DaoException;

import java.util.List;

public interface MenuDao<Menu> extends Dao<Menu> {
    Menu findEntityById(int id) throws DaoException;
    List<Menu> findAll(int page, int recordPerPage) throws DaoException;
    Long getAmount() throws DaoException;
}
