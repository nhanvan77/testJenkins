package com.msrobot.core.web;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.msrobot.constant.Constants;
import com.msrobot.logs.MsLog;

public class Select {

	private WebDriver driver;
	private WebElement element = null;
	private Element E;
	public String elementName;
	private By by;

	public Select(Element element) {
		this.E = element;
		this.by = element.getBy();
		this.elementName = element.elementName;
		this.driver = element.getDriver();
	}

	public void selectByVisibleText(String text) throws InterruptedException {
		boolean bStatus = false;
		if (text == null) {
			MsLog.debug("Warning: Text to select is null");
			return;
		}
		try {
			this.element = E.findElement();
		} catch (Exception e) {
			MsLog.info("+ Select '" + text + "' from " + elementName);
			MsLog.fail("Failed to select by text " + text + " from " + elementName,
					"Class: com.msrobot.core.Select | Method: selectByVisibleText(String text) | Timeout occured "
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
							"Class: com.msrobot.core.Select | Method: selectByVisibleText(String text) | Timeout occured "
									+ " at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber()
									+ "- Exception:" + e.toString());
				}
				Thread.sleep(Constants.THREAD_SLEEP);
				this.element = driver.findElement(by);
			}
		}
		// VERIFY THE FIRST SELECTION OPTION IF THE SELECTION TYPE IS NOT MULTIPLE
		if (!isMultiple()) {
			MsLog.debug("check if " + text + " is already selected in " + this.elementName);
			for (int retry = 0; retry < Constants.EXPLICIT_TIMEOUT; retry++) {
				try {
					selectedOption = new org.openqa.selenium.support.ui.Select(this.element).getFirstSelectedOption()
							.getText();
					break;
				} catch (Exception e) {
					if (retry == Constants.EXPLICIT_TIMEOUT - 1) {
						MsLog.info("+ Select '" + text + "' from " + elementName);
						MsLog.fail("Failed to select by text " + text + " from " + elementName,
								"Class: com.msrobot.core.Select | Method: selectByVisibleText(String text) | Timeout occured "
										+ " at line number: "
										+ Thread.currentThread().getStackTrace()[1].getLineNumber() + "- Exception:"
										+ e.toString());
					}
					Thread.sleep(Constants.THREAD_SLEEP);
					// this.element = driver.findElement(by);
					this.element = E.findElement();
				}
			}
			// CHECK IF TEXT TO SELECT ALREADY EXISTS
			if (text.equals(selectedOption)) {
				MsLog.debug("option by text already selected. Skipping this step.");
				return;
			}
		}

		MsLog.info("+ Select '" + text + "' from " + elementName);
		// LOOP TO RETRY
		for (int retry = 0; retry < Constants.EXPLICIT_TIMEOUT; retry++) {
			try {
				new org.openqa.selenium.support.ui.Select(this.element).selectByVisibleText(text);
				Thread.sleep(Constants.THREAD_SLEEP); // Is this optional?
				bStatus = true;
				break;
			} catch (Exception e) {
				if (retry == Constants.EXPLICIT_TIMEOUT - 1) {
					MsLog.fail("Failed to select by text " + text + " from " + elementName,
							"Class: com.msrobot.core.Select | Method: selectByVisibleText(String text) | Timeout occured "
									+ " at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber()
									+ "- Exception:" + e.toString());
				}
				Thread.sleep(Constants.THREAD_SLEEP);
				this.element = driver.findElement(by);
			}
		}
	}

	public void selectByIndex(int index) throws InterruptedException{
		try {
			this.element = E.findElement();
		} catch (Exception e) {
			MsLog.info("+ Select index '" + index + "' from " + elementName);
			MsLog.fail("Failed to select by index " + index + " from " + elementName,
					"Class: com.msrobot.core.Select | Method: selectByIndex(int index) | Timeout occured "
							+ " at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber()
							+ "- Exception:" + e.toString());
		}
		// WAIT UNTIL ELEMENT IS ENABLED
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
					MsLog.info("+ Select index '" + index + "' from " + elementName);
					MsLog.fail("Failed to select by index " + index + " from " + elementName + " (Element not enabled)",
							"Class: com.msrobot.core.Select | Method: selectByIndex(int index) | Timeout occured "
									+ " at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber()
									+ "- Exception:" + e.toString());
				}
				Thread.sleep(Constants.THREAD_SLEEP);
				this.element = driver.findElement(by);
			}
		}

		MsLog.info("+ Select index '" + index + "' from " + elementName);
		// LOOP TO RETRY
		for (int retry = 0; retry < Constants.EXPLICIT_TIMEOUT; retry++) {
			try {
				new org.openqa.selenium.support.ui.Select(this.element).selectByIndex(index);
				Thread.sleep(Constants.THREAD_SLEEP); // Is this optional?
				break;
			} catch (Exception e) {
				if (retry == Constants.EXPLICIT_TIMEOUT - 1) {
					MsLog.fail("Failed to select by index " + index + " from " + elementName,
							"Class: com.msrobot.core.Select | Method: selectByIndex(int index) | Timeout occured "
									+ " at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber()
									+ "- Exception:" + e.toString());
				}
				Thread.sleep(Constants.THREAD_SLEEP);
				this.element = driver.findElement(by);
			}
		}
	}

	public WebElement getFirstSelectedOption() {
		try {
			this.element = E.findElement();
		} catch (Exception e) {
			MsLog.info("+ Get first selected option from '" + E.getElementName());
			MsLog.fail("Failed to Get first selected option from " + E.getElementName(),
					"Class: com.msrobot.core.Select | Method: getFirstSelectedOption()" + " at line number: "
							+ Thread.currentThread().getStackTrace()[1].getLineNumber() + "- Exception:"
							+ e.toString());
		}
		return new org.openqa.selenium.support.ui.Select(this.element).getFirstSelectedOption();
	}

	public void deselectAll() throws InterruptedException {
		if (!isMultiple()) {
			MsLog.debug("In Class: com.msrobot.core.Select | Method: deselectAll()" + " for " + this.elementName
					+ " is not finished successfully." + " Caused element " + this.elementName
					+ " is not multiple selection type");

		} else {
			// run deslect all when element is multiple select type
			try {
				this.element = E.findElement();
			} catch (Exception e) {
				MsLog.info("+ Deselect all from " + elementName);
				MsLog.fail("Failed to deselect all options from " + elementName,
						"Class: com.msrobot.core.Select | Method: deselectAll() | Timeout occured "
								+ " at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber()
								+ "- Exception:" + e.toString());
			}
			// WAIT UNTIL ELEMENT IS ENABLED
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
						MsLog.info("+ Deselect all from " + elementName);
						MsLog.fail("Failed to deselect all options from " + elementName + " (Element not enabled)",
								"Class: com.msrobot.core.Select | Method: deselectAll() | Timeout occured "
										+ " at line number: "
										+ Thread.currentThread().getStackTrace()[1].getLineNumber() + "- Exception:"
										+ e.toString());
					}
					Thread.sleep(Constants.THREAD_SLEEP);
					this.element = driver.findElement(by);
				}
			}

			MsLog.info("+ Deselect all from " + elementName);
			// LOOP TO RETRY
			for (int retry = 0; retry < Constants.EXPLICIT_TIMEOUT; retry++) {
				try {
					new org.openqa.selenium.support.ui.Select(this.element).deselectAll();
					Thread.sleep(Constants.THREAD_SLEEP); // Is this optional?
					break;
				} catch (UnsupportedOperationException e) {
					MsLog.fail("Element " + elementName + " is not supported for deselect method",
							"Class: com.msrobot.core.Select | Method: deselectAll(text) | Timeout occured "
									+ " at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber()
									+ "- Exception:" + e.toString());
				} catch (Exception e) {
					if (retry == Constants.EXPLICIT_TIMEOUT - 1) {
						MsLog.fail("Failed to deselect all from " + elementName,
								"Class: com.msrobot.core.Select | Method: deselectAll() | Timeout occured "
										+ " at line number: "
										+ Thread.currentThread().getStackTrace()[1].getLineNumber() + "- Exception:"
										+ e.toString());
					}
					Thread.sleep(Constants.THREAD_SLEEP);
					this.element = driver.findElement(by);
				}
			}
		}

	}

	public void deselectByVisibleText(String text) throws InterruptedException {
		if (!isMultiple()) {
			MsLog.debug("In Class: com.msrobot.core.Select | Method: deselectByVisibleText()" + " for "
					+ this.elementName + " is not finished successfully." + "  Caused element " + this.elementName
					+ " is not multiple selection type");

		} else {
			if (text == null) {
				MsLog.debug("Warning: Text to select is null");
				return;
			}
			try {
				this.element = E.findElement();
			} catch (Exception e) {
				MsLog.info("+ Deselect visible text '" + text + "' from " + elementName);
				MsLog.fail("Failed to deselect by visible text " + text + " from " + elementName,
						"Class: com.msrobot.core.Select | Method: deselectByVisibleText(String text) | Timeout occured "
								+ " at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber()
								+ "- Exception:" + e.toString());
			}
			// WAIT UNTIL ELEMENT IS ENABLED
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
						MsLog.info("+ Deselect visible text '" + text + "' from " + elementName);
						MsLog.fail(
								"Failed to deselect by visible text " + text + " from " + elementName
										+ " (Element not enabled)",
								"Class: com.msrobot.core.Select | Method: deselectByVisibleText(String text) | Timeout occured "
										+ " at line number: "
										+ Thread.currentThread().getStackTrace()[1].getLineNumber() + "- Exception:"
										+ e.toString());
					}
					Thread.sleep(Constants.THREAD_SLEEP);
					this.element = driver.findElement(by);
				}
			}

			MsLog.info("+ Deselect visible text '" + text + "' from " + elementName);
			// LOOP TO RETRY
			for (int retry = 0; retry < Constants.EXPLICIT_TIMEOUT; retry++) {
				try {
					new org.openqa.selenium.support.ui.Select(this.element).deselectByVisibleText(text);
					Thread.sleep(Constants.THREAD_SLEEP); // Is this optional?
					break;
				} catch (UnsupportedOperationException e) {
					MsLog.fail("Element " + elementName + " is not supported for deselect method",
							"Class: com.msrobot.core.Select | Method: deselectByVisibleText(String text) | Timeout occured "
									+ " at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber()
									+ "- Exception:" + e.toString());
				} catch (Exception e) {
					if (retry == Constants.EXPLICIT_TIMEOUT - 1) {
						MsLog.fail("Failed to deselect by visible text " + text + " from " + elementName,
								"Class: com.msrobot.core.Select | Method: deselectByVisibleText(String text) | Timeout occured "
										+ " at line number: "
										+ Thread.currentThread().getStackTrace()[1].getLineNumber() + "- Exception:"
										+ e.toString());
					}
					Thread.sleep(Constants.THREAD_SLEEP);
					this.element = driver.findElement(by);
				}
			}
		}
	}

	public void deselectByIndex(int index) throws InterruptedException {
		MsLog.info("+ Deselect index '" + index + "' from " + elementName);
		try {
			this.element = E.findElement();
		} catch (Exception e) {
			MsLog.fail("Failed to deselect by index " + index + " from " + elementName,
					"Class: com.msrobot.core.Select | Method: deselectByIndex(int index) | Timeout occured "
							+ " at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber()
							+ "- Exception:" + e.toString());
		}
		// WAIT UNTIL ELEMENT IS ENABLED
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
					MsLog.info("+ Deselect by index '" + index + "' from " + elementName);
					MsLog.fail(
							"Failed to deselect by index " + index + " from " + elementName + " (Element not enabled)",
							"Class: com.msrobot.core.Select | Method: deselectByIndex(int index) | Timeout occured "
									+ " at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber()
									+ "- Exception:" + e.toString());
				}
				Thread.sleep(Constants.THREAD_SLEEP);
				this.element = driver.findElement(by);
			}
		}

		MsLog.info("+ Deselect index '" + index + "' from " + elementName);
		// LOOP TO RETRY
		for (int retry = 0; retry < Constants.EXPLICIT_TIMEOUT; retry++) {
			try {
				new org.openqa.selenium.support.ui.Select(this.element).deselectByIndex(index);
				Thread.sleep(Constants.THREAD_SLEEP); // Is this optional?
				break;
			} catch (UnsupportedOperationException e) {
				MsLog.fail("Element " + elementName + " is not supported for deselect method",
						"Class: com.msrobot.core.Select | Method: deselectByIndex(int index) | Timeout occured "
								+ " at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber()
								+ "- Exception:" + e.toString());
			} catch (Exception e) {
				if (retry == Constants.EXPLICIT_TIMEOUT - 1) {
					MsLog.fail("Failed to deselect by index " + index + " from " + elementName,
							"Class: com.msrobot.core.Select | Method: deselectByIndex(int index) | Timeout occured "
									+ " at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber()
									+ "- Exception:" + e.toString());
				}
				Thread.sleep(Constants.THREAD_SLEEP);
				this.element = driver.findElement(by);
			}
		}

	}

	public void deselectByValue(String text) throws InterruptedException {
		MsLog.info("+ Deselect by value '" + text + "' from " + elementName);
		if (text == null) {
			MsLog.debug("Warning: Text to select is null");
			return;
		}
		try {
			this.element = E.findElement();
		} catch (Exception e) {
			MsLog.fail("Failed to deselect by value " + text + " from " + elementName,
					"Class: com.msrobot.core.Select | Method: deselectByValue(String text) | Timeout occured "
							+ " at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber()
							+ "- Exception:" + e.toString());
		}
		// WAIT UNTIL ELEMENT IS ENABLED
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
					MsLog.info("+ Deselect value '" + text + "' from " + elementName);
					MsLog.fail(
							"Failed to deselect by value " + text + " from " + elementName + " (Element not enabled)",
							"Class: com.msrobot.core.Select | Method: deselectByValue(String text) | Timeout occured "
									+ " at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber()
									+ "- Exception:" + e.toString());
				}
				Thread.sleep(Constants.THREAD_SLEEP);
				this.element = driver.findElement(by);
			}
		}

		MsLog.info("+ Deselect visible text '" + text + "' from " + elementName);
		// LOOP TO RETRY
		for (int retry = 0; retry < Constants.EXPLICIT_TIMEOUT; retry++) {
			try {
				new org.openqa.selenium.support.ui.Select(this.element).deselectByValue(text);
				Thread.sleep(Constants.THREAD_SLEEP); // Is this optional?
				break;
			} catch (UnsupportedOperationException e) {
				MsLog.fail("Element " + elementName + " is not supported for deselect method",
						"Class: com.msrobot.core.Select | Method: deselectByValue(String text) | Timeout occured "
								+ " at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber()
								+ "- Exception:" + e.toString());

			} catch (Exception e) {
				if (retry == Constants.EXPLICIT_TIMEOUT - 1) {
					MsLog.fail("Failed to deselect by value " + text + " from " + elementName,
							"Class: com.msrobot.core.Select | Method: deselectByValue(String text) | Timeout occured "
									+ " at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber()
									+ "- Exception:" + e.toString());
				}
				Thread.sleep(Constants.THREAD_SLEEP);
				this.element = driver.findElement(by);
			}
		}
	}

	public Boolean isMultiple() {
		Boolean isM = false;
		try {
			this.element = E.findElement();
			isM = new org.openqa.selenium.support.ui.Select(this.element).isMultiple();

		} catch (Exception e) {
			MsLog.fail("Failed to check multiple select from " + elementName,
					"Class: com.msrobot.core.Select | Method: isMultiple() | Timeout occured " + " at line number: "
							+ Thread.currentThread().getStackTrace()[1].getLineNumber() + "- Exception:"
							+ e.toString());
		}
		return isM;
	}
}
