package com.shtyka.dao;

import com.shtyka.entity.User;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


@Rollback
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/testContext.xml")
@Transactional(transactionManager = "txManager", propagation = Propagation.SUPPORTS)
public class BaseDaoTest {
	User user = new User();

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private UserDao userDao;


	@Before
	public void setUp() throws Exception {
		user.setName("Michel");
		user.setRoleId(2);
		user.setTableNumber(8);
		user.setPassword("2222");
		userDao.saveOrUpdate(user);
		sessionFactory.getCurrentSession().flush();
	}


	@After
	public void close() throws Exception {
		userDao.delete(user.getId());
		sessionFactory.getCurrentSession().flush();
	}

    @Test
    public void get() throws Exception {
		User userTest = (User) userDao.get(user.getId());
		assertNotNull(userTest);
		assertEquals(userTest.getClass(), user.getClass());
		assertEquals(userTest.getTableNumber(), user.getTableNumber());
		assertEquals(userTest.getId(), user.getId());
		assertEquals(userTest.getName(), user.getName());
		assertEquals(userTest.getRoleId(), user.getRoleId());
    }

    @Test
    public void findAll() throws Exception {
		userDao.saveOrUpdate(user);
		sessionFactory.getCurrentSession().flush();
        List <User>results = userDao.findAll();
        assertNotNull(results);
		assertEquals(results.get(0).getTableNumber(), user.getTableNumber());
    }
}