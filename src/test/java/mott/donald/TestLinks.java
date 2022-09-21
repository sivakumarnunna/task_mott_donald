package mott.donald;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import org.testng.Assert;

public class TestLinks extends WebDriverUtils {

	public static String ACCEPT_COOKIES_LINK = null;
	public static String ACCEPT_LANGUAGE_LINK = null;
	public static String SEARCH_TEXTFIELD = null;
	public static String SEARCH_BUTTON = null;
	public static String VIEW_JOB_BUTTON = null;
	public static String MESSAGE_NO_RESULT = null;

	static Properties properties = new Properties();
	protected static Logger logger = LogManager.getLogger(TestLinks.class);

	static {
		try {
			properties.load(new FileInputStream("src\\test\\resources\\properties\\locators.properties"));

			ACCEPT_COOKIES_LINK = properties.getProperty("lnkacceptcoockies");
			ACCEPT_LANGUAGE_LINK = properties.getProperty("lnkacceptlanguage");
			SEARCH_TEXTFIELD = properties.getProperty("tfsearchjob");
			SEARCH_BUTTON = properties.getProperty("btnsearchicon");
			VIEW_JOB_BUTTON = properties.getProperty("lnkviewjob");
			MESSAGE_NO_RESULT = properties.getProperty("msgnoresults");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void acceptCookies() {

		click(By.id(ACCEPT_COOKIES_LINK));
		click(By.linkText(ACCEPT_LANGUAGE_LINK));
	}

	public boolean veiwJobDescription(String jobTitle) {

		openURL(ApplicationConstants.BASE_URL + ApplicationConstants.CAREER_SEARCH_PATH);
		acceptCookies();

		EnterText(By.xpath(SEARCH_TEXTFIELD), jobTitle);
		// driver.findElement(By.xpath(SEARCH_TEXTFIELD)).sendKeys(Keys.ENTER);
		click(By.xpath(SEARCH_BUTTON));

		if (isElementPresent(By.id(MESSAGE_NO_RESULT))) {
			logger.info("no jobs found with the given keyword " + jobTitle);
		}
		click(By.xpath(VIEW_JOB_BUTTON));
		Assert.assertEquals(driver.findElement(By.xpath("//h3[normalize-space()='Job Description']")).getText(),
				"Job Description");
		return true;
	}

	public void verifyLinks(String pageurl) {

		openURL(pageurl);
		acceptCookies();
		List<WebElement> lsw = driver.findElements(By.tagName("a"));
		System.out.println("total links.." + lsw.size());
		String url = null;
		HttpURLConnection huc = null;
		int respCode;
		for (WebElement wb : lsw) {
			java.net.CookieManager cm = new java.net.CookieManager();
			java.net.CookieHandler.setDefault(cm);
			url = wb.getAttribute("href");
			if (url == null || url.isEmpty()) {
				logger.info(url + "  URL is either not configured for anchor tag or it is empty");
				continue;
			}
			try {
				huc = (HttpURLConnection) (new URL(url).openConnection());
				huc.setRequestMethod("HEAD");
				huc.connect();
				respCode = huc.getResponseCode();
				if (respCode >= 400) {
					logger.info(url + " is a broken link");
				} else {
					logger.info(url + " is a valid link");
				}
			} catch (MalformedURLException e) {
			} catch (IOException e) {
			}
		}

	}

	
	public void verifyLinks() throws InterruptedException {
		verifyLinks(ApplicationConstants.BASE_URL);
	}

	
	public void testSearchJob() throws InterruptedException {

		veiwJobDescription("Lead estimator");
	}

}
