package com.shtyka.dao;

import com.shtyka.dao.exceptions.DaoException;

import java.util.List;

public interface MenuDao<Menu> extends Dao<Menu> {
    Menu findEntityById(Integer id) throws DaoException;
    List<Menu> findAll(int page, int recordPerPage) throws DaoException;
    List<Menu> findAll(int page, int recordPerPage, Integer minPrice, Integer maxPrice) throws DaoException;
    List<Menu> findAll(int page, int recordPerPage,Integer minPrice, Integer maxPrice, Integer minWeight, Integer maxWeight) throws DaoException;
    Long getAmount() throws DaoException;
    Long getNumberPageWithFilter (Integer minPrice, Integer maxPrice, Integer minWeight, Integer maxWeight) throws DaoException;
}
