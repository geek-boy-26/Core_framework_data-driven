package com.qtpselenium.Login_Suite;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
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
		
		openBrowser();// so that this function would call the browser as passed in the config properties
		driver.get(config.getProperty("testSiteName"));
		driver.manage().window().maximize();
		driver.findElement(By.id(OR.getProperty("doctorID"))).sendKeys(col1); 
		driver.findElement(By.id(OR.getProperty("password"))).sendKeys(col2);
		driver.findElement(By.xpath(OR.getProperty("Submitbutton"))).click();
		
		//check the landing page is office visit or not
		if(!compare_URL("https://specsav.ecwlab.com/mobiledoc/jsp/visionemr/jellybean/officevisit/officeVisits.jsp"))
		{
			fail=true;
			//return; if you want to stop exceution here
		}
		
		
		driver.findElements(By.xpath(OR.getProperty("left_hand_icon"))).size();
		WebElement left_nav = driver.findElement(By.id(OR.getProperty("Navigation")));
		int num_of_list = left_nav.findElements(By.tagName(OR.getProperty("tag_name"))).size();
		
		if(!compare_left_nav_elements(8, num_of_list))
		{
			fail=true;
			//return; if you want to stop exceution here
		}
		
		System.out.println("Total menu items are " + num_of_list);
		closeBrowser();
	
		
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
