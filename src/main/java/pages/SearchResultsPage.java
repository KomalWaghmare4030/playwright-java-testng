package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import factory.DriverFactory;
import utils.WebActions;

public class SearchResultsPage {

	private Page page;
	private final Locator SEARCH_RESULTS;

	public SearchResultsPage(Page page) {
		this.page = page;
		this.SEARCH_RESULTS = page.locator("xpath=//h2[text()='Results']");
	}

	public void selectProductFromSerachPage(String product) {
		WebActions.waitUntilElementDisplayed(SEARCH_RESULTS, 15);
		new DriverFactory();
		Page newPage = DriverFactory.context.waitForPage(() -> {
			page.locator("xpath=//span[contains(text(),'" + product + "')]/parent::a").nth(0).click();
		});
		DriverFactory.threadLocalDriver.set(newPage);
	}

}
