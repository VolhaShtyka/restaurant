package com.shtyka.services;

import com.shtyka.dao.BaseDao;
import com.shtyka.dao.exceptions.DaoException;
import com.shtyka.services.exceptions.ServiceException;
import com.shtyka.util.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.Serializable;

public class BaseService<T> implements IService<T> {
    protected final String TRANSACTION_SUCCESS = "Transaction is completed successfully";
    protected final String TRANSACTION_FAIL = "Transaction failed. Error in service.";
    protected static HibernateUtil util = HibernateUtil.getHibernateUtil();
    private final Logger log = Logger.getLogger(BaseService.class);
    private BaseDao baseDao;

    public void delete(Serializable id) throws ServiceException {

    }

    public void saveOrUpdate(T t) throws ServiceException{
        Session session = util.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            baseDao.saveOrUpdate(t);
            transaction.commit();
            log.info(TRANSACTION_SUCCESS);
        } catch (DaoException e) {
            transaction.rollback();
            log.error(TRANSACTION_FAIL);
            throw new ServiceException(e.getMessage());
        }
    }
    public T get(Serializable id) throws ServiceException{
        T t;
        Session session = util.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            t = (T)baseDao.get(id);
            transaction.commit();
            log.info(TRANSACTION_SUCCESS);
        } catch (DaoException e) {
            transaction.rollback();
            log.error(TRANSACTION_FAIL);
            throw new ServiceException(e.getMessage());
        }
        return t;
    }

//    public List findAll() throws ServiceException {
//        List<User> entitys = null;
//        try {
//            if(getPersistentClass().equals(UserDaoImpl.class)){
//
//                entitys = UserDaoImpl.getUserDaoImpl().findAll();
//            }
//            log.info(entitys);
//            log.info(TRANSACTION_SUCCESS);
//        } catch (DaoException e) {
//            log.error(TRANSACTION_FAIL, e);
//            throw new ServiceException(TRANSACTION_FAIL, e);
//        }
//        return entitys;
//    }
}
