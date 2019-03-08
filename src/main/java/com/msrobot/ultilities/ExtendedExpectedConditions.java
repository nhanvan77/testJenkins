package com.msrobot.ultilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.ExpectedCondition;

public class ExtendedExpectedConditions {
	 public static ExpectedCondition<Boolean> findWindowWithTitleAndSwitchToIt(final String title){
		   return new ExpectedCondition<Boolean>() {
			      @Override
			      public Boolean apply(WebDriver driver) {
			    	  try{
				    	  for(String handle : driver.getWindowHandles()){
				   	       	driver.switchTo().window(handle);
				   	       	if(driver.getTitle().equals(title)) return true;
				    	  }
			    	  }catch (WebDriverException e) {
			              return null;
			          }
			   	   
			    	  return false;
			      }

			      @Override
			      public String toString() {
			    	  return String.format("text ('%s') to be present in window %s", title);
			      }
			  };
	   }
	 public static ExpectedCondition<Boolean> findWindowContainsTitleAndSwitchToIt(final String title){
		   return new ExpectedCondition<Boolean>() {
			      @Override
			      public Boolean apply(WebDriver driver) {
			    	  try{
				    	  for(String handle : driver.getWindowHandles()){
				    		  driver.switchTo().window(handle);
				    		  if(driver.getTitle().contains(title)) return true;
				    	  }
		    	  }catch (WebDriverException e) {
		              return null;
		          }
			   	   return false;
			      }

			      @Override
			      public String toString() {
			    	  return String.format("text ('%s') to be present in window %s", title);
			      }
			  };
	   }
}
