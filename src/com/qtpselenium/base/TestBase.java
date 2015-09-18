package com.qtpselenium.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

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
}