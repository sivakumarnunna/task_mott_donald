package mott.donald.step;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import io.cucumber.java.After;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import mott.donald.TestLinks;

public class StepDefinitions extends TestLinks {

	String searchKeyword;

	public Set<String> getAllLinks() {
		Set<String> allLinks = new HashSet<String>();
		List<WebElement> lsw = driver.findElements(By.tagName("a"));
		String url = null;
		for (WebElement wb : lsw) {
			java.net.CookieManager cm = new java.net.CookieManager();
			java.net.CookieHandler.setDefault(cm);
			url = wb.getAttribute("href");
			allLinks.add(url);
		}
		logger.info("#############################################");
		logger.info("total links.." + allLinks.size());
		logger.info("#############################################");

		return allLinks;

	}

	@When("I open mott donald homepage {string}")
	public void i_open_mott_donald_homepage(String homepage) {
		openURL(homepage);
		acceptCookies();
	}

	@Then("I validate all the links")
	public Set<String> i_validate_all_the_links() {

		HttpURLConnection huc = null;
		Set<String> allLinks = getAllLinks();
		Set<String> brokenlinks = new HashSet<String>();
		int respCode;
		for (String link : allLinks) {
			if (link == null || link.isEmpty()) {
				logger.info(link + "  URL is either not configured for anchor tag or it is empty");
				continue;
			}
			try {
				huc = (HttpURLConnection) (new URL(link).openConnection());
				huc.setRequestMethod("HEAD");
				huc.connect();
				respCode = huc.getResponseCode();
				if (respCode >= 400) {
					logger.info(link + " is a broken link");
					brokenlinks.add(link);
				} else {
					logger.info(link + " is a valid link");
				}
			} catch (MalformedURLException e) {
			} catch (IOException e) {
			}
		}
		return brokenlinks;
	}

	@When("I open mott donald job search page {string}")
	public void i_open_mott_donald_job_search_page(String jobsearchpage) {
		openURL(jobsearchpage);
		acceptCookies();
	}

	@When("Enters the search keyword {string}")
	public void enters_the_search_keyword(String keyword) {
		searchKeyword = keyword;
		EnterText(By.xpath(SEARCH_TEXTFIELD), searchKeyword);
	}

	@When("clicks on search icon")
	public void clicks_on_search_icon() throws InterruptedException {
		// click(By.xpath(SEARCH_BUTTON));
		Thread.sleep(2000);
		driver.findElement(By.xpath(SEARCH_TEXTFIELD)).sendKeys(Keys.ENTER);

	}

	@Then("ViewJob button should be displayed")
	public void view_job_button_should_be_displayed() {
		Assert.assertEquals(isElementPresent(By.xpath(MESSAGE_NO_RESULT)), false);
	}

	@When("I clicked on ViewJob button")
	public void i_clicked_on_view_job_button() {
		click(By.xpath(VIEW_JOB_BUTTON));
		Assert.assertEquals(driver.findElement(By.xpath("//h3[normalize-space()='Job Description']")).getText(),
				"Job Description");
	}

	@Then("Job Description page should be displayed")
	public void job_description_page_should_be_displayed() {
		Assert.assertEquals(driver.findElement(By.xpath("//h3[normalize-space()='Job Description']")).getText(),
				"Job Description");
	}

	@After
	public void afterScenario() {
		driver.quit();
	}

}
