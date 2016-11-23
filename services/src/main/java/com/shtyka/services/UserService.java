package com.shtyka.services;

import com.shtyka.services.exceptions.ServiceException;

import java.util.List;

public abstract class UserService<User> extends BaseService<User>{
    public abstract User findByLogin(String login) throws ServiceException;
    public abstract List<User> findAll() throws ServiceException;
    public abstract int countOrder(User user) throws ServiceException;
    public abstract String checkLoginAdmin(String enterLogin, String enterPassword) throws ServiceException;
}
