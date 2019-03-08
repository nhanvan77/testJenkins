package com.msrobot.logs;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.MediaEntityModelProvider;
import com.msrobot.constant.Constants;

/**
 * @author Thao Le
 * This class to hold test step information
 * Test Step will display description/error or exception message and screen shot
 */
public class TestStep {
	private String status;
	private String testDescription=null;
	private String screenShotFileName=null;
	private MediaEntityModelProvider mediaFile=null; //mediaFile can be a screen-shot
	private Throwable exception=null;
	private String logTime=null;
	private Date date;
	private String strDateFormat = "HH:mm:ss a";
	private SimpleDateFormat sdf;
	
	public TestStep(String status, String testDescription) {		
		this.status = status;
		this.testDescription = testDescription;
		setCurrentTime();
	}
	public TestStep(String status, Throwable exception) {		
		this.status = status;
		this.exception = exception;
		setCurrentTime();
	}
	public TestStep(String status,String testDescription, String screenShotFileName) {		
		this.status = status;
		this.testDescription = testDescription;
		this.screenShotFileName = screenShotFileName;
		setCurrentTime();
	}
	public TestStep(String status, Throwable exception, String screenShotFileName) {		
		this.status = status;
		this.exception = exception;
		this.screenShotFileName = screenShotFileName;
		setCurrentTime();
	}
	public Throwable getException() {
		return exception;
	}
	public void setException(Throwable exception) {
		this.exception = exception;
	}
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTestDescription() {
		return testDescription;
	}

	public void setTestDescription(String testDescription) {
		this.testDescription = testDescription;
	}

	public MediaEntityModelProvider getMediaFile() {
		try {
			this.mediaFile=MediaEntityBuilder.createScreenCaptureFromPath(screenShotFileName).build();
		} catch (Exception e) {
			return null;
		}
		return mediaFile;
	}

	public String getScreenShotFilePath() {
		return Constants.SCREENSHOT_FOLDER + this.screenShotFileName;
	}
	
	public void setMediaFile(String screenShotFileName) throws IOException {
		this.mediaFile=MediaEntityBuilder.createScreenCaptureFromPath(screenShotFileName).build();
	}
	
	public String getLogTime() {
		return logTime;
	}
	
	public void setCurrentTime() {
	    date = new Date();
	    sdf = new SimpleDateFormat(strDateFormat);
	    logTime = sdf.format(date);
	}

}
