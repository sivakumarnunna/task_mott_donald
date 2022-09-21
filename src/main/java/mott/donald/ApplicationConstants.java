package mott.donald;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 * This class is for all constants used across framework. It will read all the
 * variables from "resources\\properties\\applicationUnderTest.properties" file
 * and assgin those values to the constants here.
 */

public class ApplicationConstants {

	static Properties properties = new Properties();
	static Logger logger = LogManager.getLogger(ApplicationConstants.class);

	

	public static String BASE_URL;
	public static String CAREER_SEARCH_PATH;
	public static String BROWSER_TYPE;



	static {
		try {
			properties.load(new FileInputStream("src\\test\\resources\\properties\\applicationundertest.properties"));
			BASE_URL = properties.getProperty("baseUrl");
			BROWSER_TYPE= properties.getProperty("browsertype");
			CAREER_SEARCH_PATH= properties.getProperty("careersearchpath");

			logger.info("Base URL :::" + BASE_URL);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
