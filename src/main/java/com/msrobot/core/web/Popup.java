package com.msrobot.core.web;

import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.msrobot.constant.Constants;
import com.msrobot.logs.MsLog;

	/**
	 * This class to handle all actions in popup window or second window 
	 * (For Windows-based alert pop-ups - this class doesn't handle Web-based alert pop-ups)
	 * @author thao.le
	 *
	 */
	public class Popup {
		/**
		 * To wait until popup displayed with expected text
		 * @param driver
		 * @param text
		 * @param seconds
		 * @return
		 * @throws Exception
		 * @author Thao Le
		 */
		public static boolean waitUntilPopupDisplayWithText(WebDriver driver, String text,int seconds) throws Exception{
			boolean status = false;
			MsLog.debug("+ Wait until popup display with test '"+ text+"'");
			for(int i=0;i<seconds;i++){
				if(Window.waitUntilNumberOfWindowsAre(driver,1)){
					String parentWindowHandler = driver.getWindowHandle(); // Store parent window
					String subWindowHandler = null;
					Set<String> handles = driver.getWindowHandles(); // get all window handles
					Iterator<String> iterator = handles.iterator();
					while (iterator.hasNext()){
					    subWindowHandler = iterator.next();
					}
					driver.switchTo().window(subWindowHandler); // switch to popup window
					if(driver.findElement(By.tagName("body")).getText().contains(text)){
						driver.switchTo().window(parentWindowHandler); // switch to parent window
						status = true;
						break;
					}
				}
				try {
					Thread.sleep(1000);
				} catch (Exception e) {
					MsLog.fail("Popup doesn't display '"+text+"'",e.toString());
					throw(e);
				}
			}
			if(!status){
				MsLog.fail("Popup doesn't display '"+text+"'","");
				throw new Exception("WrongTextInPopUp");
			}
			return status;		
		}
		/**
		 * To click on button to close popup
		 * @param driver
		 * @param element
		 * @return
		 * @throws Exception
		 * @author Thao Le
		 */
		public static boolean clickButtonToClosePopup(WebDriver driver, Element element) throws Exception{
			MsLog.debug("+ Click on " +element.elementName +" on the Popup");
			if(Window.waitUntilNumberOfWindowsAre(driver,1)){
				String parentWindowHandler = driver.getWindowHandle(); // Store parent window
				String subWindowHandler = null;
				Set<String> handles = driver.getWindowHandles(); // get all window handles
				Iterator<String> iterator = handles.iterator();
				while (iterator.hasNext()){
				    subWindowHandler = iterator.next();
				}
				driver.switchTo().window(subWindowHandler); // switch to popup window
				try{				
					element.click();
					Page.waitForPageLoad(driver);
					Thread.sleep(1000);
					driver.switchTo().window(parentWindowHandler); // switch to parent window
					return true;
				}catch(Exception e){	
					MsLog.fail("Cannot click on "+element.elementName+" on PopUp","Exception occured - "+e.toString() +
							" at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber());
					throw(e);				
				}			
			}
			throw new Exception("UnableToClickbuttonOnPopUp");
		}
		/**
		 * To move the focus on Popup so that elements on popup can be implemented
		 * @param driver
		 * @return
		 * @author Thao Le
		 */
		public static boolean focusOnPopup(WebDriver driver){
			try{
				if(Window.waitUntilNumberOfWindowsAre(driver,1)){				
					String subWindowHandler = null;
					Set<String> handles = driver.getWindowHandles(); // get all window handles
					Iterator<String> iterator = handles.iterator();
					while (iterator.hasNext()){
					    subWindowHandler = iterator.next();
					}
					driver.switchTo().window(subWindowHandler); // switch to popup window
					return true;			
				}
			}catch(NoSuchWindowException e){
				throw(e);
			}
			return false;		
		}
		/**
		 * To get text on popup
		 * @param driver
		 * @return
		 * @author Thao Le
		 */
		public static String getTextOnPopup(WebDriver driver){		
			String textOnPopup = "";	
			try{
				if(Window.waitUntilNumberOfWindowsAre(driver,1)){
					String parentWindowHandler = driver.getWindowHandle(); // Store parent window
					String subWindowHandler = null;
					Set<String> handles = driver.getWindowHandles(); // get all window handles
					Iterator<String> iterator = handles.iterator();
					while (iterator.hasNext()){
					    subWindowHandler = iterator.next();
					}
					driver.switchTo().window(subWindowHandler); // switch to popup window
					textOnPopup = driver.findElement(By.tagName("body")).getText();	
					driver.switchTo().window(parentWindowHandler); // switch to parent window
				}
			}catch(Exception e){			
				e.printStackTrace();
			}
			return textOnPopup;	
		}
		/**
		 * Click button to close popup using javascript.
		 * @author Thao Le
		 * @WebDriver
		 * @param element	: Button item to click.
		 * @String js		: javascript string.
		 * @return 			: True always
		 * @throws Exception 
		 */		
		public static boolean clickButtonToClosePopup(WebDriver driver, Element element, String js) throws Exception{
			MsLog.debug("+ Click on " +element.elementName +" using javascript");
			new WebDriverWait(driver, Constants.EXPLICIT_WAITTIME).until(ExpectedConditions.visibilityOfElementLocated(element.getBy()));
			
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript(js);
			Page.waitForPageLoad(driver); //seconds
			MsLog.debug("Executing javascript on Element " + element.elementName + " completed.");
			return true;
		}
		/**
		 * To wait until popup displayed with expected text
		 * @param driver
		 * @param text
		 * @return
		 * @throws Exception
		 * @author Thao Le
		 */
		public static boolean waitUntilPopupDisplayWithText(WebDriver driver, String text) throws Exception{
			boolean status = false;
			int seconds = Constants.EXPLICIT_WAITTIME;
			MsLog.info("+ Wait until popup display with message '"+ text+"'");
			String body;
			for(int i=0;i<seconds;i++){
				if(Window.waitUntilNumberOfWindowsAre(driver,1)){
					String parentWindowHandler = driver.getWindowHandle(); // Store parent window
					String subWindowHandler = null;
					Set<String> handles = driver.getWindowHandles(); // get all window handles
					Iterator<String> iterator = handles.iterator();
					while (iterator.hasNext()){
					    subWindowHandler = iterator.next();
					}
					driver.switchTo().window(subWindowHandler); // switch to popup window
					body = driver.findElement(By.tagName("body")).getText();
					if(body.contains(text)){
						driver.switchTo().window(parentWindowHandler); // switch to parent window
						status = true;
						break;
					}
				}
				try {
					Thread.sleep(1000);
				} catch (Exception e) {
					MsLog.fail("Popup doesn't display '"+text+"'","Exception occured - "+e.toString() +
						" at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber());
					throw(e);
				}
			}
			if(!status){
				MsLog.fail("Popup doesn't display '"+text+"'","");
				throw new Exception("WrongTextInPopUp");
			}else{
				MsLog.pass("Popup with message '"+text+"' is displayed.");
			}
			return status;		
		}
	}
