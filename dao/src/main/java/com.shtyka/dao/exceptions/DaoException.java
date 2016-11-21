package com.shtyka.dao.exceptions;

import org.hibernate.HibernateException;

public class DaoException extends Exception {
    public DaoException(HibernateException e) {}
    public DaoException(String message){
        super(message);
    }
}
