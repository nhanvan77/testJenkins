package ecommerceDemoSite.Pages;

import org.openqa.selenium.By;

import com.msrobot.core.web.Element;

public class ECommerceDemoSiteMsRobot_Page{
    public static Element eleNameId = new Element(By.id("name-id"),"user");
    public static Element elePw = new Element(By.id("pw"),"password");
    public static Element elePwbtn = new Element(By.name("pwbtn"),"login");
}