package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import utils.WebActions;

public class StorePage {

	private Page page;
	private final Locator NAVIGATION;
	private final Locator PRODUCT;
	private final Locator PRODUCT_MODAL_TITLE;
	private final Locator QUICK_LOOK;
	public String productTitle;

	public StorePage(Page page) {
		this.page = page;
		this.NAVIGATION = page.getByTestId("navigation");
		this.PRODUCT = page.locator("//div[@data-widgettype='EditorialRow']//a[@role='link']");
		this.PRODUCT_MODAL_TITLE = page.getByTestId("product-showcase-title");
		this.QUICK_LOOK = page.getByTestId("quick-look-button");
	}

	public void clickOnOption(String option) {
		page.waitForLoadState();
		WebActions.waitUntilElementDisplayed(NAVIGATION, 10);
		this.page.waitForSelector("//nav[@aria-label='Home page Navigation Bar']//span[text()='" + option + "']/parent::a").click();
	}

	public void hoverOnElement(String option, int index) {
		WebActions.waitUntilElementDisplayed(PRODUCT.nth(index - 1), 10);
		productTitle = PRODUCT.nth(index - 1).getAttribute("title");
		PRODUCT.nth(index - 1).hover();
		WebActions.waitUntilElementDisplayed(QUICK_LOOK.first(), 10).click();;
	}

	public String getTextOfNewModal() {
		return WebActions.waitUntilElementDisplayed(PRODUCT_MODAL_TITLE, 10).getAttribute("title");
	}

}
