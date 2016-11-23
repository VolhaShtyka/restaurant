package com.shtyka.services.serviceImpl;

import com.shtyka.dao.daoImpl.MenuDaoImpl;
import com.shtyka.dao.exceptions.DaoException;
import com.shtyka.entity.Menu;
import com.shtyka.services.MenuService;
import com.shtyka.services.exceptions.ServiceException;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class MenuServiceImpl extends MenuService<Menu> {
    private final Logger log = Logger.getLogger(MenuServiceImpl.class);
    private MenuDaoImpl menuDao = MenuDaoImpl.getMenuDaoImpl();
    private static MenuServiceImpl menuService;

    public MenuServiceImpl(){}
    public static synchronized MenuServiceImpl getMenuServiceImpl() {
        if (menuService == null) {
            menuService = new MenuServiceImpl();
        }
        return menuService;
    }
    @Override
    public List<Menu> findAll(int recordsPerPage, int currentPage) throws ServiceException {
        List<Menu> menu;
        Session session = util.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            menu = menuDao.findAll(recordsPerPage, currentPage);
            transaction.commit();
            log.info(menu + TRANSACTION_SUCCESS);
        } catch (DaoException e) {
            transaction.rollback();
            log.error(TRANSACTION_FAIL, e);
            throw new ServiceException(TRANSACTION_FAIL, e);
        }
        return menu;
    }
    @Override
    public int getNumberOfPages(int recordsPerPage) throws ServiceException {
        int numberOfPages;
        Session session = util.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Long numberOfRecords = menuDao.getAmount();
            numberOfPages = (int) Math.ceil(numberOfRecords * 1.0 / recordsPerPage);
            transaction.commit();
            log.info(numberOfPages);
            log.info(TRANSACTION_SUCCESS);
        } catch (DaoException e) {
            transaction.rollback();
            log.error(TRANSACTION_FAIL);
            throw new ServiceException(e.getMessage());
        }
        return numberOfPages;
    }

    @Override
    public Menu findEntityById(int id) throws ServiceException {
        Menu menu;
        Session session = util.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            menu = menuDao.findEntityById(id);
            transaction.commit();
            log.info(menu + TRANSACTION_SUCCESS);
        } catch (DaoException e) {
            transaction.rollback();
            log.error(TRANSACTION_FAIL);
            throw new ServiceException(e.getMessage());
        }
        return menu;
    }

//    @Override
//    public Menu get(Serializable id) throws ServiceException {
//        return null;
//    }
}
