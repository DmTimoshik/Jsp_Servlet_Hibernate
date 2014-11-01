package hibernate;

import org.hibernate.Session;

public abstract class AbsctractHiberDao {

	public Session createSession() {
		return HibernateUtil.getSessionFactory().openSession();
	}
}
