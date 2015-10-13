package test.qtpselenium.com;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class testlogout {

	public static void main(String[] args)

	{
	
	
	/*WebDriver driver = new FirefoxDriver();
	driver.get("https://specsav.ecwlab.com/mobiledoc/jsp/visionemr/visionlogin.jsp");
	driver.manage().window().maximize();
	driver.findElement(By.id("doctorID")).sendKeys("sam"); 
	driver.findElement(By.id("password")).sendKeys("password$1");
	driver.findElement(By.xpath("html/body/div[1]/div[1]/form/button")).click();
	
	WebElement left_nav = driver.findElement(By.id("leftnav"));
	left_nav.click();
	driver.findElement(By.xpath("html/body/div[1]/div[3]/div[1]/nav/ul[2]/li[2]/a")).click();*/
	
/*	DateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	Date date = new Date();
	String str = (format.format(date)).toString();
	System.out.println(str);
	String temp = str.replace("/", ".");
	String x = temp.replace(" ", ".");
	System.out.println(x);*/
		for(int i=1;i<100;i++)
		{
		Random rand = new Random();
		int value = rand.nextInt(i);
		System.out.println(value);
String str = RandomStringUtils.randomAlphabetic(value);
System.out.println("Random 1 =" + str);
		}

	
	}
}
