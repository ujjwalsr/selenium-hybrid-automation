package com.hedis.automation.base;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.hedis.automation.constants.HedisTestConstants;
import com.hedis.automation.testdrivers.TestDriversImpl;
import com.hedis.automation.utils.HedisTestUtils;

public class BaseTest extends TestDriversImpl {
	
	private static Logger LOGGER = LogManager.getLogger(BaseTest.class);
	
	public ExtentTest TEST_LOGGER;
	public static ExtentReports extentReport;
    private static String reportFileName = "hedis-test-report"+".html";
    private static String fileSeperator = System.getProperty("file.separator");
    private static String reportFilepath = System.getProperty("user.dir") + fileSeperator + "test-output";
    private static String reportFileLocation =  reportFilepath + fileSeperator + reportFileName;

	@BeforeSuite(alwaysRun = true)
	public void setUpSuite() throws FileNotFoundException, IOException {
		LOGGER.info("@BeforeSuite invoked.");
		
		loadProperties();
		HedisTestUtils.loadTestProperties();
		initExtentReport();	
	}

	@BeforeTest(alwaysRun = true)
	public void setUpTest() throws IOException {
		LOGGER.info("@BeforeTest invoked.");
	
		initializeDriver();
		openAppUrlInBrowser();
		waitForJsToLoad();

	}
	
	@AfterClass(alwaysRun = true)
	public void tearDownClass() {
		LOGGER.info("@AfterClass invoked.");
		quitBrowser();
	}
	
	@AfterMethod(alwaysRun = true)
	public void tearDownAfterMethod(ITestResult result) throws IOException {
		LOGGER.info("@AfterMethod invoked.");
		if(result.getStatus() == ITestResult.FAILURE) {
			TEST_LOGGER.fail("Failed screenshot - ", MediaEntityBuilder.createScreenCaptureFromPath(takeScreenshot()).build());
		}
		
		extentReport.flush();
	}
	
	
    private void initExtentReport() {
    	LOGGER.info("initExtentReport method invoked.");
        String fileName = getReportPath(reportFilepath);
       
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(fileName);

        htmlReporter.config().setTheme(Theme.STANDARD);
        htmlReporter.config().setDocumentTitle(reportFileName);
        htmlReporter.config().setEncoding("utf-8");
        htmlReporter.config().setReportName(reportFileName);
        htmlReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");
 
        extentReport = new ExtentReports();
        extentReport.attachReporter(htmlReporter);
        
        extentReport.setSystemInfo("OS", "Windows");
        extentReport.setSystemInfo("AUT", "QA");
 
    }
    
    public ExtentReports getExtentReport() {
    	if(null == extentReport) {
    		throw new NullPointerException(HedisTestConstants.ErrorMessages.EXTENT_REPORT_NOT_INITIALIZED);
    	}
    	
    	return extentReport;
    }

    private static String getReportPath (String path) {
    	LOGGER.info("getReportPath method invoked.");
    	File testDirectory = new File(path);
        if (!testDirectory.exists()) {
        	if (testDirectory.mkdir()) {
                System.out.println("Directory: " + path + " is created!" );
                return reportFileLocation;
            } else {
                System.out.println("Failed to create directory: " + path);
                return System.getProperty("user.dir");
            }
        } else {
            System.out.println("Directory already exists: " + path);
        }
		return reportFileLocation;
    }
    
    /**
	 * *Taking snapshot
	 */
	private String takeScreenshot() {
		LOGGER.info("takeScreenshot method invoked.");
		String fname = null;
		
		File scrFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
		String imgFileName = new SimpleDateFormat("MM-dd-yyyy_HH-mm-ss-SS").format(new GregorianCalendar().getTime());

		try {
			fname = HedisTestConstants.SCREENSHOT_FOLDER_PATH + imgFileName + HedisTestConstants.PNG_EXTENTION;
			FileUtils.copyFile(scrFile, new File(fname));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return fname;
	}

}
