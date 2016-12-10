package com.shtyka.services.serviceImpl;

import com.shtyka.dao.MenuDao;
import com.shtyka.dao.OrderDao;
import com.shtyka.dao.UserDao;
import com.shtyka.dao.exceptions.DaoException;
import com.shtyka.entity.Menu;
import com.shtyka.entity.Order;
import com.shtyka.entity.User;
import com.shtyka.services.BaseService;
import com.shtyka.services.UserService;
import com.shtyka.services.exceptions.ServiceException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class UserServiceImpl extends BaseService<User> implements UserService<User> {

    private final Logger log = Logger.getLogger(UserServiceImpl.class);

    @Autowired
    UserDao userDao;

    @Autowired
    MenuDao menuDao;

    @Autowired
    OrderDao orderDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public User findByLogin(String login) throws ServiceException {
        User user;
        try {
            user = (User) userDao.findByLogin(login);
            log.info(user);
            log.info(TRANSACTION_SUCCESS);
        } catch (DaoException e) {
            log.error(TRANSACTION_FAIL, e);
            throw new ServiceException(TRANSACTION_FAIL, e);
        }
        return user;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<User> findAll() throws ServiceException {
        List<User> users;
        try {
            users = userDao.findAll();
            log.info(users);
            log.info(TRANSACTION_SUCCESS);
        } catch (DaoException e) {
            log.error(TRANSACTION_FAIL);
            throw new ServiceException(e.getMessage());
        }
        return users;
    }

    //    public User get(Serializable id) throws ServiceException{
//        User user ;
//
//        Transaction transaction = null;
//        try {
//        transaction = session.beginTransaction();
//        user = userDao.get(id);
//        transaction.commit();
//        log.info(TRANSACTION_SUCCESS);
//        } catch (DaoException e) {
//            transaction.rollback();
//            log.error(TRANSACTION_FAIL);
//            throw new ServiceException(e.getMessage());
//        }
//        return user; }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public int countOrder(User user) throws ServiceException {
        int count;
        try {
            count = userDao.countOrder(user);
            log.info(count);
            log.info(TRANSACTION_SUCCESS);
        } catch (DaoException e) {
            log.error(TRANSACTION_FAIL);
            throw new ServiceException(e.getMessage());
        }
        return count;
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public String checkLoginAdmin(String enterLogin, String enterPassword) throws ServiceException {
        String status;
        try {
            status = userDao.checkLoginAdmin(enterLogin, enterPassword);
            log.info(status);
            log.info(TRANSACTION_SUCCESS);
        } catch (DaoException e) {
            log.error(TRANSACTION_FAIL, e);
            throw new ServiceException(TRANSACTION_FAIL, e);
        }
        return status;
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<User> findAll(int recordsPerPage, int currentPage, Integer minPrice, Integer maxPrice, Integer minTableNumber, Integer maxTableNumber, String ASC) throws ServiceException {
        List<User> results;
        List<User> clients = new ArrayList<>();
        List<Order> orders;
        List<Menu> menus;
        try {
            int sumMaxPrice = 0;
            orders = orderDao.findAll();
            results = userDao.findAll(recordsPerPage, currentPage, minTableNumber, maxTableNumber, ASC);
            menus = menuDao.findAll();
            if (maxPrice == null) {
                for (Menu menu : menus) {
                    if (menu.getPrice() > sumMaxPrice) {
                        sumMaxPrice = menu.getPrice();
                        maxPrice = menu.getPrice();
                    }
                }
            }
            for (User user : results) {
                Integer sum = 0;
                for (Order order : orders) {
                    for (Menu menu : menus) {
                        if (user.getId().equals(order.getClientId()) && order.getMenuId().equals(menu.getMenuId())) {
                            sum += menu.getPrice();
                        }
                    }
                }
                if (!clients.contains(user) && sum <= maxPrice && sum >= minPrice) {
                    clients.add(user);
                }
                if (ASC.equals("sumUp")) {
                    for (int i = 0; i < clients.size()-1; i++) {
                       if(userDao.countOrder(clients.get(i)) > userDao.countOrder(clients.get(i+1))){
                           User client = clients.get(i+1);
                           clients.remove(i+1);
                           clients.add(0, client);
                       }
                    }
                } else if (ASC.equals("sumDown")) {
                    for (int i = 0; i < clients.size()-1; i++) {
                        if(userDao.countOrder(clients.get(i)) < userDao.countOrder(clients.get(i+1))){
                            User client = clients.get(i+1);
                            clients.remove(i+1);
                            clients.add(0, client);
                        }
                    }

                } else {
                    clients.add(user);
                }
            }
            log.info(TRANSACTION_SUCCESS);
        } catch (DaoException e) {
            log.error(TRANSACTION_FAIL, e);
            throw new ServiceException(TRANSACTION_FAIL, e);
        }

        return clients;
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Integer getNumberPageWithFilter(Integer minPrice, Integer maxPrice, Integer minTableNumber, Integer maxTableNumber) throws ServiceException {
        Integer amount;
        Integer numberOfPages;
        try {
            amount = userDao.getNumberPageWithFilter(minTableNumber, maxTableNumber);
            numberOfPages = (int) Math.ceil(amount * 1.0 / 4);
            log.info(amount + TRANSACTION_SUCCESS);
        } catch (DaoException e) {
            log.error(TRANSACTION_FAIL, e);
            throw new ServiceException(TRANSACTION_FAIL, e);
        }
        return numberOfPages;
    }
}
