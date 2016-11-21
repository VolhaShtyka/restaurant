package com.shtyka.services;

import com.shtyka.services.exceptions.ServiceException;

import java.io.Serializable;

public interface IService<T> {
    void saveOrUpdate(T t) throws ServiceException;
    T get (Serializable id) throws ServiceException;
    void delete (T t) throws ServiceException;
//    List<T> findAll() throws ServiceException;
}
