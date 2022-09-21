package mott.donald;

import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebDriverUtils {
	public static Logger logger = LogManager.getLogger(WebDriverUtils.class);
	public static WebDriver driver = null;

	public static void lauchBrowser() {

		System.out.println("Browser is " + (ApplicationConstants.BROWSER_TYPE));
		switch (ApplicationConstants.BROWSER_TYPE) {

		case "chrome":
			System.setProperty("webdriver.chrome.driver", "Driver/chromedriver.exe");
			driver = new ChromeDriver();
			break;
		case "firefox":
			System.setProperty("webdriver.gecko.driver", "Driver/geckodriver.exe");
			driver = new FirefoxDriver();
			break;
		case "msedge":
			System.setProperty("webdriver.edge.driver", "Driver/msedgedriver.exe");
			driver = new EdgeDriver();
			break;

		default:
			System.out.println("No driver found");

			break;
		}
		logger.info("Browser is :: " + ApplicationConstants.BROWSER_TYPE);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	}

	public boolean isElementPresent(By locator) {

		if (driver.findElements(locator).size() > 0) {
			return true;
		}

		return false;
	}
	
	public void openURL(String url) {
		lauchBrowser();
		driver.get(url);
		driver.manage().window().maximize();
	}

	public boolean EnterText(By by, String text) {
		try {
			if (isElementPresent(by)) {
				logger.info("Element located by "+by);
				driver.findElement(by).clear();
				driver.findElement(by).sendKeys(text);
				logger.info("Entered " + text + " in the filed " + by);
				return true;
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return false;
	}

	public boolean click(By by) {
		try {
			if (isElementPresent(by)) {
				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
				wait.until(ExpectedConditions.elementToBeClickable(by));
				logger.info("Element located by "+by);
				driver.findElement(by).click();
				logger.info("Clicked successfully link/button:: " + by);
				return true;
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return false;
	}

}
