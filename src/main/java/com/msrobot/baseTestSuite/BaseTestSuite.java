package com.msrobot.baseTestSuite;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.PropertyConfigurator;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentTest;
import com.msrobot.constant.Constants;
import com.msrobot.listeners.RetryAnalyzer;
import com.msrobot.logs.MsLog;
import com.msrobot.logs.TestCase;
import com.msrobot.msDriver.ExecutionManager;
import com.msrobot.msDriver.MsWebDriver;
import com.msrobot.report.ExtentManager;
import com.msrobot.report.ExtentTestManager;
import com.msrobot.ultilities.LanguageHandler;
import com.msrobot.ultilities.ObjectLocator;


/**
 * Base test suite to run before start Test Class
 * @author thao.le
 *
 */
public class BaseTestSuite{       
   public String className;
   public String userName;
   public String password;
   public ExtentTest parentTest;  
   public String TestDataPath;
   public MsWebDriver driver;
   public String testName;
   public TestCase testCase;
   public String testDescription = "";
   public String ULRTest;
   public LanguageHandler testData;
   public String browser;
   
   @BeforeSuite   (alwaysRun=true)
   @Parameters({"consoleLogLevel","userName","password","retryFailedTestCase","environmentTest","language"})
   public void TestSuiteSetUp(          
         @Optional String consoleLogLevel,
         @Optional String UserName,
         @Optional String Password,
         @Optional String retryFailedTestCase,
         @Optional String environmentTest,
         @Optional String Language) throws IOException{
      String activeEnvironment = "defaultEnvironment";
      BasicConfigurator.configure();       
      PropertyConfigurator.configure("src/test/resources/Properties/log4j.properties");
      String extentReportFolder = Constants.workingDir + "/reportLogs";
      String extentReportFile = "reportLogs/extentReport.html";
      String ConsoleLogLevel;
      String language;
      //Set value from project.properties file:
      ObjectLocator projectProperties = new ObjectLocator(Constants.PROJECT_PROPERTIES_PATH);
      Constants.MAX_RETRY_FAILED_TESTCASE = Integer.parseInt(projectProperties.getPropertyValue("retryFailedTestCase"));
      RetryAnalyzer.setRetryMaximum(Constants.MAX_RETRY_FAILED_TESTCASE);
      userName = projectProperties.getPropertyValue("userName");
      password = projectProperties.getPropertyValue("password");
      ConsoleLogLevel = projectProperties.getPropertyValue("consoleLogLevel");
      language = projectProperties.getPropertyValue("language");
      if(retryFailedTestCase!=null && !retryFailedTestCase.isEmpty()){
         Constants.MAX_RETRY_FAILED_TESTCASE = Integer.parseInt(retryFailedTestCase);
         RetryAnalyzer.setRetryMaximum(Constants.MAX_RETRY_FAILED_TESTCASE);
      }
      if(UserName!=null && !UserName.isEmpty()) {
          userName = UserName;
       }
       if(Password!=null && !Password.isEmpty()) {
          password = Password;
       }
       if(environmentTest!=null && !environmentTest.isEmpty()){
          activeEnvironment = environmentTest;
          ULRTest = projectProperties.getPropertyValue(activeEnvironment);
       }else{
    	   ULRTest = projectProperties.getPropertyValue(projectProperties.getPropertyValue(activeEnvironment));
       }
       if(consoleLogLevel!=null && !consoleLogLevel.isEmpty()){
    	   ConsoleLogLevel = consoleLogLevel;
       }
       if(Language!=null && !Language.isEmpty()){
    	   language = Language;
       }
      FileUtils.deleteQuietly(new File(extentReportFolder));
      new File(extentReportFolder).mkdir();
      this.className = this.getClass().getSimpleName();     
      ExtentManager.getReporter(extentReportFile);
       parentTest = ExtentTestManager.startModule(this.className);    
       MsLog.setExtentReport(parentTest);
       MsLog.setCustomLogLevel(Level.DEBUG, ConsoleLogLevel);
       testData = new LanguageHandler(Constants.LANGUAGE_PROPERTIES_PATH, Constants.LANGUAGE_PROPERTIES_FILENAME, language);
       
   }
   
   @BeforeMethod (alwaysRun=true)
   public void TestMethodSetUp(Method method) throws Exception {
      testName = method.getName();      
      String threadName = className + "_" + testName;
      Thread.currentThread().setName(threadName);    
      testCase = new TestCase(this.className, testName, testDescription);
      MsLog.setTestCase(testCase);

   }  
   
   @AfterSuite (alwaysRun=true)
   public void SuiteTearDown(){
      ExtentManager.getReporter().flush();
   }
   
}