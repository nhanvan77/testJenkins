package com.msrobot.constant;

import org.openqa.selenium.By;

import com.msrobot.core.web.Element;

public class Constants {	
	public static final String PROJECT_NAME = null;
	 public static String workingDir = System.getProperty("user.dir");
	    public static final int GLOBAL_TIMEOUT = 5; //second
	    public static final String BROWSER = "Chrome";
	    public static final String  SCREENSHOT_FOLDER = workingDir + "/reportLogs/";
	    public static final String  CUSTOMIZE_EMAILABLE_REPORT_FOLDER =workingDir + "/reportLogs/";
	    public static int EXPLICIT_WAITTIME = 10; //second
	    					
	     public static final int THREAD_SLEEP = 500; //milliseconds
	     public static final int EXPLICIT_TIMEOUT = EXPLICIT_WAITTIME * 2; 
	    public static final int IMPLICIT_WAITTIME = 10;
	    public static final int SLEEP_TIME = 2000; //milliseconds
	    public static final int PAGE_TIMEOUT = 5;//second
	    public static final int POPUP_TIMEOUT = 60;//second    	   
	    public static String locale = "US";
	    public static final String PROJECT_PROPERTIES_PATH = workingDir+"/src/test/resources/Properties/project.properties";
	    public static final String CONFIG_PROPERTIES_PATH = workingDir+"/src/main/resources/Properties/config.properties";
	    public static final String LANGUAGE_PROPERTIES_PATH = "Properties/";
	    public static final String LANGUAGE_PROPERTIES_FILENAME = "language";
	    public static final String APPIUM_PROPERTIES_PATH = workingDir+"/src/test/resources/Properties/appium.properties";
	    // ------- EXTENT REPORT -----------------------
	    public static String projectName=null;
	    public static int MAX_RETRY_FAILED_TESTCASE = 0;
	    public boolean isMAX_RETRY_FAILED_TESTCASE = false;
	  //*** DRIVERS PATH FOR BROWSERS 
	  	public static final String DRIVER_PATH=workingDir + "/Drivers/";

}
