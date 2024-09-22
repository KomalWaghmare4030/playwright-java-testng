package stepdefinitions;

import java.util.List;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.aventstack.extentreports.testng.listener.ExtentITestListenerClassAdapter;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import factory.DriverFactory;
import pages.ProductPage;
import pages.SearchResultsPage;
import pages.StorePage;
import pages.TestPage;
import reports.ExtentManager;
import utils.WebActions;

public class TestSteps {

	@BeforeSuite
	public void beforeSuite() {
		ExtentManager.setExtent();
	}

	@BeforeTest
	public void launchBrowser() {
		String browserName = WebActions.getProperty("browser");
		DriverFactory driverFactory = new DriverFactory();
		driverFactory.initDriver(browserName);
	}

	@Test
	public void testAmazon() {
		TestPage testPage = new TestPage(DriverFactory.getPage());
		testPage.navigateToUrl();
		testPage.selectFromDropdown("Electronics");
		testPage.enterValueInTextField("iPhone 13");
		List<Locator> listOfSuggestions = testPage.getAllElementsFromList("iphone 13");
		for (Locator element : listOfSuggestions) {
			String text = element.getAttribute("aria-label");
			Assert.assertTrue(text.contains("iphone 13"), text + " does not contain " + "iphone 13");
		}
		testPage.enterValueInTextField("iPhone 13 128GB");
		testPage.clickOnSuggestion(1);
		SearchResultsPage searchResultsPage = new SearchResultsPage(DriverFactory.getPage());
		searchResultsPage.selectProductFromSerachPage("iPhone 13 (128GB)");
		ProductPage productPage = new ProductPage(DriverFactory.getPage());
		Assert.assertTrue(productPage.verifyTitleOfPage().contains("Amazon.in: Electronics"),
				"Amazon.in: Electronics" + " is not opened");
		productPage.clickOnLink();
		StorePage storePage = new StorePage(DriverFactory.getPage());
		storePage.clickOnOption("Apple Watch");
		storePage.clickOnOption("Apple Watch SE (GPS + Cellular)");
		storePage.hoverOnElement("Quick look", 1);
		Assert.assertEquals(storePage.getTextOfNewModal(), storePage.productTitle, "product title is not same");
	}

	@AfterTest()
	public void quitBrowser() {
		DriverFactory.getPage().close();
	}

	@AfterSuite
	public void afterSuite() {
		ExtentManager.endReport();
	}

}
