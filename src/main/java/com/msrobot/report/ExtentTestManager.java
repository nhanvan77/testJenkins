package com.msrobot.report;

import java.util.HashMap;
import java.util.Map;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;


public class ExtentTestManager {  // new
    static Map<Integer, ExtentTest> extentTestMap = new HashMap<Integer, ExtentTest>();
    private static ExtentReports extent = ExtentManager.getReporter();
    
    
    public static ExtentReports getExtentReports(){    	
    	//System.out.println("###ProjectName:" + extent.getProjectName());
    	return extent;
    }

    public static synchronized ExtentTest getTest() {
        return (ExtentTest) extentTestMap.get((int) (long) (Thread.currentThread().getId()));
    }

    public static synchronized void reportEnvironment(String className,String browser,String browserVersion, String driverVersion, String operatingSystem) {
        //extent.endTest((ExtentTest) extentTestMap.get((int) (long) (Thread.currentThread().getId())));
    	
    	extent.setSystemInfo("<font size = 2 color=\"blue\"><b><u>Test Suite: ", className);
    	extent.setSystemInfo("Browser: ",browser);
        extent.setSystemInfo("Browser version: ",browserVersion);
        extent.setSystemInfo("Driver version: ",driverVersion);        
        extent.setSystemInfo("Operating System: ",operatingSystem);
       // extent.setSystemInfo("Time Taken: ",ExtentManager.getRunTime(GlobalConstants.executionReportName)); 
        
    }
    public static synchronized void reportTotalTime(String startTime, String endTime,String totalTime) {   
    	extent.setSystemInfo("Start time: ",startTime);
    	extent.setSystemInfo("End time: ",endTime);
    	extent.setSystemInfo("Total Time(hh:mm:ss): ",totalTime);
   
    }
    public static synchronized void endTest() {
//        extent.endTest((ExtentTest) extentTestMap.get((int) (long) (Thread.currentThread().getId())));    	
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
    
}