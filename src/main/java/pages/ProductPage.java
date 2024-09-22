package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import utils.WebActions;

public class ProductPage {

	private Page page;
	private final Locator IMAGE_BLOCK;
	private final Locator VISIT_APPLE_STORE;

	public ProductPage(Page page) {
		this.page = page;
		this.IMAGE_BLOCK = page.locator("#imageBlock");
		this.VISIT_APPLE_STORE = page.locator("#bylineInfo");
	}

	public String verifyTitleOfPage() {
		WebActions.waitUntilElementDisplayed(IMAGE_BLOCK, 15);
		String title = this.page.title();
		return title;
	}

	public void clickOnLink() {
		WebActions.waitUntilElementDisplayed(VISIT_APPLE_STORE, 5).click();
	}
	
}
