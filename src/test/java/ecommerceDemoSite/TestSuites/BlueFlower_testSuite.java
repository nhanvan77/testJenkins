package ecommerceDemoSite.TestSuites;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.msrobot.baseTestSuite.BaseTestSuite;
import com.msrobot.msDriver.MsWebDriver;

import ecommerceDemoSite.Pages.BlueFlowerMsRobotCorp_Page;
import ecommerceDemoSite.Pages.ECommerceDemoSiteMsRobot_Page;
import ecommerceDemoSite.Pages.ECommerceSiteMsRobotCorp_Page;
import ecommerceDemoSite.Pages.MsRobotCorpARobot_Page;

public class BlueFlower_testSuite extends BaseTestSuite {
	private String URLTest;
	private String userName;
	private String password;
	
	@BeforeClass
	@Parameters({ "browserTest" })
	public void TestClassSetUp(@Optional("CHROME") String browserTest) {
		driver = new MsWebDriver(browserTest);
		URLTest = super.ULRTest;
		userName = super.userName;
		password = super.password;
	}

	@AfterClass
	public void TestClassTearDown() {
		driver.closeBrowser();
	}
	@Test
    public void Tc001Gotoecommercesitehomepage() {
        driver.get(URLTest);
        
        driver.findElement(MsRobotCorpARobot_Page.lnkDemoSites).click();
        driver.findElement(MsRobotCorpARobot_Page.lnkECommerceDemoSite).click();
        driver.findElement(ECommerceDemoSiteMsRobot_Page.eleNameId).click();
        
        driver.findElement(ECommerceDemoSiteMsRobot_Page.eleNameId).sendKeys("test");
        driver.findElement(ECommerceDemoSiteMsRobot_Page.elePw).sendKeys("Robot2018");
        driver.findElement(ECommerceDemoSiteMsRobot_Page.elePwbtn).click();
        driver.findElement(ECommerceSiteMsRobotCorp_Page.cssElement1).verifyText("E-Commerce Site");
    }
	@Test
    public void Tc002Blueflowerpageitems() {
        driver.get(URLTest);
        
        driver.findElement(MsRobotCorpARobot_Page.lnkDemoSites).click();
        driver.findElement(MsRobotCorpARobot_Page.lnkECommerceDemoSite).click();
        driver.findElement(ECommerceDemoSiteMsRobot_Page.eleNameId).click();
        driver.findElement(ECommerceDemoSiteMsRobot_Page.eleNameId).sendKeys("test");
        driver.findElement(ECommerceDemoSiteMsRobot_Page.elePw).sendKeys("Robot2018");
        driver.findElement(ECommerceDemoSiteMsRobot_Page.elePwbtn).click();
        driver.findElement(ECommerceSiteMsRobotCorp_Page.cssElement1).click();
        driver.findElement(BlueFlowerMsRobotCorp_Page.cssElement1).click();
        driver.findElement(BlueFlowerMsRobotCorp_Page.cssElement1).verifyText("Blue Flower");
        driver.findElement(BlueFlowerMsRobotCorp_Page.cssElement2).click();
        driver.findElement(BlueFlowerMsRobotCorp_Page.cssElement2).verifyText("This delightful bouquet will be the perfect choice for a cottage, condo, or even a grand estate. Its beauty will provide smiles to all who receive it! Choose this bouquet");
        driver.findElement(BlueFlowerMsRobotCorp_Page.cssElement3).verifyText("$29.99");
    }
}
