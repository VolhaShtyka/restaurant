package com.shtyka.dao.daoImpl;

import com.shtyka.dao.BaseDao;
import com.shtyka.dao.OrderDao;
import com.shtyka.dao.exceptions.DaoException;
import com.shtyka.entity.Menu;
import com.shtyka.entity.Order;
import com.shtyka.entity.StatusMeal;
import com.shtyka.entity.User;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderDaoImpl extends BaseDao<Order> implements OrderDao<Order>{

    private static Logger log = Logger.getLogger(OrderDaoImpl.class);
    private static final String HQL_SELECT_ALL_MEALNAME = "FROM Order WHERE clientId= :id";
    private Session session;

    @Autowired
    public OrderDaoImpl (SessionFactory sessionFactory){
        super(sessionFactory);
        session = sessionFactory.openSession();
    }

    @Override
    public List<Order> findClientOrder(Integer id) throws DaoException {
        List<Order> orders;
        try {
            Query query = session.createQuery(HQL_SELECT_ALL_MEALNAME);
            query.setParameter("id", id);
            orders = query.list();
            log.info(orders);
        }catch (HibernateException e){
            log.info("Error in DAO findEntityById");
            throw new DaoException(e);
        }
        return orders;
    }

    @Override
    public Order createByMenu(Menu menu, User user) throws DaoException {
        Order order = new Order();
        List<Menu> menus = new ArrayList<Menu>();
        menus.add(menu);
        order.setStatusOrder(StatusMeal.NOREADY.name());
        order.setClientId(user.getId());
        order.setMenuId(menu.getMenuId());
        order.setMenus(menus);
        session.saveOrUpdate(order);
        return order;
    }
}
