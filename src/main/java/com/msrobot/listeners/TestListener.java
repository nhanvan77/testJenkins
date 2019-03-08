package com.msrobot.listeners;

import java.util.Date;
import java.util.Iterator;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.msrobot.baseTestSuite.BaseTestSuite;
import com.msrobot.logs.MsLog;
import com.msrobot.msDriver.ExecutionManager;
import com.msrobot.msDriver.MsWebDriver;

public class TestListener implements ITestListener {
	
	@Override
	public void onTestStart(ITestResult result) {
		MsLog.startTestCase(result.getMethod().getConstructorOrMethod().getName());
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		RetryAnalyzer.setCountRetry(0);
		Object instance = result.getInstance();
		//MsWebDriver driver = BaseTestSuite.class.cast(instance).driver;
		MsWebDriver driver = ExecutionManager.getSession();
		MsLog.endTestCase(result.getMethod().getConstructorOrMethod().getName(),true,"",driver); 
	}

	@Override
	public void onTestFailure(ITestResult result) {
		// TODO Auto-generated method stub
		Object instance = result.getInstance();
		//MsWebDriver driver = BaseTestSuite.class.cast(instance).driver;
		MsWebDriver driver = ExecutionManager.getSession();
		MsLog.endTestCase(result.getMethod().getConstructorOrMethod().getName(),false,"",driver);
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFinish(ITestContext listner) {					
		Iterator<ITestResult> skippedTest;		  
		Iterator<ITestResult> passedTest;	
		Iterator<ITestResult> failedTest;
		ITestResult currentPassedTest;
		ITestResult currentFailedTest;
		String skippedTestCase;
		String passedTestCase;
		String failedTestCase;
		Date startTime = listner.getStartDate();
		Date endTime = listner.getEndDate();
		
//		Long totalTime = endTime-startTime;
//		String reportTotalTime = millisToTimeConversion(totalTime);
//		ExtentTestManager.reportTotalTime(startTime.toString(),
//				endTime.toString(),"1:0:0");
		
		boolean removeSkippedTest = false;		
		skippedTest = listner.getSkippedTests().getAllResults().iterator();	
		while(skippedTest.hasNext()){
		  skippedTestCase = skippedTest.next().getMethod().getMethodName();
		  removeSkippedTest = false;
		  passedTest = listner.getPassedTests().getAllResults().iterator();	
		  failedTest = listner.getFailedTests().getAllResults().iterator();
		  while(passedTest.hasNext()){
			  currentPassedTest = passedTest.next();
			  passedTestCase = currentPassedTest.getMethod().getMethodName();			  
			  if(skippedTestCase.equals(passedTestCase)){
				  //Remove skipped Test case					  
				  skippedTest.remove();				  
				  currentPassedTest.getMethod().setDescription("RE-RUN this test case due to the previous test run is failed.");
				  removeSkippedTest = true;
				  break;
			  }
		  }		
		  while(failedTest.hasNext() && !removeSkippedTest){
			  currentFailedTest = failedTest.next();
			  failedTestCase = currentFailedTest.getMethod().getMethodName();
			  if(skippedTestCase.equals(failedTestCase)){
				  //Remove skipped Test case					  
				  skippedTest.remove();				 
				  currentFailedTest.getMethod().setDescription("RE-RUN this test case due to the previous test run is failed.");
				  break;
			  }
		  }	
		}	
		
	}
	
}
