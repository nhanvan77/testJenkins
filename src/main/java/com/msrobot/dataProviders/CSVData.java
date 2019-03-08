package com.msrobot.dataProviders;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.msrobot.logs.MsLog;
import com.msrobot.ultilities.FileHandler;

public class CSVData {
	private static String delimiter = ",";

    /**
     * To get the test data from a csv file. It returns all the data in array
     * @author Thao.le
     * @throws Exception 
     */
    public static Object[][] getData(String filePath, boolean excludeHeaderRow) throws Exception {
        MsLog.debug("+ In Class: CSVData | Method: getData() ");
        String line = "";
        String[][] dataArray = null;
        List<String> csvRowList = new ArrayList<String>();
        String[] rowSplit;
        int columnCount = 0;
        int rowCount = 0;

        // Get the file location from the project main/resources folder
        if (!(filePath.contains(":") || filePath.startsWith("/"))) {
            URL file = CSVData.class.getResource(filePath);
            if (file == null) {
               MsLog.debug("+ No file was found on path [ " + filePath + " ]");
            }
            filePath = file.getPath();
        }

        // in case file path has a %20 for a whitespace, replace with actual
        // whitespace
        filePath = filePath.replace("%20", " ");

        MsLog.debug("+ File path of CSV to open [ " + filePath + " ]");
        // open the CSV and add each line into a list
        try (BufferedReader bufferedReader = FileHandler.openTextFileFromProject(filePath)) {
        	 MsLog.debug("+ Successfully loaded FileReader object into BufferedReader");

        	 MsLog.debug("+ Read in file and load each line into a List");
            while ((line = bufferedReader.readLine()) != null) {
                csvRowList.add(line);
            }
            MsLog.debug("+ Successfully read in [ " + csvRowList.size() + " ] lines from file");
        } catch (IOException e) {
            throw new Exception("Failed to read in CSV file", e);
        }

        if (excludeHeaderRow) {
            // Remove first line of headers
            csvRowList.remove(0);
        }

        MsLog.debug("+ Determining column count based on delimiter [ " + delimiter + " ]");
        columnCount = csvRowList.get(0).split(delimiter).length;
        MsLog.debug("+ Found [ " + (columnCount + 1) + " ] columns");

        MsLog.debug("+ Determining row count");
        rowCount = csvRowList.size();
        MsLog.debug("+ Found [ " + (rowCount + 1) + " ] rows");

        MsLog.debug("+ Attempting to create an array based on rows and columns. Array to built [" + (rowCount + 1) + "][" + (columnCount) + "]");
        dataArray = new String[rowCount][columnCount];

        // transform the list into 2d array
        // start at row 1 since, first row 0 is column headings
        MsLog.debug("+ Transferring data to Array");
        for (int rowNum = 0; rowNum <= rowCount - 1; rowNum++) {

            // take the row which is a string, and split it
            rowSplit = csvRowList.get(rowNum).split(delimiter);

            for (int colNum = 0; colNum < columnCount; colNum++) {
                try {
                    dataArray[rowNum][colNum] = rowSplit[colNum];
                } catch (ArrayIndexOutOfBoundsException e) {
                    dataArray[rowNum][colNum] = "";
                }
            }
        }

        MsLog.debug("+ Exiting CSVData#getData");
        return dataArray;
    }

    public static Object[][] getData(String filePath, String delimiterValue, boolean excludeHeaderRow) throws Exception {
        MsLog.debug("+ Overriding default delimiter of [ , ] to be [ " + delimiter + " ]");
        delimiter = delimiterValue;
        return getData(filePath, excludeHeaderRow);
    }

    public static Object[][] getData(String filePath, String delimiterValue) throws Exception {
        MsLog.debug("+ Overriding default delimiter of [ , ] to be [ " + delimiter + " ]");
        delimiter = delimiterValue;
        return getData(filePath, true);
    }

    public static Object[][] getData(String filePath) throws Exception {
        return getData(filePath, true);
    }
}
