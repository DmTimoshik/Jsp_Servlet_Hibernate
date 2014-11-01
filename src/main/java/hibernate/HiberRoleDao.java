package hibernate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class HiberRoleDao extends AbsctractHiberDao implements RoleDao {
	private static final Log log = LogFactory.getLog(HiberUserDao.class);

	public HiberRoleDao() {
	}

	public void create(Role role) {
		Session session = null;
		try {
			session = createSession();
			session.beginTransaction();
			session.save(role);
			session.getTransaction().commit();
		} catch (Exception e) {
			log.error("Can't update user - ", e);
			session.getTransaction().rollback();
			throw new RuntimeException(e);
		} finally {
			session.close();
		}
	}

	public void update(Role role) {
		Session session = null;
		try {
			session = createSession();
			session.beginTransaction();
			session.update(role);
			session.getTransaction().commit();
		} catch (Exception e) {
			log.error("Can't update user - ", e);
			session.getTransaction().rollback();
			throw new RuntimeException(e);
		} finally {
			session.close();
		}
	}

	public void remove(Role role) {
		Session session = null;
		try {
			session = createSession();
			session.beginTransaction();
			session.delete(role);
			session.getTransaction().commit();
		} catch (Exception e) {
			log.error("Can't update user - ", e);
			session.getTransaction().rollback();
			throw new RuntimeException(e);
		} finally {
			session.close();
		}
	}

	public Role findByName(String string) {
		Session session = null;
		Role role = null;
		try {
			session = createSession();
			session.beginTransaction();
			role = (Role) session.createCriteria(Role.class)
					.add(Restrictions.eq("name", string)).uniqueResult();
			session.getTransaction().commit();
		} catch (Exception e) {
			log.error("Can't update user - ", e);
			session.getTransaction().rollback();
			throw new RuntimeException(e);
		} finally {
			session.close();
		}

		return role;
	}
	
	public static void main(){
		
	}
	
}
