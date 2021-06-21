package com.hedis.automation.pageobjects;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import com.aventstack.extentreports.ExtentTest;
import com.hedis.automation.constants.HedisConstants.FindStrategy;
import com.hedis.automation.constants.HedisTestConstants;
import com.hedis.automation.testdrivers.TestDriversImpl;

public class Search extends TestDriversImpl {

	private static Logger LOGGER = LogManager.getLogger(Search.class);

	WebDriver driver;

	@FindBy(xpath = "//a[contains(@href, 'advance-search')]")
	private WebElement search_tab;

	@FindBy(xpath = "//input[@placeholder='Member Id']")
	private WebElement memberId_txt;

	@FindBy(xpath = "//mat-select[@title='Search by organization']")
	private WebElement clinic_name_drpdwn;

	@FindBy(id = "mat-input-40")
	private WebElement service_date_inp;

	@FindBy(xpath = "//div[@class='buttons_input']/button[@title='Clear']")
	private WebElement service_date_clear_btn;

	@FindBy(xpath = "//div[@class='buttons_input']/button[@class='btn']")
	private WebElement service_date_apply_btn;

	@FindBy(xpath = "//button[contains(text(), 'Search')]")
	private WebElement search_btn;

	@FindBy(xpath = "//mat-header-row[@class='mat-header-row cdk-header-row ng-star-inserted']")
	private WebElement search_result_table;

	@FindBy(xpath = "//div[@class='mat-paginator-range-label'][contains(text(), 'of')]")
	private WebElement search_result_count;

	@FindBy(xpath = "//input[@placeholder='First Name']")
	private WebElement first_name_txt;

	@FindBy(xpath = "//input[@placeholder='Last Name']")
	private WebElement last_name_txt;
	
	@FindBy(xpath = "//input[@placeholder='Phone No']")
	private WebElement phone_txt;
	
	@FindBy(xpath = "//input[contains(@placeholder, 'Choose a date')]")
	private WebElement dob_txt;
	
	@FindBy(xpath = "//span[@class='mat-button-wrapper']")
	private WebElement dob_calender_icon;
	
	@FindBy(xpath = "//button[@aria-label='Choose month and year']")
	private WebElement dob_year_month_picker_button;
	
	@FindBy(xpath = "//button[@aria-label='Previous 20 years']")
	private WebElement dob_previous_year_nav;
	
	@FindBy(xpath = "//button[@aria-label='Next 20 years']")
	private WebElement dob_next_year_nav;
	
	@FindBy(xpath = "//i[@title='Patient Chart']")
	private WebElement patient_chart_icon;
	
	@FindBy(xpath = "//i[@title='View Patient CCDA']")
	private WebElement patient_CCDA_icon;
	
	@FindBy(xpath = "//i[@title='View Patient NCQA CCDA']")
	private WebElement patient_NCDA_CCDA_icon;

	public Search(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public void navigateToSearchTab(ExtentTest testLogger) {
		LOGGER.info("navigateToSearchTab method invoked.");

		getTestObject(search_tab, null).click();

		testLogger.pass("Navigated to Search tab.");

	}

	public void selectServiceDateRange(String range, ExtentTest testLogger) {
		LOGGER.info("selectServiceDateRange method invoked.");
		testLogger.info(String.format("Selecting the service date range of {}", range));

		WebElement dateRange = getTestObject(
				driver.findElement(By.xpath("//li/button[contains(text(), '" + range + "')]")),
				FindStrategy.IS_CLICKABLE.getFilter());
		dateRange.click();

		testLogger.pass(String.format("service date range of {} is selected.", range));
	}

	public void selectClinicName(String clinicName, ExtentTest testLogger) {
		LOGGER.info("selectClinicName method invoked.");
		testLogger.info("Selecting the clinic name.");

		getTestObject(clinic_name_drpdwn, null).click();
		getTestObject(driver.findElement(By.xpath("//mat-option/span[contains(text(), '" + clinicName + "')]")),
				FindStrategy.IS_CLICKABLE.getFilter()).click();

		testLogger.pass(String.format("Clinic name '%s' is selected.", clinicName));
	}

	public void clickOnSearchButton(ExtentTest testLogger) {
		LOGGER.info("clickOnSearchButton method invoked.");

		getTestObject(search_btn, FindStrategy.IS_CLICKABLE.getFilter()).click();
		testLogger.pass("Search button is clicked.");

		waitForJsToLoad();
	}

	public String getTotalCountOfSearchResult() throws Exception {
		LOGGER.info("getTotalCountOfSearchResult method invoked.");
		String count = "";
		try {
		count = search_result_count.getAttribute("innerHTML");
		} catch(NoSuchElementException exp) {
			return count = "0";
		}
		
		count = count.substring(count.indexOf("f") + 1);

		return count.trim();
	}

	public void enterFirstAndLastName(String firstName, String lastName, ExtentTest testLogger) {
		LOGGER.info("enterFirstAndLastName method invoked.");

		if (StringUtils.isEmpty(firstName) && StringUtils.isEmpty(lastName)) {
			throw new NullPointerException(HedisTestConstants.ErrorMessages.FIRST_LAST_NAME_NULL);
		}

		getTestObject(first_name_txt, null).clear();
		getTestObject(last_name_txt, null).clear();

		if (StringUtils.isNotEmpty(firstName)) {
			getTestObject(first_name_txt, null).sendKeys(firstName);
			testLogger.pass("Enterd first name: " + firstName);
		}

		if (StringUtils.isNotEmpty(lastName)) {
			getTestObject(last_name_txt, null).sendKeys(lastName);
			testLogger.pass("Enterd last name: " + lastName);
		}
	}
	
	/*
	 * @param takes date of birth as string in given format MM/DD/YYYY
	 */
	public void enterDateOfBirth(String dob, ExtentTest testLogger) {
		LOGGER.info("enterDateOfBirth method invoked.");
		
		String month = dob.split("/")[0];
		String date = dob.split("/")[1];
		String year = dob.split("/")[2];
		
		month = month.replaceFirst("^0+(?!$)", "");
		date = date.replaceFirst("^0+(?!$)", "");
		
		month = HedisTestConstants.MAP_MONTH_NAME_BY_NUMBER.get(Integer.parseInt(month));
		
		getTestObject(dob_calender_icon, null).click();
		testLogger.info("Clicked on Year and Month picker");
		
		getTestObject(dob_year_month_picker_button, FindStrategy.IS_CLICKABLE.getFilter()).click();
		LOGGER.info("Year and Month picker button clicked");
		
		for(int i = 0; i<5; i++) {
			LOGGER.info("Selecting the year");
			List<WebElement> yearObject = driver.findElements(By.xpath("//div[contains(@class, 'mat-calendar-body-cell-content')][contains(text(), '" + year + "')]"));
			
			if(yearObject.size() > 0) {
				yearObject.get(0).click();
				testLogger.pass("Selected year: " + year);
				break;
			} else {
				getTestObject(dob_previous_year_nav, FindStrategy.IS_CLICKABLE.getFilter()).click();
				LOGGER.info("Navigating back");
			}
			
			if(i>5) {
				throw new NullPointerException(HedisTestConstants.ErrorMessages.YEAR_NOT_FOUND);
			}
		}
		
		LOGGER.info("Selecting the month");
		try {
			WebElement monthObject = driver.findElement(By.xpath("//div[contains(@class, 'mat-calendar-body-cell-content')][contains(text(), '"+ month +"')]"));
			getTestObject(monthObject, FindStrategy.IS_CLICKABLE.getFilter()).click();
			
			testLogger.pass("Selected month: " + month);
		}
		catch(NoSuchElementException ex
				) {
			LOGGER.error("Unable to select month ");
			ex.printStackTrace();
		}
		
		LOGGER.info("Selecting the date");
		try {
			WebElement dateObject = driver.findElement(By.xpath("//div[contains(@class, 'mat-calendar-body-cell-content')][text()=' " + date + " '] "));
			getTestObject(dateObject, FindStrategy.IS_CLICKABLE.getFilter()).click();
			
			testLogger.pass("Selected date: " + date);
		}
		catch(NoSuchElementException ex
				) {
			LOGGER.error("Unable to select date ");
			ex.printStackTrace();
		}
		
	}

	public boolean verifySearchResultDisplayedForOrg(String organizationName, ExtentTest testLogger) throws Exception {
		LOGGER.info("verifySearchResultDisplayedForOrg method invoked.");

		waitForJsToLoad();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//mat-header-row[@class='mat-header-row cdk-header-row ng-star-inserted']")));

		testLogger.info("Verifing the search result for organizatoin " + organizationName);
		List<WebElement> searchResultList = driver.findElements(
				By.xpath("//mat-row[@class='mat-row cdk-row ng-star-inserted']/mat-cell[2][contains(text(), '"
						+ organizationName + "')]"));

		if (searchResultList.size() > 0) {
			testLogger.pass("Verified the search result is displayed for organizatoin " + organizationName);
			testLogger.pass("Total search result count are " + getTotalCountOfSearchResult());
			return true;
		}

		return false;
	}

	public boolean verifySearchResultDisplayedForName(String name, ExtentTest testLogger) throws Exception {
		LOGGER.info("verifySearchResultDisplayedForName method invoked.");

		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//mat-header-row[@class='mat-header-row cdk-header-row ng-star-inserted']")));

		testLogger.info("Verifing the search result for name " + name);
		List<WebElement> searchResultList = driver.findElements(By.xpath(
				"//mat-row[@class='mat-row cdk-row ng-star-inserted']/mat-cell[1][contains(text(), '" + name + "')]"));

		String totalResultCount = getTotalCountOfSearchResult();

		if (searchResultList.size() > 0
				&& StringUtils.equals(String.valueOf(searchResultList.size()), totalResultCount)) {
			testLogger.pass("Verified the search result is displayed for name " + name);
			testLogger.pass("Total search result count are " + totalResultCount);
			return true;
		}

		return false;
	}
	
	public boolean verifySearchResultDisplayedForDob(String dob, ExtentTest testLogger) throws Exception {
		LOGGER.info("verifySearchResultDisplayedForDob method invoked.");

		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//mat-header-row[@class='mat-header-row cdk-header-row ng-star-inserted']")));

		testLogger.info("Verifing the search result for date of birth " + dob);
		List<WebElement> searchResultList = driver.findElements(By.xpath(
				"//mat-row[@class='mat-row cdk-row ng-star-inserted']/mat-cell[3]/div[contains(text(), '" + dob + "')]"));

		String totalResultCount = getTotalCountOfSearchResult();

		if (searchResultList.size() > 0
				&& StringUtils.equals(String.valueOf(searchResultList.size()), totalResultCount)) {
			testLogger.pass("Verified the search result is displayed for date of birth " + dob);
			testLogger.pass("Total search result count are " + totalResultCount);
			return true;
		}

		return false;
	}

}
