package com.shtyka.dao.daoImpl;

import com.shtyka.dao.BaseDao;
import com.shtyka.dao.MenuDao;
import com.shtyka.dao.exceptions.DaoException;
import com.shtyka.entity.Menu;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Projections;

import java.util.List;

public class MenuDaoImpl extends BaseDao<Menu> implements MenuDao<Menu> {
    private static Logger log = Logger.getLogger(MenuDaoImpl.class);
    private static final String HQL_SELECT_MENU = "from Menu where menu_id= :id";
    private static MenuDaoImpl menuDao;


    public static synchronized MenuDaoImpl getMenuDaoImpl(){
        if (menuDao == null) {
            menuDao = new MenuDaoImpl();
        }
        return menuDao;
    }

    public Menu findEntityById(int id) throws DaoException {
        Menu menu;
        try {
            Query query = util.getSession().createQuery(HQL_SELECT_MENU);
            query.setParameter("id", id);
            menu = (Menu) query.uniqueResult();
            log.info(menu);
        }catch (HibernateException e){
            log.info("Error in DAO findEntityById");
            throw new DaoException(e);
        }
        return menu;
    }

    public List<Menu> findAll(int recordsPerPage, int currentPage) throws DaoException {
        List<Menu> results;
        try {
            Criteria criteria = util.getSession().createCriteria(Menu.class);
            criteria.setFirstResult((currentPage - 1) * recordsPerPage);
            criteria.setMaxResults(recordsPerPage);
            results = criteria.list();
        } catch (HibernateException e) {
            log.error("Error in DAO " + e);
            throw new DaoException(e);
        }
        return results;
    }

    public Long getAmount() throws DaoException {
        Long amount;
        try {
            Criteria criteria = util.getSession().createCriteria(Menu.class);
            criteria.setProjection(Projections.rowCount());
            criteria.setCacheable(true);
            amount = (Long) criteria.uniqueResult();
            log.info(amount);
        } catch (HibernateException e) {
            log.error("Unable to get number of records. Error in DAO");
            throw new DaoException(e);
        }
        return amount;
    }
}