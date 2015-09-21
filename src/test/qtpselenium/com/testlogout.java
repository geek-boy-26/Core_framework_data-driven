package test.qtpselenium.com;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class testlogout {

	public static void main(String[] args)

	{
	
	
	WebDriver driver = new FirefoxDriver();
	driver.get("https://specsav.ecwlab.com/mobiledoc/jsp/visionemr/visionlogin.jsp");
	driver.manage().window().maximize();
	driver.findElement(By.id("doctorID")).sendKeys("sam"); 
	driver.findElement(By.id("password")).sendKeys("password$1");
	driver.findElement(By.xpath("html/body/div[1]/div[1]/form/button")).click();
	
	WebElement left_nav = driver.findElement(By.id("leftnav"));
	left_nav.click();
	driver.findElement(By.xpath("html/body/div[1]/div[3]/div[1]/nav/ul[2]/li[2]/a")).click();
	
	}
}
