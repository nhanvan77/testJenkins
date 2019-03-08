package com.msrobot.core.web;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.msrobot.constant.Constants;
import com.msrobot.logs.MsLog;

public class Frame {
		
	public static void findAndSwitchToFrame(WebDriver driver, String frame) {
		int timeout = Constants.EXPLICIT_WAITTIME;
		
		driver.switchTo().defaultContent();
		WebDriverWait wait = new WebDriverWait(driver,timeout);
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frame));		
	}
	
	public static String getCurrentFrameName(WebDriver driver) {
		String frameName = ((JavascriptExecutor) driver).executeScript("return self.name").toString();
		if (frameName.isEmpty())
			frameName = null;
		return frameName;
	}

	public static void moveToDefaultContext(WebDriver driver) {
		driver.switchTo().defaultContent();
	}

	public static void moveToParentFrame(WebDriver driver) {
		driver.switchTo().parentFrame();
	}

	public static void moveToSiblingFrame(WebDriver driver, String frameName) {
		moveToParentFrame(driver);
		/*driver.switchTo().defaultContent();
		driver.switchTo().parentFrame();
		driver.switchTo().frame(frameName);*/
		switchToFrame(driver, frameName);
	}

	public static void moveToSiblingFrame(WebDriver driver, By byFrameLocator) {
		moveToParentFrame(driver);
		switchToFrame(driver, byFrameLocator);
	}

	public static void moveToChildFrame(WebDriver driver, String frameName) {
		switchToFrame(driver, frameName);
	}

	public static void moveToChildFrame(WebDriver driver, By byFrameLocator) {
		switchToFrame(driver, byFrameLocator);
	}

	public static void moveToChildFrame(WebDriver driver, String[] frameName) {
		for (int x = 0; x < frameName.length; x++) {
			moveToChildFrame(driver, frameName[x]);
		}
	}	
	/**
	 * Move from parent frame to child frame, then to sub child frame
	 * @param driver
	 * @param parentFrame
	 * @param childFrame
	 * @param subChildFrame
	 * @author Thao Le
	 */
	public static void moveToChildFrame(WebDriver driver, String parentFrame, String childFrame, By subChildFrame) {
		try{
			MsLog.debug("Move from '"+ parentFrame +"' iframe to "+childFrame+" iframe, then to '"+subChildFrame+"' iframe");
			moveToDefaultContext(driver);
			moveToChildFrame(driver, parentFrame);
			moveToChildFrame(driver, childFrame);
			moveToChildFrame(driver, subChildFrame);
		}catch(Exception e){
			MsLog.fail("Fail to move from '"+ parentFrame +"' iframe to "+childFrame+" iframe, then to '"+subChildFrame+"' iframe");
		}
	}	
	/**
	 * Move from parent frame to child frame
	 * @param driver
	 * @param parentFrame
	 * @param childFrame
	 * @author Thao Le
	 */
	public static void moveToChildFrame(WebDriver driver, String parentFrame, String childFrame) {
		try{
			MsLog.debug("Move from '"+parentFrame +"' iframe to '"+childFrame+"' iframe");
			moveToDefaultContext(driver);
			moveToChildFrame(driver, parentFrame);
			moveToChildFrame(driver, childFrame);
		}catch(Exception e){
			MsLog.fail("Fail to move from '"+parentFrame +"' iframe to '"+childFrame+"' iframe",e.toString());
		}
	}	
	
	public static void waitFramesDisplay(WebDriver driver, String parentFrame, String childFrame, int timeout) {
		driver.switchTo().defaultContent();
		WebDriverWait wait = new WebDriverWait(driver,timeout);					
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(parentFrame));		
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(childFrame));
	}
	public static void moveToChildFrame(WebDriver driver, By[] frameName) {
		for (int x = 0; x < frameName.length; x++) {
			moveToChildFrame(driver, frameName[x]);
		}
	}

	private static void switchToFrame(WebDriver driver, String frameName) {
		int timeout = 5;
		
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameName));
	}

	private static void switchToFrame(WebDriver driver, By byFrameLocator) {
		int timeout = 5;
		
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(byFrameLocator));
	}
}