package com.shtyka.dao.daoImpl;

import com.shtyka.dao.MenuDao;
import com.shtyka.dao.OrderDao;
import com.shtyka.dao.UserDao;
import com.shtyka.entity.Menu;
import com.shtyka.entity.Order;
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

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;

@Rollback
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/testContext.xml")
@Transactional(transactionManager = "txManager", propagation = Propagation.SUPPORTS)
public class OrderDaoImplTest {
	Order order = new Order();
	User user = new User();
	Menu menu = new Menu();

	@Autowired
	private SessionFactory sessionFactory;

    @Autowired
    private OrderDao orderDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private MenuDao menuDao;

    @Before
    public void setUp() throws Exception {
		List<Menu> menus = new ArrayList<>();
		user.setName("Michel");
		user.setRoleId(2);
		user.setTableNumber(8);
		user.setPassword("2222");
		userDao.saveOrUpdate(user);
		order.setMenus(menus);
		order.setStatusOrder("READY");
		order.setClientId(user.getId());
		orderDao.saveOrUpdate(order);
		menu.setMealName("Draniki");
		menu.setNameen("Драники");
		menu.setOrder(order);
		menu.setPrice(100);
		menu.setWeight(200);
		menuDao.saveOrUpdate(menu);
		sessionFactory.getCurrentSession().flush();
    }

    @After
    public void close() throws Exception {
		menuDao.delete(menu.getMenuId());
		orderDao.delete(order.getOrderId());
		userDao.delete(user.getId());
		sessionFactory.getCurrentSession().flush();
    }

    @Test
	public void findClientOrder() throws Exception {
        List<Order> orders = orderDao.findClientOrder(user.getId());
		sessionFactory.getCurrentSession().flush();
		assertNotNull(orders);
		assertEquals(orders.get(0).getOrderId(), order.getOrderId());
    }
}