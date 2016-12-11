package com.shtyka.dao;

import com.shtyka.dao.exceptions.DaoException;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

@Repository
public class BaseDao<T> implements Dao<T> {
    private static Logger log = Logger.getLogger(BaseDao.class);
    private SessionFactory sessionFactory;
    BaseDao(){}

    @Autowired
    public BaseDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    public Session getSession(){
        return sessionFactory.getCurrentSession();
    }

    @Override
    public void saveOrUpdate(T t) throws DaoException {
        try {
            getSession().saveOrUpdate(t);
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
            t = (T) getSession().get(getPersistentClass(), id);
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
            T t = (T) getSession().get(getPersistentClass(), id);
            getSession().delete(t);
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
            Criteria criteria = getSession().createCriteria(getPersistentClass());
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

