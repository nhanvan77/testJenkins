package demoSites;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.msrobot.baseTestSuite.BaseTestSuite;
import com.msrobot.constant.Constants;
import com.msrobot.core.web.Element;
import com.msrobot.core.web.Page;
import com.msrobot.core.web.Select;
import com.msrobot.msDriver.MsWebDriver;
import com.msrobot.testData.CSV_TestData;
import com.msrobot.testData.Excel_TestData_format1;
import com.msrobot.ultilities.FileHandler;

public class DemoSites_UnitTests extends BaseTestSuite {
//	private String URLTest = "http://msrobotassistant.com/basic-demo-site/";
	private String URLTest;
	private String userName;
	private String password;
	
	@BeforeClass (alwaysRun=true)
	@Parameters({ "browserTest" })
	public void TestClassSetUp(@Optional("CHROME") String browserTest) {
		browser = browserTest;
		driver = new MsWebDriver(browser);
		URLTest = super.ULRTest;
		userName = super.userName;
		password = super.password;
	}

	@AfterClass (alwaysRun=true)
	public void TestClassTearDown() {
		driver.closeBrowser();
	}

	 @Test(groups = {"smoke"})
	 public void UnitTest01_selectByVisibleText() throws Exception{
		 testCase.setTestCaseDescription("For testing select.selectByVisibleText");
		driver.get(URLTest);
		driver.findElement(By.id("name-id")).sendKeys(userName);
		driver.findElement(By.id("pw")).clear();
		driver.findElement(By.id("pw")).enterPassword(password);
		driver.findElement(By.name("pwbtn")).click();
		new Select(driver.findElement(By.name("country-name"))).selectByVisibleText("Canada");
		String result = new Select(driver.findElement(By.name("country-name"))).getFirstSelectedOption().getText();
		Assert.assertEquals(result, "Canada");
	}

	@Test(groups = {"smoke"})
	public void UnitTest02_selectByIndex() throws Exception {
		testCase.setTestCaseDescription("For testing select.selectByIndex");
		driver.get(URLTest);
		driver.findElement(By.id("name-id")).sendKeys(userName);
		driver.findElement(By.id("pw")).clear();
		driver.findElement(By.id("pw")).enterPassword(password);
		driver.findElement(By.name("pwbtn")).click();
		new Select(driver.findElement(By.name("country-name"))).selectByIndex(2);
		String result = new Select(driver.findElement(By.name("country-name"))).getFirstSelectedOption().getText();
		Assert.assertEquals(result, "Angola");
	}

	@Test(groups = {"smoke"})
	public void UnitTest03_UploadFile() throws Exception {
		testCase.setTestCaseDescription("For testing FileHandler.fileUpload()");
		driver.get(URLTest);
		driver.findElement(By.id("name-id")).sendKeys(userName);
		driver.findElement(By.id("pw")).clear();
		driver.findElement(By.id("pw")).enterPassword(password);
		driver.findElement(By.name("pwbtn")).click();
		driver.findElement(By.name("base-fname")).sendKeys("First");
		driver.findElement(By.name("base-lname")).sendKeys("Last");
		driver.findElement(By.xpath("//input[@type='radio'][@value='Single']")).click();
		driver.findElement(By.name("your-email")).sendKeys("MsRobot@msrobotassistant.com");
		driver.findElement(By.name("your-phone")).sendKeys("123456");
		driver.findElement(By.name("pic-file")).click();
		FileHandler file = new FileHandler();
		file.uploadFile(Constants.workingDir+"\\src\\test\\resources\\images\\", "msrobot.png");
		driver.findElement(By.xpath("//input[@type='submit']")).click();
		driver.findElement(By.xpath("//*[@id=\"wpcf7-f424-p389-o1\"]/form/div[12]"))
				.verifyText("Thank you for your message. It has been sent.");
		Page.scrollDown(driver.getDriver());

	}

	@Test(groups = {"smoke"})
	public void UnitTest04_UploadFile_Fail() throws Exception {
		testCase.setTestCaseDescription("For testing FileHandler.fileUpload(): test is failed because Path does not exist.");
		driver.get(URLTest);
		driver.findElement(By.id("name-id")).sendKeys(userName);
		driver.findElement(By.id("pw")).clear();
		driver.findElement(By.id("pw")).enterPassword(password);
		driver.findElement(By.name("pwbtn")).click();
		driver.findElement(By.name("base-fname")).sendKeys("First");
		driver.findElement(By.name("base-lname")).sendKeys("Last");
		driver.findElement(By.xpath("//input[@type='radio'][@value='Single']")).click();
		driver.findElement(By.name("your-email")).sendKeys("MsRobot@msrobotassistant.com");
		driver.findElement(By.name("your-phone")).sendKeys("123456");
		driver.findElement(By.name("pic-file")).click();
		FileHandler file = new FileHandler();
		file.uploadFile(Constants.workingDir+"/src/test/resources/images/", "File_NOT_Exist.jpg");
		driver.findElement(By.xpath("//input[@type='submit']")).click();
		driver.findElement(By.xpath("//*[@id=\"wpcf7-f424-p389-o1\"]/form/div[12]"))
				.verifyText("Thank you for your message. It has been sent.");
		Page.scrollDown(driver.getDriver());

	}

	@Test(groups = {"smoke"})
	public void UnitTest05_CSVData() throws Exception {
		testCase.setTestCaseDescription("For testing CSV_TestData.getData()");
		String filePath = Constants.workingDir+"/src/test/java/demoSites/Data/DemoSites_UnitTests.csv";
		CSV_TestData testData = new CSV_TestData(filePath, testName);
		driver.get(URLTest);
		driver.findElement(By.id("name-id")).sendKeys(userName);		
		driver.findElement(By.id("pw")).clear();
		driver.findElement(By.id("pw")).enterPassword(password);
		driver.findElement(By.name("pwbtn")).click();
		driver.findElement(By.name("base-fname")).sendKeys(testData.getData("FirstName"));
		driver.findElement(By.name("base-lname")).sendKeys(testData.getData("LastName"));
		driver.findElement(By.xpath("//input[@type='radio'][@value='Single']")).click();
		driver.findElement(By.name("your-email")).sendKeys(testData.getData("Email"));
		driver.findElement(By.name("your-phone")).sendKeys(testData.getData("Phone"));
		driver.findElement(By.xpath("//input[@type='submit']")).click();
		driver.findElement(By.xpath("//*[@id=\"wpcf7-f424-p389-o1\"]/form/div[12]"))
				.verifyText("One or more fields have an error. Please check and try again.");
		Page.scrollDown(driver.getDriver());

	}

	@Test(groups = {"smoke"})
	public void UnitTest07_ExcelData() throws Exception {
		testCase.setTestCaseDescription("For testing Excel_TestData_format1.getData()");
		String filePath = System.getProperty("user.dir")
				+ "\\src\\test\\java\\demoSites\\Data\\DemoSites_UnitTests.xlsx";
		Excel_TestData_format1 testData = new Excel_TestData_format1(filePath, testName);
		driver.get(URLTest);
		driver.findElement(By.id("name-id")).sendKeys(userName);
		driver.findElement(By.id("pw")).clear();
		driver.findElement(By.id("pw")).enterPassword(password);
		driver.findElement(By.name("pwbtn")).click();
		driver.findElement(By.name("base-fname")).sendKeys(testData.getData("Main", "FirstName"));
		driver.findElement(By.name("base-lname")).sendKeys(testData.getData("Main", "LastName"));
		driver.findElement(By.xpath("//input[@type='radio'][@value='Single']")).click();
		driver.findElement(By.name("your-email")).sendKeys(testData.getData("BasicSite", "Email"));
		driver.findElement(By.name("your-phone")).sendKeys(testData.getData("BasicSite", "Phone"));
		driver.findElement(By.xpath("//input[@type='submit']")).click();
		driver.findElement(By.xpath("//*[@id=\"wpcf7-f424-p389-o1\"]/form/div[12]"))
				.verifyText("One or more fields have an error. Please check and try again.");
		Page.scrollDown(driver.getDriver());
	}

	@Test(groups = {"smoke"})
	public void UnitTest08_deselectByVisibleText() throws Exception {
		testCase.setTestCaseDescription("For testing select.deselectByVisibleText");
		driver.get(URLTest);
		driver.findElement(By.id("name-id")).sendKeys(userName);
		driver.findElement(By.id("pw")).clear();
		driver.findElement(By.id("pw")).enterPassword(password);
		driver.findElement(By.name("pwbtn")).click();

		Select select = new Select(driver.findElement(By.name("selenium-commands[]")));
		select.selectByVisibleText("Wait Commands");
		select.selectByIndex(1);
		select.deselectByVisibleText("Wait Commands");

		Page.scrollDown(driver.getDriver());
	}

	@Test(groups = {"smoke"})
	public void UnitTest09_deselectAll() throws Exception {
		testCase.setTestCaseDescription("For testing select.deselectAll");
		driver.get(URLTest);
		driver.findElement(By.id("name-id")).sendKeys(userName);
		driver.findElement(By.id("pw")).clear();
		driver.findElement(By.id("pw")).enterPassword(password);
		driver.findElement(By.name("pwbtn")).click();

		Select select = new Select(driver.findElement(By.name("selenium-commands[]")));
		select.selectByVisibleText("Wait Commands");
		select.selectByIndex(1);
		select.deselectAll();

		Page.scrollDown(driver.getDriver());
	}
	@Test(groups = {"regression"})
	public void UnitTest10_deselectByIndex() throws Exception {
		testCase.setTestCaseDescription("For testing select.deselectByIndex");
		String url = "https://www.seleniumeasy.com/test/basic-select-dropdown-demo.html";
		driver.get(url);

		Select select = new Select(driver.findElement(By.name("States")));
		select.selectByVisibleText("Florida");
		select.selectByIndex(5);
		select.deselectByIndex(5);
		Page.scrollDown(driver.getDriver());
	}
	
	@Test(groups = {"regression"})
	public void UnitTest11_deselectByValue() throws Exception {
		testCase.setTestCaseDescription("For testing select.deselectByValue");
		String url = "https://www.seleniumeasy.com/test/basic-select-dropdown-demo.html";
		driver.get(url);

		Select select = new Select(driver.findElement(By.name("States")));
		select.selectByVisibleText("Florida");
		select.selectByIndex(5);
		select.deselectByValue("Florida");
		Page.scrollDown(driver.getDriver());
	}
	
	@Test(groups = {"regression"})
	public void UnitTest12_dragAndDrop() throws Exception {
		testCase.setTestCaseDescription("For testing Element.dragAndDrop");
		driver.get("http://demo.guru99.com/test/drag_drop.html");

		Element elementFrom = driver.findElement(By.xpath("(//*[contains(@class,'button button-orange')])[2]"));
		Element elementTo = driver.findElement(By.xpath("(//*[contains(@class,'placeholder')])[2]"));
		elementFrom.dragAndDrop(elementTo);
		Thread.sleep(5000);
	}
	
	@Test(groups = {"regression"})
	public void UnitTest13_dragAndDropJS() throws InterruptedException {
		testCase.setTestCaseDescription("For testing Element.dragAndDropJS");
		driver.get("https://smnbbrv.github.io/angular-sortablejs-demo/multiple-lists");

		Element elementFrom = driver.findElement(By.xpath("(//*[contains(text(),'Element 8')])[1]"));
		Element elementTo = driver.findElement(By.xpath("(//*[contains(text(),'Element 2')])[1]"));
		
		elementFrom.dragAndDropJS(elementTo);
		Thread.sleep(5000);
	}
	
	@Test(groups = {"regression"})
	public void UnitTest14_languageHandler() throws Exception {
		testCase.setTestCaseDescription("For testing LanguageHandler class");
		driver.get("https://www.google.com/?hl=en");
		String ExpectedText = testData.getProperty("googleSearch");
		driver.findElement(By.xpath("(//input[@name='btnK'])[2]")).verifyText(ExpectedText);
	}

	@Test(groups= {"testing"})
	public void UnitTest15_verifyElementDisplayed() {
		testCase.setTestCaseDescription("For testing verifyElementDisplayed");
		driver.get("http://msrobotassistant.com");
		driver.findElement(By.linkText("DEMO SITES")).click();
        driver.findElement(By.linkText("E-Commerce Demo Site")).click();
		driver.findElement(By.id("name-id")).sendKeys(userName);
		driver.findElement(By.id("pw")).clear();
		driver.findElement(By.id("pw")).enterPassword(password);
		driver.findElement(By.name("pwbtn")).click();
        driver.findElement(By.cssSelector("img[src*='blue-flower']")).verifyElementDisplayed();
	}
	
	@Test(groups= {"testing"})
	public void UnitTest16_verifyElementEnabledDisabled() {
		testCase.setTestCaseDescription("For testing verifyElementEnabled");
		driver.get("http://msrobotassistant.com");
		driver.findElement(By.linkText("DEMO SITES")).click();
        driver.findElement(By.linkText("E-Commerce Demo Site")).click();
		driver.findElement(By.name("pwbtn")).verifyElementEnabled();
	}
	
	@Test(groups= {"testing"})
	public void UnitTest17_verifyPageScreenShot(){
		testCase.setTestCaseDescription("For testing verifyScreenShotOnPage");
		driver.get("http://msrobotassistant.com");
		Page.verifyScreenShotOnPage(Constants.workingDir+"/src/test/resources/images/homepageScreen.jpg");
        
	}

}