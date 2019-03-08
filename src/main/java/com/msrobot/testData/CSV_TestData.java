package com.msrobot.testData;

import com.msrobot.dataProviders.CSVData;
import com.msrobot.logs.MsLog;

public class CSV_TestData {
	private String filePath;
	private String testCaseID;
	private Object[][] data;
	private int TC_Row = 0;
	private int TC_Col = 0;
	private String cellData = null;

	public CSV_TestData(String filePath) {
		this.filePath = filePath;
		readCSV();
	}

	public CSV_TestData(String filePath, String testCaseID) {
		this.filePath = filePath;
		this.testCaseID = testCaseID;
		readCSV();
	}

	public String getData(String ColumnName) {
		try {
			if (checkTesCaseID() && checkTestCaseColumnName(ColumnName)) {
				this.cellData = (String) this.data[TC_Row][this.TC_Col];
				MsLog.debug("+ In Class: testData.CSV_TestData | Method: getData(), get '" + ColumnName
						+ "' for TestCaseID : '" + this.testCaseID + "' is finished successfully. Data receiving: '"
						+ this.cellData + "'");
			} else {
				MsLog.debug("+ In Class: testData.CSV_TestData | Method: getData(), get '" + ColumnName
						+ "' for TestCaseID : '" + this.testCaseID + "' is NOT finished successfully.");
			}
		} catch (Exception e) {
			MsLog.fail(
					"+ In Class: testData.CSV_TestData | Method: getData() is NOT finished successfully.| Exception occured - "
							+ e.toString());
		}
		return this.cellData;
	}

	public boolean checkTesCaseID() {
		boolean foundTC = false;
		try {
			for (int row = 0; row <= this.data.length; row++) {
				if (data[row][0].equals(this.testCaseID)) {
					this.TC_Row = row;
					foundTC = true;
					break;
				}
			}
			if (!foundTC) {
				MsLog.debug("+ In Class: testData.CSV_TestData | Method: checkTesCaseID() for TestCaseID : '"
						+ this.testCaseID + "' is NOT finished successfully caused test case ID: " + this.testCaseID
						+ " is not matched with CSV data");
			}

		} catch (Exception e) {
			MsLog.fail("+ In Class: testData.CSV_TestData | Method: checkTesCaseID() for TestCaseID : '"
					+ this.testCaseID + "' is NOT finished successfully.| Exception occured - " + e.toString());
		}
		return foundTC;
	}

	public boolean checkTestCaseColumnName(String ColumnName) {
		boolean foundCol = false;
		try {
			for (int col = 0; col <= this.data[0].length; col++) {
				if (data[0][col].equals(ColumnName)) {
					this.TC_Col = col;
					foundCol = true;
					break;
				}
			}
			if (!foundCol) {
				MsLog.debug("+ In Class: testData.CSV_TestData | Method: checkTestCaseColumnName() for ColumnName : '"
						+ ColumnName + "' is NOT finished successfully caused column name: " + ColumnName
						+ " is not matched with CSV data");
			}
		} catch (Exception e) {
			MsLog.fail("+ In Class: testData.CSV_TestData | Method: checkTestCaseColumnName() for ColumnName : '"
					+ ColumnName + "' is NOT finished successfully.| Exception occured - " + e.toString());
		}
		return foundCol;
	}

	public void setTestCaseID(String testCaseID) {
		this.testCaseID = testCaseID;
	}

	public void readCSV() {
		try {
			this.data = CSVData.getData(this.filePath, false);
		} catch (Exception e) {
			MsLog.fail(
					"+ In Class: testData.CSV_TestData | Method: readCSV() is NOT finished successfully caused read CSV file failed. | Exception occured - "
							+ e.toString());
		}
	}
}
