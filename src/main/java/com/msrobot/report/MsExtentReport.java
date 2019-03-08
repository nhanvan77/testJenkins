package com.msrobot.report;

import java.util.HashMap;
import java.util.Map;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.msrobot.constant.Constants;

public class MsExtentReport {  
    private static Map<Integer, ExtentTest> extentTestMap = new HashMap<Integer, ExtentTest>();    
    private static ExtentReports extent=null;
    private static ExtentHtmlReporter htmlReporter;
    private static Map<Long, String> threadToExtentTestMap = new HashMap<Long, String>();
    private static Map<String, ExtentTest> nameToTestMap = new HashMap<String, ExtentTest>();
    
    
    public static ExtentReports getExtentReports(){    	
    	//System.out.println("###ProjectName:" + extent.getProjectName());
    	return extent;
    }

    public static synchronized ExtentTest getTest() {
        return (ExtentTest) extentTestMap.get((int) (long) (Thread.currentThread().getId()));
    }

    public static synchronized void reportEnvironment() {
//	        //extent.endTest((ExtentTest) extentTestMap.get((int) (long) (Thread.currentThread().getId())));
//	    	
//	    	extent.setSystemInfo("<font size = 2 color=\"blue\"><b><u>**** Module Name: ", GlobalConstants.moduleName +" ******");
//	    	extent.setSystemInfo("Automation Mode: ",GlobalConstants.mobileAutomationMode);
//	        extent.setSystemInfo("Mobile App: ",GlobalConstants.mobileAppAPKName);
//	        extent.setSystemInfo("Mobile Device: ",GlobalConstants.mobileDeviceName);        
//	        extent.setSystemInfo("Mobile Platform Name: ",GlobalConstants.mobilePlatformName);
//	       // extent.setSystemInfo("Time Taken: ",ExtentManager.getRunTime(GlobalConstants.executionReportName)); 
//	        
    }
    public static synchronized void endTest() {
//	        extent.endTest((ExtentTest) extentTestMap.get((int) (long) (Thread.currentThread().getId())));    	
    }
    public static synchronized ExtentTest startModule(String testName) {
        return startModule(testName, "");
    }

    public static synchronized ExtentTest startModule(String testName, String desc) {
        ExtentTest test = extent.createTest(testName, desc);       

        return test;
    }
    
    public static synchronized ExtentTest startNode(ExtentTest parentTest, String testName, String desc){
    	ExtentTest test=parentTest.createNode(testName + " :  "+desc);    	
    	 extentTestMap.put((int) (long) (Thread.currentThread().getId()), test);
    	 return test;
    }
    public static synchronized ExtentTest startTest(String testName, String desc){
    	ExtentTest test=extent.createTest(testName,desc);
    	 extentTestMap.put((int) (long) (Thread.currentThread().getId()), test);
    	 return test;
    }
    public synchronized static ExtentReports getReporter(String filePath) {
        if (extent == null) {
        	
        	       	
        	htmlReporter= new ExtentHtmlReporter(filePath);
        	
        	
        	 htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
             htmlReporter.config().setChartVisibilityOnOpen(true);
             htmlReporter.config().setTheme(Theme.STANDARD);
             htmlReporter.config().setDocumentTitle(Constants.PROJECT_NAME);
             htmlReporter.config().setEncoding("utf-8");
             htmlReporter.config().setReportName(Constants.PROJECT_NAME);
             
             htmlReporter.config().setTimeStampFormat("mm/dd/yyyy hh:mm:ss a");
        	        	
            extent = new ExtentReports();
            extent.attachReporter(htmlReporter);
            
            //htmlReporter.loadXMLConfig(new File(GlobalConstants.extentConfigFilePath));
            //System.out.println("***Extent Report Created: " +filePath );                        
        }
        
        return extent;
    }
    public static String getRunTime(String filePath){
    	if (extent == null) {         	 	       	
         	htmlReporter= new ExtentHtmlReporter(filePath);         	
         	long millis = htmlReporter.getRunDuration();
         	long secs = millis / 1000;
         	long ms = millis % 1000;
         	long mins = secs / 60;
         	secs = (secs % 60);
         	long hours = mins / 60;
         	mins = mins % 60;

         	return hours + "h " + mins + "m " + secs + "s+" + ms + "ms";  
    	}
    	return "0h:0m:0s:0ms";
    }
    public synchronized static ExtentReports getReporter() {
        return extent;
    }
}
