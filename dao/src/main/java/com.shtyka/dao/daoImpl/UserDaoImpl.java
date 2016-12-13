package com.shtyka.dao.daoImpl;

import com.shtyka.dao.BaseDao;
import com.shtyka.dao.UserDao;
import com.shtyka.dao.exceptions.DaoException;
import com.shtyka.entity.Menu;
import com.shtyka.entity.Order;
import com.shtyka.entity.User;
import org.apache.log4j.Logger;
import org.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class UserDaoImpl extends BaseDao<User> implements UserDao<User>{
    private static final String HQL_SELECT_CLIENT = "FROM User WHERE name= :name";
    private final static String HQL_ADMIN_OR_USER = "FROM User WHERE name= :login";
    private static final String HQL_SELECT_MENU = "FROM Menu WHERE menu_id= :id";
    private static final String HQL_SELECT_ALL_ORDERS = "FROM Order WHERE clientId= :id";
    private static final String HQL_SELECT_USERS_WITH_FILTER_NAME_ASC = "FROM User WHERE tableNumber BETWEEN :minTableNumber AND :maxTableNumber ORDER BY name ASC";
    private static final String HQL_SELECT_USERS_WITH_FILTER_NAME_DESC = "FROM User WHERE tableNumber BETWEEN :minTableNumber AND :maxTableNumber ORDER BY name DESC";
    private static final String HQL_SELECT_USERS_WITH_FILTER_TABLE_ASC = "FROM User WHERE tableNumber BETWEEN :minTableNumber AND :maxTableNumber ORDER BY tableNumber ASC";
    private static final String HQL_SELECT_USERS_WITH_FILTER_TABLE_DESC = "FROM User WHERE tableNumber BETWEEN :minTableNumber AND :maxTableNumber ORDER BY tableNumber DESC";
    private static final String HQL_SELECT_USERS_WITH_FILTER = "FROM User WHERE tableNumber BETWEEN :minTableNumber AND :maxTableNumber";
    private final Logger log = Logger.getLogger(UserDaoImpl.class);

    UserDao userDao;


    @Autowired
    public UserDaoImpl (SessionFactory sessionFactory){
        super(sessionFactory);
    }

    @Override
    public User findByLogin(String login) throws DaoException {
        User user;
        try {
            Query query = getSession().createQuery(HQL_SELECT_CLIENT);
            query.setParameter("name", login);
            user = (User) query.uniqueResult();
            log.info(user);
        } catch (HibernateException e) {
            log.info("Unable to login. Error in DAO");
            throw new DaoException(e);
        }
        return user;
    }

    @Override
    public int countOrder(User user) throws DaoException {
        int sum = 0;
        List<Order> orders;
        try {
            Query query = getSession().createQuery(HQL_SELECT_ALL_ORDERS);
            query.setParameter("id", user.getId());
            orders = query.list();
            List<Menu> menus;
            for (Order order : orders) {
                query = getSession().createQuery(HQL_SELECT_MENU);
                query.setParameter("id", order.getMenuId());
                menus = query.list();
                for (int i = 0; i < menus.size(); i++) {
                    sum += menus.get(i).getPrice();
                }
            }

            log.info(user + "order create.");
        } catch (HibernateException e) {
            log.info("Error in count order");
            throw new DaoException(e);
        }
        return sum;
    }

    @Override
    public String checkLoginAdmin(String enterLogin) throws DaoException {
        User user;
        Integer userName = 2;
        Integer administrator = 1;
        String loginStatus = "guest";
        try {
            Query query = getSession().createQuery(HQL_ADMIN_OR_USER);
            query.setParameter("login", enterLogin);
            user = (User) query.uniqueResult();
            if (user != null) {
                if (user.getName().equals(enterLogin)
                        && user.getRoleId().equals(administrator)) {
                    loginStatus = "administrator";
                } else if (user.getName().equals(enterLogin)
                        && user.getRoleId().equals(userName)) {
                    loginStatus = "user";
                }
                log.info(user);
            }
        } catch (HibernateException e) {
            log.error("Unable to login. Error in DAO");
            throw new DaoException(e);
        }
        return loginStatus;
    }



    public List<User> findAll(int recordsPerPage, int currentPage, Integer minTableNumber, Integer maxTableNumber, String DESC) throws DaoException {
        List<User> results;
        if (maxTableNumber == null){
            List<User> users = userDao.findAll();
            int max = 0;
            for (User user:users){
                if(user.getTableNumber()>max){
                    max = user.getTableNumber();
                    maxTableNumber = user.getTableNumber();
                }
            }
        }
        try {
            Session session = getSession();
            Query query;
            switch (DESC){
                case "nameUp":
                    query = session.createQuery(HQL_SELECT_USERS_WITH_FILTER_NAME_DESC);
                    break;
                case "nameDown":
                    query = session.createQuery(HQL_SELECT_USERS_WITH_FILTER_NAME_ASC);
                    break;
                case "tableUp":
                    query = session.createQuery(HQL_SELECT_USERS_WITH_FILTER_TABLE_DESC);
                    break;
                case "tableDown":
                    query = session.createQuery(HQL_SELECT_USERS_WITH_FILTER_TABLE_ASC);
                    break;
                default:
                    query = session.createQuery(HQL_SELECT_USERS_WITH_FILTER);
                    break;
            }

            query.setParameter("minTableNumber", minTableNumber);
            query.setParameter("maxTableNumber", maxTableNumber);
            query.setFirstResult((currentPage - 1) * recordsPerPage);
            query.setMaxResults(recordsPerPage);
            results = query.list();
        } catch (HibernateException e) {
            log.error("Error in DAO " + e);
            throw new DaoException(e);
        }finally {

        }
        return results;
    }



    public int getNumberPageWithFilter(Integer minTableNumber, Integer maxTableNumber) throws DaoException {
        int amount;
        try {
            Session session = getSession();
            Query query = session.createQuery(HQL_SELECT_USERS_WITH_FILTER);
            query.setParameter("minTableNumber", minTableNumber);
            query.setParameter("maxTableNumber", maxTableNumber);
            List results = query.list();
            amount = results.size();
            log.info(amount);
        } catch (HibernateException e) {
            log.error("Unable to get number of records. Error in DAO");
            throw new DaoException(e);
        }
        return amount;
    }
}
