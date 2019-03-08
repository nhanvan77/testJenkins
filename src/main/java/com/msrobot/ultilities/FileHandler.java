package com.msrobot.ultilities;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

import com.msrobot.constant.Constants;
import com.msrobot.logs.MsLog;

//RISK: if fileName is not available in location
public class FileHandler {
	public Robot robot;
	public void uploadFile(String filePath, String fileName) throws AWTException, InterruptedException {
		this.robot = new Robot();
		if(fileName==null) {
			MsLog.error("In Class: ultilities.FileHandler | Method: uploadFile() ERROR Cause: File name is Null");
			closeWindow();
			return;
		}
		try {
			MsLog.info("+ Upload file '"+filePath+fileName+"'");
			setClipboardData(filePath+fileName);
			pasteClipboard();
			Thread.sleep(500);
			submit();
		} catch (Exception e) {
			MsLog.fail("** Actual result: User can NOT Open file "+filePath+fileName ,"Class: utilities.FileHandler | Method: uploadFile | Exception occured: "+e.toString() +
       				" at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber());
		}
		if (!verifyFileUploadSuccess()) {			
 			MsLog.reportFailWithScreenShot("** Actual result: User can NOT Open file " + filePath + fileName,
					"Class: utilities.FileHandler | Method: uploadFile | Exception occured: ", "uploadFileFailed", "png");
 			closeAllFileUploadWindow();
			MsLog.fail("Test case is failed");
		}
	}
	
	public void setClipboardData(String text) {
		try {
			StringSelection stringSelection = new StringSelection(text);
	        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
		} catch (Exception e) {
			MsLog.debug("+ In Class: ultilities.FileHandler | Method: setClipboardData() is NOT finished successfully");
		}
	}
	
	public void pasteClipboard() {
		try {
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_V);
		} catch (IllegalArgumentException e) {
			MsLog.debug("+ In Class: ultilities.FileHandler | Method: pasteClipboard() is NOT finished successfully cause keycode is not a valid key");
		} catch (Exception e) {
			MsLog.debug("+ In Class: ultilities.FileHandler | Method: pasteClipboard() is NOT finished successfully.");
		}
	}
	
	public void submit() {
		try {
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
		} catch (IllegalArgumentException e) {
			MsLog.debug("+ In Class: ultilities.FileHandler | Method: submit() is NOT finished successfully cause keycode is not a valid key");
		} catch (Exception e) {
			MsLog.debug("+ In Class: ultilities.FileHandler | Method: submit() is NOT finished successfully.");
		}
	}
	
	public void closeWindow() {
		try {
			robot.keyPress(KeyEvent.VK_ESCAPE);
			robot.keyRelease(KeyEvent.VK_ESCAPE);
		} catch (IllegalArgumentException e) {
			MsLog.debug("+ In Class: ultilities.FileHandler | Method: closeWindow() is NOT finished successfully cause keycode is not a valid key");
		} catch (Exception e) {
			MsLog.debug("+ In Class: ultilities.FileHandler | Method: closeWindow() is NOT finished successfully.");
		}
	}
	
	public static BufferedReader openTextFileFromProject(String filePath) throws Exception {
        MsLog.debug("+ In Calss FileLoader | Method: openFileFromProject");
        filePath = filePath.replace("%20", " ");
        if (!filePath.startsWith("/")) {
            filePath = "/" + filePath;
        }
        if (!isReadableFile(filePath)) {
            throw new Exception("File path of [ " + filePath + " ] was invalid or file was unreadable");
        }

        MsLog.debug("+ Attempting to load file from path [ " + filePath + " ]");
        FileReader fileReader = new FileReader(getAbsolutePathForResource(filePath));
        MsLog.debug("+ Successfully loaded file into FileReader");

        MsLog.debug("+ Loading FileReader object into BufferedReader");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        MsLog.debug("+ Successfully loaded FileReader object into BufferedReader");

        MsLog.debug("+ File successfully loaded");
        MsLog.debug("+ Exiting FileLoader#openFileFromProject");
        return bufferedReader;
    }
	  public static boolean isReadableFile(String filePath) {
	        MsLog.debug("+ Entering FileLoader#isReadableFile");

	        MsLog.debug("+ Validating file from path [ " + filePath + " ] is readable");
	        boolean readable = false;
	        File file = new File(filePath);
	        if (!file.isDirectory() && file.exists() && file.canRead()) {
	            readable = true;
	        } else if (null != FileHandler.class.getResource(filePath)) {
	            readable = true;
	        }

	        MsLog.debug("+ File was readable returning [ " + readable + " ]");
	        MsLog.debug("+ Exiting FileLoader#isReadableFile");
	        return readable;
	    }
	  public static String getAbsolutePathForResource(String filePath) {
	        URL url = FileHandler.class.getResource(filePath);
	        if (null == url) {
	            return filePath;
	        }

	        File file = new File(url.getPath());

	        return file.getPath();
	    }
		public boolean verifyFileUploadSuccess() throws InterruptedException {
			MsLog.debug("+ Verify file upload success");
			String path = System.getProperty("user.dir") + "//drivers//autoIT//fileUpoadHandler.txt";
			String exePath = System.getProperty("user.dir") + "//drivers//autoIT//verifyFileUploadWindow.exe";
			runAutoITscript(exePath);
			Thread.sleep(3000);
			try {
				BufferedReader br = openTextFileFromProject(path);
				String line = null;
				while ((line = br.readLine()) != null) {
					if (line.contains("FileUploadWindowNotDisplayed")) {
						if (line.contains("true")) {
							MsLog.debug("In Class: ultilities.FileHandler | Method: verifyFileUploadSuccess() | get FileUploadWindowNotDisplayed=true");
							br.close();
							return true;
						} else if (line.contains("false")) {
							MsLog.debug("In Class: ultilities.FileHandler | Method: verifyFileUploadSuccess() | get FileUploadWindowNotDisplayed=false");
							br.close();
							return false;
						} else {
							br.close();
							MsLog.error(
									"In Class: ultilities.FileHandler | Method: verifyFileUploadSuccess() ERROR Cause: Can not get FileUploadWindow status");

						}
						break;
					}
				}
			} catch (Exception e) {
				MsLog.debug(
						"+ In Class: ultilities.FileHandler | Method: verifyFileUploadSuccess() is NOT finished successfully.");
			}
			return false;
		}

		public void closeAllFileUploadWindow() {
			MsLog.debug("+ Close all file upload windows");
			String scriptPath = System.getProperty("user.dir") + "//drivers//autoIT//closeFileUpload.exe";
			runAutoITscript(scriptPath);
		}

		public void runAutoITscript(String filePath) {
			try {
				MsLog.debug("+ Run script " + filePath);
				Runtime.getRuntime().exec(filePath);
				Thread.sleep(3000);
			} catch (IOException | InterruptedException e) {
				MsLog.debug(
						"+ In Class: ultilities.FileHandler | Method: runAutoITscript() | is NOT finished successfully, Exception occured: "
								+ e.toString() + " at line number: "
								+ Thread.currentThread().getStackTrace()[1].getLineNumber());
			}
		}
}
