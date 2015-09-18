package com.qtpselenium.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.Assert;

import com.qtpselenium.util.ErrorUtil;
import com.qtpselenium.util.Xls_Reader;

public class TestBase 
{
//declare objects at global level
public static Logger APP_LOGS=null;	
public static Properties config=null;
public static Properties OR=null;
public static Xls_Reader suiteXls=null;
public static Xls_Reader LoginSuiteXls=null;
public static Xls_Reader suiteBXls=null;
public static Xls_Reader suiteCXls=null;
public static boolean isInitalized=false;
public static 	WebDriver driver; 
	//initializing the tests like logs, conifg, excel file..
	public void initialize() throws IOException
	{
	//initialize logs
		if(!isInitalized)
	{		
	APP_LOGS = Logger.getLogger("devpinoyLogger");
	//config file 
	APP_LOGS.debug("Loading property files");
	config = new Properties();
	FileInputStream ip = new FileInputStream(System.getProperty("user.dir")+"//src//com//qtpselenium//config//config.properties");
	config.load(ip);

	OR = new Properties();
	ip = new FileInputStream(System.getProperty("user.dir")+"//src//com//qtpselenium//config//OR.properties");
	OR.load(ip);
	APP_LOGS.debug("Loader property file succesfully");

	APP_LOGS.debug("Loading Xls files");
	//xls file
	LoginSuiteXls = new Xls_Reader(System.getProperty("user.dir")+"//src//com//qtpselenium//xls//Login Suite.xlsx");
	suiteBXls = new Xls_Reader(System.getProperty("user.dir")+"//src//com//qtpselenium//xls//B suite.xlsx");
	suiteCXls = new Xls_Reader(System.getProperty("user.dir")+"//src//com//qtpselenium//xls//C suite.xlsx");
	suiteXls = new Xls_Reader(System.getProperty("user.dir")+"//src//com//qtpselenium//xls//TestSuite.xlsx");
	
	APP_LOGS.debug("Loaded Xls files");
	isInitalized=true;
		
	}
}
	
	//selenium code
	public void openBrowser()
	{
		
			if(config.getProperty("browserType").equals("Mozilla"))
			{
			driver = new FirefoxDriver();
			}
		else if(config.getProperty("browserType").equals("IE"))
			{
			driver = new InternetExplorerDriver();
			}
		else if(config.getProperty("browserType").equals("Chrome"))
			{
			driver = new ChromeDriver();
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"//driver//chromedriver.exe");
			
			}

		}
	
	public void closeBrowser()
	{
	
		driver.quit();
	}
	
	public boolean compare_URL(String expected)
	{
		try
		{
		Assert.assertEquals(driver.getCurrentUrl(),expected);
		}
		catch(Throwable T)
		{
			ErrorUtil.addVerificationFailure(T);
			APP_LOGS.debug("Titles do not match");
			//fail=true;  //for reporting error we need to use the try and catch block with verification failure to report error in excel file
			//return;  if you don't want to continue test ahead App_LOS.debug("Titles do not match")
			return false;
		}
		return true;
	}
	
	
	public boolean compare_left_nav_elements(int expected, int actualvalue)
	{
		try
		{
		Assert.assertEquals(actualvalue, expected);
		}
		catch(Throwable T)
		{
			ErrorUtil.addVerificationFailure(T);
			APP_LOGS.debug("Values do not match");
			//fail=true;  //for reporting error we need to use the try and catch block with verification failure to report error in excel file
			//return;  if you don't want to continue test ahead App_LOS.debug("Titles do not match")
			return false;
		}
		return true;
	}
	
	//to check if any element present or not
	public boolean check_element_present(String xpath)
	{
		int count = driver.findElements(By.xpath(config.getProperty(xpath))).size();
		try
		{
		Assert.assertTrue(count>0,"No Element Present");
		}
		catch(Throwable T)
		{
			ErrorUtil.addVerificationFailure(T);
			APP_LOGS.debug("No Element Present");
			//fail=true;  //for reporting error we need to use the try and catch block with verification failure to report error in excel file
			//return;  if you don't want to continue test ahead App_LOS.debug("Titles do not match")
			return false;
		}
		return true;
	}
	
	
}
