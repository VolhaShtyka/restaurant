package com.shtyka.dao;

import com.shtyka.dao.exceptions.DaoException;
import com.shtyka.entity.Menu;
import com.shtyka.entity.User;

import java.util.List;

public interface OrderDao<Order> extends Dao<Order>{
   List<Order> findClientOrder(Integer id) throws DaoException;
   Order createByMenu(Menu menu, User user) throws DaoException;
}
