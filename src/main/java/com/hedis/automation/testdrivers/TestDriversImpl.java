package com.hedis.automation.testdrivers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.hedis.automation.constants.HedisConstants;
import com.hedis.automation.constants.HedisConstants.FindStrategy;

public class TestDriversImpl implements TestDrivers {

	private static Logger LOGGER = LogManager.getLogger(TestDriversImpl.class);

	private static WebDriver driver;
	private Properties properties;
	public static WebDriverWait wait;

	@Override
	public void loadProperties() throws FileNotFoundException, IOException {
		LOGGER.info("loadProperties method invoked");

		FileInputStream inputstream = new FileInputStream(HedisConstants.TestDrivers.PROPERTY_FILE_PATH);

		properties = new Properties();
		properties.load(inputstream);
		LOGGER.info("driver properties loaded");
	}

	@Override
	public void initializeDriver() throws IOException {
		LOGGER.info("initializeDriver method invoked.");
		if (null == properties) {
			LOGGER.error(HedisConstants.ErrorMessages.PROPERTY_FILE_NOT_INITIALIZED);
			throw new NullPointerException(HedisConstants.ErrorMessages.PROPERTY_FILE_NOT_INITIALIZED);
		}

		if (null == properties.getProperty(HedisConstants.TestDrivers.KEY_BROWER_NAME)) {
			LOGGER.error(HedisConstants.ErrorMessages.BROWSER_NAME_NULL);
			throw new NullPointerException(HedisConstants.ErrorMessages.BROWSER_NAME_NULL);
		}

		if (properties.getProperty(HedisConstants.TestDrivers.KEY_BROWER_NAME).equals("chrome")) {
			LOGGER.info("invoking chrome browser.");
			System.setProperty(HedisConstants.TestDrivers.CHROME_DRIVER_KEY,
					HedisConstants.TestDrivers.CHROME_DRIVER_PATH);

			ChromeOptions options = new ChromeOptions();

			options.setPageLoadStrategy(PageLoadStrategy.NONE);
			options.addArguments("--start-maximized");
			options.addArguments("disable-infobars");

			ChromeDriverService service = new ChromeDriverService.Builder().usingAnyFreePort()
					.usingDriverExecutable(new File(HedisConstants.TestDrivers.CHROME_DRIVER_PATH)).build();
			service.start();

			driver = new ChromeDriver(service, options);

			// Setting the implicit wait
			driver.manage().timeouts().implicitlyWait(25, TimeUnit.SECONDS);
			LOGGER.info("chrome browser invoked.");
		}
	}

	@Override
	public WebDriver getDriver() {
		if (null == driver) {
			LOGGER.error(HedisConstants.ErrorMessages.WEBDRIVER_NOT_INITIALIZED);
			throw new NullPointerException(HedisConstants.ErrorMessages.WEBDRIVER_NOT_INITIALIZED);
		}

		return driver;
	}

	@Override
	public void openAppUrlInBrowser() {
		LOGGER.info("openAppUrlInBrowser method invoked.");
		wait = new WebDriverWait(driver, 50);

		if (null == properties.getProperty(HedisConstants.TestDrivers.KEY_APP_URL)) {
			throw new NullPointerException(HedisConstants.ErrorMessages.APPLICATION_URL_NULL);
		}

		getDriver().get(properties.getProperty(HedisConstants.TestDrivers.KEY_APP_URL));
	}

	@Override
	public void quitBrowser() {
		LOGGER.info("quitBrowser method invoked.");
		getDriver().quit();
	}

	@Override
	public void closeBrowser() {
		LOGGER.info("closeBrowser method invoked.");
		getDriver().close();
	}

	@Override
	public boolean waitForJsToLoad() {
		LOGGER.info("waitForJsToLoad method invoked.");

		ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				try {
					JavascriptExecutor jsExecutor = null;
					jsExecutor = (JavascriptExecutor) driver;
					return ((Long) jsExecutor.executeScript("return jQuery.active") == 0);
				} catch (Exception e) {
					return true;
				}
			}
		};

		// wait for JavaScript to load
		ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				try {
					JavascriptExecutor jsExecutor = null;
					jsExecutor = (JavascriptExecutor) driver;
					return jsExecutor.executeScript("return document.readyState").toString().equals("complete");
				} catch (Exception e) {
					return true;
				}
			}
		};

		return wait.until(jQueryLoad) &&  wait.until(jsLoad);
	}

	@Override
	public WebElement getTestObject(WebElement testObjeElement, String objectFindingStrategy) {
		LOGGER.info("getTestObject method invoked.");
		WebElement element = testObjeElement;
		boolean isElementFound = false;

		if (null == objectFindingStrategy) {
			objectFindingStrategy = "";
		}

		if (FindStrategy.IS_ENABLED.getFilter().equals(objectFindingStrategy)) {
			isElementFound = element.isEnabled();
		} else if (FindStrategy.IS_CLICKABLE.getFilter().equals(objectFindingStrategy)) {
			return wait.until(ExpectedConditions.elementToBeClickable(element));
		} else {
			isElementFound = element.isDisplayed();
		}

		if (isElementFound) {
			return element;
		}

		return null;
	}

	public static boolean isClickable(WebElement testObject) {
		LOGGER.info("isClickable method invoked.");
		if (null == wait) {
			throw new NullPointerException(HedisConstants.ErrorMessages.WEBDRIVER_WAIT_NOT_INITIALIZED);
		}

		try {
			wait.until(ExpectedConditions.elementToBeClickable(testObject));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public void sleep(long timesinmilliseconds) {
		try {
			Thread.sleep(timesinmilliseconds);
		} catch (Exception e) {
			// nothing to throw.
		}

	}


	@Override
	public void swipetoElementVisible(WebElement testElement) throws Exception {
		try {

			((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);",
					getTestObject(testElement, null));

		} catch (Exception e) {
			throw new Exception();
		}
	}

}
