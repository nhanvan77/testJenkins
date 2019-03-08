package com.msrobot.logs;

import java.util.ArrayList;
import java.util.List;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityModelProvider;
import com.msrobot.listeners.RetryAnalyzer;
import com.msrobot.msDriver.ExecutionManager;
import com.msrobot.report.MsExtentReport;

/**
 * @author Automation
 *
 */
public class TestCase {
	private List<TestStep> testSteps = new ArrayList<>();
	private String moduleName = null;
	private String testCaseName = null;
	private String testCaseDescription = null;		

	public TestCase(String moduleName, String testCaseName, String testCaseDescription) {				
		this.moduleName = moduleName;
		this.testCaseName = testCaseName;
		this.testCaseDescription = testCaseDescription;
		//ExecutionManager.getSession().setTestcase(this);
	}
	public void setModuleName(String testModuleName){
		moduleName = testModuleName;
	}
	public String getModuleName(){
		return moduleName;
	}
	public void setTestCaseName(String testName){
		testCaseName = testName;
	}
	public void setTestCaseDescription(String testDescription){
		//testCaseDescription = testDescription;
		ExecutionManager.getSession().getTestcase().testCaseDescription = testDescription;
	}
	public String getTestCaseDescription(){		
		//return testCaseDescription;
		return ExecutionManager.getSession().getTestcase().testCaseDescription;
	}
	public String getTestCaseName(){
		return testCaseName;
	}
	public void addTestStep(TestStep testStep){
		testSteps.add(testStep);
	}	
	
	/**
	 * To print test steps to Extent Report
	 */
	public void printTestCaseAndTestStepsToExtentReport(ExtentTest parentReport){			
		String testDescription;
		if(RetryAnalyzer.getCountRetry()>0){
			testDescription = "RETRY "+RetryAnalyzer.getCountRetry()+" TIMES DUE TO PREVIOUS TEST RUN IS FAILED - "+getTestCaseDescription();
		}else{
			testDescription = getTestCaseDescription();
		}		
		ExtentTest extentReport = MsExtentReport.startNode(parentReport, testCaseName, testDescription);
		for(TestStep testStep:testSteps){										
			if(testStep.getTestDescription()!=null && testStep.getMediaFile()!=null){
				printTestStep(extentReport,testStep.getStatus(),testStep.getTestDescription(),testStep.getMediaFile());
			}else{
				if(testStep.getException()!=null && testStep.getMediaFile()!=null){
					printTestStep(extentReport,testStep.getStatus(),testStep.getException(),testStep.getMediaFile());
				}else{
					if(testStep.getTestDescription()!=null){
						printTestStep(extentReport,testStep.getStatus(),testStep.getTestDescription());	
					}else if(testStep.getException()!=null){
						printTestStep(extentReport,testStep.getStatus(),testStep.getException());	
					}
				}
			}
		}		
	}
	
	/**
	 * To print test steps to TestNG Report
	 */
	public void printTestCaseAndTestStepsToTestNGReport(){			
		String testDescription;
		if(RetryAnalyzer.getCountRetry()>0){
			testDescription = "RETRY "+RetryAnalyzer.getCountRetry()+" TIMES DUE TO PREVIOUS TEST RUN IS FAILED - "+getTestCaseDescription();
		}else{
			testDescription = getTestCaseDescription();
		}		
		testNGReport.log(testDescription);
		for(TestStep testStep:testSteps){										
			if(testStep.getTestDescription()!=null && testStep.getMediaFile()!=null){
				printTestStepTestNGReport(testStep.getStatus(),testStep.getLogTime(),testStep.getTestDescription(),testStep.getScreenShotFilePath());
			}else{
				if(testStep.getException()!=null && testStep.getMediaFile()!=null){
					printTestStepTestNGReport(testStep.getStatus(),testStep.getLogTime(),testStep.getException(),testStep.getScreenShotFilePath());
				}else{
					if(testStep.getTestDescription()!=null){
						printTestStepTestNGReport(testStep.getStatus(),testStep.getLogTime(),testStep.getTestDescription());	
					}else if(testStep.getException()!=null){
						printTestStepTestNGReport(testStep.getStatus(),testStep.getLogTime(),testStep.getException());	
					}
				}
			}
		}		
	}
	/**
	 * To print into Extent report with Test Description
	 * @param extentReport
	 * @param status
	 * @param testDescription
	 */
	private void printTestStep(ExtentTest extentReport,String status,String testDescription){		
		switch (status){
			case "PASS":	extentReport.pass(testDescription);
							break;		
			case "FAIL":	extentReport.fail(testDescription);
							break;
			case "FATAL":	extentReport.debug(testDescription);
							break;					
			case "INFO":	extentReport.info(testDescription);
							break;		
			case "WARNING":	extentReport.warning(testDescription);
							break;
			case "ERROR":	extentReport.error(testDescription);
							break;		
			case "SKIP":	extentReport.skip(testDescription);
							break;
			case "DEBUG":	extentReport.debug(testDescription);
							break;		

		}
	}
	/**
	 * * To print into Extent report with exception message
	 * @param extentReport
	 * @param status
	 * @param exception
	 */
	private void printTestStep(ExtentTest extentReport,String status,Throwable exception){		
		switch (status){
			case "PASS":	extentReport.pass(exception);
							break;		
			case "FAIL":	extentReport.fail(exception);
							break;
			case "FATAL":	extentReport.debug(exception);
							break;	
			case "INFO":	extentReport.info(exception);
							break;		
			case "WARNING":	extentReport.warning(exception);
							break;
			case "ERROR":	extentReport.error(exception);
							break;		
			case "SKIP":	extentReport.skip(exception);
							break;
			case "DEBUG":	extentReport.debug(exception);
							break;		

		}
	}
	
	/**
	 * To print into Extent report with Test Description and screen shot
	 * @param extentReport
	 * @param status
	 * @param testDescription
	 * @param mediaFile
	 */
	private void printTestStep(ExtentTest extentReport,String status, String testDescription, MediaEntityModelProvider mediaFile){				
		switch (status){
			case "PASS":	extentReport.pass(testDescription,mediaFile);
							break;		
			case "FAIL":	extentReport.fail(testDescription,mediaFile);
							break;
			case "FATAL":	extentReport.debug(testDescription,mediaFile);
							break;
			case "INFO":	extentReport.info(testDescription,mediaFile);
							break;		
			case "WARNING":	extentReport.warning(testDescription,mediaFile);
							break;
			case "ERROR":	extentReport.error(testDescription,mediaFile);
							break;		
			case "SKIP":	extentReport.skip(testDescription,mediaFile);
							break;
			case "DEBUG":	extentReport.debug(testDescription,mediaFile);
							break;				
		}
	}
	
	/**
	 * To print into Extent report with exception message and screen shot
	 * @param extentReport
	 * @param status
	 * @param exception
	 * @param mediaFile
	 */
	private void printTestStep(ExtentTest extentReport,String status, Throwable exception, MediaEntityModelProvider mediaFile){				
		switch (status){
			case "PASS":	extentReport.pass(exception,mediaFile);
							break;		
			case "FAIL":	extentReport.fail(exception,mediaFile);
							break;
			case "FATAL":	extentReport.debug(exception,mediaFile);
							break;
			case "INFO":	extentReport.info(exception,mediaFile);
							break;		
			case "WARNING":	extentReport.warning(exception,mediaFile);
							break;
			case "ERROR":	extentReport.error(exception,mediaFile);
							break;		
			case "SKIP":	extentReport.skip(exception,mediaFile);
							break;
			case "DEBUG":	extentReport.debug(exception,mediaFile);
							break;				
		}
	}	
	
	/**
	 * To print into TestNG report with Test Description
	 * @param status
	 * @param testDescription
	 */
	private void printTestStepTestNGReport(String status, String time,String testDescription){		
		switch (status){
			case "PASS":	testNGReport.pass(status,time,testDescription);
							break;		
			case "FAIL":	testNGReport.fail(status,time,testDescription);
							break;
			case "FATAL":	testNGReport.debug(status,time,testDescription);
							break;					
			case "INFO":	testNGReport.info(status,time,testDescription);
							break;		
			case "WARNING":	testNGReport.warning(status,time,testDescription);
							break;
			case "ERROR":	testNGReport.error(status,time,testDescription);
							break;		
			case "SKIP":	testNGReport.skip(status,time,testDescription);
							break;
			case "DEBUG":	testNGReport.debug(status,time,testDescription);
							break;		
		}
	}
	
	/**
	 * To print into TestNG report with exception message
	 * @param status
	 * @param testDescription
	 * @param exception
	 */
	private void printTestStepTestNGReport(String status, String time,Throwable exception){		
		switch (status){
			case "PASS":	testNGReport.pass(status,time,exception.toString());
							break;		
			case "FAIL":	testNGReport.fail(status,time,exception.toString());
							break;
			case "FATAL":	testNGReport.debug(status,time,exception.toString());
							break;					
			case "INFO":	testNGReport.info(status,time,exception.toString());
							break;		
			case "WARNING":	testNGReport.warning(status,time,exception.toString());
							break;
			case "ERROR":	testNGReport.error(status,time,exception.toString());
							break;		
			case "SKIP":	testNGReport.skip(status,time,exception.toString());
							break;
			case "DEBUG":	testNGReport.debug(status,time,exception.toString());
							break;		
		}
	}
	
	/**
	 * To print into TestNG report with exception message and screen shot
	 * @param status
	 * @param exception
	 * @param screenShotFileName
	 */
	private void printTestStepTestNGReport(String status, String time,Throwable exception, String screenShotFileName){		
		switch (status){
			case "PASS":	testNGReport.pass(status,time,exception.toString(),screenShotFileName);
							break;		
			case "FAIL":	testNGReport.fail(status,time,exception.toString(),screenShotFileName);
							break;
			case "FATAL":	testNGReport.debug(status,time,exception.toString(),screenShotFileName);
							break;					
			case "INFO":	testNGReport.info(status,time,exception.toString(),screenShotFileName);
							break;		
			case "WARNING":	testNGReport.warning(status,time,exception.toString(),screenShotFileName);
							break;
			case "ERROR":	testNGReport.error(status,time,exception.toString(),screenShotFileName);
							break;		
			case "SKIP":	testNGReport.skip(status,time,exception.toString(),screenShotFileName);
							break;
			case "DEBUG":	testNGReport.debug(status,time,exception.toString(),screenShotFileName);
							break;		
		}
	}
	
	/**
	 * To print into TestNG report with Test Description and screen shot
	 * @param status
	 * @param exception
	 * @param screenShotFileName
	 */
	private void printTestStepTestNGReport(String status, String time,String testDescription, String screenShotFileName){		
		switch (status){
			case "PASS":	testNGReport.pass(status,time,testDescription,screenShotFileName);
							break;		
			case "FAIL":	testNGReport.fail(status,time,testDescription,screenShotFileName);
							break;
			case "FATAL":	testNGReport.debug(status,time,testDescription,screenShotFileName);
							break;					
			case "INFO":	testNGReport.info(status,time,testDescription,screenShotFileName);
							break;		
			case "WARNING":	testNGReport.warning(status,time,testDescription,screenShotFileName);
							break;
			case "ERROR":	testNGReport.error(status,time,testDescription,screenShotFileName);
							break;		
			case "SKIP":	testNGReport.skip(status,time,testDescription,screenShotFileName);
							break;
			case "DEBUG":	testNGReport.debug(status,time,testDescription,screenShotFileName);
							break;		
		}
	}
}


