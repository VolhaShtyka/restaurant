package com.shtyka.services.serviceImpl;

import com.shtyka.dao.daoImpl.OrderDaoImpl;
import com.shtyka.dao.exceptions.DaoException;
import com.shtyka.entity.Menu;
import com.shtyka.entity.Order;
import com.shtyka.entity.User;
import com.shtyka.services.OrderService;
import com.shtyka.services.exceptions.ServiceException;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.Serializable;
import java.util.List;

public class OrderServiceImpl extends OrderService<Order> {
    private final Logger log = Logger.getLogger(OrderServiceImpl.class);
    private OrderDaoImpl orderDao = OrderDaoImpl.getOrderDaoImpl();
    private static OrderServiceImpl orderService;

    public OrderServiceImpl(){}
    public static synchronized OrderServiceImpl getOrderServiceImpl() {
        if (orderService == null) {
            orderService = new OrderServiceImpl();
        }
        return orderService;
    }

    public List<Order> findOrderClient(Integer userId) throws ServiceException {
        List<Order> orders;
        Session session = util.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            orders = orderDao.findOrderClient(userId);
            transaction.commit();
            log.info(orders + TRANSACTION_SUCCESS);
        } catch (DaoException e) {
            transaction.rollback();
            log.error(TRANSACTION_FAIL, e);
            throw new ServiceException(TRANSACTION_FAIL, e);
        }
        return orders;
    }

    @Override
    public Order createByMenu(Menu menu, User user) throws ServiceException {
        Order order;
        Session session = util.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            order = orderDao.createByMenu(menu, user);
            transaction.commit();
            log.info(order + TRANSACTION_SUCCESS);
        } catch (DaoException e) {
            transaction.rollback();
            log.error(TRANSACTION_FAIL);
            throw new ServiceException(e.getMessage());
        }
        return order;
    }

    public List<Order> findAll() throws ServiceException {
        List<Order> orders;
        Session session = util.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            orders = orderDao.findAll();
            transaction.commit();
            log.info(orders);
            log.info(TRANSACTION_SUCCESS);
        } catch (DaoException e) {
            transaction.rollback();
            log.error(TRANSACTION_FAIL);
            throw new ServiceException(e.getMessage());
        }
        return orders;
    }

    @Override
    public Order get(Serializable id) throws ServiceException {
        return null;
    }
}
