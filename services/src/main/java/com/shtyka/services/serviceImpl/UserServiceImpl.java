package com.shtyka.services.serviceImpl;

import com.shtyka.dao.MenuDao;
import com.shtyka.dao.UserDao;
import com.shtyka.dao.exceptions.DaoException;
import com.shtyka.entity.Menu;
import com.shtyka.entity.Order;
import com.shtyka.entity.User;
import com.shtyka.services.BaseService;
import com.shtyka.services.UserService;
import com.shtyka.services.exceptions.ServiceException;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl extends BaseService<User> implements UserService<User> {
    private UserDao userDao;
    private final Logger log = Logger.getLogger(UserServiceImpl.class);
    private static UserServiceImpl userService;
    Session session = util.getSession();

    public UserServiceImpl() {
    }

    @Autowired
    private MenuDao menuDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    public static synchronized UserServiceImpl getUserServiceImpl() {
        if (userService == null) {
            userService = new UserServiceImpl();
        }
        return userService;
    }

    @Override
    public User findByLogin(String login) throws ServiceException {
        User user;
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            user = (User) userDao.findByLogin(login);
            transaction.commit();
            log.info(user);
            log.info(TRANSACTION_SUCCESS);
        } catch (DaoException e) {
            transaction.rollback();
            log.error(TRANSACTION_FAIL, e);
            throw new ServiceException(TRANSACTION_FAIL, e);
        }
        return user;
    }

    @Override
    public List<User> findAll() throws ServiceException {
        List<User> users;
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            users = userDao.findAll();
            transaction.commit();
            log.info(users);
            log.info(TRANSACTION_SUCCESS);
        } catch (DaoException e) {
            transaction.rollback();
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

    public int countOrder(User user) throws ServiceException {
        int count;
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            count = userDao.countOrder(user);
            transaction.commit();
            log.info(count);
            log.info(TRANSACTION_SUCCESS);
        } catch (DaoException e) {
            transaction.rollback();
            log.error(TRANSACTION_FAIL);
            throw new ServiceException(e.getMessage());
        }
        return count;
    }

    public String checkLoginAdmin(String enterLogin, String enterPassword) throws ServiceException {
        String status;
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            status = userDao.checkLoginAdmin(enterLogin, enterPassword);
            transaction.commit();
            log.info(status);
            log.info(TRANSACTION_SUCCESS);
        } catch (DaoException e) {
            transaction.rollback();
            log.error(TRANSACTION_FAIL, e);
            throw new ServiceException(TRANSACTION_FAIL, e);
        }
        return status;
    }

    public List<User> findAll(int recordsPerPage, int currentPage, Integer minPrice, Integer maxPrice, Integer minTableNumber, Integer maxTableNumber, String ASC) throws ServiceException {
        List<User> results;
        List<User> clients = new ArrayList<>();
        List<Order> orders;
        List<Menu> menus;
        Transaction transaction = null;
        try {
            int sumMaxPrice = 0;
            transaction = session.beginTransaction();
            orders = OrderServiceImpl.getOrderServiceImpl().findAll();
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
            ArrayList<Integer> sumPrev = new ArrayList<>();
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


            transaction.commit();
            log.info(clients);
            log.info(TRANSACTION_SUCCESS);
        } catch (DaoException e) {
            transaction.rollback();
            log.error(TRANSACTION_FAIL, e);
            throw new ServiceException(TRANSACTION_FAIL, e);
        }

        return clients;
    }

    public Integer getNumberPageWithFilter(Integer minPrice, Integer maxPrice, Integer minTableNumber, Integer maxTableNumber) throws ServiceException {
        Integer amount;
        Integer numberOfPages;
        Session session = util.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            amount = userDao.getNumberPageWithFilter(minTableNumber, maxTableNumber);
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
}
