package com.msrobot.logs;

import org.testng.Reporter;

public class testNGReport {
	private static String imageSize = "width='860'";
	private static String statusColWidth = "5";
	private static String timeColWidth = "10%";
	private static String messageColWidth = "85%";

	public static void log(String s) {
		Reporter.log(s + "<br>");
	}

	public static void fail(String status, String time, String testDescription) {
		String message = generateHTML(status,time, testDescription);
		Reporter.log(message);
	}

	public static void fail(String status, String time, String testDescription, String screenShotFileName) {
		String message = generateHTML(status,time, testDescription,screenShotFileName);
		Reporter.log(message);
	}

	public static void pass(String status, String time, String testDescription) {
		String message = generateHTML(status,time, testDescription);
		Reporter.log(message);
	}

	public static void pass(String status, String time, String testDescription, String screenShotFileName) {
		String message = generateHTML(status,time, testDescription,screenShotFileName);
		Reporter.log(message);
	}

	public static void fatal(String status, String time, String testDescription) {
		String message = generateHTML(status,time, testDescription);
		Reporter.log(message);
	}

	public static void fatal(String status, String time, String testDescription, String screenShotFileName) {
		String message = generateHTML(status,time, testDescription,screenShotFileName);
		Reporter.log(message);
	}

	public static void warning(String status, String time, String testDescription) {
		String message = generateHTML(status,time, testDescription);
		Reporter.log(message);
	}

	public static void warning(String status, String time, String testDescription, String screenShotFileName) {
		String message = generateHTML(status,time, testDescription,screenShotFileName);
		Reporter.log(message);
	}

	public static void debug(String status, String time, String testDescription) {
		String message = generateHTML(status,time, testDescription);
		Reporter.log(message);
	}

	public static void debug(String status, String time, String testDescription, String screenShotFileName) {
		String message = generateHTML(status,time, testDescription,screenShotFileName);
		Reporter.log(message);
	}

	public static void info(String status, String time, String testDescription) {
		String message = generateHTML(status,time, testDescription);
		Reporter.log(message);
	}

	public static void info(String status, String time, String testDescription, String screenShotFileName) {
		String message = generateHTML(status,time, testDescription,screenShotFileName);
		Reporter.log(message);
	}

	public static void skip(String status, String time, String testDescription) {
		String message = generateHTML(status,time, testDescription);
		Reporter.log(message);
	}

	public static void skip(String status, String time, String testDescription, String screenShotFileName) {
		String message = generateHTML(status,time, testDescription,screenShotFileName);
		Reporter.log(message);
	}

	public static void error(String status, String time, String testDescription) {
		String message = generateHTML(status,time, testDescription);
		Reporter.log(message);
	}

	public static void error(String status, String time, String testDescription, String screenShotFileName) {
		String message = generateHTML(status,time, testDescription,screenShotFileName);
		Reporter.log(message);
	}

	public static String generateHTML(String status, String time, String message) {
		String html = 
			"<table width='100%'>" + 
			"  <tr align= 'center'>" + 
			"    <td width='"+statusColWidth+"'><font color='"+getTextColor(status)+"'>"+status+"</font></td>" + 
			"    <td>|</td>" + 
			"    <td width='"+timeColWidth+"'>"+time+"</td>" + 
			"    <td>|</td>" + 
			"    <td  align= 'left' width='"+messageColWidth+"'><font color='"+getTextColor(status)+"'>"+message+"</font>" +
			"  </tr>" + 
			"</table>";
		return html;
	}

	public static String generateHTML(String status, String time, String message, String screenShotFileName) {
		String html = 
			"<table width='100%'>" + 
			"  <tr align= 'center'>" + 
			"    <td width='"+statusColWidth+"'><font color='"+getTextColor(status)+"'>"+status+"</font></td>" + 
			"    <td>|</td>" + 
			"    <td width='"+timeColWidth+"'>"+time+"</td>" + 
			"    <td>|</td>" + 
			"    <td  align= 'left' width='"+messageColWidth+"'><font color='"+getTextColor(status)+"'>"+message+"</font>"
					+ "<br><img src='"+screenShotFileName+"'"+imageSize+"/></td>" + 
			"  </tr>" + 
			"</table>";
		return html;
	}
	
	public static String getTextColor(String status) {
		String fontColor = "black";
		switch (status){
			case "PASS":
				fontColor = "green";
				break;
			case "FAIL":
				fontColor = "red";
				break;
			case "FATAL":
				fontColor = "red";	
				break;
			case "INFO":
				fontColor = "black";
				break;
			case "WARNING":
				fontColor = "yellow";
				break;
			case "ERROR":
				fontColor = "red";	
				break;
			case "SKIP":
				fontColor = "black";
				break;
			case "DEBUG":
				fontColor = "black";
				break;
	}
		return fontColor;
	}
}