package com.shtyka.dao;

import com.shtyka.dao.exceptions.DaoException;

public abstract class UserDao<User> extends BaseDao <User>{
    public abstract User findByLogin(String login) throws DaoException;

    /*
		 * create customer, orders and find it
		 * all in assigning attributes
		 * Since the client is a code shortened
		 * by 2 loop lines , and taken one client
		 */
    public abstract int countOrder(User user) throws DaoException;

    /*
			 * check the password and login role
			 * from the database to the similarity
			 * with the data entered by md5
			 */
    public abstract String checkLoginAdmin(String enterLogin, String enterPassword) throws DaoException;
}
