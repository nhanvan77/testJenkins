package mobileTest;

import java.io.IOException;
import java.net.MalformedURLException;

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
import com.msrobot.msDriver.MsAppiumDriver;
import com.msrobot.msDriver.MsWebDriver;
import com.msrobot.testData.CSV_TestData;
import com.msrobot.testData.Excel_TestData_format1;
import com.msrobot.ultilities.FileHandler;

public class MobileAppiumDriverTest {
	
	@BeforeClass (alwaysRun=true)
	@Parameters({ "mobilePropertiesFileTest" })
	public void TestClassSetUp(@Optional("android.properties") String mobilePropertiesFileTest) {
		String mobilePropertiesFile = mobilePropertiesFileTest;
		MsAppiumDriver driver = new MsAppiumDriver(mobilePropertiesFile);

	}



	@Test(groups= {"appium"})
	@Parameters({ "mobilePropertiesFileTest" })
	public void UnitTest18_verifyinitializeAppiumDriver() throws IOException {
		//testCase.setTestCaseDescription("For testing verifyScreenShotOnPage");
		MsAppiumDriver ap=new MsAppiumDriver("android.properties");
	}
	
	
}