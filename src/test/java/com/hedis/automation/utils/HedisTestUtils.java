package com.hedis.automation.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.hedis.automation.constants.HedisTestConstants;

public class HedisTestUtils {

	private static Properties testProperties = null;
	private static Map<String, String> globalVarMap = new HashMap<String, String>();

	public static String getValueFromGlobalVarMap(String key) {

		if (!globalVarMap.containsKey(key)) {
			return globalVarMap.get(key.toLowerCase());
		}

		return globalVarMap.get(key);
	}

	public static void putValueInGlobalVarMap(String key, String value) {

		globalVarMap.put(key.toLowerCase(), value);
	}

	public static void loadTestProperties() throws IOException {
		FileInputStream inputStream = new FileInputStream(HedisTestConstants.PROPERTY_FILE_PATH);

		testProperties = new Properties();
		testProperties.load(inputStream);

		putValueInGlobalVarMap(HedisTestConstants.KEY_USERNAME,
				testProperties.getProperty(HedisTestConstants.KEY_USERNAME));
		putValueInGlobalVarMap(HedisTestConstants.KEY_PASSWORD,
				testProperties.getProperty(HedisTestConstants.KEY_PASSWORD));
		putValueInGlobalVarMap(HedisTestConstants.KEY_SECURITY_ANSWER,
				testProperties.getProperty(HedisTestConstants.KEY_SECURITY_ANSWER));

	}

}
