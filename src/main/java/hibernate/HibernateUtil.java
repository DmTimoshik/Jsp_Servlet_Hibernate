package hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class HibernateUtil {
	private static SessionFactory sessionFactory;
	private static ServiceRegistry serviceRegistry;
	

	/**
	 * Build the service registry accounting Create a link SessionFactory
	 * */
	private static SessionFactory configSessionFactoty() {
		
		/**
		 * A new Configuration will use the properties specified in
		 * hibernate.properties by default.
		 */
		Configuration config = new Configuration();

		/**
		 * Use the mappings and properties specified in an application resource
		 * named hibernate.cfg.xml .
		 */
		config.configure();

		/**
		 * Build the service registry accounting for all settings and service
		 * initiators and services.
		 */
		serviceRegistry = new ServiceRegistryBuilder().applySettings(
				config.getProperties()).buildServiceRegistry();

		/**
		 * Create a link SessionFactory using the properties and mappings in
		 * this configuration.
		 */
		sessionFactory = config.buildSessionFactory(serviceRegistry);

		
		return sessionFactory;
	}

	/**
	 * Get the sessionFactory a private method configSessionFactoty()
	 * */
	public static SessionFactory getSessionFactory() {
		if(sessionFactory == null){
			return configSessionFactoty();
		}
		return sessionFactory;
	}

}
