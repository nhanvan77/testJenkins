package com.msrobot.msDriver;

import java.io.File;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;

import com.aventstack.extentreports.ExtentTest;
import com.msrobot.constant.Constants;
import com.msrobot.core.web.Element;
import com.msrobot.core.web.Page;
import com.msrobot.logs.MsLog;
import com.msrobot.logs.TestCase;

import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;

public class MsWebDriver {
	private WebDriver driver;
	private String browser;
	private TestCase testcase;
	
	public MsWebDriver(){
		browser = "CHROME";	
		launchBrowser();
	}

	public MsWebDriver(String sBrowser) {
		browser = sBrowser.toUpperCase();
		launchBrowser();
	}

	public Element findElement(By by){
		return new Element(by,by.toString());
	}
	
	public Element findElement(Element element){
		return element;
	}
	
	public List<WebElement> findElements(By by) throws Exception{
		return new Element(by,by.toString()).findElements();
	}
	
	public List<WebElement> findElements(Element element) throws Exception{
		return element.findElements();
	}
	
	public WebDriver getDriver(){
		return driver;
	}
	
	public void launchBrowser(){
		
		String DriverPath=System.getProperty("user.dir") + "/drivers/";
		String OS = System.getProperty("os.name").toUpperCase();
		if(OS.equals("MAC")){
			DriverPath = DriverPath + "/mac64/";
		}
		try{
			Thread.sleep(Constants.THREAD_SLEEP);
			switch(browser){
				case "FIREFOX":
					System.setProperty("webdriver.gecko.driver", DriverPath + "geckodriver.exe");
					driver = new FirefoxDriver();
					break;
				case "IE":
					DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();;
					capabilities.setCapability(CapabilityType.BROWSER_NAME, "IE");
					capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
					capabilities.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING,true);
					capabilities.setCapability(InternetExplorerDriver.NATIVE_EVENTS,true);				
					capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true); 
					capabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.ACCEPT);

					InternetExplorerOptions options = new InternetExplorerOptions(capabilities);
					String startCmd = "cmd /c start " + Constants.DRIVER_PATH+ "DisableIESecuritiySettings.vbs";
					MsLog.debug("Run DisableIESecuritiySettings.vbs to disable all IE Security Setting.");
					Runtime.getRuntime().exec(startCmd);
					System.setProperty("webdriver.ie.driver", DriverPath + "IEDriverServer.exe");			 				
					driver =new InternetExplorerDriver(options);
					break;
				case "SAFARI":
					//SafariDriver requires Safari 10 running on OSX El Capitan or greater.
					driver = new SafariDriver();
					break;
				case "EDGE":
					System.setProperty("webdriver.edge.driver", DriverPath + "MicrosoftWebDriver.exe");			 				
					driver = new EdgeDriver();
					break;
				case "OPERA":
					System.setProperty("webdriver.opera.driver", DriverPath + "operadriver.exe");			 				
					driver = new OperaDriver();
					break;
				default: 
					System.setProperty("webdriver.chrome.driver", DriverPath + "chromedriver.exe");
					driver = new ChromeDriver();
					break;
				}
		}catch (Exception e){
			MsLog.fail("FAILED : "+ browser + " browser is not opened","Class: core.Browser | Method: launchBrowser | Exception occured - "+e.toString() +
					" at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber());
		}
		driver.manage().window().maximize();
		ExecutionManager.setSession(this);
	}

	public void get(String sURL) {
		MsLog.info("+ Navigate to "+ sURL);
		try {
			//driver.get(sURL);
			ExecutionManager.getSession().getDriver().get(sURL);
			Page.waitForPageLoad(ExecutionManager.getSession().getDriver());
			Thread.sleep(Constants.THREAD_SLEEP);
		} catch (Exception e) {
			MsLog.fail("FAILED : '"+sURL + "' is not opened.","Class: core.Browser | Method: navigateURL | Exception occured - "+e.toString() +
					" at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber());
		}
	}

	public String getBrowser() {
		return browser;
	}
	
	public void closeBrowser(){
		try {
			MsLog.info("+ Close "+browser+" browser");
		    //driver.quit();
			Thread.sleep(Constants.THREAD_SLEEP);
			ExecutionManager.getSession().getDriver().quit();
			ExecutionManager.removeSession();
			Thread.sleep(Constants.THREAD_SLEEP);
		} catch (Exception e) {
			MsLog.fail("FAILED : "+ browser + " browser is not closed","Class: core.Browser | Method: closeBrowser | Exception occured - "+e.toString() +
					" at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber());
		}
	}

	public File getScreenshotAsFile() {
		return ((TakesScreenshot)ExecutionManager.getSession().getDriver()).getScreenshotAs(OutputType.FILE);
	}
	
	public String getPageSource() {
		return ExecutionManager.getSession().getDriver().getPageSource();
	}
	
	public void setTestcase(TestCase test) {
		this.testcase = test;
	}
	
	public TestCase getTestcase() {
		return this.testcase;
	}
}
