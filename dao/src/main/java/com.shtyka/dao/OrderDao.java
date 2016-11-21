package com.shtyka.dao;

import com.shtyka.dao.exceptions.DaoException;
import com.shtyka.entity.Menu;
import com.shtyka.entity.User;

import java.util.List;

public abstract class OrderDao<Order> extends BaseDao<Order>{
    public abstract List<Order> findOrderClient(Integer id) throws DaoException;
    public abstract Order createByMenu(Menu menu, User user) throws DaoException;
}
