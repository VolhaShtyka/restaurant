package com.shtyka.services;

import com.shtyka.services.exceptions.ServiceException;
import com.shtyka.util.HibernateUtil;
import org.apache.log4j.Logger;

import java.io.Serializable;

public abstract class BaseService<T> implements IService<T> {
    protected final String TRANSACTION_SUCCESS = "Transaction is completed successfully";
    protected final String TRANSACTION_FAIL = "Transaction failed. Error in service.";
    protected static HibernateUtil util = HibernateUtil.getHibernateUtil();
    private final Logger log = Logger.getLogger(BaseService.class);
    public void delete(T t) throws ServiceException{}
    public void saveOrUpdate(T t) throws ServiceException{}



//        Session session = util.getSession();
//        Transaction transaction = null;
//        try {
//            transaction = session.beginTransaction();
//            t = t.s
//            transaction.commit();
//            log.info(TRANSACTION_SUCCESS);
//        } catch (DaoException e) {
//            transaction.rollback();
//            log.error(TRANSACTION_FAIL);
//            throw new ServiceException(e.getMessage());
//        }
//    }
    public abstract T get(Serializable id) throws ServiceException;
//    private BaseDao<T> baseDao;
//
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
//
//    private Class getPersistentClass() {
//        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
//    }
}
