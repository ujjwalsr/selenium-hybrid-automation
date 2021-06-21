package com.hedis.automation.testdrivers;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public interface TestDrivers {
	
	public void initializeDriver() throws IOException;
	
	public WebDriver getDriver();
	
	public void openAppUrlInBrowser();
	
	public void loadProperties() throws FileNotFoundException, IOException;
	
	public boolean waitForJsToLoad();
	
	public WebElement getTestObject(WebElement testObjeElement, String objectFindingStrategy);
	
	public void quitBrowser();
	
	public void closeBrowser();
	
	public void sleep(long timesinmilliseconds);
	
	public void swipetoElementVisible(WebElement testElement) throws Exception;
}
