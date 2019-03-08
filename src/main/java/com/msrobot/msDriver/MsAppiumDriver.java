package com.msrobot.msDriver;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.remote.DesiredCapabilities;

import com.msrobot.logs.MsLog;
import com.msrobot.logs.TestCase;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;


public class MsAppiumDriver {
	private AppiumDriver driver;
	private Properties appiumprop=new Properties();
	private TestCase testcase;
	
	public MsAppiumDriver(String mobilePropertiesFile) {
		initializeMsAppiumDriver(mobilePropertiesFile);
	}

	public AppiumDriver getDriver(){
		return driver;
	}	

	public void initializeMsAppiumDriver(String mobilePropertiesFile)
	{
		
		DesiredCapabilities capabilities=new DesiredCapabilities();
		try{

			FileInputStream fis= new FileInputStream("./src/test/resources/Properties/"+ mobilePropertiesFile);
			appiumprop.load(fis);
			
			
			String platform = appiumprop.getProperty("platform");
			String device = appiumprop.getProperty("device");
			String application_path = appiumprop.getProperty("application_path");
			String appium_server = appiumprop.getProperty("appium_server");
			File appFile = new File(application_path);
			if(platform.toUpperCase().equals("ANDROID")) 
			{	
				capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
				if(device.toUpperCase().equals("ANDROID EMULATOR")||device==null||device.isEmpty())
				{
					capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
				}
				else
				{
					capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, device);
				}
				if(application_path!=null||!application_path.isEmpty())
				{
					capabilities.setCapability(MobileCapabilityType.APP, appFile);
				}
				else
				{
					MsLog.error("Please set application path in appium.properties file");
				}

				driver = new AndroidDriver(new URL(appium_server), capabilities);

			}
				
			
			if(platform.toUpperCase().equals("IOS")) 
			{
				
				capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.IOS);
				if(device.toUpperCase().equals("IPHONE SIMULATOR")||device==null||device.isEmpty())
				{
					capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone Simulator");
				}
				if(device.toUpperCase().equals("IPAD SIMULATOR"))
				{
					capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPad Simulator");
				}
				else
				{
					capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, device);
				}
				if(application_path!=null||!application_path.isEmpty())
				{
					capabilities.setCapability(MobileCapabilityType.APP, appFile);
				}
				else
				{
					MsLog.error("Please set application path in " + mobilePropertiesFile+ " file");
				}
				driver = new IOSDriver(new URL(appium_server), capabilities);
			}					
			else
			{
				MsLog.error("Please set a platform (Android or IOS) in  " + mobilePropertiesFile+ " file");
			}

		}catch (Exception e){
			MsLog.fail("FAILED : MsAppiumDriver is not initialized","Class: msDriver.MsAppiumDriver | Method: initializeMsAppiumDriver | Exception occured - "+e.toString() +
					" at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber());
		}
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
	}
	

	public void setTestcase(TestCase test) {
		this.testcase = test;
	}
	
	public TestCase getTestcase() {
		return this.testcase;
	}
}