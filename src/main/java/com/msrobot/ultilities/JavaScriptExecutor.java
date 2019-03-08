package com.msrobot.ultilities;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.msrobot.constant.Constants;
import com.msrobot.core.web.Element;
import com.msrobot.core.web.Page;
import com.msrobot.logs.MsLog;
import com.msrobot.msDriver.ExecutionManager;
import com.msrobot.msDriver.MsWebDriver;

import java.util.concurrent.TimeUnit;

public class JavaScriptExecutor {
	private WebDriver driver;

	public void clickJs(Element element) {
		MsLog.info("+ Click on " + element.getElementName() );
		//driver = MsWebDriver.getDriver();
		driver = ExecutionManager.getSession().getDriver();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		try {
			js.executeScript("arguments[0].click();", element.findElement());
		} catch (Exception e) { // StaleElementReferenceException
				MsLog.fail("** Actual result: User can NOT click on " + element.getElementName(),
						"Class: ultilities.JavaScriptExecutor | Method: clickJs | Exception occured - " + e.toString() + " at line number: "
								+ Thread.currentThread().getStackTrace()[1].getLineNumber());
		}
	}

	public String getDomainJs() {
		MsLog.debug("+ Get domain");
		//driver = MsWebDriver.getDriver();
		driver = ExecutionManager.getSession().getDriver();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		String DomainName = js.executeScript("return document.domain;").toString();
		return DomainName;

	}

	public String getURLJs() {
		MsLog.debug("+ Get URL");
		//driver = MsWebDriver.getDriver();
		driver = ExecutionManager.getSession().getDriver();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		String url = js.executeScript("return document.URL;").toString();
		return url;
	}

	public String getTitleJs() {
		MsLog.debug("+ Get Page title");
		//driver = MsWebDriver.getDriver();
		driver = ExecutionManager.getSession().getDriver();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		String TitleName = js.executeScript("return document.title;").toString();
		return TitleName;
	}

	public void navigateToNewURLJs(String URL) {
		MsLog.info("+ Navigate to "+URL);
		//driver = MsWebDriver.getDriver();
		driver = ExecutionManager.getSession().getDriver();
		JavascriptExecutor js = (JavascriptExecutor) driver;

		js.executeScript("window.location = '"+URL+"'");
	}

	public void clickBackwardBrowserJs() {
		MsLog.info("+ Click on backward button of browser");
		//driver = MsWebDriver.getDriver();
		driver = ExecutionManager.getSession().getDriver();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.history.back()");
	}

	public void clickForwardBrowserJs() {
		MsLog.info("+ Click on forward button of browser");
		//driver = MsWebDriver.getDriver();
		driver = ExecutionManager.getSession().getDriver();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.history.forward()");
	}

	public void scrollDownJs() {
		MsLog.debug("+ Scroll down");
		//driver = MsWebDriver.getDriver();
		driver = ExecutionManager.getSession().getDriver();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(0,-document.body.scrollHeight);");
	}

	public void scrollUpJs() {
		MsLog.debug("+ Scroll Up");
		//driver = MsWebDriver.getDriver();
		driver = ExecutionManager.getSession().getDriver();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(0,document.body.scrollHeight);");
	}

	public WebElement findElementByIdJs(String id) {
		MsLog.debug("+ Find element by ID");
		//driver = MsWebDriver.getDriver();
		driver = ExecutionManager.getSession().getDriver();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebElement ele = (WebElement) js.executeScript("return document.getElementById('"+id+"');");
		return ele;
	}

	public WebElement findElementByTagNameJs(String tagName) {
		MsLog.debug("+ Find element by Tag name");
		//driver = MsWebDriver.getDriver();
		driver = ExecutionManager.getSession().getDriver();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebElement ele = (WebElement) js.executeScript("return document.getElementsByTagName('"+tagName+"');");
		return ele;
	}

	public WebElement findElementByClassNameJs(String className) {
		MsLog.debug("+ Find element by class name");
		//driver = MsWebDriver.getDriver();
		driver = ExecutionManager.getSession().getDriver();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebElement ele = (WebElement) js.executeScript("return document.getElementsByClassName('"+className+"');");
		return ele;
	}

//	public void getLoadingPageStatusJs() {
//		JavascriptExecutor js = (JavascriptExecutor) driver;
//		driver.get("https://www.google.ca/");
//		System.out.println("Loading status: " + js.executeScript("return document.readyState;"));
//	}

	public Object findElementsByCssJs(String cssName) {
		MsLog.debug("+ Find element by Css");
		//driver = MsWebDriver.getDriver();
		driver = ExecutionManager.getSession().getDriver();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		driver.get("https://www.google.ca/");
		Object cssList = js.executeScript("return document.querySelectorAll('"+cssName+"');");
		return cssList;
	}

	// ******* need test
//	public void findAllIframesJs() {
//		JavascriptExecutor js = (JavascriptExecutor) driver;
//		driver.get("https://www.google.ca/");
//		Object iframeList = js.executeScript("return document.forms[\"frm1\"];");
//	}

	public void mouseHoverElementJs(Element HoverElement) {
		String mouseOverScript = "if(document.createEvent){var evObj = document.createEvent('MouseEvents');evObj.initEvent('mouseover',"
				+ "true, false); arguments[0].dispatchEvent(evObj);} else if(document.createEventObject) { arguments[0].fireEvent('onmouseover');}";

		JavascriptExecutor js = (JavascriptExecutor) driver;
		driver.get("https://www.google.ca/");
		try {
			js.executeScript(mouseOverScript, HoverElement.findElement());
		} catch (StaleElementReferenceException e) {
			MsLog.fail(
					HoverElement.getElementName() + "is not attached to the page document" + e.getStackTrace());
		} catch (NoSuchElementException e) {
			MsLog.fail(HoverElement.getElementName() + " was not found in DOM" , e.getStackTrace().toString());
		} catch (Exception e) {
			e.printStackTrace();
			MsLog.fail("Error occurred while hovering "+HoverElement.getElementName(), e.getStackTrace().toString());
		}
	}
	
	public void sendKeyJs(String elementId,String elementName, String text) {
		MsLog.debug("+ Enter '"+text+"' into "+elementName);
		//driver = MsWebDriver.getDriver();
		driver = ExecutionManager.getSession().getDriver();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("document.getElementById('"+elementId+"').value='"+text+"';");
	}
	public void sendKeyJs(Element element,String text) {
		MsLog.info("+ Enter '"+text+"' into " + element.getElementName());
		//driver = MsWebDriver.getDriver();
		driver = ExecutionManager.getSession().getDriver();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		try {
			js.executeScript("arguments[0].value='"+text+"';", element.findElement());
		} catch (Exception e) { // StaleElementReferenceException
				MsLog.fail("** Actual result: User can NOT enter '"+text+"' into " + element.getElementName(),
						"Class: ultilities.JavaScriptExecutor | Method: sendKeyJs | Exception occured - " + e.toString() + " at line number: "
								+ Thread.currentThread().getStackTrace()[1].getLineNumber());
		}			
	}
	public void checkOnCheckBox(String elementId,String elementName) {
		MsLog.debug("+ Select "+elementName);
		//driver = MsWebDriver.getDriver();
		driver = ExecutionManager.getSession().getDriver();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		driver.get("https://www.google.ca/");
		js.executeScript("document.getElementById('"+elementId+"').checked=false;");
	}
	public void uncheckOnCheckBox(String elementId,String elementName) {
		MsLog.debug("+ Uncheck "+elementName);
		//driver = MsWebDriver.getDriver();
		driver = ExecutionManager.getSession().getDriver();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		driver.get("https://www.google.ca/");
		js.executeScript("document.getElementById('"+elementId+"').checked=false;");
	}
}