package com.shtyka.dao;

import com.shtyka.dao.exceptions.DaoException;
import com.shtyka.util.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public class BaseDao<T> implements Dao<T> {
    private static Logger log = Logger.getLogger(BaseDao.class);
    protected static HibernateUtil util = HibernateUtil.getHibernateUtil();
    private Session session;

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    public BaseDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        this.session = sessionFactory.openSession();
    }


//    public static synchronized BaseDao getBaseDao() {
//        if (baseDao == null) {
//            baseDao = new BaseDao();
//        }
//        return baseDao;
//    }

    @Override
    public void saveOrUpdate(T t) throws DaoException {
        try {
            session.saveOrUpdate(t);
            log.info("saveOrUpdate(t): " + t);
        } catch (HibernateException e) {
            log.error("Error save or update " + getPersistentClass() + " in Dao " + e);
            throw new DaoException(e);
        }
    }

    @Override
    public T get(Serializable id) throws DaoException {
        T t;
        try {
            t = (T) session.get(getPersistentClass(), id);
            log.info("get clazz:" + t);
        } catch (HibernateException e) {
            log.error("Error get " + getPersistentClass() + " in Dao" + e);
            throw new DaoException(e);
        }
        return t;
    }

    @Override
    public void delete(Serializable id) throws DaoException {
        try {
            T t = (T) session.get(getPersistentClass(), id);
            util.getSession().delete(t);
            log.info("Delete:" + id);
        } catch (HibernateException e) {
            log.error("Error delete " + getPersistentClass() + " in Dao" + e);
            throw new DaoException(e);
        }
    }

    @Override
    public List findAll() throws DaoException {
        List results;
        try {
            Criteria criteria = session.createCriteria(getPersistentClass());
            results = criteria.list();
        } catch (HibernateException e) {
            log.error("Error in DAO " + e);
            throw new DaoException(e);
        }
        return results;
    }

    public Class getPersistentClass() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
}

