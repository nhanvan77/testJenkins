package com.msrobot.core.web;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.msrobot.constant.Constants;
import com.msrobot.logs.MsLog;
import com.msrobot.ultilities.ExtendedExpectedConditions;


public class Window {
		public static boolean waitUntilNumberOfWindowsAre(WebDriver driver, int expectedNumberOfWindows){
			return waitUntilNumberOfWindowsAre(driver, expectedNumberOfWindows, Constants.EXPLICIT_WAITTIME);
		}

		//Description: wait until until expected number of window in timeout
		public static boolean waitUntilNumberOfWindowsAre(WebDriver driver, int expectedNumberOfWindows, int timeoutInSeconds){									
			try{	    	
				WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);	    	
				wait.until(ExpectedConditions.numberOfWindowsToBe(expectedNumberOfWindows));				
			}catch(TimeoutException e){	    			
				return false;
			}		
			return true;
		}

		//Description:
		public static boolean waitUntilWindowExistsTitleContains(WebDriver driver, String windowNameOrHandle, int timeoutInSeconds){
			MsLog.debug("Wait until window exists with title contains '"+windowNameOrHandle+"' in "+timeoutInSeconds +" seconds.");		
			
		    try{	    	
				WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);	    	
				wait.until(ExtendedExpectedConditions.findWindowContainsTitleAndSwitchToIt(windowNameOrHandle));			
		    }catch(TimeoutException e){	    	
		    	MsLog.fail("Failed to find Window with title of [ " + windowNameOrHandle + " ]",e.toString() +
		    			" at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber());
		    	return false;
		    }		
		    return true;
		}
}
