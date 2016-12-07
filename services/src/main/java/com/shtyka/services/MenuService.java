package com.shtyka.services;

import com.shtyka.services.exceptions.ServiceException;

import java.util.List;

public interface MenuService<Menu> extends IService<Menu>{
    List<Menu> findAll(int page, int recordePerPage) throws ServiceException;
    List<Menu> findAll(int recordsPerPage, int currentPage,Integer minPrice, Integer maxPrice, Integer minWeight, Integer maxWeight) throws ServiceException;
    List<Menu> findAll(int recordsPerPage, int currentPage,Integer minPrice, Integer maxPrice) throws ServiceException;
    List<Menu> findAll() throws ServiceException;
    List<Menu> sortingMenu(List<Menu> menus, int numberSort) throws ServiceException;
    int getNumberOfPages(int recordsPerPage) throws ServiceException;
    Menu findEntityById(Integer id) throws ServiceException;
    Integer getNumberPageWithFilter (Integer minPrice, Integer maxPrice, Integer minWeight, Integer maxWeight) throws ServiceException;
}
