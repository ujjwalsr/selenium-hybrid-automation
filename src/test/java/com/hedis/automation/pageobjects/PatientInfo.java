package com.hedis.automation.pageobjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.hedis.automation.constants.HedisConstants.FindStrategy;
import com.hedis.automation.testdrivers.TestDriversImpl;

public class PatientInfo extends TestDriversImpl {

	private static Logger LOGGER = LogManager.getLogger(PatientInfo.class);

	WebDriver driver;

	@FindBy(id = "Group_287")
	private WebElement back_icon;

	public PatientInfo(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public boolean verifyNameVisible(String patientName) {
		LOGGER.info("verifyNameVisible method invoked.");
		try {
			driver.findElement(By.xpath("//strong[@class='ng-tns-c216-63'][contains(text(), '" + patientName + "')]"));
		} catch (NoSuchElementException e) {
			return false;
		}

		return true;
	}
	
	public void navigateBackFromPatientInfoPage() {
		LOGGER.info("navigateBackFromPatientInfoPage method invoked.");
		
		getTestObject(back_icon, FindStrategy.IS_CLICKABLE.getFilter()).click();
	}
}
