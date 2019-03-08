package com.msrobot.ultilities;

import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;
import com.msrobot.logs.MsLog;

/*
	Description: This is a core functions for Image Recognition.
	This class has all methods to perform an action to image: click, enter text
	@author Thao.Le
*/

public class ImageRecognition {

	/**
	 * Find an element on page using an image 
	 * @param String imageFile file path to the image
	 * @return boolean
	 * @throws Exception 
	 */
	public Boolean findImageOnScreen(String imageFile) throws Exception {
		int waitTime = 10;
		try {
			Screen screen = new Screen();
			Pattern image = new Pattern(imageFile);
			MsLog.debug("In Class: ultilities.ImageRecognition | Method: findImageOnScreen() for "+imageFile+" is starting...");	
			screen.wait(image, waitTime);			
		} catch (Exception e) {
			MsLog.debug("In Class: ultilities.ImageRecognition | Method: findImageOnScreen() for "+imageFile+" is NOT finished successfully. Total time waiting: "+waitTime+" seconds.");
			throw e;
		}
		return true;
	}

	/**
	 * Click on Image 
	 * @param String imageFile file path to the image
	 */
	public void clickOnImage(String imageFile) {
		try {
			Screen screen = new Screen();
			Pattern image = new Pattern(imageFile);
			if (this.findImageOnScreen(imageFile)) {
			
				MsLog.info("+ Click on '"+imageFile+"'");
				screen.click(image);
			}
		} catch (Exception e) {
			MsLog.fail("In Class: ultilities.ImageRecognition | Method: clickOnImage() for "+imageFile+" is NOT finished successfully.");
		}
	}

	/**
	 * Send key on Image 
	 * @param String imageFile file path to the image
	 * @param String text value to send key
	 * @throws Exception 
	 */
	public void sendKeyOnImage(String imageFile, String elementName, String text){
		try {
			Screen screen = new Screen();
			Pattern image = new Pattern(imageFile);
			if (this.findImageOnScreen(imageFile)) {
				
					MsLog.info("Enter '"+ text+"' into " +elementName+" - using ultilities.ImageRecognition");
					screen.type(image, text);
			}
		} catch (Exception e) {
			MsLog.fail("Enter "+ text+" into " +elementName+" - In Class: ultilities.ImageRecognition | Method: sendKeyOnImage() for "+imageFile+" is NOT finished successfully.");
			
		}
	}
}
