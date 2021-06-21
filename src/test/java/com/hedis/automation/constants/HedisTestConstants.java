package com.hedis.automation.constants;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

public final class HedisTestConstants {

	private HedisTestConstants() {
	}

	public static final String USER_DIR = "user.dir";

	public static final String PROPERTY_FILE_PATH = System.getProperty(USER_DIR)
			+ "/src/test/java/resource/test.properties";
	
	public static final String KEY_USERNAME = "username";
	
	public static final String KEY_PASSWORD = "password";
	
	public static final String KEY_SECURITY_ANSWER = "security.answer";
	
	public static final String SCREENSHOT_FOLDER_PATH = System.getProperty(USER_DIR) + "/test-output/Screenshots/";
	
	public static final String PNG_EXTENTION = ".png"; 
	
	public static final String DELIMITER_SPACE = " ";
	
	public static final Map<Integer, String> MAP_MONTH_NAME_BY_NUMBER = ImmutableMap.<Integer, String>builder()
			.put(1, "JAN")
			.put(2, "FEB")
			.put(3, "MAR")
			.put(4, "APR")
			.put(5, "MAY")
			.put(6, "JUN")
			.put(7, "JUL")
			.put(8, "AUG")
			.put(9, "SEP")
			.put(10, "OCT")
			.put(11, "NOV")
			.put(12, "DEC").build();
	
	public static final class ErrorMessages {
		private ErrorMessages() {}
		
		public static final String EXTENT_REPORT_NOT_INITIALIZED = "Extent report has not been initialized";
		public static final String FIRST_LAST_NAME_NULL = "Please provide first or last name";
		public static final String YEAR_NOT_FOUND = "Year not found";
		
	}


}
