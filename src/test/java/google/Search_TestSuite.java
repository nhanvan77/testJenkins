package google;

import org.openqa.selenium.Keys;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.msrobot.baseTestSuite.BaseTestSuite;
import com.msrobot.msDriver.MsWebDriver;
import com.msrobot.testData.Excel_TestData_format1;
import com.msrobot.ultilities.TestEnvironment;

import google.Pages.Search_Page;
import google.Pages.Seleniumhq_Page;


public class Search_TestSuite extends BaseTestSuite {
	TestEnvironment testClassEnvironment = new com.msrobot.ultilities.TestEnvironment();
	private String URLTest;
	private Excel_TestData_format1 testData;
	@BeforeClass	
	@Parameters({"browserTest"})
	public void TestClassSetUp(@Optional("CHROME") String browserTest){		
		String filePath = System.getProperty("user.dir")
				+ "\\src\\test\\java\\google\\Data\\SmokeTest_Testdata_QA.xlsx"; 
		testData = new Excel_TestData_format1(filePath);
		//driver = new MsWebDriver(browserTest);
		URLTest = super.ULRTest;
	}
	
	@BeforeMethod
	@Parameters({"browserTest"})
	public void setupBeforeTest(@Optional("CHROME") String browserTest){		
		driver = new MsWebDriver(browserTest);
	}
	@AfterClass
	public void TestClassTearDown(){
		driver.closeBrowser();
	}
	@Test
	public void TC01() throws Exception{
		testData.setTestCaseID(testName);
		testCase.setTestCaseDescription(testData.getData("Main", "TEST_DESCRIPTION"));
		driver.get(URLTest);
		driver.findElement(Search_Page.txtSearch).click();
        driver.findElement(Search_Page.txtSearch).sendKeys("selenium automation");
        driver.findElement(Search_Page.txtSearch).enterKeyBoard(Keys.ENTER);
        driver.findElement(Search_Page.lnkSeleniumHq).verifyText("Selenium - Web Browser Automation");
        driver.findElement(Search_Page.lnkSeleniumHq).click();
        driver.findElement(Search_Page.lnkBrowserAutomation).verifyText("Browser Automation");
        driver.findElement(Seleniumhq_Page.txtSeleniumWebDriver).verifyText("Selenium WebDriver");
        driver.findElement(Seleniumhq_Page.txtSeleniumIDE).verifyText("Selenium IDE");
	}
	@Test
	public void TC02() throws Exception{
		testData.setTestCaseID(testName);
		testCase.setTestCaseDescription(testData.getData("Main", "TEST_DESCRIPTION"));
		driver.get(URLTest);
		driver.findElement(Search_Page.lnkStore).click();
		driver.findElement(Search_Page.imgGoogleStorelogo).verifyElementDisplayed();
		driver.findElement(Search_Page.iconShoppingCart).verifyElementDisplayed();

	}
	@Test
	public void TC03() throws Exception{
		testData.setTestCaseID(testName);
		testCase.setTestCaseDescription(testData.getData("Main", "TEST_DESCRIPTION"));
		driver.get(URLTest);
		driver.findElement(Search_Page.lnkAbout).click();
		driver.findElement(Search_Page.lnkOurProduct).verifyElementDisplayed();
	}
	@Test
	public void TC04() throws Exception{
		testData.setTestCaseID(testName);
		testCase.setTestCaseDescription(testData.getData("Main", "TEST_DESCRIPTION"));
		driver.get(URLTest);
		driver.findElement(Search_Page.lnkAbout).click();
		driver.findElement(Search_Page.lnkWrongLink).verifyElementDisplayed();
	}
}
