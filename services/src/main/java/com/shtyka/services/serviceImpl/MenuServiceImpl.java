package com.shtyka.services.serviceImpl;

import com.shtyka.dao.MenuDao;
import com.shtyka.dao.exceptions.DaoException;
import com.shtyka.entity.Menu;
import com.shtyka.services.BaseService;
import com.shtyka.services.MenuService;
import com.shtyka.services.exceptions.ServiceException;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class MenuServiceImpl extends BaseService<Menu> implements MenuService<Menu>{
    private final Logger log = Logger.getLogger(MenuServiceImpl.class);
    private MenuDao menuDao;
    private static MenuServiceImpl menuService;

    @Autowired
    public MenuServiceImpl(MenuDao menuDao){this.menuDao = menuDao;}


//    public static synchronized MenuServiceImpl getMenuServiceImpl() {
//        if (menuService == null) {
//            menuService = new MenuServiceImpl();
//        }
//        return menuService;
//    }

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

    public List<Menu> findAll(int recordsPerPage, int currentPage,Integer minPrice, Integer maxPrice) throws ServiceException {
        List<Menu> menu;
        Session session = util.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            menu = menuDao.findAll(recordsPerPage, currentPage, minPrice, maxPrice);
            transaction.commit();
            log.info(menu + TRANSACTION_SUCCESS);
        } catch (DaoException e) {
            transaction.rollback();
            log.error(TRANSACTION_FAIL, e);
            throw new ServiceException(TRANSACTION_FAIL, e);
        }
        return menu;
    }

    public List<Menu> findAll(int recordsPerPage, int currentPage,Integer minPrice, Integer maxPrice, Integer minWeight, Integer maxWeight) throws ServiceException {
        List<Menu> menu;
        Session session = util.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            menu = menuDao.findAll(recordsPerPage, currentPage, minPrice, maxPrice, minWeight, maxWeight);
            transaction.commit();
            log.info(menu + TRANSACTION_SUCCESS);
        } catch (DaoException e) {
            transaction.rollback();
            log.error(TRANSACTION_FAIL, e);
            throw new ServiceException(TRANSACTION_FAIL, e);
        }
        return menu;
    }

    public List<Menu> findAll() throws ServiceException {
        List<Menu> menu;
        Session session = util.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            menu = menuDao.findAll();
            transaction.commit();
            log.info(menu + TRANSACTION_SUCCESS);
        } catch (DaoException e) {
            transaction.rollback();
            log.error(TRANSACTION_FAIL, e);
            throw new ServiceException(TRANSACTION_FAIL, e);
        }
        return menu;
    }

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


    public Menu findEntityById(Integer id) throws ServiceException {
        Menu menu;
        Session session = util.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            menu = (Menu) menuDao.findEntityById(id);
            transaction.commit();
            log.info(menu + TRANSACTION_SUCCESS);
        } catch (DaoException e) {
            transaction.rollback();
            log.error(TRANSACTION_FAIL);
            throw new ServiceException(e.getMessage());
        }
        return menu;
    }


    public Integer getNumberPageWithFilter(Integer minPrice, Integer maxPrice, Integer minWeight, Integer maxWeight) throws ServiceException {
        Integer amount;
        Integer numberOfPages;
        Session session = util.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            amount = (int) (long)menuDao.getNumberPageWithFilter(minPrice, maxPrice, minWeight, maxWeight);
            numberOfPages = (int) Math.ceil(amount * 1.0 / 4);
            transaction.commit();
            log.info(amount + TRANSACTION_SUCCESS);
        } catch (DaoException e) {
            transaction.rollback();
            log.error(TRANSACTION_FAIL, e);
            throw new ServiceException(TRANSACTION_FAIL, e);
        }
        return numberOfPages;
    }

    public List<Menu> sortingMenu(final List<Menu> menus, int numberSort) throws ServiceException{
        List menu = menus;
        switch (numberSort){
            case 1:
                Collections.sort(menu, new Comparator<Menu>() {
                    @Override
                    public int compare(Menu menu, Menu menuTwo) {
                        return menu.getPrice().compareTo(menuTwo.getPrice());
                    }
                });
                break;
            case 2:
                Collections.sort(menu, new Comparator<Menu>() {
                    @Override
                    public int compare(Menu menu, Menu menuTwo) {
                        return menuTwo.getPrice().compareTo(menu.getPrice());
                    }
                });
                break;
            case 3:
                Collections.sort(menu, new Comparator<Menu>() {
                    @Override
                    public int compare(Menu menu, Menu menuTwo) {
                        return menu.getWeight().compareTo(menuTwo.getWeight());
                    }
                });
                break;
            case 4:
                Collections.sort(menu, new Comparator<Menu>() {
                    @Override
                    public int compare(Menu menu, Menu menuTwo) {
                        return menuTwo.getWeight().compareTo(menu.getWeight());
                    }
                });
                break;
        }
        return menu;
    }
}
