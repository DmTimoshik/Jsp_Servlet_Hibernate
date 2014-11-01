

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Statement;

import hibernate.HiberRoleDao;
import hibernate.HibernateUtil;
import hibernate.PropertiesParser;
import hibernate.Role;

import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.h2.jdbcx.JdbcConnectionPool;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class HiberRoleDaoTest extends DBTestCase {
	HiberRoleDao roleDao = new HiberRoleDao();
	
	public HiberRoleDaoTest() throws Exception {
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
	public void testCreate() throws Exception {
		IDataSet databaseDataSet = getConnection().createDataSet();
		ITable actualTable = databaseDataSet.getTable("ROLE");
		int i = actualTable.getRowCount();
		Role role2 = new Role();
		role2.setId(5L);
		role2.setName("guest");
		roleDao.create(role2);
		databaseDataSet = getConnection().createDataSet();
		actualTable = databaseDataSet.getTable("ROLE");
		assertEquals(i + 1, actualTable.getRowCount());
	}

	@Test
	public void testfindName() throws Exception {
		Role res = roleDao.findByName("admin");
		assertNotNull(res);
		assertEquals(res.getName(), "admin");
		res = roleDao.findByName("guest");
		assertNull(res);
	}

	@Test
	public void testRemove() throws Exception {
		IDataSet databaseDataSet = getConnection().createDataSet();
		ITable actualTable = databaseDataSet.getTable("ROLE");
		int i = actualTable.getRowCount();
		Role role = new Role();
		role.setId(3L);
		role.setName("super");
		roleDao.remove(role);
		databaseDataSet = getConnection().createDataSet();
		actualTable = databaseDataSet.getTable("ROLE");
		assertEquals(i-1, actualTable.getRowCount());
	}

	@Test
	public void testUpdate() throws Exception {
		Role role2 = new Role();
		role2.setId(2L);
		role2.setName("guest");
		roleDao.update(role2);
		IDataSet databaseDataSet = getConnection().createDataSet();
		ITable actualTable = databaseDataSet.getTable("ROLE");
		assertEquals("guest", actualTable.getValue(1, "NAME"));
	}

}
