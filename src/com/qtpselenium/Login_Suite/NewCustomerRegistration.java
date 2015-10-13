package com.qtpselenium.Login_Suite;

import java.awt.AWTException;
import java.awt.Label;
import java.awt.Robot;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import net.sourceforge.htmlunit.corejs.javascript.ast.NewExpression;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpConnection;
import org.bouncycastle.asn1.ocsp.Request;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.ClickAction;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.remote.HttpSessionId;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.opera.core.systems.internal.ImplicitWait;
import com.opera.core.systems.internal.input.KeyEvent;
import com.qtpselenium.util.ErrorUtil;
import com.qtpselenium.util.TestUtil;
import com.qtpselenium.util.Xls_Reader;

public class NewCustomerRegistration extends TestSuiteBase{

	static boolean skip = false;
	static boolean pass = false;	
	static boolean fail = false;
	static boolean isTestPass = true;
	static boolean is_customer_registration_open;
	static String Customer_Surname;
	static Xls_Reader xls;
public int sum=0;
public Set<Cookie> cookie=null;
	String runmodes[]=null;  //this is to check the run mode of data in test case with the values Y or N
	static int count=-1;
	
	//Check run mode in suite
		@BeforeTest
		public void checkTestSkip()
		{
			if(!TestUtil.isTestCaseRunnable(LoginSuiteXls, this.getClass().getSimpleName()))
			{
				APP_LOGS.debug("Skipping testcase as test"+this.getClass().getSimpleName()+" case runmode set to no in test case"); //logs
				throw new SkipException("Test Case skipped as runmode set to no");
			}
			
			//load runmodes off the test
			runmodes= TestUtil.getDataSetRunmodes(LoginSuiteXls, this.getClass().getSimpleName());
		}

	@Test(dataProvider="getTestData")
	public void testCaseA2(
			String title,
			String Surname,
			String Firstname,
			String dateofbirth,
			String Gender,
			String prefereedphone
			) throws InterruptedException, AWTException, IOException
	{
		//test run mode of current data set
				count++;
				if(!runmodes[count].equalsIgnoreCase("Y"))
				{
					skip=true;
					throw new SkipException("Runmode for test set data set to NO"+count);
				}
				
				openBrowser();
				
				if(cookie==null)
				login("sam", "password$1");
				cookie = driver.manage().getCookies();
				System.out.println(cookie);
				
				
				
	//	compare_mandate_fields(); //This would get the matching fields of overall registration form
				try
				{
			
				Thread.sleep(5000);
				driver.get("http://10.100.20.45:9090/mobiledoc/jsp/visionemr/jellybean/officevisit/officeVisits.jsp");
				customer_registration_button();
				Thread.sleep(3000);
				
				Thread.sleep(5000);
				combine_alert_submit();
				if(title.equalsIgnoreCase("Mr."))
				{
					driver.findElement(By.xpath(OR.getProperty("Mr."))).click();
				}
				else	if(title.equalsIgnoreCase("Mrs."))
				{
					driver.findElement(By.xpath(OR.getProperty("Mrs."))).click();
				}
				else if(title.equalsIgnoreCase("Miss."))
				{
					driver.findElement(By.xpath(OR.getProperty("Miss."))).click();
				}
				else if(title.equalsIgnoreCase("Sir"))
				{
					driver.findElement(By.xpath(OR.getProperty("Sir"))).click();
				}
				else if(title.equalsIgnoreCase("Dr."))
				{
					driver.findElement(By.xpath(OR.getProperty("Dr."))).click();
				}
				else if(title.equalsIgnoreCase("Ms."))
				{
					driver.findElement(By.xpath(OR.getProperty("Ms."))).click();
				}
				APP_LOGS.debug("Title is Selected");
				combine_alert_submit();
			
				
				//********To enter field values ******///
				
				driver.findElement(By.xpath(OR.getProperty("Surname"))).sendKeys(Surname);
				APP_LOGS.debug("Surname is added");
				combine_alert_submit();
				Thread.sleep(2000);
				driver.findElement(By.xpath(OR.getProperty("Firstname"))).sendKeys(Firstname);
				APP_LOGS.debug("FirstName is added");
				combine_alert_submit();
				//********enter an invalid date*******/////
				/*JavascriptExecutor test =(JavascriptExecutor)driver;
				test.executeScript("alert('Enter an invalid date');");
				Thread.sleep(2000);
				driver.switchTo().alert().accept();
				driver.findElement(By.xpath(OR.getProperty("dob"))).click();
				driver.findElement(By.xpath("//*[@id='ui-datepicker-div']/div[2]/button[1]")).click();
				combine_alert_submit();	*/	
				//****END***////
				driver.findElement(By.xpath(OR.getProperty("dob"))).click();
				driver.findElement(By.xpath(OR.getProperty("dob"))).clear();
				driver.findElement(By.xpath(OR.getProperty("dob"))).sendKeys(dateofbirth);
				APP_LOGS.debug("DateOfBirth is added");
				combine_alert_submit();
						
				//********To enter field Gender******///
				if(Gender.equalsIgnoreCase("M"))
				{
					driver.findElement(By.xpath(OR.getProperty("Male"))).click();
				}
				if(Gender.equalsIgnoreCase("F"))
				{
					driver.findElement(By.xpath(OR.getProperty("Female"))).click();
				}
				APP_LOGS.debug("Gender is Selected");
				combine_alert_submit();
				//********To enter field values Title******///
				driver.findElement(By.xpath(".//*[@id='txtPhone']")).click();
				driver.findElement(By.xpath(".//*[@id='txtPhone']")).sendKeys(prefereedphone);
				APP_LOGS.debug("PhoneNumber is added");
				combine_alert_submit();
			
				if(Flag==1)
				{
					String str = RandomStringUtils.randomAlphabetic(8);
					driver.findElement(By.xpath(OR.getProperty("Surname"))).sendKeys(str);
					Customer_Surname=driver.findElement(By.xpath(OR.getProperty("Surname"))).getAttribute("value");
					System.out.println("Customer Surname is "+":"+Customer_Surname);
					//System.out.println(Flag+":"+"Value of Flag");
				}
				if(driver.findElement(By.xpath(OR.getProperty("Click_Form_ok"))).isDisplayed())
				 click(OR.getProperty("Click_Form_ok"));	
				//Form Submitted
				
				
				//check the customer in customer Search option
				//check customer in search option
				Thread.sleep(3000);
				 driver.findElement(By.xpath("//div[@id='jellyBeanDiv']/div/div/a/i")).click();
				 Thread.sleep(2000);
				 driver.findElement(By.id("custLookup")).sendKeys(Customer_Surname);
				 Thread.sleep(2500);
				 capturescreenshot(Customer_Surname);
			
				 
				}
				catch(Throwable t)
				{
					ErrorUtil.addVerificationFailure(t);
					System.out.println(t);
					//here
					xls.setCellData(this.getClass().getSimpleName(), "Error",count+2, t.toString());
					Assert.fail();
					fail=true;
					return;
				}
				
	}

	
	public void combine_alert_submit() throws InterruptedException, IOException
	{
		String comp = "Customer already exist";
		String date = "Date Of Birth can not be greater than or equal to today's date.";
		try
		{
		Assert.assertTrue(driver.findElement(By.xpath(OR.getProperty("Click_Form_ok"))).isDisplayed());
		if(driver.findElement(By.xpath(OR.getProperty("Click_Form_ok"))).isDisplayed())
		{
			//System.out.println(driver.findElement(By.xpath(OR.getProperty("Click_Form_ok"))).isDisplayed()+"::"+"yes displayed");
			//click ok
			  click(OR.getProperty("Click_Form_ok"));
					Thread.sleep(5000);
				//	capturescreenshot("Alert"+camera);
					//click X alert button
					if(driver.findElement(By.className("bootbox-body")).isDisplayed()==true)
					{
						text =driver.findElement(By.className("bootbox-body")).getText();	
						//System.out.println(driver.findElement(By.className("bootbox-body")).getText());
						
						if(text.startsWith(comp))
						{
							System.out.println("Customer Exits");
							APP_LOGS.debug("Customer Exits");
							Flag=1;
						}
						 if(text.equals(date))
						{
							System.out.println("Got Invalid Date");
							APP_LOGS.debug("Inavlid Date");
						}
						click(OR.getProperty("Alert_X_button"));
						Thread.sleep(5000);
						driver.switchTo().activeElement();
					}
										else if(driver.findElement(By.className("bootbox-body")).isDisplayed()==false)
					{
						System.out.println("Not Displayed");
					}
				
			
			
		}
		else
		{
			System.out.println("here");
		}
		}
		catch(Throwable t)
		{
			ErrorUtil.addVerificationFailure(t);
			APP_LOGS.debug("Element not found");
			fail=true;
			return;
		}
		  	
				
	}
	
	
	
	
	
	
public void count_mandate_fields() throws InterruptedException
{
	Thread.sleep(8000);
	WebElement ele = driver.findElement(By.id(OR.getProperty("Customer_Registration_form"))); // gives all the elements of the form 
	List<WebElement> temp = ele.findElements(By.tagName(OR.getProperty("Registration_form_tag")));

	Iterator<WebElement> i = temp.iterator();
	while(i.hasNext())
	{
		WebElement dum = i.next();
		//System.out.println(dum.getText());
		String str = dum.getText().toString();
//			System.out.println(str);	
		
		if(str.contains("*"))
		{
			
			//System.out.println("true");
			//System.out.println(dum.getText().toString());
			String str_man = dum.getText().toString();
			System.out.println(str_man);
			
			sum=sum+1;
			
		}
		
		
	}
		System.out.println(sum+":"+"in customer registration form");
}
//This function would compare the registration mandate fields along with the back end settings mandate fields
public void compare_mandate_fields() throws InterruptedException, IOException
{
	customer_registration_button();
	Thread.sleep(3000);
	//***********Get the mandate fields from the customer registration form********////
	count_mandate_fields();
//	capturescreenshot("Customer Registration Mandate Fields"+camera+2);
	//***********End	********////
	//close the form
	driver.findElement(By.xpath(OR.getProperty("X_Button"))).click();
	
	navigate_to_mandate_field_settings();//check mandatory fields in setting 
//	capturescreenshot("Customer Settings Mandate Fields"+camera+1);
	Thread.sleep(5000);
	pre_registration_column_checkbox_checked();
//	get_checkbox_checked();
	if(sum==checkbox)
	{
		System.out.println("Fields are matching");
		}
	
	
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
		{
			TestUtil.reportDataSetResults(LoginSuiteXls, "Test Cases", TestUtil.getRowNum(LoginSuiteXls, this.getClass().getSimpleName()), "PASS");
			
		}
		
			else
			{
			TestUtil.reportDataSetResults(LoginSuiteXls, "Test Cases", TestUtil.getRowNum(LoginSuiteXls, this.getClass().getSimpleName()), "FAIL");
	//		TestUtil.reportDataSetResults(LoginSuiteXls, "Test Cases", TestUtil.getRowNum(LoginSuiteXls, this.getClass().getSimpleName()), "PASS");
			}
		
	}
	
	
	@DataProvider
	public Object[][] getTestData()
	{
		return TestUtil.getData(LoginSuiteXls, this.getClass().getSimpleName());
	}
	


}
