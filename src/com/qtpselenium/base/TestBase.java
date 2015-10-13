package com.qtpselenium.base;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.*;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.ini4j.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.internal.Locatable;
import org.testng.Assert;

import java.util.Hashtable;

import sun.util.locale.ParseStatus;

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
public static boolean browser_alive=false;
public static WebDriver driver; 
public static 	List<WebElement> elements;
public static int checkbox=0;
public static int checkbox_checked_overall=0;
public static int camera=0;
public static int Flag=0;
public static int value=0;
public static String text;
public static Hashtable<String, String> sessionData = new java.util.Hashtable<String, String>();
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
	if(!isBrowserOpened)
	{
		browser_alive=true;
		System.out.println("browser open");
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
		
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"//driver//chromedriver.exe");
			driver = new ChromeDriver();
			
			
			}
		isBrowserOpened=true;
		String wait_Time= config.getProperty("default_implicit");
		driver.manage().timeouts().implicitlyWait(Long.valueOf(wait_Time), TimeUnit.SECONDS);
		browser_alive=false;
		}
	else if (browser_alive=false)
	{
		System.out.println("browser crashed");
	}
	
	}
	
	public void closeBrowser()
	{
	
		driver.close();
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
	
	public WebElement getObject(String xpathKey){
		
		try{
		WebElement x = driver.findElement(By.xpath(OR.getProperty(xpathKey)));
		return x;
		}catch(Throwable t){
			// report error
			ErrorUtil.addVerificationFailure(t);			
			APP_LOGS.debug("Cannot find object with key -- " +xpathKey);
			return null;
		}
		
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
	public static boolean login(String username, String password) throws InterruptedException
	{
		//selenium code for the specific user to login
		try
		{
		driver.get(config.getProperty("testSiteName"));
		driver.manage().window().maximize();
		WebElement user =driver.findElement(By.id(OR.getProperty("doctorID")));
		WebElement pass =driver.findElement(By.id(OR.getProperty("password")));
		
		if(user.isDisplayed())
		{
			user.sendKeys(username); 
		}
		else
		{
			Assert.fail("unable to find element");
		}
		pass.sendKeys(password);
		Thread.sleep(2000);
		driver.findElement(By.xpath(OR.getProperty("Submitbutton"))).click();
		
		}
		catch(Throwable t)
		{
			ErrorUtil.addVerificationFailure(t);
			APP_LOGS.debug("username element not found");
			
		}
		return true;
	}
	
	public void customer_registration_button()
	{
		Actions action = new Actions(driver);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		try
		{
			WebElement links = getObject("mouse_hover");
			Assert.assertEquals(getObject("mouse_hover"), links);
			action.moveToElement(links);
			action.perform();
			driver.findElement(By.xpath(OR.getProperty("NewCustomer"))).click();
			APP_LOGS.debug("Executing NewCustomerRegistration");

		}
		catch(Throwable t)
		{
			ErrorUtil.addVerificationFailure(t);
			System.out.println(t);
			Assert.fail();
			return;
		}
		
		
	}
	
	
	public static void click(String xpath)
	{
		driver.findElement(By.xpath(xpath)).click();
	}
	
	
	public static void navigate_to_mandate_field_settings() throws InterruptedException, IOException
	{
		Thread.sleep(5000);
		driver.findElement(By.xpath(OR.getProperty("left_hand_icon"))).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath(OR.getProperty("setting_button"))).click();
		APP_LOGS.debug("In settings congif screen");
		Thread.sleep(3000);
		driver.findElement(By.xpath(OR.getProperty("customer_config"))).click();;
		driver.findElement(By.xpath(OR.getProperty("Register_mandat"))).click();
		APP_LOGS.debug("In customer congif screen");
		Thread.sleep(2000);
		
	}
	
	//get table details from the setting page
	public void get_settings_page_table_details()
	{
		
		WebElement htmltable = driver.findElement(By.xpath(OR.getProperty("get_table_body")));
	//	System.out.println(htmltable.findElement(By.tagName("tr")).getText());
		
		List<WebElement> rows = htmltable.findElements(By.tagName(OR.getProperty("get_rows")));
		//System.out.println(rows.size());
	
		for(int i=1;i<=rows.size();i++)
		{
			//System.out.println(i);
		//	System.out.println(driver.findElement(By.xpath("html/body/div[1]/div[3]/section/div[3]/div/section/div[1]/div[2]/form/div/div/div/div[1]/div/div/div[1]/div[2]/table/tbody/tr["+i+"]")).getText());
			if(i==18)
				setting_page_scroll();
		}


	}
	
	public  void get_checkbox_checked() throws InterruptedException
	{
		
		WebElement htmltable = driver.findElement(By.xpath(OR.getProperty("get_table_body")));
		List<WebElement> rows = htmltable.findElements(By.className("text-center"));
		List<WebElement> rows_first_column = htmltable.findElements(By.tagName(OR.getProperty("get_rows")));
		//System.out.println(rows.size()+":"+" "+"rows");
		//System.out.println(rows_first_column.size()+":"+" "+"rows_first_column_size");
		setting_page_scroll_up();
		for(int i=1;i<rows.size()+1;i++)
		{
			List<WebElement> checkbox = driver.findElements(By.id("checkbox"+i));
		
			
			for(int j=0;j<checkbox.size();j++ )
			{
				if(driver.findElement(By.id("checkbox"+i)).isSelected())
					{
						if(i==48)
						{	
							setting_page_scroll();
						}
					
						
					WebElement checkbox_select = driver.findElement(By.id("checkbox"+i));
					elements = new ArrayList<WebElement>();
					elements.add(j, checkbox_select);
					setting_page_scroll();
		//			System.out.println(elements);
					checkbox_checked_overall = checkbox_checked_overall + 1;
					  //this returns the id of checkbox which are checked 
			
					}
			}
	
		}
		System.out.println(checkbox_checked_overall+":"+"overall checkbox checked in the tabel");
	}
	public void pre_registration_column_checkbox_checked()
	{
		WebElement htmltable = driver.findElement(By.xpath(OR.getProperty("get_table_body")));
		
		List<WebElement>  rows_first_column = htmltable.findElements(By.tagName(OR.getProperty("get_rows")));
		System.out.println(rows_first_column.size()+":"+" "+"rows_first_column_size");
		for(int i=1;i<rows_first_column.size();i++)
		{
			
			WebElement selected = driver.findElement(By.xpath("html/body/div[1]/div[3]/section/div[3]/div/section/div[1]/div[2]/form/div/div/div/div[1]/div/div/div[1]/div[2]/table/tbody/tr["+i+"]/td[2]"));
		//	System.out.println(selected.findElement(By.tagName("input")).isSelected());
			if(selected.findElement(By.tagName("input")).isSelected())
			{
				checkbox = checkbox+1;
			}
			
		}
		System.out.println(checkbox+":"+"check box check in pre-registration column");
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
	
	
	
	public void capturescreenshot(String filename) throws IOException
	{
		
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
	    FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir")+"\\screenshots\\"+filename+".jpg"));
	}
	
	public static void random()
	{
		Random rand = new Random();
		value = rand.nextInt(10);
		
		
	}
	
	
	public boolean logout() throws InterruptedException
	{
		Thread.sleep(3000);
		try
		{
		WebElement left_nav = driver.findElement(By.className("navgator"));
		left_nav.click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(OR.getProperty("logout"))).click();
		}
		catch(Throwable t)
		{
		ErrorUtil.addVerificationFailure(t);	
		System.out.println(t);
		Assert.fail("Failed");
			
		}
		return true;
	}
	
	public void visibile_object(String key)
	{
		try{
			WebElement x = driver.findElement(By.xpath(OR.getProperty(key)));
			if(x.isDisplayed()==false);
			
			}catch(Throwable t){
				// report error
				ErrorUtil.addVerificationFailure(t);			
				APP_LOGS.debug("Cannot find object with key -- " +key);
				
			}
	}
}
