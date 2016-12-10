package com.shtyka.services;

import com.shtyka.services.exceptions.ServiceException;

import java.util.List;

public interface UserService<User> extends IService<User>{
    User findByLogin(String login) throws ServiceException;
    List<User> findAll() throws ServiceException;
    List<User> findAll(int recordsPerPage, int currentPage, Integer minPrice, Integer maxPrice, Integer minTableNumber, Integer maxTableNumber, String ASC) throws ServiceException;
    int countOrder(User user) throws ServiceException;
    String checkLoginAdmin(String enterLogin, String enterPassword) throws ServiceException;
    Integer getNumberPageWithFilter(Integer minPrice, Integer maxPrice, Integer minTableNumber, Integer maxTableNumber) throws ServiceException;
}
