package com.hedis.automation.pageobjects;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;

import com.hedis.automation.constants.HedisConstants.FindStrategy;
import com.hedis.automation.testdrivers.TestDriversImpl;

public class Login extends TestDriversImpl{
	private static Logger LOGGER = LogManager.getLogger(Login.class);

	WebDriver driver;

	@FindBy(id = "mat-input-0")
	private WebElement username_txt;

	@FindBy(id = "mat-input-1")
	private WebElement password_txt;

	@FindBy(id = "mat-checkbox-1-input")
	private WebElement remember_me_chkbx;

	@FindBy(xpath = "//input[@value = 'SIGN IN']")
	private WebElement signin_btn;

	@FindBy(id = "mat-input-3")
	private WebElement security_answer_txt;

	@FindBys({ @FindBy(className = "submit coman-btn-1") })
	private List<WebElement> authenticate_btn;

	public Login(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public void answerSecurityQuestion(String answer) {
		LOGGER.info("answerSecurityQuestion method invoked.");
		
		if (authenticate_btn.size() > 0) {
			LOGGER.info("Entering security answer");
			getTestObject(security_answer_txt, FindStrategy.IS_DISPLAYED.getFilter()).sendKeys(answer);
			getTestObject(authenticate_btn.get(0), FindStrategy.IS_CLICKABLE.getFilter()).click();
			LOGGER.info("successfully entered the security answer..");
		}

		waitForJsToLoad();		
	}

	public void loginToApp(String username, String password) {
		LOGGER.info("loginToApp method invoked.");
		LOGGER.info("Started login to KPCA application");

		getTestObject(username_txt, FindStrategy.IS_DISPLAYED.getFilter()).sendKeys(username);
		getTestObject(password_txt, FindStrategy.IS_DISPLAYED.getFilter()).sendKeys(password);
		getTestObject(signin_btn, FindStrategy.IS_CLICKABLE.getFilter()).click();
		LOGGER.info("Successfully logged into KPCA application");

		waitForJsToLoad();		
	}
}
