package com.qtpselenium.Login_Suite;

import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qtpselenium.util.ErrorUtil;
import com.qtpselenium.util.TestUtil;

public class Checklogin extends TestSuiteBase
{

	String runmodes[]=null;  //this is to check the run mode of data in test case with the values Y or N
	static int count=-1;
	static boolean pass = false;	
	static boolean fail = false;
	static boolean skip = false;
	static boolean isTestPass = true;
	
	//Check run mode of test case in suite
	@BeforeTest
	public void checkTestSkip()
	{
		if(!TestUtil.isTestCaseRunnable(LoginSuiteXls, this.getClass().getSimpleName()))
		{
			APP_LOGS.debug("Skipping testcase as test"+this.getClass().getSimpleName()+" case runmode set to no in test case"); //logs
			throw new SkipException("Test Case skipped as runmode set to no Login");//reports
		}
		
		
		//load runmodes off the test
		runmodes= TestUtil.getDataSetRunmodes(LoginSuiteXls, this.getClass().getSimpleName());
	}
	
	
	@Test(dataProvider="getTestData")// class name is not same else it would consider contrructor 
	public void CheckLogin(String col1,String col2) //Arguments should be same as number of columns
	{
		
		//test run mode of current data set
		count++;
		if(!runmodes[count].equalsIgnoreCase("Y"))
		{
			skip=true;
			throw new SkipException("Runmode for test set data set to NO"+count);
		}
		
		//test method would be called 4 times
		APP_LOGS.debug("Executing Checklogin");
		APP_LOGS.debug(col1+ "--"+col2 );
		
		
		//Selenium Code
		String expected_title = "XYZ";
		String actual_title = "XYZ";
		System.out.println("before");
		try{
		Assert.assertEquals(expected_title, actual_title);
		}
		catch(Throwable t)
		{
			//code to report error in testNG
			ErrorUtil.addVerificationFailure(t);
			fail=true;
			return;
			//report error in xlsx file
		}
		System.out.println("After*****");
	
		
	}
	
	
	@AfterMethod //each det set is exceuted
	public void reporterDataSetResult()
	{
		if(skip)
		TestUtil.reportDataSetResults(LoginSuiteXls, this.getClass().getSimpleName(), count+2, "SKIP");
		else if(fail) {
			isTestPass=false;
			TestUtil.reportDataSetResults(LoginSuiteXls, this.getClass().getSimpleName(), count+2, "FAIL");
			
		}
			else
			TestUtil.reportDataSetResults(LoginSuiteXls, this.getClass().getSimpleName(), count+2, "PASS");
		skip=false;
		fail=false;
	}
	
	
	@AfterTest
	public void reportTestResult()
	{
		if (isTestPass)
			TestUtil.reportDataSetResults(LoginSuiteXls, "Test Cases", TestUtil.getRowNum(LoginSuiteXls, this.getClass().getSimpleName()), "PASS");
		else
			TestUtil.reportDataSetResults(LoginSuiteXls, "Test Cases", TestUtil.getRowNum(LoginSuiteXls, this.getClass().getSimpleName()), "FAIL");
		
	}
	
	@DataProvider
	public Object[][] getTestData()
	{
		return TestUtil.getData(LoginSuiteXls, this.getClass().getSimpleName());
	}

}
