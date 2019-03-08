package com.msrobot.report;


import java.util.HashMap;
import java.util.Map;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.msrobot.constant.Constants;

public class ExtentManager {
    private static ExtentReports extent=null;
    private static ExtentHtmlReporter htmlReporter;
	public static Map<Long, String> threadToExtentTestMap = new HashMap<Long, String>();
	public static Map<String, ExtentTest> nameToTestMap = new HashMap<String, ExtentTest>();
    
    public synchronized static ExtentReports getReporter(String filePath) {
        if (extent == null) {
        	
        	       	
        	htmlReporter= new ExtentHtmlReporter(filePath);
        	
        	
        	 htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
             htmlReporter.config().setChartVisibilityOnOpen(true);
             htmlReporter.config().setTheme(Theme.STANDARD);
             htmlReporter.config().setDocumentTitle(Constants.projectName);
             htmlReporter.config().setEncoding("utf-8");
             htmlReporter.config().setReportName(Constants.projectName);
             
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
   
    public static synchronized void setEnvironmentView() {
    	
    }
}