package com.qtpselenium.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.ini4j.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.internal.Locatable;
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
public static boolean isBrowserOpened = false; //if exceute test in 1 browser
public static WebDriver driver; 
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
		String wait_Time= config.getProperty("default_implicit");
		driver.manage().timeouts().implicitlyWait(Long.valueOf(wait_Time), TimeUnit.SECONDS);
		
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
			APP_LOGS.debug("URL do not match");
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
	
	//just to do one time login into application this function can be used
	public boolean login(String username, String password)
	{
		//selenium code for the specific user to login
		driver.get(config.getProperty("testSiteName"));
		driver.manage().window().maximize();
		driver.findElement(By.id(OR.getProperty("doctorID"))).sendKeys(username); 
		driver.findElement(By.id(OR.getProperty("password"))).sendKeys(password);
		driver.findElement(By.xpath(OR.getProperty("Submitbutton"))).click();
		
		return true;
	}
	
	public void customer_registration_button()
	{
		Actions action = new Actions(driver);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		WebElement links = driver.findElement(By.xpath(OR.getProperty("mouse_hover")));
		action.moveToElement(links);
		action.perform();
		driver.findElement(By.xpath(OR.getProperty("NewCustomer"))).click();
		APP_LOGS.debug("Executing NewCustomerRegistration");
		
		
	}
	
	
	
	public void navigate_to_mandate_field_settings()
	{
		driver.findElement(By.xpath(OR.getProperty("left_hand_icon"))).click();
		driver.findElement(By.xpath(OR.getProperty("setting_button"))).click();
		APP_LOGS.debug("In settings congif screen");
		driver.findElement(By.xpath(OR.getProperty("customer_config"))).click();;
		driver.findElement(By.xpath(OR.getProperty("Register_mandat"))).click();
		APP_LOGS.debug("In customer congif screen");
	}
	
	//get table details from the setting page
	public void get_settings_page_table_details()
	{
		
		WebElement htmltable = driver.findElement(By.xpath(OR.getProperty("get_table_body")));
	//	System.out.println(htmltable.findElement(By.tagName("tr")).getText());
		
		List<WebElement> rows = htmltable.findElements(By.tagName(OR.getProperty("get_rows")));
		System.out.println(rows.size());
	
		for(int i=1;i<=rows.size();i++)
		{
			System.out.println(i);
			System.out.println(driver.findElement(By.xpath("html/body/div[1]/div[3]/section/div[3]/div/section/div[1]/div[2]/form/div/div/div/div[1]/div/div/div[1]/div[2]/table/tbody/tr["+i+"]")).getText());
			if(i==18)
				setting_page_scroll();
		}


	}
	
	public void setting_page_scroll()
	{
		WebElement element = driver.findElement(By.id("checkbox72"));
		Coordinates cor = ((Locatable)element).getCoordinates(); 
		cor.onPage();
		cor.inViewPort();
		
	}
	
	public void setting_page_scroll_up()
	{
		WebElement element = driver.findElement(By.id("checkbox1"));
		Coordinates cor = ((Locatable)element).getCoordinates(); 
		cor.onPage();
		cor.inViewPort();
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public boolean logout()
	{
		WebElement left_nav = driver.findElement(By.id(OR.getProperty("Navigation")));
		left_nav.click();
		driver.findElement(By.xpath(OR.getProperty("logout"))).click();
		return true;
	}
}
