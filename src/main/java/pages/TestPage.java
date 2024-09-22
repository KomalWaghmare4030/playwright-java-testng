package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import utils.WebActions;
import java.util.List;

public class TestPage {
	private Page page;
	private final Locator HOME_PAGE;
	private final Locator CATEGORIES_DROPDOWN;
	private final Locator SEARCH_BOX;
	private final Locator SEARCH_SUGGETIONS;

	public TestPage(Page page) {
		this.page = page;
		this.HOME_PAGE = page.locator("#nav-logo-sprites");
		this.CATEGORIES_DROPDOWN = page.locator("#searchDropdownBox");
		this.SEARCH_BOX = page.locator("#twotabsearchtextbox");
		this.SEARCH_SUGGETIONS = page.locator("xpath=//div[contains(@class,'s-suggestion') and @role='button']");
	}

	public void navigateToUrl() {
		this.page.navigate(WebActions.getProperty("url"));
		WebActions.waitUntilElementDisplayed(HOME_PAGE, 10);
	}

	public void clickOnIcon(String iconName) {
		this.page.getByText(iconName, new Page.GetByTextOptions().setExact(true)).click(); // Clicks on the Exact text
	}

	public void selectFromDropdown(String option) {
		CATEGORIES_DROPDOWN.selectOption(option);
	}

	public void enterValueInTextField(String value) {
		WebActions.waitUntilElementDisplayed(SEARCH_BOX, 10).clear();
		SEARCH_BOX.fill(value);
	}

	public List<Locator> getAllElementsFromList(String value) {
		WebActions.waitUntilElementDisplayed(SEARCH_SUGGETIONS.nth(0), 5);
		return SEARCH_SUGGETIONS.all();
	}

	public void clickOnSuggestion(int value) {
		WebActions.waitUntilElementDisplayed(SEARCH_SUGGETIONS.nth(value - 1), 5).click();
	}

}
