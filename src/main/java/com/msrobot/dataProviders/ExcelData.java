package com.msrobot.dataProviders;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelData {
	private XSSFWorkbook excelWorkbook;
	private XSSFSheet excelSheet;
	private XSSFCell Cell;	
	public ExcelData(String FilePath,String SheetName){
		try{
			FileInputStream fis = new FileInputStream(FilePath);
			this.excelWorkbook = new XSSFWorkbook(fis);		
			excelSheet = excelWorkbook.getSheet(SheetName);
		} catch (IOException e) {			
			e.printStackTrace();			
		}
	}	
	public void ReinitializeWorkSheet(String SheetName){			
		excelSheet = excelWorkbook.getSheet(SheetName);
	}			
    //This method is to set the File path and to open the Excel file, Pass Excel Path and Sheetname as Arguments to this method
    public void setExcelFile(String Path,String SheetName) throws Exception {
    	try {
               // Open the Excel file
            FileInputStream ExcelFile = new FileInputStream(Path);
            // Access the required test data sheet
            excelWorkbook = new XSSFWorkbook(ExcelFile);
            excelSheet = excelWorkbook.getSheet(SheetName);            
        } catch (Exception e){
            throw (e);
        }
    }

	public int RowCount(){
		int rowCount = -1;
		int rowCnt2 = excelSheet.getPhysicalNumberOfRows();
		rowCount = excelSheet.getLastRowNum();		
//		return rowCount-1;
		return rowCnt2;
	}
	
	public int ColCount(){
		int colCount = -1;
		if(excelSheet.getLastRowNum()>0){			
			colCount = excelSheet.getRow(0).getLastCellNum();
		}
		return colCount;
	}
		
	public String[] getHeader() throws IOException {		 										
		String Header[] = new String[ColCount()];	
			for (int cNum = 0; cNum < ColCount(); cNum++) {										
				Header[cNum] = getCellData(1,cNum); 						
			}					
		return Header;
	}
	
	
	@SuppressWarnings("deprecation")
	public String getCellData(int rowNum, int colNum) {	
		String cellData = "";
		try {
			Cell = excelSheet.getRow(rowNum).getCell(colNum);
			if(Cell == null) return "";			
			switch (Cell.getCellType()) {
			case 0:
				DataFormatter formatter = new DataFormatter();
				cellData=formatter.formatCellValue(Cell);
				break;
		
			case 1:
				cellData = Cell.getStringCellValue();
				break;
		
			case 2:
				cellData = String.valueOf(Cell.getCellFormula());
				break;
		
			case 4:
				cellData = String.valueOf(Cell.getBooleanCellValue());
				break;		
			default:
				break;
			}            
		} catch (Exception e) {
			//e.printStackTrace();
			return "";
		}		
		return cellData;
	}

	public String[] getDataInColName(String colName){		
		String data[] = new String[RowCount()];				
		String actualColName;
		int colIndex = 1;
		for (int cNum = 0; cNum < ColCount(); cNum++) {				
			actualColName = getCellData(0,cNum); 			
			if (actualColName.equals(colName)){								
				colIndex=cNum;
				break;
			}
		}					
		for (int rNum = 0; rNum < RowCount(); rNum++) {				
			//data[rNum-1] = getCellData(rNum+1,colIndex); 									
			data[rNum] = getCellData(rNum,colIndex);
		}	
		return data;				
	}

	//Description: Get Data of Column based on column index (colIndex) in Excel file
	public String[] getDataInColIndex(int colIndex) throws IOException {		 							
		String data[] = new String[RowCount()];						
		for (int rNum = 1; rNum <= RowCount(); rNum++) {										
			data[rNum-1] = getCellData(rNum+1,colIndex); 							
		}
		return data;
	}


	//Description: Get data in Cell
	public Object[][] getAllData() throws IOException {		 				
		Object data[][] = new Object[RowCount()][ColCount()];	
		//int dataRow=0;
		for (int rNum = 1; rNum <= RowCount(); rNum++) {
			for (int cNum = 0; cNum < ColCount(); cNum++) {
				//System.out.print("data["+dataRow+"]["+cNum+"]" + "-"+getCellData(excelSheet, cNum, rNum) + " ");							
				data[rNum-1][cNum] = getCellData(rNum,cNum); 				
			}
			//System.out.println();
		}
		return data;
	}	

	public String[] getDataInRowIndex(int rowIndex) throws IOException {		 							
		String data[] = new String[ColCount()];						
		for (int colNum = 0; colNum < ColCount(); colNum++) {										
			data[colNum] = getCellData(rowIndex,colNum); 							
		}
		return data;
	}

	public String[] getDataInRowName(String rowName) throws IOException {		 								
		String data[] = new String[ColCount()];				
		String actualRowName;
		int rowIndex = 1;
		for (int rNum = 1; rNum <=RowCount(); rNum++) {				
			actualRowName = getCellData(rNum,0); 			
			if (actualRowName.equals(rowName)){								
				rowIndex=rNum;
				break;
			}
		}					
		for (int cNum = 0; cNum < ColCount()-1; cNum++) {				
			data[cNum] = getCellData(rowIndex,cNum); 									
		}		
		return data;		
	}
	public void closeExcel(){
		try {
			excelWorkbook.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public int findRowOfCellValue(String colName, String CellValue){
		int rowNum = -1;
		try{
			int rowCountNo = RowCount();
			String data[] = new String[RowCount()];	
			data = getDataInColName(colName);			
			for(int i=0;i<data.length;i++){
				if(CellValue.equals(data[i])){
					rowNum = i;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rowNum;
	}
}
