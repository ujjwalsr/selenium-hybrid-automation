package com.hedis.automation.uitest.search;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.google.common.base.Strings;
import com.hedis.automation.base.BaseTest;
import com.hedis.automation.constants.HedisTestConstants;
import com.hedis.automation.pageobjects.Login;
import com.hedis.automation.pageobjects.Search;
import com.hedis.automation.utils.HedisTestUtils;

public class SearchTest extends BaseTest {
	private static Logger LOGGER = LogManager.getLogger(SearchTest.class);

	private Login loginPage = null;
	private Search searchPage = null;

	@BeforeTest
	public void setUp() {
		LOGGER.info("@BeforeMethod invoked.");

		loginPage = new Login(getDriver());
		searchPage = new Search(getDriver());

		loginPage.loginToApp(HedisTestUtils.getValueFromGlobalVarMap(HedisTestConstants.KEY_USERNAME),
				HedisTestUtils.getValueFromGlobalVarMap(HedisTestConstants.KEY_PASSWORD));
		loginPage.answerSecurityQuestion(
				HedisTestUtils.getValueFromGlobalVarMap(HedisTestConstants.KEY_SECURITY_ANSWER));
	}

	@Test(description = "Test search by organization", dataProvider = "getOrganizationName")
	public void testSearchByOrg(String organizationName) throws Exception {
		LOGGER.info("testSearchByOrg test method invoked.");

		getDriver().navigate().refresh();

		waitForJsToLoad();
		
		TEST_LOGGER = getExtentReport().createTest("Test search by organization " + organizationName);

		TEST_LOGGER.info("Test search by organization - started.");

		searchPage.navigateToSearchTab(TEST_LOGGER);

		searchPage.selectClinicName(organizationName, TEST_LOGGER);

		searchPage.clickOnSearchButton(TEST_LOGGER);

		boolean actualResult = searchPage.verifySearchResultDisplayedForOrg(organizationName, TEST_LOGGER);

		assertTrue(actualResult);
	}

	@DataProvider(name = "getOrganizationName")
	Object[][] getOrganizationName() {

		return new Object[][] { { "Billy  Nutbeem CORP" } };
	}

	@Test(description = "Test search by first and last name", dataProvider = "testSearchByName")
	public void testSearchByName(String testName, String firstName, String lastName, boolean expectedResult)
			throws Exception {
		LOGGER.info("testSearchByName test method invoked");

		getDriver().navigate().refresh();

		waitForJsToLoad();

		TEST_LOGGER = getExtentReport().createTest(testName);
		TEST_LOGGER.info(testName + " - started.");

		searchPage.navigateToSearchTab(TEST_LOGGER);

		searchPage.selectClinicName("Billy  Nutbeem CORP", TEST_LOGGER);

		searchPage.enterFirstAndLastName(firstName, lastName, TEST_LOGGER);

		searchPage.clickOnSearchButton(TEST_LOGGER);
		
		String name = "";
		if(Strings.isNullOrEmpty(firstName)) {
			name = lastName;
		} else {
			name = firstName;
		}

		boolean actualResult = searchPage.verifySearchResultDisplayedForName(name, TEST_LOGGER);

		assertEquals(expectedResult, actualResult);
	}

	@DataProvider(name = "testSearchByName")
	Object[][] testSearchByName() {

		return new Object[][] { { "Test search by first name, expecting result", "SOUTH", "", true },
			{ "Test search by last name, expecting result", "", "DEATON", true },
			{ "Test search by first and last name, expecting result", "SOUTH", "DEATON", true },
			{ "Test search by non-existing name, expecting no result", "hello", "", false } };
	}
	
	@Test(description = "Test search by date of birth", dataProvider = "testSearchByDob")
	public void testSearchByDob(String organizationName, String dob) throws Exception {
		LOGGER.info("testSearchByDob test method invoked.");

		getDriver().navigate().refresh();

		waitForJsToLoad();
		
		TEST_LOGGER = getExtentReport().createTest("Test search by date of birth " + dob);

		TEST_LOGGER.info("Test search by date of birth - started.");

		searchPage.navigateToSearchTab(TEST_LOGGER);

		searchPage.selectClinicName(organizationName, TEST_LOGGER);
		
		searchPage.enterDateOfBirth(dob, TEST_LOGGER);

		searchPage.clickOnSearchButton(TEST_LOGGER);

		boolean actualResult = searchPage.verifySearchResultDisplayedForDob(dob, TEST_LOGGER);

		assertTrue(actualResult);
	}
	
	@DataProvider(name = "testSearchByDob")
	Object[][] testSearchByDob() {

		return new Object[][] { { "Billy  Nutbeem CORP", "08/14/1950" }};
	}
	
	@Test(description = "Test search result icons patient chart, CCDA and NCDA CCDA", dataProvider = "testSearchResultIcons")
	public void testSearchResultIcons(String testName, String firstName, String lastName, boolean expectedResult)
			throws Exception {
		LOGGER.info("testSearchResultIcons test method invoked");

		getDriver().navigate().refresh();

		waitForJsToLoad();

		TEST_LOGGER = getExtentReport().createTest(testName);
		TEST_LOGGER.info(testName + " - started.");

		searchPage.navigateToSearchTab(TEST_LOGGER);

		searchPage.selectClinicName("Billy  Nutbeem CORP", TEST_LOGGER);

		searchPage.enterFirstAndLastName(firstName, lastName, TEST_LOGGER);

		searchPage.clickOnSearchButton(TEST_LOGGER);
		
		String name = "";
		if(Strings.isNullOrEmpty(firstName)) {
			name = lastName;
		} else {
			name = firstName;
		}

		boolean actualResult = searchPage.verifySearchResultDisplayedForName(name, TEST_LOGGER);

		assertEquals(expectedResult, actualResult);
	}

	@DataProvider(name = "testSearchResultIcons")
	Object[][] testSearchResultIcons() {

		return new Object[][] { { "Test search by first name, expecting result", "SOUTH", "DEATON", true }};
	}

}
