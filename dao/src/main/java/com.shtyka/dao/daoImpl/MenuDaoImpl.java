package com.shtyka.dao.daoImpl;

import com.shtyka.dao.BaseDao;
import com.shtyka.dao.MenuDao;
import com.shtyka.dao.exceptions.DaoException;
import com.shtyka.entity.Menu;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class MenuDaoImpl extends BaseDao<Menu> implements MenuDao<Menu> {
    private static Logger log = Logger.getLogger(MenuDaoImpl.class);
    private static final String HQL_SELECT_MENU = "FROM Menu WHERE menu_id= :id";
    private static final String HQL_SELECT_MENU_OF_PRICE = "FROM Menu WHERE price BETWEEN :minPrice AND :maxPrice";
    private static final String HQL_SELECT_MENU_OF_PRICE_AND_WEIGHT = "FROM Menu WHERE price BETWEEN :minPrice AND :maxPrice AND weight BETWEEN :minWeight AND :maxWeight";
    private static MenuDaoImpl menuDao;


    @Autowired
    public MenuDaoImpl (SessionFactory sessionFactory){
        super(sessionFactory);
    }
    @Override
    public Menu findEntityById(Integer id) throws DaoException {
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
    @Override
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

    @Override
    public List<Menu> findAll(int recordsPerPage, int currentPage, Integer minPrice, Integer maxPrice) throws DaoException {
        List<Menu> results;
        try {

            Criteria criteria = util.getSession().createCriteria(Menu.class);
            criteria.add(Restrictions.between("price", minPrice, maxPrice));
            criteria.setFirstResult((currentPage - 1) * recordsPerPage);
            criteria.setMaxResults(recordsPerPage);
            results = criteria.list();
        } catch (HibernateException e) {
            log.error("Error in DAO " + e);
            throw new DaoException(e);
        }
        return results;
    }

    @Override
    public List<Menu> findAll(int recordsPerPage, int currentPage, Integer minPrice, Integer maxPrice, Integer minWeight, Integer maxWeight) throws DaoException {
        List<Menu> results;
        try {

            Criteria criteria = util.getSession().createCriteria(Menu.class);
            criteria.setFirstResult((currentPage - 1) * recordsPerPage);
            criteria.setMaxResults(recordsPerPage);
            criteria.add(Restrictions.between("price", minPrice, maxPrice));
            criteria.add(Restrictions.between("weight", minWeight, maxWeight));

            results = criteria.list();
        } catch (HibernateException e) {
            log.error("Error in DAO " + e);
            throw new DaoException(e);
        }
        return results;
    }


    @Override
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

    @Override
    public Long getNumberPageWithFilter(Integer minPrice, Integer maxPrice, Integer minWeight, Integer maxWeight) throws DaoException {
        Long amount;
        try {
            Criteria criteria = util.getSession().createCriteria(Menu.class);
            criteria.add(Restrictions.between("price", minPrice, maxPrice));
            criteria.add(Restrictions.between("weight", minWeight, maxWeight));

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