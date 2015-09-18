package test.qtpselenium.com;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class testlogin {
	
	public static void main(String[] args)

	{
		WebDriver driver = new FirefoxDriver();
		driver.get("https://specsav.ecwlab.com/mobiledoc/jsp/visionemr/visionlogin.jsp");
		driver.manage().window().maximize();
		driver.findElement(By.id("doctorID")).sendKeys("sam"); 
		driver.findElement(By.id("password")).sendKeys("password$1");
		driver.findElement(By.xpath("html/body/div[1]/div[1]/form/button")).click();
	
		driver.findElements(By.xpath("html/body/div[1]/div[1]/div[1]/a/i")).size();
		WebElement left_nav = driver.findElement(By.id("leftnav"));
		int num_of_list = left_nav.findElements(By.tagName("i")).size();
		System.out.println("Total menu items are " + num_of_list);
	}
	

}
