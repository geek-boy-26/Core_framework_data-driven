package com.qtpselenium.suiteA;

import org.testng.SkipException;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qtpselenium.util.TestUtil;

public class TestCase_A1 extends TestSuiteBase
{
	
	
	//Check run mode of test case in suite
	@BeforeTest
	public void checkTestSkip()
	{
		if(!TestUtil.isTestCaseRunnable(suiteAXls, this.getClass().getSimpleName()))
		{
			APP_LOGS.debug("Skipping testcase as test"+this.getClass().getSimpleName()+" case runmode set to no in test case"); //logs
			throw new SkipException("Test Case skipped as runmode set to no A1");//reports
		}
	}
	
	
	@Test(dataProvider="getTestData")
	public void testCaseA1(String col1,String col2,String col3,String col4) //Arguments should be same as number of columns
	{
		//test method would be called 4 times
		APP_LOGS.debug("Executing TestCase_A1");
		APP_LOGS.debug(col1+ "--"+col2+"---"+col3+"--"+col4 );
	}
	
	@DataProvider
	public Object[][] getTestData()
	{
		return TestUtil.getData(suiteAXls, this.getClass().getSimpleName());
	}

}
