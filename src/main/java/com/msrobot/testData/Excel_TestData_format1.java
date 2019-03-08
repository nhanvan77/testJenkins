package com.msrobot.testData;

import com.msrobot.dataProviders.ExcelData;
import com.msrobot.logs.MsLog;

public class Excel_TestData_format1 {

	private String testCaseID = null;
	private String FilePath = null;
	private ExcelData excelData;

	public Excel_TestData_format1(String FilePath, String testCaseID) {
		this.testCaseID = testCaseID;
		this.FilePath = FilePath;
	}
	
	public Excel_TestData_format1(String FilePath) {
		this.FilePath = FilePath;
	} 
	public String getData(String sheetName, String columnName) {
		String cellData = "";
		MsLog.info("+ Get Data "+columnName+" from excel sheet: "+sheetName);
		if(columnName == null) {
			MsLog.error("In Class: dataProviders.Excel_TestData_format1 | Method: getData() ERROR Cause: File name is Null");
		}
		try {
			excelData = new ExcelData(FilePath, sheetName);
			if (verifyTestCaseColumnExisting()) {
				int row = getTestCaseRowIndex();
				int col = getColumnIndex(columnName);
				cellData = excelData.getCellData(row, col);
			} else {
				MsLog.error("+ In Class: dataProviders.Excel_TestData_format1 | Method: getData() "
						+ "is NOT finished successfully caused TestcaseID column is not existing in sheet "+sheetName);
			}
		} catch (Exception e) {
			MsLog.error("** Actual result: User can NOT get "+columnName+" data from sheet "+sheetName+" ,"
					+ "Class: dataProviders.Excel_TestData_format1 | Method: getData | Exception occured: "+e.toString() +
       				" at line number: " + Thread.currentThread().getStackTrace()[1].getLineNumber());
		}
		return cellData;
	}

	public boolean verifyTestCaseColumnExisting() {
		boolean TC_ID = false;
		try {
			int colNumber = excelData.ColCount();
			for (int col = 0; col < colNumber; col++) {
				if (excelData.getCellData(0, col).equals("TestCaseID")) {
					TC_ID = true;
					break;
				}
			}
		} catch (Exception e) {
			MsLog.error("+ In Class: dataProviders.Excel_TestData_format1 | Method: verifyTestCaseColumnExisting() "
					+ "is NOT finished successfully");
		}
		
		return TC_ID;
	}

	public int getTestCaseRowIndex() {
		int rowCount = excelData.RowCount();
		int TCColIndex = getColumnIndex("TestCaseID");
		try {
			for (int row = 1; row < rowCount; row++) {
				if (excelData.getCellData(row, TCColIndex).contains(testCaseID)) {
					TCColIndex = row;
					break;
				}
			}
		} catch (Exception e) {
			MsLog.error("+ In Class: dataProviders.Excel_TestData_format1 | Method: getTestCaseRowIndex() "
					+ "is NOT finished successfully");
		}
		return TCColIndex;
	}

	public int getColumnIndex(String columnName) {
		int colCount = excelData.ColCount();
		int colIndex = 0;
		try {
			for (int col = 1; col < colCount; col++) {
				if (excelData.getCellData(0, col).contains(columnName)) {
					colIndex = col;// s
					break;
				}
			}
		} catch (Exception e) {
			MsLog.error("+ In Class: dataProviders.Excel_TestData_format1 | Method: getColumnIndex() "
					+ "is NOT finished successfully");
		}
		return colIndex;
	}
	
	public void setTestCaseID(String testCaseID){
		this.testCaseID = testCaseID;
	}

}
