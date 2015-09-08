package com.qtpselenium.suiteC;

import org.testng.SkipException;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qtpselenium.util.TestUtil;

public class TestCase_C1 extends TestSuiteBase{
	@BeforeTest
	public void checkTestSkip()
	{
		if(!TestUtil.isTestCaseRunnable(suiteCXls, this.getClass().getSimpleName()))
		{
			APP_LOGS.debug("Skipping testcase as test"+this.getClass().getSimpleName()+" case runmode set to no in test case"); //logs
			throw new SkipException("Test Case skipped as runmode set to no");//reports
		}
	}
	@Test(dataProvider="getTestData")
	public void testCaseC1(String col1,String col2,String col3,String col4) //Arguments should be same as number of columns
	{
		//test method would be called 4 times
		APP_LOGS.debug("Executing TestCase_C1");
		APP_LOGS.debug(col1+ "--"+col2+"---"+col3+"--"+col4 );
	}
	
	@DataProvider
	public Object[][] getTestData()
	{
		return TestUtil.getData(suiteCXls, this.getClass().getSimpleName());
	}
}
