package com.qtpselenium.util;
//various Methods
public class TestUtil

{
	
		// finds if test suite is runnabel 
		public static boolean isSuiteRunnable(Xls_Reader cls, String suitename )
		{
			boolean isExceutable=false;
			
			for(int i=2; i<=cls.getRowCount("TestSuite"); i++)
			{
				
				String suite = cls.getCellData("TestSuite", "TSID", i);
				String runmode = cls.getCellData("Testsuite", "Runmode", i);
				if( suite.equals(suitename))
				{
					if(runmode.equalsIgnoreCase("Y"))
					{
						isExceutable = true;
					}else
					{
						isExceutable = false;
					}
				}
			}
			cls=null;
			return isExceutable;
		}
	
	
		// returns true if runmode of the test is equal to Y
		public static boolean isTestCaseRunnable(Xls_Reader xls, String testCaseName){
			boolean isExecutable=false;
			for(int i=2; i<= xls.getRowCount("Test Cases") ; i++){
				/*String tcid=xls.getCellData("Test Cases", "TCID", i);
				String runmode=xls.getCellData("Test Cases", "Runmode", i);
				System.out.println(tcid +" -- "+ runmode);
				*/
				
				if(xls.getCellData("Test Cases", "TCID", i).equalsIgnoreCase(testCaseName)){
					if(xls.getCellData("Test Cases", "Runmode", i).equalsIgnoreCase("Y")){
						isExecutable= true;
					}else{
						isExecutable= false;
					}
				}
			}
			
			return isExecutable;
			
		}

		
		//return test data from a test in two dimensional array
		public static Object[][] getData(Xls_Reader xls,String testCasename)
		{
			//if the sheet is not present 
			if(!xls.isSheetExist(testCasename))
			{
				xls=null; //for memory clean up
				//there is no test data so 0 and 1 because exceute test case once
				return new Object[1][0];
			}
			
			
			int rows= xls.getRowCount(testCasename);
			int cols= xls.getColumnCount(testCasename);
			System.out.println("Rows are "+ rows + "cols are "+ cols);
			
			//add the data into objetc array
					Object[][] data = new Object[rows-1][cols-3]; 
					
			//xtract data 
			for(int rowNum=2; rowNum<=rows;rowNum++)
			{
				for(int colNum=0; colNum<cols-3;colNum++)
				{
					//System.out.print(xls.getCellData(testCasename, colNum, rowNum)+ "---");
					data[rowNum-2][colNum] =xls.getCellData(testCasename, colNum, rowNum);
					
				}
				//System.out.println();
			}
			
			return data;
			
		}

		//update results for paticular data set
		public static void reportDataSetResults(Xls_Reader xls, String testCaseName, int rowNum, String result)
		{
			//make sure in which sheet you need to write result and which row number you want to print the resuults
			
			xls.setCellData(testCaseName, "Results", rowNum, result);
		}
		
		
		//Check runmode for dataset
		public static String[] getDataSetRunmodes(Xls_Reader xlsFile,String sheetName){
			String[] runmodes=null;
			if(!xlsFile.isSheetExist(sheetName)){
				xlsFile=null;
				sheetName=null;
				runmodes = new String[1];
				runmodes[0]="Y";
				xlsFile=null;
				sheetName=null;
				return runmodes;
			}
			runmodes = new String[xlsFile.getRowCount(sheetName)-1];
			for(int i=2;i<=runmodes.length+1;i++){
				runmodes[i-2]=xlsFile.getCellData(sheetName, "Runmode", i);
			}
			xlsFile=null;
			sheetName=null;
			return runmodes;
			
		}
		
		
		//return row number for a test
		public static int getRowNum(Xls_Reader xlsFile, String id)
		{
			for(int i=2; i<= xlsFile.getRowCount("Test Cases") ; i++)
			{
				String tcid=xlsFile.getCellData("Test Cases", "TCID", i);
				
				if(tcid.equals(id))
				{
					xlsFile=null;
					return i;
				}
				
			}
			return -1;
		}
}
