package com.qtpselenium.Login_Suite;

import java.awt.AWTException;
import java.awt.Robot;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


















import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.ClickAction;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.internal.Locatable;

import com.opera.core.systems.internal.input.KeyEvent;
import com.qtpselenium.util.TestUtil;

public class NewCustomerRegistration extends TestSuiteBase{

	static boolean skip = false;
	static boolean pass = false;	
	static boolean fail = false;
	static boolean isTestPass = true;
	
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
			) throws InterruptedException, AWTException
	{
		//test run mode of current data set
				count++;
				if(!runmodes[count].equalsIgnoreCase("Y"))
				{
					skip=true;
					throw new SkipException("Runmode for test set data set to NO"+count);
				}
				
				openBrowser();
				login("sam", "password$1"); //keywords
				
				navigate_to_mandate_field_settings();//check mandatory fields in setting 
			//	customer_registration_button(); //navigate to window of customer registration // not working need to check later 
				get_settings_page_table_details(); // this would get the elements form the table  // 
				
		//*********************************************************get Checkbox******		
				WebElement htmltable = driver.findElement(By.xpath(OR.getProperty("get_table_body")));
				List<WebElement> rows = htmltable.findElements(By.className("text-center"));
				System.out.println(rows.size()+"Rows"); //overall columns of the rest of the table //we want only column where check box exists
				setting_page_scroll_up();
				for(int i=1;i<rows.size()+1;i++)
				{
					List<WebElement> checkbox = driver.findElements(By.id("checkbox"+i));
					System.out.println(checkbox.size()+"checkbox size"+":" +"I"+i);
					for(int j=0;j<checkbox.size();j++ )
					{
						System.out.println(driver.findElement(By.id("checkbox"+i)));
						if(driver.findElement(By.id("checkbox"+i)).isSelected())
						{
							if(i==48)
								setting_page_scroll();
							System.out.println("true"+i);
							System.out.println(driver.findElement(By.xpath("html/body/div/div[3]/section/div[3]/div/section/div[1]/div[2]/form/div/div/div/div[1]/div/div/div[1]/div[2]/table/tbody/tr["+i+"]/td[4]")).getText()+"TR");
							
						}
						/*WebElement temp = driver.findElement(By.id("checkbox"+i));
						ArrayList<String> Element = new ArrayList<String>();
						Element.addAll(driver.findElement(By.id("checkbox"+i))*/
					}
			//	System.out.println(driver.findElements(By.id("checkbox"+i)));
				/*WebElement temp = driver.findElement(By.tagName("label"));
				if(temp != null)
				{
			
					System.out.println("True");
				}
				else
				{
					System.out.println("false");
				}
			*/		
				//System.out.println(driver.findElement(By.tagName("label")));
					//System.out.println(driver.findElement(By.id("checkbox"+i)));
				
/**/					
				/*	else if(driver.findElement(By.id("checkbox"+i)).isEnabled())
					{
						System.out.println("Not displayed");
					}*/
					
			/*		if(driver.findElement(By.id("checkbox"+i)).isDisplayed())
					{
						if(driver.findElement(By.id("checkbox"+i)).isSelected())
						{
							System.out.println("Checked");
							//System.out.println(driver.findElement(By.tagName("label")));
						}
						if(!driver.findElement(By.id("checkbox"+i)).isSelected())
						{
							System.out.println("UnChecked");
							//System.out.println(driver.findElement(By.tagName("label")));
						}
					}*/
					
				}
				
				/*for(int i=1;i<rows.size();i++)
				{
							System.out.println(driver.findElements(By.id("checkbox"+i)));
							
						List<WebElement> checkboxes = driver.findElements(By.id("checkbox"+i));
						System.out.println(checkboxes.size());
							if(i==18)
							{
								System.out.println("in lop");
								setting_page_scroll();
							}
							
							for(int j=0; j<checkboxes.size();j++)
							{
								
								if(checkboxes.get(j).isSelected())
								{
								int k=1;
								System.out.println(driver.findElement(By.xpath("html/body/div[1]/div[3]/section/div[3]/div/section/div[1]/div[2]/form/div/div/div/div[1]/div/div/div[1]/div[2]/table/tbody/tr["+k+"]/td[1]")).getText()+"checkboxes which are selected");
								k++;
							
								System.out.println( "checked"+j);
								}
								else if(!checkboxes.get(j).isSelected())
								{
									System.out.println( "unchecked"+j);
									//System.out.println(driver.findElement(By.tagName("tr"+j)).getText()+"checkboxes which are NOT selected");
									//checkboxes.get(j).click();
								}
								
							}
							
			
			}*/
		

				
	
		
	
		//********************************************************		
				
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
