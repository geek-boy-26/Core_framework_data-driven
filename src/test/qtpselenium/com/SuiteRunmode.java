package test.qtpselenium.com;

import com.qtpselenium.util.Xls_Reader;

public class SuiteRunmode {
	
	
	public static void main (String[] args)
	{
		Xls_Reader read = new Xls_Reader(System.getProperty("user.dir") + "\\src\\com\\qtpselenium\\xls\\TestSuite.xls");
		isSuiteRunnable(read, "A Suite", null);
		
	}

	// finds if test suite is runnabel 
	public static boolean isSuiteRunnable(Xls_Reader cls, String suite, String suiteName )
	{
		
		for(int i=2; i<=cls.getRowCount("Testsuite"); i++)
		{
			System.out.println(cls.getCellData("Testsuite", "TSID", i));
		}
		return false;
	}
}
