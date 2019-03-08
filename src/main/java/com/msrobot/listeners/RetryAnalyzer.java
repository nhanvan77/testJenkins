package com.msrobot.listeners;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import com.msrobot.constant.Constants;
import com.msrobot.logs.MsLog;


public class RetryAnalyzer implements IRetryAnalyzer {
	private int countRetry = 0;
	public static int startRetry = 0;
	private static int retryMaximum = Constants.MAX_RETRY_FAILED_TESTCASE;
	private static boolean maxRetryFailedTestCase= startRetry==retryMaximum?true:false;
	
//	public static int retryMaximum = DTNConstant.MAX_RETRY_FAILED_TESTCASE;
//	public static boolean maxRetryFailedTestCase = startRetry==retryMaximum?true:false;
	
	public boolean retry(ITestResult result) {		
		if(countRetry<retryMaximum){
			countRetry++;		
			MsLog.debug("Try to re-run test case again, count retry = "+ countRetry);
			startRetry = countRetry;
			if(countRetry==retryMaximum){
				maxRetryFailedTestCase = true;
			}else
				maxRetryFailedTestCase = false;
			return true;
		}
		return false;
	}
	public static void setCountRetry(int startCount){
		startRetry = startCount;
		if(startRetry==retryMaximum)
			maxRetryFailedTestCase=true;
		else
			maxRetryFailedTestCase = false;
	}	
	public static boolean isMaxRetryFailedTestCase(){		
		return maxRetryFailedTestCase;		
	}
	public static int getRetryMaxminum(){
		return retryMaximum;
	}
	public static int getCountRetry(){
		return startRetry;
	}
	public static void setRetryMaximum(int retryMaximum) {
		RetryAnalyzer.retryMaximum = retryMaximum;
		maxRetryFailedTestCase= startRetry==retryMaximum?true:false;
	}
}