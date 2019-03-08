package com.msrobot.core.web;

import java.time.Duration;
import java.time.LocalTime;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.Screen;

import com.msrobot.constant.Constants;
import com.msrobot.logs.MsLog;
import com.msrobot.msDriver.MsWebDriver;
import com.msrobot.ultilities.ImageRecognition;


/**
 * To handle all methods related with Page such as verify elements on Page, 
 * verify title of page, verify text in page, wait for page completely loaded
 * 
 * @author thao.le
 *
 */
public class Page {
	/**
	 * To verify if expected elements are displayed on page
	 * @param PageName
	 * @param elements
	 * @throws Exception
	 */
	public static void verifyPageWithElement(String PageName,Element...elements) throws Exception{
		String listElementNames = "";		
		int i=0;		
		for(Element element:elements){	
			MsLog.debug("Verify "+ element.getElementName()+" is displayed on "+ PageName);
			try{
				if(i>0)
					listElementNames = element.getElementName()+ "," +listElementNames;
				else
					listElementNames = element.getElementName();
				if(!element.isDisplayed()){			
					MsLog.info("*** Expected result:"+ element.getElementName()+" is displayed on "+ PageName);
					MsLog.fail("*** Actual result:"+ element.getElementName()+" is NOT displayed on "+ PageName,"Class: core.Page | Method: verifyPageWithElement - Element: "+element.getElementName()+ " - locator - "+element.getBy().toString());	       		
					throw new Exception("Missing element "+ element.getElementName());
				}
				i++;
			}catch(Exception e){
				MsLog.info("*** Expected result:"+ element.getElementName()+" is displayed on "+ PageName);
	       		MsLog.fail("*** Actual result:"+ element.getElementName()+" is NOT displayed on "+ PageName,"Class: core.Page | Method: verifyPageWithElement - Element: "+element.getElementName()+ " - locator - "+element.getBy().toString()+" - Exception: "+ e.toString());	       		   		       	
				throw new Exception("Missing element "+ element.getElementName());			
			}
		}				
		MsLog.pass("*** Actual result:"+PageName + " displayed with "+ listElementNames + " successfully");
	}
	
	
		/**
		 * To verify if text is displayed on page successfully.
		 * @param driver
		 * @param pageTitle
		 * @param text
		 * @throws Exception
		 * @author Thao Le
		 */
		public static void verifyPageDisplayedWithText(WebDriver driver,String pageTitle,String text) throws Exception{				
			boolean bStatus = false;	
			String bodyText = "";
			int timeOut = Constants.EXPLICIT_WAITTIME;
			try{	
				MsLog.debug("Verify if "+ text +" is displayed on "+pageTitle+" page");
				for (int i = 0;i<timeOut; i++) {						
					bodyText = driver.getPageSource();
					if(bodyText.contains(text)){
			    		bStatus = true;	
			    		break;		    		 
			    	}
			    	Thread.sleep(1000);// in milliseconds		    
			    }					
			}catch(Exception e){		
				MsLog.info("*** Expected result: '"+text+"' is displayed on "+ pageTitle+" page");
	       		MsLog.fail("*** Actual result: '"+text+"' is NOT displayed on "+ pageTitle+" page","Class: core.Page | Method: verifyPageDisplayedWithText - Exception: "+ e.toString());	       		   		       	
				throw new Exception("Missing Text on Page");			
			}
			if (!bStatus) {
				MsLog.info("*** Expected result: '"+text+"' is displayed on "+ pageTitle+" page");
				MsLog.fail("*** Actual result: '"+text +"' is NOT displayed on " + pageTitle +" page.");
				throw new Exception("Missing Text on Page");
			}
			MsLog.pass("*** Actual result:'"+text+"' is displayed on "+ pageTitle+" page successfully");
		}
		
		/**
		 * To validate page Title is same as expected
		 * @param driver
		 * @param pageTitle
		 * @return
		 * @author Thao Le
		 */
		private static boolean validatePage(WebDriver driver,String pageTitle){	
			String currentPage;
			try{		
				currentPage = driver.getTitle();
				if(currentPage.equals(pageTitle)){
					return true;								
				}else{					
					return false;
				}
			}catch(Exception e){						
				return false;
			}		
		}
		
		/**
		 * To wait for page loading completely
		 * @param driver
		 * @throws InterruptedException
		 * @author Thao Le
		 */
		public static void waitForPageLoad(WebDriver driver) throws InterruptedException{
			int seconds = 50;
			JavascriptExecutor js = (JavascriptExecutor)driver;	
			MsLog.debug("Waiting for page load complete...");			
			LocalTime startTime = LocalTime.now();		
			LocalTime endTime;
			Long second;
			Long milliSecond;			
//			for (int i=0; i<seconds; i++){ 
//			   try {
//				   Thread.sleep(1000);
//			   }catch (InterruptedException e) {
//				   throw(e);
//			   } 
//			   //To check page ready state.
//			   if (js.executeScript("return document.readyState").toString().equals("complete")){ 
//				   break; 
//			   }
//			}
			
			waitForDomLoad(driver);
			endTime = LocalTime.now();	
			second = Duration.between(startTime, endTime).getSeconds();
			milliSecond = Duration.between(startTime, endTime).toMillis()-(second*1000);
			MsLog.debug("Page load completed. Loading time (hh:mm:ss,sss) = "+Duration.between(startTime, endTime).toHours()+":"+ Duration.between(startTime, endTime).toMinutes()+":"+ second +","+milliSecond);
		}
				

		/**
		 * Wait until popup message to show up and disappear
		 * @author Quinn Song
		 * @param driver
		 * @param by		: Element by
		 * @param seconds	: Wait timeout
		 * @return			: True if popup is handled successfully; false if exception occurs
		 * @throws Exception
		 */
		public static boolean waitUntilPopupDisappear(WebDriver driver, By by,int seconds) throws Exception{	
			//	
			MsLog.debug("Handling Modal popup...");			
			LocalTime startTime = LocalTime.now();		
			LocalTime endTime;
			Long second;
			Long milliSecond;
			try {
				MsLog.debug("Waiting until Modal popup shows up...");
				new WebDriverWait(driver, seconds).until(ExpectedConditions.presenceOfElementLocated(by));
				MsLog.debug("Waiting until Modal popup disappears...");
				new WebDriverWait(driver, seconds).until(ExpectedConditions.invisibilityOfElementLocated(by));
				endTime = LocalTime.now();	
				second = Duration.between(startTime, endTime).getSeconds();
				milliSecond = Duration.between(startTime, endTime).toMillis()-(second*1000);
				MsLog.debug("Popup alert completed. Loading time (hh:mm:ss,sss) = "+Duration.between(startTime, endTime).toHours()+":"+ Duration.between(startTime, endTime).toMinutes()+":"+ second +","+milliSecond);			
				return true;
			}catch(Exception e) {
				MsLog.fail("Popup message is not displayed.", "Class: dtn.automation.core | Method: waitUntilPopupDisappear | Exception occured - "+ e.getMessage());
				MsLog.debug("Popup alert is not displayed after " + seconds + " seconds.");
				return false;
		    }
		}
		public static void waitUntilPopupDisappear(WebDriver driver, By by) throws Exception{	
			//	
			MsLog.debug("Handling Modal popup...");			
			LocalTime startTime = LocalTime.now();		
			LocalTime endTime;
			Long second;
			int timeOut = 1;
			Long milliSecond;
			try {
				MsLog.debug("Waiting until Modal popup shows up...");
				new WebDriverWait(driver, timeOut).until(ExpectedConditions.presenceOfElementLocated(by));
				MsLog.debug("Waiting until Modal popup disappears...");
				new WebDriverWait(driver, Constants.EXPLICIT_WAITTIME).until(ExpectedConditions.invisibilityOfElementLocated(by));
				endTime = LocalTime.now();	
				second = Duration.between(startTime, endTime).getSeconds();
				milliSecond = Duration.between(startTime, endTime).toMillis()-(second*1000);
				MsLog.debug("Popup alert completed. Loading time (hh:mm:ss,sss) = "+Duration.between(startTime, endTime).toHours()+":"+ Duration.between(startTime, endTime).toMinutes()+":"+ second +","+milliSecond);							
			}catch(Exception e) {				
				MsLog.debug("Popup alert is not displayed after " + timeOut + " seconds.");				
		    }
		}
		/**
		 * To verify if expected elements are displayed on page
		 * @author Quinn Song
		 * @param PageName
		 * @param element
		 * @param js		:javascript string.
		 * @param seconds	:timeout in seconds.
		 * @throws Exception
		 */	
		public static void verifyPageWithElementJs(WebDriver driver, String PageName, Element element, String js, int seconds) throws Exception  {
			JavascriptExecutor jse = (JavascriptExecutor)driver;
			boolean bStatus = false;
			MsLog.debug("verify started");
			
			for (int i = 0;i<seconds; i++) {		
			try {
					//FrameHandler.moveToChildFrame(driver,CommonObjects.parentFrame, CommonObjects.mainFrame);				
					String re = (jse.executeScript("return window.document.getElementById('txtApplicationNumber').value")).toString();
					MsLog.debug(re);
					bStatus = (! re.isEmpty());
					
					if (bStatus) {
						MsLog.debug("done with verify");
						break;
					}
					else {
						Thread.sleep(1000);
						MsLog.debug("after." + Frame.getCurrentFrameName(driver));
					}
				}
				
				catch(Exception e) {
					//MsLog.debug(e.getMessage());
					Thread.sleep(1000);
				}
			}

			
			if (!bStatus){
				MsLog.fail("*** Expected result:"+ element.getElementName()+" is displayed on "+ PageName);
				MsLog.fail("*** Actual result:"+ element.getElementName()+" is NOT displayed on "+ PageName,"Class: core.Page | Method: verifyPageWithElement - Element: "+element.getElementName()+ " - locator - "+element.getBy().toString());
				throw new Exception("Missing element "+ element.getElementName());	
			}
			else {
				MsLog.pass("*** Actual result:"+PageName + " displayed with "+ element.elementName + " successfully");
			}
		}	
		/**
	     * @author thao.le
	     * To scroll down until the end of page
	     * @param WebDriver
	     */
	    public static void scrollDown(WebDriver driver)
	    {	    	
	    	try{	    		
	    		MsLog.debug("Scroll down to the end of page");    			    		
				JavascriptExecutor jse = (JavascriptExecutor) driver;
				jse.executeScript("window.scrollTo(0,document.body.scrollHeight);");
				Thread.sleep(2000);
	    	}catch(Exception e){
	    		MsLog.info("Scroll down to the end of page");
				MsLog.fail("*** Actual result: FAIL to to the end of page","Class: core.Page | Method: scrollDown()| Exception occured - "+e.toString());   		
	    	}
	    }
	    /**
	     * @author thao.le
	     * To scroll up until the end of page
	     * @param WebDriver
	     */
	    public static void scrollUp(WebDriver driver)
	    {	    	
	    	try{	    		
	    		MsLog.debug("Scroll up to the beginning of page");    			    		
				JavascriptExecutor jse = (JavascriptExecutor) driver;
				jse.executeScript("window.scrollTo(0,-document.body.scrollHeight);");
				Thread.sleep(2000);
	    	}catch(Exception e){
	    		MsLog.info("Scroll up to the beginning of page");
				MsLog.fail("*** Actual result: FAIL to scroll up to the beginning of page","Class: core.Page | Method: scrollUp()| Exception occured - "+e.toString());    		
	    	}
	    }
	    
	    private static void waitForJQueryLoad(WebDriver driver) {
	        (new WebDriverWait(driver, 1)).until(new ExpectedCondition<Boolean>() {
	            public Boolean apply(WebDriver d) {
	                JavascriptExecutor js = (JavascriptExecutor) d;
	                return (Boolean) js.executeScript("return !!window.jQuery && window.jQuery.active == 0");
	            }
	        });
	    }
	    private static void waitForDomLoad(WebDriver driver){
	    	(new WebDriverWait(driver, 1)).until(new ExpectedCondition<Boolean>() {
	            public Boolean apply(WebDriver d) {
	                JavascriptExecutor js = (JavascriptExecutor) d;
	                return (Boolean) js.executeScript("return document.readyState").equals("complete");
	            }
	        });
	    }
	    private static void waitForAjaxLoad(WebDriver driver){
	    	Dimension zeroDim = new Dimension(0,0);
	    	(new WebDriverWait(driver, 1)).until(new ExpectedCondition<Boolean>() {
	            public Boolean apply(WebDriver d) {	                
	                return driver.findElement(By.cssSelector(".waiting, .tb-loading")).getSize() == zeroDim;
	            }
	        });
	    }
	    
	    public static void verifyScreenShotOnPage(String expectedScreenShot) {
	    	try{
	    		MsLog.info("+ Verify: screeen-shot("+expectedScreenShot+") is displayed on Page");
		    	ImageRecognition img = new ImageRecognition();
		    	boolean result = img.findImageOnScreen(expectedScreenShot);
		    	if(result==true)
		    	{
		    		MsLog.pass("*** Actual result: The Screen-shot("+expectedScreenShot+") is displayed as expected.");
		    	}
	    	}catch(Exception e){    		
				MsLog.fail("*** Actual result: The Screen-shot("+expectedScreenShot+") is NOT displayed as expected.","Class: core.Page | Method: scrollUp()| Exception occured - "+e.toString());    		
	    	}
	    }
}
