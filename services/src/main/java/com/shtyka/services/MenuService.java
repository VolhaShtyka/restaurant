package com.shtyka.services;

import com.shtyka.services.exceptions.ServiceException;

import java.util.List;

public abstract class MenuService<Menu> extends BaseService<Menu>{
    public abstract List<Menu> findAll(int page, int recordePerPage) throws ServiceException;
    public abstract int getNumberOfPages(int recordsPerPage) throws ServiceException;
    public abstract Menu findEntityById(int id) throws ServiceException;
}
