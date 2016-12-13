package com.shtyka.dao.daoImpl;

import com.shtyka.dao.MenuDao;
import com.shtyka.entity.Menu;
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
import static org.junit.Assert.assertTrue;

@Rollback
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/testContext.xml")
@Transactional(transactionManager = "txManager", propagation = Propagation.SUPPORTS)
public class MenuDaoImplTest {
	Menu menu = new Menu();
	private int recordsPerPage = 4;
	private int currentPage = 1;
	private Integer minPrice = 0;
	private Integer maxPrice = 1000;
	private Integer minWeight = 0;
	private Integer maxWeight = 1000;

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private MenuDao menuDao;

	@Before
	public void setUp() throws Exception {
		menu.setMealName("Draniki");
		menu.setNameen("Драники");
//		menu.setOrder(order);
		menu.setPrice(100);
		menu.setWeight(200);
		menuDao.saveOrUpdate(menu);
		sessionFactory.getCurrentSession().flush();
	}

	@After
	public void close() throws Exception {
		menuDao.delete(menu.getMenuId());
		sessionFactory.getCurrentSession().flush();
	}

	@Test
	public void findEntityById() throws Exception {
		Menu menuTest = (Menu) menuDao.findEntityById((int) (long) (menu.getMenuId()));
		assertNotNull(menuTest);
		assertEquals(menuTest.getPrice(), menu.getPrice());
		assertEquals(menuTest.getWeight(), menu.getWeight());
		assertEquals(menuTest.getMealName(), menu.getMealName());
	}

	@Test
	public void findAll() throws Exception {
		List<Menu> menus = menuDao.findAll(recordsPerPage, currentPage);
		assertNotNull(menus);
		assertEquals(menus.get(0).getMealName(), menu.getMealName());
		assertTrue(menus.size() <= (currentPage));
	}

	@Test
	public void getNumberPageWithFilter() throws Exception {
		Long amount = menuDao.getNumberPageWithFilter(minPrice,maxPrice, minWeight, maxWeight);
		List<Menu> menus = menuDao.findAll();
		assertNotNull(amount);
		assertTrue(amount<=menus.size());
	}

	@Test
	public void findAllWithfilter() throws Exception {
		List<Menu> menus = menuDao.findAll(recordsPerPage, currentPage, minPrice,maxPrice, minWeight, maxWeight);
		assertNotNull(menus);
		assertEquals(menus.get(0).getMealName(), menu.getMealName());
		assertTrue(menus.size() <= (currentPage));
		assertTrue(menus.get(0).getPrice() <= (maxPrice));
		assertTrue(menus.get(0).getWeight() >= (minWeight));
	}
}