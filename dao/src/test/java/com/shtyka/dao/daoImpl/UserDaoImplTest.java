package com.shtyka.dao.daoImpl;


import com.shtyka.dao.UserDao;
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

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

@Rollback
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/test-context.xml")
@Transactional(transactionManager = "txManager", propagation = Propagation.SUPPORTS)
public class UserDaoImplTest {
	User user = new User();
	private String loginAdmin = "administrator";
	private String loginUser = "user";

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
    public void findByLogin() throws Exception {
		User userTest = (User) userDao.findByLogin(user.getName());
		assertNotNull(userTest);
        assertEquals(user.getName(), userTest.getName());
        assertEquals(user.getId(), userTest.getId());
        assertEquals(user.getTableNumber(), userTest.getTableNumber());
    }

    @Test
    public void findAll() throws Exception {
        List<User> usersTest = userDao.findAll();
        assertNotNull(usersTest);
		assertEquals(usersTest.get(0).getTableNumber(),user.getTableNumber());
	}


    @Test
    public void countOrder() throws Exception {
        int sumTest = userDao.countOrder(user);
		assertNotNull(sumTest);
    }

    @Test
    public void checkLoginAdmin() throws Exception {
        String status = userDao.checkLoginAdmin(user.getName());
        assertNotNull(status);
        assertTrue(loginAdmin==status||loginUser==status);
    }
}