package com.msrobot.core.web;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.msrobot.constant.Constants;
import com.msrobot.logs.MsLog;
import com.msrobot.msDriver.ExecutionManager;
import com.msrobot.msDriver.MsWebDriver;

/*
 	Description: This is a core functions for all elements in Web application.
 	This class has all methods to perform an action of HTML element such as click, enter text, select from the list,...
 	@author Thao.Le
 */

public class Element {
	private WebDriver driver;
	private WebElement element = null;
	private List<WebElement> elements = null;
	private By by;
	public String elementName;

	/**
	 * This is a construction method to initial a Element object
	 * 
	 * @param Driver
	 * @param by
	 * @param elementName
	 */
	public Element(WebDriver Driver, By by, String elementName) {
		try {
			this.driver = Driver;
			this.by = by;
			this.elementName = elementName;
		} catch (Exception e) {
			throw (e);
		}
	}

	/**
	 * This is a construction method to initial a Element object
	 * 
	 * @param Driver
	 * @param by
	 * @param elementName
	 */
	public Element(By by, String elementName) {
		try {
			this.by = by;
			if(elementName.isEmpty()){
				this.elementName = by.toString();
			}else{
				this.elementName = elementName;
			}
		} catch (Exception e) {
			throw (e);
		}
	}

	/**
	 * Wait until the expected element is displayed. Time out for waiting period is
	 * in second.
	 * 
	 * @author Thao Le
	 * @param by      Element by value
	 * @param iSecond Time in second to wait until the element is displayed
	 * @return True if element is displayed, False if element is not displayed
	 */
	public boolean waitUntilElementDisplay(By by, int iSecond) {
		boolean bStatus = false;
		try {
			for (int second = 0; second < iSecond; second++) {
				if (isElementPresent(by)) {
					bStatus = true;
					break;
				}
				Thread.sleep(Constants.THREAD_SLEEP);
			}
		} catch (Exception e) {
			return false;
		}
		return bStatus;
	}

	/**
	 * Wait for the element visible on page using a constant timeout.
	 * 
	 * @update Thao Le
	 * @param by Element by value
	 * @return True if element is displayed, False if element is not displayed
	 */
	public boolean waitElementVisible(By by) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Constants.EXPLICIT_TIMEOUT);
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			element = driver.findElement(by);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Find an element on page using constant timeout.
	 * 
	 * @param by Element by value
	 * @return an element
	 */
	public WebElement findElement() {
		try {
			MsLog.debug("In Class: core.Element | Method: findElement() for " + this.elementName + " is starting...");
			driver = ExecutionManager.getSession().getDriver();
			for (int timeout = 0; timeout < Constants.EXPLICIT_TIMEOUT; timeout++) {
				try {
					WebDriverWait wait = new WebDriverWait(driver, 1);
					wait.until(ExpectedConditions.visibilityOfElementLocated(by));
					element = driver.findElement(by);
					break;
				} catch (Exception e) {
					if (timeout == Constants.EXPLICIT_TIMEOUT - 1) {
						MsLog.debug("In Class: core.Element | Method: findElement() for " + this.elementName
								+ " is NOT finished successfully. Total time waiting: " + timeout + 1 + " seconds.");
						throw e;
					}
				}
			}
			MsLog.debug("In Class: core.Element | Method: findElement() for " + this.elementName
					+ " is finished successfully.");
		} catch (Exception e) {
			MsLog.debug("In Class: core.Element | Method: findElement() for " + this.elementName
					+ " is NOT finished successfully.");
			throw e;
		}
		return element;
	}

	/**
	 * Find an element on page using constant timeout.
	 * 
	 * @param by Element by value
	 * @return an element
	 */
	public WebElement findElement(int timeOut) {
		try {
			MsLog.debug("In Class: core.Element | Method: findElement() for " + this.elementName + " is starting...");
			driver = ExecutionManager.getSession().getDriver();
			for (int timeout = 0; timeout < timeOut; timeout++) {
				try {
					WebDriverWait wait = new WebDriverWait(driver, 1);
					wait.until(ExpectedConditions.visibilityOfElementLocated(by));
					break;
				} catch (Exception e) {
					if (timeout == timeOut - 1) {
						MsLog.debug("In Class: core.Element | Method: findElement() for " + this.elementName
								+ " is NOT finished successfully. Total time waiting: " + timeout + 1 + " seconds.");
						throw e;
					}
				}
			}
			element = driver.findElement(by);
			MsLog.debug("In Class: core.Element | Method: findElement() for " + this.elementName
					+ " is finished successfully.");
		} catch (Exception e) {
			MsLog.debug("In Class: core.Element | Method: findElement() for " + this.elementName
					+ " is NOT finished successfully.");
			throw e;
		}
		return element;
	}

	/**
	 * Check if element is enable.
	 * 
	 * @return True if element is enable, False if element is disable.
	 */
	public boolean isEnabled() {
		boolean enabled = element.isEnabled();
		return enabled;
	}

	/**
	 * Check if element is displayed on page.
	 * 
	 * @return True if element is displayed, False if element is not displayed.
	 * @throws Exception
	 */
	public boolean isDisplayed() {
		return findElement().isDisplayed();
	}

	/**
	 * Check if element is displayed on page.
	 * 
	 * @return True if element is displayed, False if element is not displayed.
	 * @throws Exception
	 */
	public boolean isDisplayed(int timeOut) {
		return findElement(timeOut).isDisplayed();
	}

	/**
	 * Check if element is present on page (element may be presented on page, but
	 * disable or not displayed).
	 * 
	 * @param by
	 * @return True if element is present, False if element is not present.
	 */
	private boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	/**
	 * Check if an element (checkbox or radio-box) is selected or not.
	 * 
	 * @return True if element is checked, False if element is unchecked.
	 * @throws Exception
	 */
	public boolean isSelected() {
		this.element = findElement();
		boolean selected = this.element.isSelected();
		return selected;
	}

	/**
	 * Click on the element and wait until Page is loaded completely. This method
	 * will log as debug in log file.
	 * 
	 * @throws Exception
	 */
	public void click() {
		WebDriverWait wait;
		// int timeOut = Constants.EXPLICIT_TIMEOUT<=1 ? 1:Constants.EXPLICIT_TIMEOUT/2;
		try {
			MsLog.info("+ Click on '" + elementName + "'");
			this.element = findElement();
			Thread.sleep(200);
			// if element is disable, then wait until element is enable
			wait = new WebDriverWait(driver, Constants.EXPLICIT_TIMEOUT);
			wait.until(ExpectedConditions.elementToBeClickable(by)); // TimeoutException
			for (int retry = 0; retry < Constants.EXPLICIT_TIMEOUT; retry++) {
				try {
					Thread.sleep(Constants.THREAD_SLEEP);
					this.element = driver.findElement(by);
					clickDifferentBrowser(this.by);
					Page.waitForPageLoad(driver);
					break;
				} catch (StaleElementReferenceException e1) {
					MsLog.debug("StaleElementReferenceException: " + retry + " retry to findElement()");
					if (retry == Constants.EXPLICIT_TIMEOUT - 1) {
						throw (e1);
					}

				} catch (Exception e) { // StaleElementReferenceException
					MsLog.debug(e.toString());
					if (retry == Constants.EXPLICIT_TIMEOUT - 1) {
						throw (e);
					}
				}
			}
		} catch (Exception e) {
			MsLog.fail("** Actual result: User can NOT click on '" + elementName + "'",
					"Class: core.Element | Method: click | Exception occured - " + e.toString() + " at line number: "
							+ Thread.currentThread().getStackTrace()[1].getLineNumber());
		}
	}

	/**
	 * Click on the element and wait until Page is loaded completely. This method
	 * will log as debug in log file.
	 * 
	 * @throws Exception
	 */
	public void click(Element loadingPopup) {
		try {
			MsLog.info("+ Click on " + elementName);
			this.element = findElement();
			// new Actions(driver).moveToElement(this.element).perform();
			Thread.sleep(200);
			// if element is disable, then wait until element is enable
			WebDriverWait wait = new WebDriverWait(driver, Constants.EXPLICIT_TIMEOUT);
			wait.until(ExpectedConditions.elementToBeClickable(by)); // TimeoutException
			for (int retry = 0; retry < Constants.EXPLICIT_TIMEOUT; retry++) {
				try {
					Thread.sleep(Constants.THREAD_SLEEP);
					this.element = driver.findElement(by);
					clickDifferentBrowser(this.by);
					Thread.sleep(Constants.THREAD_SLEEP);
					Page.waitForPageLoad(driver);
					Page.waitUntilPopupDisappear(driver, loadingPopup.getBy());
					break;
				} catch (StaleElementReferenceException e1) {
					MsLog.debug("StaleElementReferenceException: " + retry + " retry to findElement()");
					if (retry == Constants.EXPLICIT_TIMEOUT - 1) {
						throw (e1);
					}
				} catch (Exception e) {
					if (retry == Constants.EXPLICIT_TIMEOUT - 1) {
						throw (e);
					}
				}
			}
		} catch (Exception e) {
			MsLog.fail("** Actual result: User can NOT click on " + elementName,
					"Class: core.Element | Method: click | Exception occured - " + e.toString() + " at line number: "
							+ Thread.currentThread().getStackTrace()[1].getLineNumber());
		}
	}

	/**
	 * Click on the element and wait until Page is loaded completely. This method
	 * will log as debug in log file.
	 * 
	 * @throws Exception
	 * @author Thao Le
	 */
	public void clickAndIgnoreAlert() {
		try {
			MsLog.info("+ Click on " + elementName);
			this.element = findElement();
			new Actions(driver).moveToElement(this.element).perform();
			Thread.sleep(200);
			// if element is disable, then wait until element is enable
			WebDriverWait wait = new WebDriverWait(driver, Constants.EXPLICIT_TIMEOUT);
			wait.until(ExpectedConditions.elementToBeClickable(by)); // TimeoutException
			for (int retry = 0; retry < Constants.EXPLICIT_TIMEOUT; retry++) {
				try {
					Thread.sleep(Constants.THREAD_SLEEP);
					this.element = driver.findElement(by);
					clickDifferentBrowser(this.by);
					Thread.sleep(Constants.THREAD_SLEEP);
					break;
				} catch (Exception e) {
					if (retry == Constants.EXPLICIT_TIMEOUT - 1) {
						MsLog.fail("** Actual result: User can NOT click on " + elementName,
								"Class: core.Element | Method: clickAndIgnoreAlert | Exception occured - "
										+ e.toString());
					}
				}
			}
			try {
				Alert alert = driver.switchTo().alert();
				alert.accept();
			} catch (Exception e) {
			}
			Page.waitForPageLoad(driver);
			Thread.sleep(Constants.THREAD_SLEEP);
		} catch (Exception e) {
			MsLog.fail("** Actual result: User can NOT click on " + elementName,
					"Class: core.Element | Method: click | Exception occured - " + e.toString() + " at line number: "
							+ Thread.currentThread().getStackTrace()[1].getLineNumber());
		}
	}

	/**
	 * Click on the element and wait until Page is loaded completely. This method
	 * will log as debug in log file.
	 * 
	 * @throws Exception
	 */
	public void clickJS() {
		try {
			MsLog.info("+ Click on " + elementName);
			JavascriptExecutor js = (JavascriptExecutor) driver;
			this.element = findElement();
			Thread.sleep(200);
			// if element is disable, then wait until element is enable
			WebDriverWait wait = new WebDriverWait(driver, Constants.EXPLICIT_TIMEOUT);
			wait.until(ExpectedConditions.elementToBeClickable(by)); // TimeoutException
			for (int retry = 0; retry < Constants.EXPLICIT_TIMEOUT; retry++) {
				try {
					// js.executeScript("document.getElementById(objectID).click();");
					Thread.sleep(Constants.THREAD_SLEEP);
					this.element = driver.findElement(by);
					js.executeScript("arguments[0].click();", this.element);
					Page.waitForPageLoad(driver);
					break;
				} catch (StaleElementReferenceException e1) {
					MsLog.debug("StaleElementReferenceException: " + retry + " retry to findElement()");
					if (retry == Constants.EXPLICIT_TIMEOUT - 1) {
						throw (e1);
					}
				} catch (Exception e) { // StaleElementReferenceException
					MsLog.debug(e.toString());
					if (retry == Constants.EXPLICIT_TIMEOUT - 1) {
						throw (e);
					}
				}
			}
		} catch (Exception e) {
			MsLog.fail("** Actual result: User can NOT click on " + elementName,
					"Class: core.Element | Method: click | Exception occured - " + e.toString() + " at line number: "
							+ Thread.currentThread().getStackTrace()[1].getLineNumber());
		}
	}

	/**
	 * Enter password into password textbox.
	 * 
	 * @param text
	 * @throws Exception
	 */
	public void enterPassword(String text) {
		try {
			if (text == null) {
				MsLog.debug("Warning: Text to select is null");
				return;
			}
			MsLog.info("+ Enter ****** into '" + elementName + "'");
			this.element = findElement();
			this.element.clear();
			this.element.sendKeys(text);
			Thread.sleep(100);
		} catch (Exception e) {
			MsLog.fail("** Actual result: user cannot enter ****** into '" + elementName + "'",
					"Class: core.Element | Method: enterPassword(String text) | Exception occured - " + e.toString()
							+ " at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber());
		}
	}

	/**
	 * Wait and select an item by index in drop-down list. Popup after is not
	 * handled Skip if text is already being selected by index from drop-down
	 * 
	 * @author Thao Le
	 * @update Thao Le : adding 2000 milliseconds sleep after selecting action
	 *         finished.
	 * @param index : item index which needed to select
	 * @throws Exception
	 *//*
		 * public void select(int index) throws Exception{ boolean bStatus = false; try
		 * { MsLog.info("+ Select index " + index + " from " + elementName);
		 * this.element = findElement(); MsLog.debug("index to select is " + index); //
		 * WAIT UNTIL ELEMENT IS ENABLED String selectedOption = ""; try {
		 * selectedOption = new
		 * Select(driver.findElement(by)).getFirstSelectedOption().getText(); } catch
		 * (Exception e1) {
		 * 
		 * WebDriverWait wait = new WebDriverWait(driver, Constants.EXPLICIT_TIMEOUT);
		 * wait.until((WebDriver dr) -> { try { this.element = driver.findElement(by);
		 * boolean enabled = this.element.isEnabled(); Thread.sleep(1000); return
		 * enabled; } catch (Exception e2) { return false; } }); }
		 * 
		 * MsLog.debug(String.valueOf(element.isEnabled()));
		 * 
		 * if (!element.isEnabled()) {
		 * MsLog.fail("Failed to select by index "+index+" from dropdown of "+
		 * elementName + " (Element not enabled)"
		 * ,"Class: com.msrobot.core.Element | Method: selectText(int) | Timeout occured."
		 * ); throw new Exception ("Element not enabled."); }
		 * MsLog.debug("option currently selected: " + selectedOption);
		 * 
		 * String indexedOption = new Select(element).getOptions().get(index).getText();
		 * // MsLog.debug(Boolean.toString(indexedOption.equals(selectedOption))); //
		 * CHECK IF TEXT TO SELECT ALREADY EXISTS if
		 * (indexedOption.equals(selectedOption)) {
		 * MsLog.debug("option by index already selected. Skipping this step."); return;
		 * }
		 * 
		 * MsLog.debug("+ Select index "+index +" from dropdown of " + elementName +
		 * " in Element " + element); // LOOP TO RETRY for (int retry =
		 * 0;retry<Constants.EXPLICIT_TIMEOUT; retry++) { try { new
		 * Select(element).selectByIndex(index); bStatus = true; Thread.sleep(1000);
		 * break;
		 * 
		 * }catch(Exception e){ Thread.sleep(1000); } }
		 * 
		 * if (bStatus){ MsLog.debug("Selecting by index " + index +
		 * " from dropdown completed."); } else {
		 * MsLog.fail("+ Cannot be able to select index "+index+" from dropdown of "+
		 * elementName,"Class: com.msrobot.core.Element | Method: select() | Timeout occured."
		 * ); MsLog.debug("Failed to select from text by index " + index); } }catch
		 * (Exception e){
		 * MsLog.fail("+ Cannot be able to select index "+index+" from dropdown of "+
		 * elementName,"Class: com.msrobot.core.Element | Method: select() | Timeout occured."
		 * ); throw(e); } }
		 */

	/**
	 * Wait and select an item by text in drop-down list Skip if text is already
	 * being selected from drop-down
	 * 
	 * @author Thao Le
	 * @update : adding 2000 milliseconds sleep after selecting action finished.
	 * @param text        : Item which needed to select
	 * @param expectPopup : True if popup will show afterwards
	 * @throws InterruptedException 
	 * @throws Exception
	 */
	public void select(String text) throws InterruptedException {
		boolean bStatus = false;
		if (text == null) {
			MsLog.debug("Warning: Text to select is null");
			return;
		}
		try {
			this.element = findElement();
		} catch (Exception e) {
			MsLog.info("+ Select '" + text + "' from " + elementName);
			MsLog.fail("Failed to select by text " + text + " from " + elementName,
					"Class: com.msrobot.core.Element | Method: selectText(String, boolean) | Timeout occured "
							+ " at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber()
							+ "- Exception:" + e.toString());
		}
		// WAIT UNTIL ELEMENT IS ENABLED
		String selectedOption = "";
		MsLog.debug("Wait until " + this.elementName + " is enable.");
		boolean enabled = false;
		for (int retry = 0; retry < Constants.EXPLICIT_TIMEOUT; retry++) {
			try {
				enabled = this.element.isEnabled();
				if (enabled) {
					break;
				}
			} catch (Exception e) {
				if (retry == Constants.EXPLICIT_TIMEOUT - 1) {
					MsLog.info("+ Select '" + text + "' from " + elementName);
					MsLog.fail("Failed to select by text " + text + " from " + elementName + " (Element not enabled)",
							"Class: com.msrobot.core.Element | Method: selectText(String, boolean) | Timeout occured "
									+ " at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber()
									+ "- Exception:" + e.toString());
				}
				Thread.sleep(Constants.THREAD_SLEEP);
				this.element = driver.findElement(by);
			}
		}
		MsLog.debug("check if " + text + " is already selected in " + this.elementName);
		for (int retry = 0; retry < Constants.EXPLICIT_TIMEOUT; retry++) {
			try {
				selectedOption = new Select(driver.findElement(by)).getFirstSelectedOption().getText();
				break;
			} catch (Exception e) {
				if (retry == Constants.EXPLICIT_TIMEOUT - 1) {
					MsLog.info("+ Select '" + text + "' from " + elementName);
					MsLog.fail("Failed to select by text " + text + " from " + elementName,
							"Class: com.msrobot.core.Element | Method: selectText(String, boolean) | Timeout occured "
									+ " at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber()
									+ "- Exception:" + e.toString());
				}
				Thread.sleep(Constants.THREAD_SLEEP);
				this.element = driver.findElement(by);
			}
		}
		// CHECK IF TEXT TO SELECT ALREADY EXISTS
		if (text.equals(selectedOption)) {
			MsLog.debug("option by text already selected. Skipping this step.");
			return;
		}

		MsLog.info("+ Select '" + text + "' from " + elementName);
		// LOOP TO RETRY
		for (int retry = 0; retry < Constants.EXPLICIT_TIMEOUT; retry++) {
			try {
				new Select(element).selectByVisibleText(text);
				Thread.sleep(Constants.THREAD_SLEEP); // Is this optional?
				bStatus = true;
				break;
			} catch (Exception e) {
				if (retry == Constants.EXPLICIT_TIMEOUT - 1) {
					MsLog.fail("Failed to select by text " + text + " from " + elementName,
							"Class: com.msrobot.core.Element | Method: selectText(String, boolean) | Timeout occured "
									+ " at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber()
									+ "- Exception:" + e.toString());
				}
				Thread.sleep(Constants.THREAD_SLEEP);
				this.element = driver.findElement(by);
			}
		}

	}

	/**
	 * Wait and select an item by text in drop-down list Skip if text is already
	 * being selected from drop-down
	 * 
	 * @author Thao Le
	 * @update Thao Le : adding 2000 milliseconds sleep after selecting action
	 *         finished.
	 * @param text        : Item which needed to select
	 * @param expectPopup : True if popup will show afterwards
	 * @throws InterruptedException 
	 * @throws Exception
	 */
	public void select(String text, Element loadingPopup) throws InterruptedException {
		boolean bStatus = false;
		if (text == null) {
			MsLog.debug("Warning: Text to select is null");
			return;
		}

		this.element = findElement();
		// WAIT UNTIL ELEMENT IS ENABLED
		String selectedOption = "";
		MsLog.debug("Wait until " + this.elementName + " is enable.");
		boolean enabled = false;
		for (int retry = 0; retry < Constants.EXPLICIT_TIMEOUT; retry++) {
			try {
				enabled = this.element.isEnabled();
				if (enabled) {
					break;
				}
			} catch (Exception e) {
				if (retry == Constants.EXPLICIT_TIMEOUT - 1) {
					MsLog.info("+ Select '" + text + "' from " + elementName);
					MsLog.fail("Failed to select by text " + text + " from " + elementName + " (Element not enabled)",
							"Class: com.msrobot.core.Element | Method: selectText(String, boolean) | Timeout occured "
									+ " at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber()
									+ "- Exception:" + e.toString());
				}
				Thread.sleep(Constants.THREAD_SLEEP);
				this.element = driver.findElement(by);
			}
		}
		MsLog.debug("check if " + text + " is already selected in " + this.elementName);
		for (int retry = 0; retry < Constants.EXPLICIT_TIMEOUT; retry++) {
			try {
				MsLog.info("+ Select '" + text + "' from " + elementName);
				selectedOption = new Select(driver.findElement(by)).getFirstSelectedOption().getText();
				break;
			} catch (Exception e) {
				if (retry == Constants.EXPLICIT_TIMEOUT - 1) {
					MsLog.fail("Failed to select by text " + text + " from " + elementName,
							"Class: com.msrobot.core.Element | Method: selectText(String, boolean) | Timeout occured "
									+ " at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber()
									+ "- Exception:" + e.toString());
				}
				Thread.sleep(Constants.THREAD_SLEEP);
				this.element = driver.findElement(by);
			}
		}
		// CHECK IF TEXT TO SELECT ALREADY EXISTS
		if (text.equals(selectedOption)) {
			MsLog.debug("option by text already selected. Skipping this step.");
			return;
		}

		MsLog.info("+ Select '" + text + "' from " + elementName);
		// LOOP TO RETRY
		for (int retry = 0; retry < Constants.EXPLICIT_TIMEOUT; retry++) {
			try {
				new Select(element).selectByVisibleText(text);
				Thread.sleep(Constants.THREAD_SLEEP);
				Page.waitUntilPopupDisappear(driver, loadingPopup.getBy());
				bStatus = true;
				break;
			} catch (Exception e) {
				if (retry == Constants.EXPLICIT_TIMEOUT - 1) {
					MsLog.fail("Failed to select by text " + text + " from " + elementName,
							"Class: com.msrobot.core.Element | Method: selectText(String, boolean) | Timeout occured "
									+ " at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber()
									+ "- Exception:" + e.toString());
				}
				Thread.sleep(Constants.THREAD_SLEEP);
				this.element = driver.findElement(by);
			}
		}

	}

	/**
	 * To hit key on keyboard
	 * 
	 * @param keyBoard : key on keyboard
	 * @throws Exception
	 * @author Thao Le
	 */
	public void enterKeyBoard(Keys keyBoard) {
		try {
			MsLog.debug("+ Enter " + keyBoard.name() + " on keyboard");
			this.element = findElement();
			this.element.sendKeys(keyBoard);
			Thread.sleep(Constants.THREAD_SLEEP);
		} catch (Exception e) {
			MsLog.fail("+ Cannot enter " + keyBoard.toString() + " on keyboard",
					"Class: core.Element | Method: enterKeyBoard | Exception occured - " + e.toString()
							+ " at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber());
		}
	}

	/**
	 * Enter a text into a textbox element.
	 * 
	 * @author Thao Le modified: Added a loop to retry Modified by Thao Le
	 * @param text
	 * @throws Exception
	 */
	public void enterWithRetry(String text) {
		try {
			if (text == null) {
				MsLog.debug("Warning: Text to be entered is null");
				return;
			}

			Thread.sleep(2000);
			this.element = findElement();
			String valueInTextbox = null;
			valueInTextbox = this.element.getAttribute("value");
			MsLog.debug("Current text is: " + valueInTextbox);
			// CHECK IF TEXT TO ENTER ALREADY EXISTS
			if (valueInTextbox.equals(text)) {
				MsLog.debug("Text to be entered already exists. Skipping this step.");
				return;
			}
			this.element = findElement();
			this.element.clear();
			Thread.sleep(200);
			char[] chars = text.toCharArray();
			String charValue;
			valueInTextbox = null;
			for (int retry = 0; retry < Constants.EXPLICIT_TIMEOUT; retry++) {
				for (char eachChar : chars) {
					charValue = String.valueOf(eachChar);
					this.element.sendKeys(charValue);
					Thread.sleep(100);
				}
				Thread.sleep(200);
				enterKeyBoard(Keys.TAB);
				Thread.sleep(Constants.THREAD_SLEEP);
				valueInTextbox = this.element.getAttribute("value");
				if (valueInTextbox.equals(text)) {
					break;
				} else {
					MsLog.debug("Retry " + retry + ": enter " + text + " into " + this.elementName);
				}
			}
			MsLog.info("+ Enter " + text + " into " + elementName);
		} catch (Exception e) {
			MsLog.info("+ Enter " + text + " into " + elementName);
			MsLog.fail("** Actual result: user cannot enter " + text + " into " + elementName,
					"Class: core.Element | Method: enter | Exception occured - " + e.toString() + " at line number: "
							+ Thread.currentThread().getStackTrace()[1].getLineNumber());
		}
	}

	/**
	 * @return driver of this element
	 */
	public WebDriver getDriver() {
		return driver;
	}

	/**
	 * @param driver Driver of this element
	 */
	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	/**
	 * @return Element object
	 */
	public WebElement getElement() {
		return element;
	}

	/**
	 * @param element Element to set for this element in this class
	 */
	public void setElement(WebElement element) {
		this.element = element;
	}

	/**
	 * @return By value of element
	 */
	public By getBy() {
		return by;
	}

	/**
	 * @param by Element by value
	 */
	public void setBy(By by) {
		this.by = by;
	}

	public String getElementName() {
		return elementName;
	}

	public void setElementName(String elementName) {
		this.elementName = elementName;
	}

	/**
	 * To verify if element contains expected text
	 * 
	 * @param text
	 * @return
	 * @author Thao Le
	 * @throws Exception
	 */
	public boolean containText(String text) throws Exception {
		try {
			MsLog.debug("+ Verify if '" + text + "' is displayed");
			this.element = findElement();
			String textOnElement = this.element.getText();
			if (!textOnElement.contains(text)) {
				throw new Exception("Expected text is missing");
			} else {
				return true;
			}
		} catch (Exception e) {
			MsLog.fail(this.elementName + " is NOT displayed",
					"Class: com.msrobot.core.Element | Method: containText() | " + e.toString() + " at line number: "
							+ Thread.currentThread().getStackTrace()[1].getLineNumber());
			throw (e);
		}
	}

	/**
	 * To get text from element
	 * 
	 * @return
	 * @throws Exception
	 * @author Thao Le
	 */
	public String getText() {
		String text = null;
		try {
			MsLog.debug("+ Get text on " + this.elementName);
			this.element = findElement();
			text = this.element.getText();
		} catch (Exception e) {
			MsLog.fail(this.elementName + " is NOT displayed.",
					"Class: com.msrobot.core.Element | Method: getTextOnLabel() | " + e.toString() + " at line number: "
							+ Thread.currentThread().getStackTrace()[1].getLineNumber());
		}
		return text;
	}

	/**
	 * To get text from element
	 * 
	 * @return
	 * @throws Exception
	 * @author Thao Le
	 */
	public String getAttribute(String value) {
		String text = null;
		try {
			MsLog.debug("+ Get text on " + this.elementName);
			this.element = findElement();
			text = this.element.getAttribute("value");
		} catch (Exception e) {
			MsLog.fail(this.elementName + " is NOT displayed.",
					"Class: com.msrobot.core.Element | Method: getAttribute() | " + e.toString() + " at line number: "
							+ Thread.currentThread().getStackTrace()[1].getLineNumber());
		}
		return text;
	}

	/**
	 * Enter a text into a textbox element.
	 * 
	 * @param text
	 * @throws Exception
	 */
	public void sendKeys(String text) {
		if (text == null) {
			MsLog.debug("Warning: Text to put into " + this.by.toString() + " is null");
			return;
		}
		try {
			MsLog.info("+ Enter '" + text + "' into '" + elementName + "'");
			this.element = findElement();
			for (int second = 0; second < Constants.EXPLICIT_TIMEOUT; second++) {
				try {
					this.element.click();
					Thread.sleep(100);
					this.element.clear();
					this.element.sendKeys(text);
					Thread.sleep(100);
					break;
				} catch (Exception e1) {
					Thread.sleep(Constants.THREAD_SLEEP);
				}
			}
		} catch (Exception e) {
			MsLog.info("** Expected result: User can be able to enter " + text + " into " + elementName);
			MsLog.fail("** Actual result: user cannot enter " + text + " into " + elementName,
					"Class: core.Element | Method: enter | Exception occured - " + e.toString());
		}
	}

	/**
	 * Enter a text into a textbox element.
	 * 
	 * @param text
	 * @throws Exception
	 */
	public void sendKeysOneByOneChar(String text) {
		if (text == null) {
			MsLog.debug("Warning: Text to select is null");
			return;
		}

		try {
			MsLog.info("+ Enter " + text + " into " + elementName);
			this.element = findElement();
			for (int second = 0; second < Constants.EXPLICIT_TIMEOUT; second++) {
				try {
					char[] chars = text.toCharArray();
					String charValue;
					this.element.click();
					Thread.sleep(100);
					this.element.clear();
					for (char eachChar : chars) {
						charValue = String.valueOf(eachChar);
						this.element.sendKeys(charValue);
						Thread.sleep(200);
					}
					Thread.sleep(Constants.THREAD_SLEEP);
					enterKeyBoard(Keys.TAB);
					Thread.sleep(Constants.THREAD_SLEEP);
					break;
				} catch (Exception e1) {
					Thread.sleep(Constants.THREAD_SLEEP);
				}
			}
		} catch (Exception e) {
			MsLog.info("** Expected result: User can be able to enter " + text + " into " + elementName);
			MsLog.fail("** Actual result: user cannot enter " + text + " into " + elementName,
					"Class: core.Element | Method: enter | Exception occured - " + e.toString());
		}
	}

	public void clear() {
	}

	protected static void clickDifferentBrowser(By by) throws InterruptedException {
		String browserTest = ExecutionManager.getSession().getBrowser();
		WebDriver driver = ExecutionManager.getSession().getDriver();;
		WebElement elementToClick;
		if (browserTest.equalsIgnoreCase("IE")) {
			WebDriverWait wait = new WebDriverWait(driver, 1);
			wait.until(ExpectedConditions.elementToBeClickable(by));
		}

		elementToClick = driver.findElement(by);
		new Actions(driver).moveToElement(elementToClick).perform();

		if (browserTest.equalsIgnoreCase("IE")) {
			Thread.sleep(100);
			new Actions(driver).click().build().perform();
		} else {
			elementToClick.click();
		}
	}

	public String getCssValue(String value) {
		String css = null;
		try {
			MsLog.debug("+ Get CSS Value '" + value + "' on " + this.elementName);
			this.element = findElement();
			css = this.element.getCssValue(value);

		} catch (NoSuchElementException e) {
			MsLog.fail("*** Actual result: " + this.elementName + " is NOT displayed.",
					"Class: com.msrobot.core.Element | Method: getCssValue() | " + e.toString() + " at line number: "
							+ Thread.currentThread().getStackTrace()[1].getLineNumber());

		} catch (Exception e) {
			MsLog.fail("*** Actual result: Can not get CSS Value " + value + " for " + elementName,
					"Class: com.msrobot.core.Element | Method: getCssValue() | Exception occured - " + e.toString());
		}
		return css;
	}

	public Point getLocation() {
		Point location = null;
		try {
			MsLog.debug("+ Get Location on " + this.elementName);
			this.element = findElement();
			location = this.element.getLocation();

		} catch (NoSuchElementException e) {
			MsLog.fail(this.elementName + " is NOT displayed.",
					"Class: com.msrobot.core.Element | Method: getLocation() | " + e.toString() + " at line number: "
							+ Thread.currentThread().getStackTrace()[1].getLineNumber());

		} catch (Exception e) {
			MsLog.fail("*** Actual result:Can not get Location for " + elementName,
					"Class: com.msrobot.core.Element | Method: getLocation() | Exception occured - " + e.toString());
		}
		return location;
	}

	// Notice method getRect() is not working on Chrome and IE driver
	public Rectangle getRect() {
		Rectangle rect = null;
		try {
			MsLog.debug("+ Get Rectangle on " + this.elementName);
			this.element = findElement();
			rect = this.element.getRect();
		} catch (NoSuchElementException e) {
			MsLog.fail(this.elementName + " is NOT displayed.", "Class: com.msrobot.core.Element | Method: getRect() | "
					+ e.toString() + " at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber());
		} catch (Exception e) {
			MsLog.fail("*** Actual result: Can not get Rectangle for " + elementName,
					"Class: com.msrobot.core.Element | Method: getRect() | Exception occured - " + e.toString());
		}
		return rect;
	}

	public String getTagName() {
		String tagName = null;
		try {
			MsLog.debug("+ Get Tag Name on " + this.elementName);
			this.element = findElement();
			tagName = this.element.getTagName();
		} catch (Exception e) {
			MsLog.fail("*** Actual result: Can not get Tag Name for " + elementName,
					"Class: com.msrobot.core.Element | Method: getTagName() | Exception occured - " + e.toString());
		}
		return tagName;
	}

	public void submit() {
		try {
			MsLog.debug("+ Submit on " + this.elementName);
			this.element = findElement();
			this.element.submit();
		} catch (NoSuchElementException e) {
			MsLog.fail(this.elementName + " is NOT displayed.", "Class: com.msrobot.core.Element | Method: submit() | "
					+ e.toString() + " at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber());
		} catch (Exception e) {
			MsLog.fail("*** Actual result: Can not Submit for " + elementName,
					"Class: com.msrobot.core.Element | Method: submit() | Exception occured - " + e.toString());
		}
	}

	/**
	 * Find elements on page using constant timeout.
	 * 
	 * @param by Element by value
	 * @return elements
	 */
	public List<WebElement> findElements() {
		try {
			MsLog.debug("In Class: core.Element | Method: findElements() for " + this.elementName + " is starting...");
			driver = ExecutionManager.getSession().getDriver();;
			for (int timeout = 0; timeout < Constants.EXPLICIT_TIMEOUT; timeout++) {
				try {
					WebDriverWait wait = new WebDriverWait(driver, 1);
					wait.until(ExpectedConditions.visibilityOfElementLocated(by));
					elements = driver.findElements(by);
					break;
				} catch (Exception e) {
					if (timeout == Constants.EXPLICIT_TIMEOUT - 1) {
						MsLog.debug("In Class: core.Element | Method: findElements() for " + this.elementName
								+ " is NOT finished successfully. Total time waiting: " + timeout + 1 + " seconds.");
						throw (e);
					}
				}
			}
			MsLog.debug("In Class: core.Element | Method: findElements() for " + this.elementName
					+ " is finished successfully.");
		} catch (Exception e) {
			MsLog.debug("In Class: core.Element | Method: findElements() for " + this.elementName
					+ " is NOT finished successfully.");
			throw (e);
		}
		return elements;
	}

	public List<WebElement> findElements(int timeOut) {
		try {
			MsLog.debug("In Class: core.Element | Method: findElements() for " + this.elementName + " is starting...");
			driver = ExecutionManager.getSession().getDriver();;
			for (int timeout = 0; timeout < timeOut; timeout++) {
				try {
					WebDriverWait wait = new WebDriverWait(driver, 1);
					wait.until(ExpectedConditions.visibilityOfElementLocated(by));
					break;
				} catch (Exception e) {
					if (timeout == timeOut - 1) {
						MsLog.debug("In Class: core.Element | Method: findElements() for " + this.elementName
								+ " is NOT finished successfully. Total time waiting: " + timeout + 1 + " seconds.");
						throw (e);
					}
				}
			}
			elements = driver.findElements(by);
			MsLog.debug("In Class: core.Element | Method: findElements() for " + this.elementName
					+ " is finished successfully.");
		} catch (Exception e) {
			MsLog.debug("In Class: core.Element | Method: findElements() for " + this.elementName
					+ " is NOT finished successfully.");
			throw (e);
		}
		return elements;
	}

	/**
	 * To verify text of element
	 * 
	 * @return
	 * @throws Exception
	 * @author Thao Le
	 */
	public void verifyText(String expectedtext) {
		String text = null;
		try {
			MsLog.debug("+ Verify " + text + " is displayed.");
			this.element = findElement();
			text = this.element.getText();
			if (text.contains(expectedtext))
				MsLog.pass("*** Actual result: '" + expectedtext + "' is displayed.");
			else{
				if(text.isEmpty()){
					text = this.element.getAttribute("value");
					if (text.contains(expectedtext))
						MsLog.pass("*** Actual result: '" + expectedtext + "' is displayed.");
					else
						MsLog.fail("*** Actual result: '" + expectedtext + "' is NOT displayed.",
							"Class: com.msrobot.core.Element | Method: verifyText(String expectedtext) | at line number: "
									+ Thread.currentThread().getStackTrace()[1].getLineNumber());
				}else{
					MsLog.fail("*** Actual result: '" + expectedtext + "' is NOT displayed.",
							"Class: com.msrobot.core.Element | Method: verifyText(String expectedtext) | at line number: "
									+ Thread.currentThread().getStackTrace()[1].getLineNumber());
				}
			}
		} catch (Exception e) {
			MsLog.fail("*** Actual result: '" + expectedtext + "' is NOT displayed.",
					"Class: com.msrobot.core.Element | Method: verifyText(String expectedtext) | " + e.toString()
							+ " at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber());
		}
	}
	/**
	 * Check if element is displayed on page.
	 * 
	 * @return True if element is displayed, False if element is not displayed.
	 * @throws Exception
	 */
	public void verifyElementDisplayed() {
		driver = ExecutionManager.getSession().getDriver();;
		for (int timeout = 0; timeout < Constants.EXPLICIT_TIMEOUT; timeout++) {
			try {
				WebDriverWait wait = new WebDriverWait(driver, 1);
				wait.until(ExpectedConditions.visibilityOfElementLocated(by));
				element = driver.findElement(by);
				break;
			} catch (Exception e) {
				if (timeout == Constants.EXPLICIT_TIMEOUT - 1) {
					MsLog.fail("*** Actual result: '" + elementName + "' is NOT displayed.");
				}
			}
		}
		if(element.isDisplayed()){
			MsLog.pass("*** Actual result: '" + elementName + "' is displayed.");
		}else{
			MsLog.fail("*** Actual result: '" + elementName + "' is NOT displayed.");
		}
	}
	public void dragAndDrop(Element ToElement) {
		MsLog.info("+ Drag element " + elementName + " and drop to element " + ToElement.elementName);
		driver = ExecutionManager.getSession().getDriver();
		try {
			Actions action = new Actions(driver);
			WebElement fromElem = findElement();
			WebElement toElem = ToElement.findElement();
			Action dragAndDrop = action.moveToElement(fromElem).pause(Constants.THREAD_SLEEP).clickAndHold(fromElem)
					.pause(Constants.THREAD_SLEEP).moveToElement(toElem).pause(Constants.THREAD_SLEEP).release(toElem)
					.pause(Constants.THREAD_SLEEP).build();
			dragAndDrop.perform();
		} catch (Exception e) {
			MsLog.fail(
					"*** Actual result: Can not execute drag and drop element " + elementName + " to element "
							+ ToElement.elementName,
					"Class: com.msrobot.core.Element | Method: dragAndDrop() | Exception occured - " + e.toString());
		}
	}

	public void dragAndDropJS(Element ToElement) {
		MsLog.info("+ Drag element " + elementName + " and drop to element " + ToElement.elementName);
		driver = ExecutionManager.getSession().getDriver();
		try {
			WebElement fromElem = findElement();
			WebElement toElem = ToElement.findElement();
			executeJSEvent(fromElem, "mousedown");
			Thread.sleep(Constants.THREAD_SLEEP);
			executeJSEvent(fromElem, "dragstart");
			Thread.sleep(Constants.THREAD_SLEEP);
			executeJSEvent(toElem, "dragenter");
			Thread.sleep(Constants.THREAD_SLEEP);
			executeJSEvent(toElem, "dragover");
			Thread.sleep(Constants.THREAD_SLEEP);
			executeJSEvent(toElem, "drop");
			Thread.sleep(Constants.THREAD_SLEEP);
		} catch (Exception e) {
			MsLog.fail(
					"*** Actual result: Can not execute drag and drop element " + elementName + " to element "
							+ ToElement.elementName,
					"Class: com.msrobot.core.Element | Method: dragAndDropJS() | Exception occured - " + e.toString());
		}
	}

	public void executeJSEvent(WebElement element, String event) {
		String jsEvent = ".dispatchEvent( new MouseEvent(\"" + event + "\", {bubbles: true}) );";
		JavascriptExecutor javaScriptExecutor = (JavascriptExecutor) driver;
		javaScriptExecutor.executeScript("arguments[0]" + jsEvent, element);
	}

	public void verifyElementEnabled() {
		try {
			MsLog.debug("+ Verify " + elementName + " is enabled.");
			this.element = findElement();
			if (this.element.isEnabled())
				MsLog.pass("*** Actual result: '" + elementName + "' is enabled.");
			else
				MsLog.fail("*** Actual result: '" + elementName + "' is NOT enabled.",
						"Class: com.msrobot.core.Element | Method: verifyElementEnabled() | at line number: "
								+ Thread.currentThread().getStackTrace()[1].getLineNumber());
		} catch (Exception e) {
			MsLog.fail("*** Actual result: '" + elementName + "' is NOT displayed.",
					"Class: com.msrobot.core.Element | Method: verifyElementEnabled() | " + e.toString()
							+ " at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber());
		}
	}

	public void verifyElementDisabled() {
		try {
			MsLog.debug("+ Verify " + elementName + " is disabled.");
			this.element = findElement();
			if (!this.element.isEnabled())
				MsLog.pass("*** Actual result: '" + elementName + "' is disabled.");
			else
				MsLog.fail("*** Actual result: '" + elementName + "' is NOT disabled.",
						"Class: com.msrobot.core.Element | Method: verifyElementDisabled() | at line number: "
								+ Thread.currentThread().getStackTrace()[1].getLineNumber());
		} catch (Exception e) {
			MsLog.fail("*** Actual result: '" + elementName + "' is NOT displayed.",
					"Class: com.msrobot.core.Element | Method: verifyElementDisabled() | " + e.toString()
							+ " at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber());
		}
	}
}
