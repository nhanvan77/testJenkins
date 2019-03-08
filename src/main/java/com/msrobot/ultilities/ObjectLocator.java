package com.msrobot.ultilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.By;

import com.msrobot.logs.MsLog;

public class ObjectLocator {
	Properties properties;
	private String mapFile;
	  
  public ObjectLocator(String mapFile)
  {
	  properties = new Properties();
	  this.mapFile = mapFile;
	  try {
		  FileInputStream PropertiesFile = new FileInputStream(mapFile);
		  properties.load(PropertiesFile);
		  PropertiesFile.close();
      }catch (IOException e) {
        System.out.println(e.toString());
     }
  }


   public By getLocator(String ElementName) throws Exception{	 
	   try{
         String locator = properties.getProperty(ElementName);

         String locatorType = locator.split(":")[0];
         String locatorValue = locator.split(":")[1];	   
           if(locatorType.toLowerCase().equals("id"))
                 return By.id(locatorValue);
           else if(locatorType.toLowerCase().equals("name"))
                 return By.name(locatorValue);
           else if((locatorType.toLowerCase().equals("classname")) || (locatorType.toLowerCase().equals("class")))
                 return By.className(locatorValue);
           else if((locatorType.toLowerCase().equals("tagname")) || (locatorType.toLowerCase().equals("tag")))
                 return By.className(locatorValue);
           else if((locatorType.toLowerCase().equals("linktext")) || (locatorType.toLowerCase().equals("link")))
                 return By.linkText(locatorValue);
           else if(locatorType.toLowerCase().equals("partiallinktext"))
                 return By.partialLinkText(locatorValue);
           else if((locatorType.toLowerCase().equals("cssselector")) || (locatorType.toLowerCase().equals("css")))
                 return By.cssSelector(locatorValue);
           else if(locatorType.toLowerCase().equals("xpath"))
                 return By.xpath(locatorValue);
           else
                   throw new Exception("Locator type '" + locatorType + "' not defined!!");
	   }catch(Exception e){
		   MsLog.fail("", "Cannot find "+ElementName + "in "+ mapFile);
		   MsLog.fail("","Class: ultility.ObjectLocator | Method: getLocator | Exception occured - "+e.toString() +
					" at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber());		   
		   throw(e);
	   }
     }
   public String getPropertyValue(String sPropertyName){
	   try{
		   return properties.getProperty(sPropertyName);
	   }catch(Exception e){		   
		   return "NO PROPERTY VALUE FOUND";
	   }
   }
}
