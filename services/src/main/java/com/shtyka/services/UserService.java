package com.shtyka.services;

import com.shtyka.services.exceptions.ServiceException;

import java.util.List;

public interface UserService<User> extends IService<User>{
    User findByLogin(String login) throws ServiceException;
    List<User> findAll() throws ServiceException;
    int countOrder(User user) throws ServiceException;
    String checkLoginAdmin(String enterLogin, String enterPassword) throws ServiceException;
}
