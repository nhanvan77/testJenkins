package com.msrobot.logs;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.Reporter;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.msrobot.constant.Constants;
import com.msrobot.listeners.RetryAnalyzer;
import com.msrobot.logs.MsLog.ConsoleLogLevel;
import com.msrobot.msDriver.ExecutionManager;
import com.msrobot.msDriver.MsWebDriver;
import com.msrobot.ultilities.ObjectLocator;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

/**
 * This is a customized log to log all information using log4j.
 * This Log has 3 logs: debug log file, Report html log file and Console information log.
 * debug.log: logs all logs at DEBUG level
 * report.html: logs at INFO level
 * console log: logs at 3 custom level. NO_STEP: no any step info will be displayed in console. 
 * TESTCASE_STEP: information of test case steps (using for user report).
 * DEBUG_STEP: information at DEBUG level for test developer debugging scripts.
 * @author thao.le
 *
 */
public class MsLog{	
	private static ConsoleLogLevel consoleLevel = ConsoleLogLevel.DEBUG_STEP;
	private static Logger logger = Logger.getRootLogger();												
	private static Logger debugLog = Logger.getLogger("debugLogger");
	//private static Logger reportsLog = Logger.getLogger("reportsLogger");
	private static Logger consoleLog = Logger.getLogger("consoleLogger");
	private static ExtentTest extentReport = null; 
	private static TestCase testCase;
	
	/**
	 * NO_STEP: no any step info will be displayed in console. 
	 * TESTCASE_STEP: information of test case steps (using for user report).
	 * DEBUG_STEP: information at DEBUG level for test developer debugging scripts.
	 * @author thao.le
	 *
	 */
	public enum ConsoleLogLevel{
		NO_STEP,
		TESTCASE_STEP,
		DEBUG_STEP;
	}	
	
	/**
	 * To set the log level (Level: debug, info, error, fatal. Console level: NO STEP, TEST CASE STEP, DEBUG STEP)
	 * @param loggerLevel
	 * @param consoleLogLevel
	 */
	public static void setCustomLogLevel(Level loggerLevel,String customConsoleLogLevel){
		ConsoleLogLevel consoleLogLevel; 
		switch (customConsoleLogLevel){
		case "NO_STEP":
			consoleLogLevel= ConsoleLogLevel.NO_STEP;
			break;
		case "TESTCASE_STEP":
			consoleLogLevel= ConsoleLogLevel.TESTCASE_STEP;
			break;
		case "DEBUG_STEP":
			consoleLogLevel= ConsoleLogLevel.DEBUG_STEP;
			break;
		default :
			consoleLogLevel= ConsoleLogLevel.DEBUG_STEP;
			break;
		}	
		//reportsLog.setLevel(loggerLevel);
		debugLog.setLevel(loggerLevel);
		consoleLog.setLevel(loggerLevel);		
		consoleLevel = consoleLogLevel;
	}
	public static void setExtentReport(ExtentTest extentTest){
		extentReport = extentTest;
	}
	public static void setTestCase(TestCase currentTestCase){
		ExecutionManager.getSession().setTestcase(currentTestCase);
		testCase = currentTestCase;
		
	}
	public static void info(String message){
		//reportsLog.info(message);
		debugLog.info(message);	
		//extentReport.info(message);
		ExecutionManager.getSession().getTestcase().addTestStep(new TestStep("INFO",message));
		if(!(consoleLevel==ConsoleLogLevel.NO_STEP))
			consoleLog.info(message);		
	}
	public static void pass(String message){
		//reportsLog.info(message);
		debugLog.info(message);		
		//extentReport.pass(message);
		ExecutionManager.getSession().getTestcase().addTestStep(new TestStep("PASS",message));
		if(!(consoleLevel==ConsoleLogLevel.NO_STEP))
			consoleLog.info(message);		
	}
	private static void infoStartEndTest(String message){
		//reportsLog.info(message);
		debugLog.info(message);
		consoleLog.info(message);			
	}
	public static void debug(String message){		
		debugLog.debug(message);		
		if(consoleLevel==ConsoleLogLevel.DEBUG_STEP)
			consoleLog.debug(message);		
	}
	public static void fail(String message){		
		debugLog.fatal(message);
		//reportsLog.fatal(message);
		//extentReport.fail(message);
		Reporter.log(message);
		ExecutionManager.getSession().getTestcase().addTestStep(new TestStep("FAIL",message));
		if(!(consoleLevel==ConsoleLogLevel.NO_STEP))
			consoleLog.fatal(message);	
		Assert.fail();
	}
	public static void error(String message){		
		debugLog.error(message);
		//reportsLog.error(message);
		//extentReport.error(message);
		Reporter.log(message);
		ExecutionManager.getSession().getTestcase().addTestStep(new TestStep("ERROR",message));
		if(!(consoleLevel==ConsoleLogLevel.NO_STEP))
			consoleLog.error(message);	
		Assert.fail();
	}
	public static void fail(String message,String exceptionMessage){		
		debugLog.fatal(message);
		//reportsLog.fatal(message);
		//extentReport.fail(message);
		Reporter.log(message);
		ExecutionManager.getSession().getTestcase().addTestStep(new TestStep("FAIL",message));
//		testCase.addTestStep(new TestStep("FAIL",exceptionMessage));
		debugLog.fatal(exceptionMessage);
		//reportsLog.fatal(exceptionMessage);		
		if(!(consoleLevel==ConsoleLogLevel.NO_STEP)){
			consoleLog.fatal(message);
			consoleLog.fatal(exceptionMessage);	
		}
		Assert.fail();
	}
	public static void error(String message,String exceptionMessage){		
		debugLog.error(message);
		//reportsLog.error(message);
		//extentReport.error(message);
		Reporter.log(message);
		ExecutionManager.getSession().getTestcase().addTestStep(new TestStep("ERROR",message));
		debugLog.error(exceptionMessage);
		//reportsLog.error(exceptionMessage);		
		if(!(consoleLevel==ConsoleLogLevel.NO_STEP)){
			consoleLog.error(message);
			consoleLog.error(exceptionMessage);
		}
		Assert.fail();
	}
	public static void startTestCase(String testCaseName){		
		infoStartEndTest("************ " +testCaseName+ " has STARTED ************");		   		
	}
	
	public static void endTestCase(String testCaseName, boolean status, String message, MsWebDriver driver){	
		String screenShotFile = Constants.SCREENSHOT_FOLDER + testCaseName;
		String format = "png";

		try{							
			if(!status){							
					infoStartEndTest("************ " +testCaseName+ " has FAILED ************");
					if(RetryAnalyzer.isMaxRetryFailedTestCase()){
						takeScreenshot(driver, screenShotFile,format);	
						String screenShotFileName = testCaseName +"."+format;
						ExecutionManager.getSession().getTestcase().addTestStep(new TestStep("FAIL","Please refer to screenshot:", screenShotFileName));
						debug("Screenshot of failed testcase is "+screenShotFile + "\\"+screenShotFileName);
						printTestReport();
						RetryAnalyzer.setCountRetry(0);
					}
	//				Assert.assertTrue(status, testCaseName + " is FAILED.");
				
			}else{
				if(!message.equals("")){
					info(message);
				}
				takeScreenshot(driver, screenShotFile,format);	
				String screenShotFileName = testCaseName +"."+format;
				ExecutionManager.getSession().getTestcase().addTestStep(new TestStep("PASS","Please refer to screenshot:",screenShotFileName));
				debug("Screenshot of passed testcase is "+screenShotFile + "\\"+screenShotFileName);
				infoStartEndTest("************ " +testCaseName+ " has PASSED ************");
				printTestReport();
	//			Assert.assertTrue(status, testCaseName + " is PASSED." + message);
			}
		}catch(Exception e){
			printTestReport();
//			Assert.assertTrue(status, testCaseName + " is FAILED.");
		}
	}	
	public static void takeScreenshot(MsWebDriver driver, String screenShotName, String format) throws Exception{
		try{			
			File scrFile = driver.getScreenshotAsFile();			
			//File screenshotFile = new File(DTNConstant.SCREENSHOT_FOLDER + sTestCaseName +".png");
			File screenshotFile = new File(screenShotName+"."+format);
			//String relativeScreenShotPath = DTNConstant.relativeSCREENSHOT_FOLDER + sTestCaseName +".png";
			if (screenshotFile.exists()){
				FileUtils.deleteQuietly(screenshotFile);
			}
			FileUtils.copyFile(scrFile,screenshotFile);				
		} catch (Exception e){
			error("Class utilities | Method takeScreenshot | Exception occured while capturing ScreenShot : "+e.getMessage());
			throw new Exception();
		}
	}
	/**
	 * Capture a screenshot (full screen)
	 * as an image which will be saved into a file.
	 * @author Thao Le
	 *
	 */
	public static void captureFullScreenshot(String screenShotFile, String format){	 
        try {
            Robot robot = new Robot();             
            String fileName = screenShotFile+ "."+ format;
             
            Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            BufferedImage screenFullImage = robot.createScreenCapture(screenRect);
            ImageIO.write(screenFullImage, format, new File(fileName));                        
        } catch (AWTException | IOException e) {
        	error("Class ultilities | Method captureFullScreenshot | Exception occured while capturing ScreenShot : "+e.getMessage());			
        }	   
	}	
	/**
	 * Capture a screenshot (full screen)
	 * as an image which will be saved into a file. And add it into Extent report.
	 * @author Thao Le
	 *
	 */
	public static void captureAndReportFullScreenShot(String testCaseStatus,String screenShotFile, String format){	 
        try {
        	captureFullScreenshot(Constants.SCREENSHOT_FOLDER + screenShotFile, format);             
            String fileName = screenShotFile+ "."+ format;
            ExecutionManager.getSession().getTestcase().addTestStep(new TestStep(testCaseStatus,"Please refer to screenshot:",fileName));
			debug("Screenshot of testcase is "+screenShotFile + "\\"+fileName);
        } catch (Exception e) {
        	error("Class ultilities | Method captureFullScreenshot | Exception occured while capturing ScreenShot : "+e.getMessage());			
        }	   
	}	
	public static void reportFailWithScreenShot(String message,String exceptionMessage,String screenShotFile, String format){		
		debugLog.fatal(message);
		//reportsLog.fatal(message);
		//extentReport.fail(message);
		Reporter.log(message);
		ExecutionManager.getSession().getTestcase().addTestStep(new TestStep("FAIL",message));
		captureAndReportFullScreenShot("FAIL", screenShotFile, format);
		debugLog.fatal(exceptionMessage);
		//reportsLog.fatal(exceptionMessage);		
		if(!(consoleLevel==ConsoleLogLevel.NO_STEP)){
			consoleLog.fatal(message);
			consoleLog.fatal(exceptionMessage);	
		}
	}
	
	public static void printTestReport() {
	    ObjectLocator configProperties = new ObjectLocator(Constants.CONFIG_PROPERTIES_PATH);
	    String runExtendReport = configProperties.getPropertyValue("reportToExtentReport");
	    String runTestNGReport = configProperties.getPropertyValue("reportToTestNGReport");
	    if(runExtendReport.equals("true")) {
	    	ExecutionManager.getSession().getTestcase().printTestCaseAndTestStepsToExtentReport(extentReport);
	    }
	    if(runTestNGReport.equals("true")) {
	    	ExecutionManager.getSession().getTestcase().printTestCaseAndTestStepsToTestNGReport();	
	    }
	}
	
}
