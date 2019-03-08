package com.msrobot.core.web;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.msrobot.constant.Constants;
import com.msrobot.logs.MsLog;
import com.msrobot.msDriver.ExecutionManager;
import com.msrobot.msDriver.MsWebDriver;

public class WebTable{
	public String tableName;
	private WebElement table;
	@SuppressWarnings("unused")
	private WebDriver driver;
	public WebTable(WebDriver driver) {		
		this.driver = driver;		
	}
	public WebTable(WebDriver driver, By by,String tableName) {		
		this.driver = driver;
		try{
			WebDriverWait wait = new WebDriverWait(driver, Constants.EXPLICIT_WAITTIME);
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			this.table = driver.findElement(by);		
			this.tableName = tableName;
		}catch(Exception e){
			MsLog.fail("Cannot find '"+tableName, "Class: core.WebTable | Method: WebTable | Exception occured - "+e.getMessage());
			throw(e);
		}
	}
	public WebTable(By by,String tableName) {		
		this.driver = ExecutionManager.getSession().getDriver();
		try{
			WebDriverWait wait = new WebDriverWait(driver, Constants.EXPLICIT_WAITTIME);
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			this.table = driver.findElement(by);		
			this.tableName = tableName;
		}catch(Exception e){
			MsLog.fail("Cannot find '"+tableName, "Class: core.WebTable | Method: WebTable | Exception occured - "+e.getMessage());
			throw(e);
		}
	}
	public WebTable(WebDriver driver, WebElement tableElement,String tableName) {
		this.driver = driver;
		this.table = tableElement;		
		this.tableName = tableName;			
	}	
	public int getBodyRowCount(){
		List<WebElement> rowCollection = table.findElements(By.xpath("tr|tbody/tr"));
		int rows = rowCollection.size();
		return rows;
	}
	public int getHeaderRowCount(){
		int rows = 0;
		try{
			List<WebElement> rowCollection = table.findElements(By.xpath("tr|thead/tr"));
			rows = rowCollection.size();
		}catch(Exception e){
			MsLog.fail("Class: core.WebTable | Method: getHeaderRowCount | Exception occured - ",e.getMessage());
			throw(e);
		}
		return rows;
	}
	public int getBodyColumnCount(){
		int cols = 0;
		try{
			List<WebElement> rowCollection = table.findElements(By.xpath("tr|tbody/tr"));			
			WebElement firstRow = rowCollection.get(0);
			List<WebElement> columnCollection = firstRow.findElements(By.xpath("th|td"));
			cols = columnCollection.size();	
		}catch(Exception e){
			MsLog.fail("Class: core.WebTable | Method: getBodyColumnCount | Exception occured - ",e.getMessage());
			throw(e);
		}
		return cols;
	}
	public int getHeaderColumnCount(){
		int cols = 0;
		try{
		List<WebElement> rowCollection = table.findElements(By.xpath("tr|thead/tr"));			
		WebElement firstRow = rowCollection.get(0);
		List<WebElement> columnCollection = firstRow.findElements(By.xpath("th|td"));
		cols = columnCollection.size();	
		}catch(Exception e){
			MsLog.fail("Class: core.WebTable | Method: getHeaderColumnCount | Exception occured - ",e.getMessage());
			throw(e);
		}
		return cols;
	}
	public String getCell(int rowIndex, int colIndex){
			String cellValue = null;
		try{
			List<WebElement> rowCollection = table.findElements(By.xpath("tr|tbody/tr"));				
			int i = 0;				//
			WebElement firstRow = rowCollection.get(rowIndex);
		    List<WebElement> columnCollection = firstRow.findElements(By.xpath("th|td"));
		    for(WebElement col:columnCollection){
		        i++;
		        if(colIndex==i){
		        	cellValue = col.getText();
		             break;
		        }
		    }
		}catch(Exception e){
			MsLog.fail("Class: core.WebTable | Method: getCell | Exception occured - ",e.getMessage());
			throw(e);
		}
		return cellValue;
	}
	public int getColIndex(String colName){		
		int j = 0;	
		String headerCol;
		try{
			List<WebElement> headRowCollection = table.findElements(By.xpath("tr|thead/tr"));
			WebElement firstRow = headRowCollection.get(0);		
			    List<WebElement> columnCollection = firstRow.findElements(By.xpath("th|td"));		   
			    for(WebElement col:columnCollection){
			        j++;   
			        headerCol = col.getText();
			        if(colName.equals(headerCol)){    		             
			           break;
			        }
			  }	
		}catch(Exception e){
			MsLog.fail("Class: core.WebTable | Method: getColIndex | Exception occured - ",e.getMessage());
			throw(e);
		}
	    return j;
	}
	
	/**
	 * To get index of Text in table
	 * 
	 * @param text
	 * @return (row,col) location of text
	 * @author Thao Le
	 */
	public int[] getRowColIndexOfText(String text){
		MsLog.debug("Get (row,column) location of '"+ text +"' in Webtable");
		List<WebElement> rowCollection = table.findElements(By.xpath("tr|tbody/tr")); 
		int rows = rowCollection.size(); 
		int rowIndex = -1;    
		int colIndex = -1;
		int j;
	    for(int i=0;i<rows;i++){ 
	        WebElement row = rowCollection.get(i); 
	        List<WebElement> columnCollection = row.findElements(By.xpath("th|td")); 
	        j = 0;
	        for(WebElement col:columnCollection){   	            
	            if(col.getText().contains(text)){ 
	                MsLog.debug(text+" is Found at (row,col) = ("+ i +","+j+")");
	                rowIndex = i;
	                colIndex = j;
	                break; 
	            }             	                   
	            j++;                 
	        }         
	    } 
	    int[] indexOfText = {rowIndex,colIndex};
	    return indexOfText;
	}
}
