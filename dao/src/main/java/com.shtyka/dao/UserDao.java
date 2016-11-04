package com.shtyka.dao;

import org.apache.log4j.Logger;
import org.hibernate.Transaction;

public class UserDao<User> extends BaseDao <User>{
    private static Logger log = Logger.getLogger(UserDao.class);
    private Transaction transaction = null;

}
