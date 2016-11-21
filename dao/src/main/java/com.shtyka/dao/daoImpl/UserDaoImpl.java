package com.shtyka.dao.daoImpl;

import com.shtyka.dao.UserDao;
import com.shtyka.dao.exceptions.DaoException;
import com.shtyka.entity.Menu;
import com.shtyka.entity.Order;
import com.shtyka.entity.User;
import com.shtyka.util.LoginMd5;
import com.shtyka.util.ManagerHQL;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

public class UserDaoImpl extends UserDao<User> {
    private static final String HQL_SELECT_CLIENT = ManagerHQL.getProperty("HQL_SELECT_CLIENT");
    private final static String HQL_ADMIN_OR_USER = ManagerHQL.getProperty("HQL_ADMIN_OR_USER");
    private static final String HQL_SELECT_MENU = ManagerHQL.getProperty("HQL_SELECT_MENU");
    private static final String HQL_SELECT_ALL_ORDERS = ManagerHQL.getProperty("HQL_SELECT_ALL_ORDERS");
    private final Logger log = Logger.getLogger(UserDaoImpl.class);
    private static UserDaoImpl userDao;
    public static synchronized UserDaoImpl getUserDaoImpl() {
        if (userDao == null) {
            userDao = new UserDaoImpl();
        }
        return userDao;
    }

    public User findByLogin(String login) throws DaoException {
        User user;
        try {
            Query query = util.getSession().createQuery(HQL_SELECT_CLIENT);
            query.setParameter("name", login);
            user = (User) query.uniqueResult();
            log.info(user);
        }catch (HibernateException e){
            e.printStackTrace();
            log.info("Unable to login. Error in DAO");
            throw new DaoException(e);
        }
        return user;
    }


    public int countOrder(User user) throws DaoException {
        int sum = 0;
        List<Order> orders;
        try{
            Query query = util.getSession().createQuery(HQL_SELECT_ALL_ORDERS);
            query.setParameter("id", user.getId());
            orders = query.list();
            List<Menu> menus;
            for (Order order: orders){
                query = util.getSession().createQuery(HQL_SELECT_MENU);
                query.setParameter("id", order.getMenuId());
                menus = query.list();
                for (int i = 0; i<menus.size();i++) {
                    sum += menus.get(i).getPrice();
                }
            }

                log.info(user + "order create.");
        }catch (HibernateException e){
            e.printStackTrace();
            log.info("Error in count order");
            throw new DaoException(e);
        }
        return sum;
    }

    public String checkLoginAdmin(String enterLogin, String enterPassword) throws DaoException {
        User user;
        Integer userName = 2;
        Integer administrator = 1;
        String loginStatus = "guest";
        try {
            Session session = util.getSession();
            Query query = session.createQuery(HQL_ADMIN_OR_USER);
            query.setParameter("login", enterLogin);
            query.setParameter("password", enterPassword);
            user = (User) query.uniqueResult();
            if (user != null) {
                if (user.getName().equals(enterLogin)
                        && LoginMd5.md5Custom(user.getPassword()).equals(LoginMd5.md5Custom(enterPassword))
                        && user.getRoleId().equals(administrator)) {
                    loginStatus = "administrator";
                } else if (user.getName().equals(enterLogin)
                        && LoginMd5.md5Custom(user.getPassword()).equals(LoginMd5.md5Custom(enterPassword))
                        && user.getRoleId().equals(userName)) {
                    loginStatus = "user";
                }
                log.info(user);
            }
            } catch(HibernateException e){
                e.printStackTrace();
                log.error("Unable to login. Error in DAO");
                throw new DaoException(e);
            }

        return loginStatus;
    }
}
