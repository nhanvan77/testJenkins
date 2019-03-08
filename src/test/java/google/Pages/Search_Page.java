package google.Pages;

import org.openqa.selenium.By;

import com.msrobot.core.web.Element;

public class Search_Page {
	 public static Element txtSearch = new Element(By.name("q"),"Search textbox");
    public static Element lnkSeleniumHq = new Element(By.xpath("//h3[contains(text(),'Selenium - Web Browser Automation')]"),"Selenium - Web Browser Automation");
    public static Element lnkBrowserAutomation = new Element(By.linkText("Browser Automation"),"Browser Automation");
	public static Element lnkStore= new Element(By.linkText("Store"),"Store");
	public static Element lnkAbout= new Element(By.linkText("About"),"About");
	public static Element lnkOurProduct= new Element(By.linkText("Our products"),"Our products");
	public static Element imgGoogleStorelogo= new Element(By.xpath("//a[@aria-label='Google Store home. Google Store logo.']"),"Google Store logo");
	public static Element iconShoppingCart= new Element(By.xpath("//a[@class='nav-link cart-link top-link']"),"Shopping Cart Icon");
	public static Element lnkWrongLink= new Element(By.linkText("Sample of FAIL link"),"Sample of FAIL link");
	
}
