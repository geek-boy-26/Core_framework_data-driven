package test.qtpselenium.com;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;



public class testConfig {

	
	public static void main (String[] args) throws IOException
	{
	
		Properties config = new Properties();
		FileInputStream ip = new FileInputStream(System.getProperty("user.dir")+"//src//com//qtpselenium//config//config.properties");
		config.load(ip);
		System.out.println(config.get("browserType"));
		//better is getproperty
		System.out.println(config.getProperty("OS"));
		
		
		Properties OR = new Properties();
		ip = new FileInputStream(System.getProperty("user.dir")+"//src//com//qtpselenium//config//OR.properties");
		OR.load(ip);
		System.out.println(OR.getProperty("username"));
	}
}
