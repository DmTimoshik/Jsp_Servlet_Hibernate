

import java.io.InputStream;
import java.util.List;

import hibernate.HiberUserDao;
import hibernate.HibernateUtil;
import hibernate.PropertiesParser;
import hibernate.Role;
import hibernate.User;



import org.junit.Test;
import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;

public class HiberUserDaoTest extends DBTestCase {
	HiberUserDao userDao = new HiberUserDao();

	public HiberUserDaoTest() throws Exception {
		HibernateUtil.getSessionFactory().openSession();
		System.setProperty(
				PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS,
				PropertiesParser.getDriver());
		System.setProperty(
				PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL,
				PropertiesParser.getUrl());
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME,
				PropertiesParser.getLogin());
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD,
				PropertiesParser.getPass());

	}

	

	
	@Override
	protected IDataSet getDataSet() throws Exception {
		ClassLoader cl = PropertiesParser.class.getClassLoader();
		InputStream in = cl.getResourceAsStream("dataset.xml");
		return new FlatXmlDataSetBuilder().build(in);
	}

	@Test
	public void testfindAll() throws Exception {
		List<User> res = userDao.findAll();
		assertTrue(res != null);
		assertEquals(2, res.size());
	}

	@Test
	public void testCreate() throws Exception {
		IDataSet databaseDataSet = getConnection().createDataSet();
		ITable actualTable = databaseDataSet.getTable("USER");
		int i = actualTable.getRowCount();
		User user4 = new User();
		user4.setId(5L);
		user4.setLogin("log5");
		user4.setPassword("pas5");
		user4.setEmail("email5");
		user4.setRole(new Role(1L,"Admin"));
		userDao.create(user4);
		databaseDataSet = getConnection().createDataSet();
		actualTable = databaseDataSet.getTable("USER");
		assertEquals(i + 1, actualTable.getRowCount());
	}

	@Test
	public void testRemove() throws Exception {
		IDataSet databaseDataSet = getConnection().createDataSet();
		ITable actualTable = databaseDataSet.getTable("USER");
		int i = actualTable.getRowCount();
		User user2 = new User();
		user2.setId(1L);
		user2.setLogin("log5");
		user2.setPassword("pas5");
		user2.setEmail("email5");
		user2.setRole(new Role(5L,"super"));
		userDao.remove(user2);
		databaseDataSet = getConnection().createDataSet();
		actualTable = databaseDataSet.getTable("USER");
		assertEquals(i - 1, actualTable.getRowCount());
	}

	@Test
	public void testfindByLogin() throws Exception {
		User res = userDao.findByLogin("log1");
		assertNotNull(res);
		assertEquals(res.getLogin(), "log1");
	}

	@Test
	public void testfindByEmail() throws Exception {
		User res = userDao.findByEmail("mail2");
		assertNotNull(res);
		assertEquals(res.getEmail(), "mail2");
	}

	@Test
	public void testUpdate() throws Exception {
		User user2 = new User();
		user2.setId(1L);
		user2.setLogin("log5");
		user2.setPassword("pas5");
		user2.setEmail("mail5");
		try {
			userDao.update(user2);
			IDataSet databaseDataSet = getConnection().createDataSet();
			ITable actualTable = databaseDataSet.getTable("USER");
			assertEquals("log5", actualTable.getValue(0, "LOGIN"));
		} catch (Exception e) {
			fail("Error! role input argument of the method update is null or Id = 0 ");
		}
	}


	
}
