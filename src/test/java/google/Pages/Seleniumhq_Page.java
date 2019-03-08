package google.Pages;

import org.openqa.selenium.By;

import com.msrobot.core.web.Element;

public class Seleniumhq_Page {
	 public static Element txtSeleniumWebDriver = new Element(By.xpath("//h3[contains(text(),'Selenium WebDriver')]"),"Selenium WebDriver");
	 public static Element txtSeleniumIDE = new Element(By.xpath("//h3[contains(text(),'Selenium IDE')]"),"Selenium IDE");
}
