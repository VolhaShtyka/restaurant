package com.shtyka.dao;

import com.shtyka.dao.exceptions.DaoException;

import java.util.List;

public abstract class MenuDao<Menu> extends BaseDao <Menu>{
    public abstract Menu findEntityById(int id) throws DaoException;
    public abstract List<Menu> findAll(int page, int recordPerPage) throws DaoException;
    public abstract Long getAmount() throws DaoException;
}
