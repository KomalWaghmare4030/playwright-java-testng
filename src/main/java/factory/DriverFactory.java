package factory;

import java.nio.file.Paths;

import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.microsoft.playwright.*;
import utils.WebActions;

public class DriverFactory {
	public Browser browser;
	public static BrowserContext context;
	public static Page page;

	public static ThreadLocal<Page> threadLocalDriver = new ThreadLocal<>();
	public static ThreadLocal<BrowserContext> threadLocalContext = new ThreadLocal<>();

	public Page initDriver(String browserName) {
		BrowserType browserType = null;
		boolean headless = Boolean.valueOf(WebActions.getProperty("headless"));
		switch (browserName) {
		case "firefox":
			browserType = Playwright.create().firefox();
			browser = browserType.launch(new BrowserType.LaunchOptions().setHeadless(headless));
			break;
		case "chrome":
			browserType = Playwright.create().chromium();
			browser = browserType.launch(new BrowserType.LaunchOptions().setChannel("chrome").setHeadless(headless));
			break;
		case "webkit":
			browserType = Playwright.create().webkit();
			browser = browserType.launch(new BrowserType.LaunchOptions().setHeadless(headless));
			break;
		}
		if (browserType == null)
			throw new IllegalArgumentException("Could not Launch Browser for type" + browserType);
		context = browser.newContext();
		context.tracing().start(new Tracing.StartOptions().setScreenshots(true).setSnapshots(true).setSources(false));
		page = context.newPage();
		threadLocalDriver.set(page);
		threadLocalContext.set(context);
		return page;
	}

	public static synchronized Page getPage() {
		return threadLocalDriver.get(); // Will return Initialized Thread Local Driver
	}

	public static synchronized BrowserContext getContext() {
		return threadLocalContext.get(); // Will return Initialized Thread Local Context
	}

	public static String takeScreenshot() {
		String path = System.getProperty("user.dir") + "/test-output/screenshots/" + System.currentTimeMillis()
				+ ".png";
		page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(path)).setFullPage(true));
		return path;
	}
}
