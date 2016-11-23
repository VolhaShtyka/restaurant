package com.shtyka.services;

import com.shtyka.entity.Menu;
import com.shtyka.entity.User;
import com.shtyka.services.exceptions.ServiceException;

import java.util.List;

public abstract class OrderService<Order> extends BaseService<Order>{
    public abstract List<Order> findClientOrder(Integer userId) throws ServiceException;
    public abstract Order createByMenu(Menu menu, User user) throws ServiceException;
}
