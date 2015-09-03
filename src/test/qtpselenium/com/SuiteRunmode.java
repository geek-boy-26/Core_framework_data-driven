package test.qtpselenium.com;

import com.qtpselenium.util.Xls_Reader;

public class SuiteRunmode {
	
	
	public static void main (String[] args)
	{
		Xls_Reader read = new Xls_Reader(System.getProperty("user.dir") + "\\src\\com\\qtpselenium\\xls\\TestSuite.xlsx");
		System.out.println(isSuiteRunnable(read, "A Suite"));
		System.out.println(isSuiteRunnable(read, "B Suite"));
		System.out.println(isSuiteRunnable(read, "C Suite"));
	}

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
}
