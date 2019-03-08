package com.msrobot.ultilities;
import org.apache.log4j.Logger;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

public class TestEnvironment {
	private String browserUnderTest = "";
	private String browserVersion = "";
	private String operatingSystem = "";
	private String driverVersion="";
	private String runLocation = "";
	private String appEnvironment = "";
	
	public TestEnvironment(){		
	}
	public TestEnvironment(String browserUnderTest, String browserVersion, String operatingSystem, String runLocation,
			String appEnvironment) {		
		this.browserUnderTest = browserUnderTest;
		this.browserVersion = browserVersion;
		this.operatingSystem = operatingSystem;
		this.runLocation = runLocation;
		this.appEnvironment = appEnvironment;		
	}
	public void setTestEnvironment(WebDriver driver) {	
		String browserName = null;
		String browserVersion = null;
		String systemOS = null;
		String driverVer = "";		
		Capabilities cap = ((RemoteWebDriver)driver).getCapabilities();
		systemOS = System.getProperty("os.name");
		switch (browserUnderTest){
		case "CHROME":			
		    browserName = cap.getBrowserName().toLowerCase();	
		    driverVer = cap.getVersion().toString();
		    Object driVer = cap.getCapability(browserName);
		    String version = driVer.toString();		    
		    if(version==null){
		    	driverVer = "";
		    }else{
		    	String[] temp = version.split(" ");
		    	driverVer = temp[0].toString();
		    	driverVer = driverVer.substring(1);
		    }	       
		    browserVersion = cap.getVersion().toString();	    		    
		    break;
		case "IE":
			browserName = cap.getBrowserName().toLowerCase();		    		    		    
			browserVersion = cap.getVersion().toString();
			break;
		case "FIREFOX":
			break;		
		}
			    
	    this.browserUnderTest = browserName;
		this.browserVersion = browserVersion;
		this.operatingSystem = systemOS;
		this.driverVersion = driverVer;
		
	}
	public String getDriverVersion() {
		return driverVersion;
	}
	public void setDriverVersion(String driverVersion) {
		this.driverVersion = driverVersion;
	}
	public String reportEnvironment(){
		String report = ""; 
		report = "Test environment: Browser:" +browserUnderTest+" - Browser version:"+ browserVersion+" - OS:"+operatingSystem+" - Application Environment:"+appEnvironment;
		return report;
	}
	public String getBrowserUnderTest() {
		return browserUnderTest;
	}
	public void setBrowserUnderTest(String browserUnderTest) {
		this.browserUnderTest = browserUnderTest;
	}
	public String getBrowserVersion() {
		return browserVersion;
	}
	public void setBrowserVersion(String browserVersion) {
		this.browserVersion = browserVersion;
	}
	public String getOperatingSystem() {
		return operatingSystem;
	}
	public void setOperatingSystem(String operatingSystem) {
		this.operatingSystem = operatingSystem;
	}
	public String getRunLocation() {
		return runLocation;
	}
	public void setRunLocation(String runLocation) {
		this.runLocation = runLocation;
	}
	public String getEnvironment() {
		return appEnvironment;
	}
	public void setAppEnvironment(String appEnvironment) {
		this.appEnvironment = appEnvironment;
	}

}

