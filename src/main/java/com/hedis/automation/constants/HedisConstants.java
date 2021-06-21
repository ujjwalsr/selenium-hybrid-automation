package com.hedis.automation.constants;

public final class HedisConstants {
	
	private HedisConstants() {}
	
	public static final String USER_DIR = "user.dir";
	
	public static final class TestDrivers {
		
		private TestDrivers() {}
		
		public static final String CHROME_DRIVER_KEY = "webdriver.chrome.driver";
		public static final String PROPERTY_FILE_PATH = System.getProperty(USER_DIR) + "/src/main/resources/testdrivers.properties";
		public static final String CHROME_DRIVER_PATH = System.getProperty(USER_DIR) + "/src/main/resources/chromedriver.exe";
		public static final String KEY_BROWER_NAME = "browser";
		public static final String KEY_APP_URL = "url";
	}
	
	public static final class ErrorMessages {
		private ErrorMessages() {}
		
		public static final String WEBDRIVER_NOT_INITIALIZED = "webriver has not been initialized";
		public static final String PROPERTY_FILE_NOT_INITIALIZED = "property file has not been initialized";
		public static final String APPLICATION_URL_NULL = "please provide the application url in testdrivers.properties file";
		public static final String BROWSER_NAME_NULL = "please provide the browser name in testdrivers.properties file";
		public static final String WEBDRIVER_WAIT_NOT_INITIALIZED = "webriver wait has not been initialized";
	}
	
	public static enum FindStrategy{
		IS_ENABLED("enabled"),
		IS_DISPLAYED("displayed"),
		IS_CLICKABLE("clickable");
		private String filter;
		private FindStrategy(String filter){
			this.filter = filter;
		}
		public String getFilter(){
			return this.filter;
		}
		
	}

}
