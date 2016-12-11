package com.shtyka.services;

import com.shtyka.dao.Dao;
import com.shtyka.dao.exceptions.DaoException;
import com.shtyka.services.exceptions.ServiceException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class BaseService<T> implements IService<T> {
    protected final String TRANSACTION_SUCCESS = "Transaction is completed successfully";
    protected final String TRANSACTION_FAIL = "Transaction failed. Error in service.";
    private final Logger log = Logger.getLogger(BaseService.class);

    private Dao<T> baseDao;

    public BaseService() {
    }
    
    @Autowired
    public BaseService(Dao<T> baseDao) {
        this.baseDao = baseDao;
    }


    public void delete(Serializable id) throws ServiceException {

    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveOrUpdate(T t) throws ServiceException{
        try {
            baseDao.saveOrUpdate(t);
            log.info(TRANSACTION_SUCCESS);
        } catch (DaoException e) {
            log.error(TRANSACTION_FAIL);
            throw new ServiceException(e.getMessage());
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public T get(Serializable id) throws ServiceException{
        T t;
        try {
            t = (T)baseDao.get(id);
            log.info(TRANSACTION_SUCCESS);
        } catch (DaoException e) {
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
