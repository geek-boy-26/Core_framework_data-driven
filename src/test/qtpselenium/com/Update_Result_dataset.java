package test.qtpselenium.com;

import com.qtpselenium.util.Xls_Reader;

public class Update_Result_dataset {
	
	
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		Xls_Reader x = new Xls_Reader((System.getProperty("user.dir") + "\\src\\com\\qtpselenium\\xls\\B suite.xlsx"));
		reportDataSetResults(x,"TestCase_B1",3,"pass");
		reportDataSetResults(x,"TestCase_B1",4,"Fail");
		
	}
	//update results for paticular data set
	public static void reportDataSetResults(Xls_Reader xls, String testCaseName, int rowNum, String result)
	{
		//make sure in which sheet you need to write result and which row number you want to print the resuults
		
		xls.setCellData(testCaseName, "Results", rowNum, result);
	}

}
