package com.shtyka.services.serviceImpl;

import com.shtyka.dao.OrderDao;
import com.shtyka.dao.exceptions.DaoException;
import com.shtyka.entity.Menu;
import com.shtyka.entity.Order;
import com.shtyka.entity.User;
import com.shtyka.services.BaseService;
import com.shtyka.services.OrderService;
import com.shtyka.services.exceptions.ServiceException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class OrderServiceImpl extends BaseService<Order> implements OrderService<Order>{
    private final Logger log = Logger.getLogger(OrderServiceImpl.class);
    private OrderDao orderDao;

    @Autowired
    public OrderServiceImpl(OrderDao orderDao){
        this.orderDao = orderDao;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<Order> findClientOrder(Integer userId) throws ServiceException {
        List<Order> orders;
        try {
            orders = orderDao.findClientOrder(userId);
            log.info(orders + TRANSACTION_SUCCESS);
        } catch (DaoException e) {
            log.error(TRANSACTION_FAIL, e);
            throw new ServiceException(TRANSACTION_FAIL, e);
        }
        return orders;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void delete(Serializable id) throws ServiceException {
        try {
            orderDao.delete(id);
            log.info(id + TRANSACTION_SUCCESS);
        } catch (DaoException e) {
            log.error(TRANSACTION_FAIL, e);
            throw new ServiceException(TRANSACTION_FAIL, e);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Order createByMenu(Menu menu, User user) throws ServiceException {
        Order order;
        try {
            order = (Order) orderDao.createByMenu(menu, user);
            log.info(order + TRANSACTION_SUCCESS);
        } catch (DaoException e) {
            log.error(TRANSACTION_FAIL);
            throw new ServiceException(e.getMessage());
        }
        return order;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<Order> findAll() throws ServiceException {
        List<Order> orders;
        try {

            orders = orderDao.findAll();
            log.info(orders);
            log.info(TRANSACTION_SUCCESS);
        } catch (DaoException e) {
            log.error(TRANSACTION_FAIL);
            throw new ServiceException(e.getMessage());
        }
        return orders;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Order get(Serializable id) throws ServiceException {
        return null;
    }
}
