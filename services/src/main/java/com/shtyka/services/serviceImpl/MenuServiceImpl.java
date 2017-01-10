package com.shtyka.services.serviceImpl;

import com.shtyka.dao.MenuDao;
import com.shtyka.dao.exceptions.DaoException;
import com.shtyka.entity.Menu;
import com.shtyka.services.BaseService;
import com.shtyka.services.MenuService;
import com.shtyka.services.exceptions.ServiceException;
import org.apache.log4j.Logger;
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

    @Autowired
    public MenuServiceImpl(MenuDao menuDao){this.menuDao = menuDao;}

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<Menu> findAll(int recordsPerPage, int currentPage) throws ServiceException {
        List<Menu> menu;
        try {
            menu = menuDao.findAll(recordsPerPage, currentPage);
            log.info(menu + TRANSACTION_SUCCESS);
        } catch (DaoException e) {
            log.error(TRANSACTION_FAIL, e);
            throw new ServiceException(TRANSACTION_FAIL, e);
        }
        return menu;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<Menu> findAll(int recordsPerPage, int currentPage,Integer minPrice, Integer maxPrice) throws ServiceException {
        List<Menu> menu;
        try {
            menu = menuDao.findAll(recordsPerPage, currentPage, minPrice, maxPrice);
            log.info(menu + TRANSACTION_SUCCESS);
        } catch (DaoException e) {
            log.error(TRANSACTION_FAIL, e);
            throw new ServiceException(TRANSACTION_FAIL, e);
        }
        return menu;
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<Menu> findAll(int recordsPerPage, int currentPage,Integer minPrice, Integer maxPrice, Integer minWeight, Integer maxWeight) throws ServiceException {
        List<Menu> menu;
        try {
            menu = menuDao.findAll(recordsPerPage, currentPage, minPrice, maxPrice, minWeight, maxWeight);
            log.info(menu + TRANSACTION_SUCCESS);
        } catch (DaoException e) {
            log.error(TRANSACTION_FAIL, e);
            throw new ServiceException(TRANSACTION_FAIL, e);
        }
        return menu;
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<Menu> findAll() throws ServiceException {
        List<Menu> menu;
        try {
            menu = menuDao.findAll();
            log.info(menu + TRANSACTION_SUCCESS);
        } catch (DaoException e) {
            log.error(TRANSACTION_FAIL, e);
            throw new ServiceException(TRANSACTION_FAIL, e);
        }
        return menu;
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public int getNumberOfPages(int recordsPerPage) throws ServiceException {
        int numberOfPages;
        try {
            Long numberOfRecords = menuDao.getAmount();
            numberOfPages = (int) Math.ceil(numberOfRecords / recordsPerPage);
            log.info(numberOfPages);
            log.info(TRANSACTION_SUCCESS);
        } catch (DaoException e) {
            log.error(TRANSACTION_FAIL);
            throw new ServiceException(e.getMessage());
        }
        return numberOfPages;
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Menu findEntityById(Integer id) throws ServiceException {
        Menu menu;
        try {
            menu = (Menu) menuDao.findEntityById(id);
            log.info(menu + TRANSACTION_SUCCESS);
        } catch (DaoException e) {
            log.error(TRANSACTION_FAIL);
            throw new ServiceException(e.getMessage());
        }
        return menu;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Integer getNumberPageWithFilter(Integer minPrice, Integer maxPrice, Integer minWeight, Integer maxWeight) throws ServiceException {
        Integer amount;
        Integer numberOfPages;
        try {

            amount = (int) (long)menuDao.getNumberPageWithFilter(minPrice, maxPrice, minWeight, maxWeight);
            numberOfPages = (int) Math.ceil(amount * 1.0 / 4);

            log.info(amount + TRANSACTION_SUCCESS);
        } catch (DaoException e) {
            log.error(TRANSACTION_FAIL, e);
            throw new ServiceException(TRANSACTION_FAIL, e);
        }
        return numberOfPages;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
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
