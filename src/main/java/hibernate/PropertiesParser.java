package hibernate;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PropertiesParser {
	private static Log log = LogFactory.getLog(PropertiesParser.class);
	private static final String propertiesFileName = "config.properties";
	private static Properties propertyFile;

	static {
		propertyFile = new Properties();
		ClassLoader cl = PropertiesParser.class.getClassLoader();
		InputStream in = cl.getResourceAsStream(propertiesFileName);

		try {
			propertyFile.load(in);
		} catch (IOException e) {
			log.error("Can't getProperties from '" + propertiesFileName + "'",
					e);
			throw new RuntimeException(e);
		}

	}

	public static String getDriver() {
		return propertyFile.getProperty("jdbc_driver_name");
	}

	public static String getUrl() {
		return propertyFile.getProperty("jdbc_url");
	}

	public static String getLogin() {
		return propertyFile.getProperty("jdbc_user");
	}

	public static String getPass() {
		return propertyFile.getProperty("jdbc_password");
	}

	public static String getDBUnitUrl() {
		return propertyFile.getProperty("jdbc_url");
	}

	public static Properties propertyFile() {
		return propertyFile;
	}

	public static void main(String[] args) {
		new PropertiesParser();
	}
}
