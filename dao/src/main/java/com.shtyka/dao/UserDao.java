package com.shtyka.dao;

import com.shtyka.dao.exceptions.DaoException;

import java.util.List;

public interface UserDao<User> extends Dao<User> {

	User findByLogin(String login) throws DaoException;

	/*
		 * create customer, orders and find it
		 * all in assigning attributes
		 * Since the client is a code shortened
		 * by 2 loop lines , and taken one client
		 */
	int countOrder(User user) throws DaoException;

	/*
			 * check the password and login role
			 * from the database to the similarity
			 * with the data entered by md5
			 */
	String checkLoginAdmin(String enterLogin) throws DaoException;

	int getNumberPageWithFilter(Integer minTableNumber, Integer maxTableNumber) throws DaoException;

	List<User> findAll(int recordsPerPage, int currentPage, Integer minTableNumber, Integer maxTableNumber, String DESC) throws DaoException;
}
