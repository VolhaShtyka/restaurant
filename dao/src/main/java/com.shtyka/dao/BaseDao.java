package com.shtyka.dao;
import com.shtyka.dao.exceptions.DaoException;
import com.shtyka.util.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

 abstract class BaseDao<T> implements Dao <T>{
    private static Logger log = Logger.getLogger(BaseDao.class);
    protected static HibernateUtil util = HibernateUtil.getHibernateUtil();
//     private Class pClass;
//     protected BaseDao(Class pClass) {
//         this.pClass = pClass;
//     }


    public void saveOrUpdate(T t) throws DaoException {
        try{
            util.getSession().saveOrUpdate(t);
            log.info("saveOrUpdate(t): " + t);
        }catch (HibernateException e){
            log.error("Error save or update " + getPersistentClass() + " in Dao " + e);
            throw new DaoException(e);
        }
    }
    public T get(Serializable id) throws DaoException {
        T t;
        try {
            t = (T) util.getSession().get(getPersistentClass(), id);
            log.info("get clazz:" + t);
        } catch (HibernateException e) {
            log.error("Error get " + getPersistentClass() + " in Dao" + e);
            throw new DaoException(e);
        }
        return t;
    }

    public void delete(Serializable id) throws DaoException {
        try {
            util.getSession().delete(id);
            log.info("Delete:" + id);
        } catch (HibernateException e) {
            log.error("Error delete " + getPersistentClass() + " in Dao" + e);
            throw new DaoException(e);
        }
    }
        public List findAll() throws DaoException {
            List results;
            try {
                Criteria criteria = util.getSession().createCriteria(getPersistentClass());
                results = criteria.list();
            } catch (HibernateException e) {
                log.error("Error in DAO " + e);
                throw new DaoException(e);
            }
            return results;
        }

    private Class getPersistentClass() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
}

