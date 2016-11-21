package com.shtyka.services.serviceImpl;

import com.shtyka.dao.daoImpl.UserDaoImpl;
import com.shtyka.dao.exceptions.DaoException;
import com.shtyka.entity.User;
import com.shtyka.services.UserService;
import com.shtyka.services.exceptions.ServiceException;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.Serializable;
import java.util.List;

public class UserServiceImpl extends UserService<User>{
    private UserDaoImpl userDao = UserDaoImpl.getUserDaoImpl();
    private final Logger log = Logger.getLogger(UserServiceImpl.class);
    private static UserServiceImpl userService;

    public UserServiceImpl() {}
    public static synchronized UserServiceImpl getUserServiceImpl() {
        if (userService == null) {
            userService = new UserServiceImpl();
        }
        return userService;
    }

    public User findByLogin(String login) throws ServiceException {
        User user;
        Session session = util.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            user = userDao.findByLogin(login);
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

    public List<User> findAll() throws ServiceException {
        List<User> users;
        Session session = util.getSession();
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
    public User get(Serializable id) throws ServiceException{
        User user ;
        Session session = util.getSession();
        Transaction transaction = null;
        try {
        transaction = session.beginTransaction();
        user = userDao.get(id);
        transaction.commit();
        log.info(TRANSACTION_SUCCESS);
        } catch (DaoException e) {
            transaction.rollback();
            log.error(TRANSACTION_FAIL);
            throw new ServiceException(e.getMessage());
        }
        return user; }

    public int countOrder(User user) throws ServiceException {
        int count = 0;
        Session session = util.getSession();
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
        Session session = util.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            status = userDao.checkLoginAdmin(enterLogin,enterPassword);
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
}
