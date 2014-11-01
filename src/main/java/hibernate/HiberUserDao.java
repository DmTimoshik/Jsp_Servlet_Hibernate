package hibernate;



import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class HiberUserDao extends AbsctractHiberDao implements UserDao {
	private static final Log log = LogFactory.getLog(HiberUserDao.class);

	public HiberUserDao() {
		
	}

	public void create(User user) {
		Session session = null;
		try {
			session = createSession();
			session.beginTransaction();
			session.save(user);
			session.getTransaction().commit();
		} catch (Exception e) {
			log.error("Can't update user - ", e);
			session.getTransaction().rollback();
			throw new RuntimeException(e);
		} finally {
			session.close();
		}
	}

	public void update(User user) {
		Session session = null;
		try {
			session = createSession();
			session.beginTransaction();
			session.update(user);
			session.getTransaction().commit();
		} catch (Exception e) {
			log.error("Can't update user - ", e);
			session.getTransaction().rollback();
			throw new RuntimeException(e);
		} finally {
			session.close();
		}
	}

	public void remove(User user) {
		Session session = null;
		try {
			session = createSession();
			session.beginTransaction();
			session.delete(user);
			session.getTransaction().commit();
		} catch (Exception e) {
			log.error("Can't update user - ", e);
			session.getTransaction().rollback();
			throw new RuntimeException(e);
		} finally {
			session.close();
		}

	}

	public List<User> findAll() {
		Session session = null;
		List<User> users = new ArrayList<User>();
		try {
			session = createSession();
			session.beginTransaction();
			users = session.createCriteria(User.class).list();
			session.getTransaction().commit();
		} catch (Exception e) {
			log.error("Can't update user - ", e);
			session.getTransaction().rollback();
			throw new RuntimeException(e);
		} finally {
			session.close();
		}
		return users;
	}

	public User findByLogin(String login) {
		Session session = null;
		User user = null;
		try {
			session = createSession();
			session.beginTransaction();
			user = (User) session.createCriteria(User.class)
					.add(Restrictions.eq("login", login)).uniqueResult();
			session.getTransaction().commit();
		} catch (Exception e) {
			log.error("Can't update user - ", e);
			session.getTransaction().rollback();
			throw new RuntimeException(e);
		} finally {
			session.close();
		}
		return user;
	}

	public User findByEmail(String email) {
		Session session = null;
		User user = null;
		try {
			session = createSession();
			session.beginTransaction();
			user = (User) session.createCriteria(User.class)
					.add(Restrictions.eq("email", email)).uniqueResult();
			session.getTransaction().commit();
		} catch (Exception e) {
			log.error("Can't update user - ", e);
			session.getTransaction().rollback();
			throw new RuntimeException(e);
		} finally {
			session.close();
		}
		return user;
	}

	
}
