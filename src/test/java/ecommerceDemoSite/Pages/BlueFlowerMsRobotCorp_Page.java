package ecommerceDemoSite.Pages;

import org.openqa.selenium.By;

import com.msrobot.core.web.Element;

public class BlueFlowerMsRobotCorp_Page{
    public static Element cssElement1 = new Element(By.cssSelector("h1.product_title.entry-title"),"Blue Flower picture");
    public static Element cssElement2 = new Element(By.cssSelector("div.woocommerce-product-details__short-description > p"),"");
    public static Element cssElement3 = new Element(By.cssSelector("span.woocommerce-Price-amount.amount"),"");
}