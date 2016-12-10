package com.shtyka.services;

import com.shtyka.entity.Menu;
import com.shtyka.entity.User;
import com.shtyka.services.exceptions.ServiceException;

import java.util.List;

public interface OrderService<Order> extends IService<Order> {
    List<Order> findClientOrder(Integer userId) throws ServiceException;

    Order createByMenu(Menu menu, User user) throws ServiceException;

    List<Order> findAll() throws ServiceException;
}
