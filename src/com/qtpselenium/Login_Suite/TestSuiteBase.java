package com.qtpselenium.Login_Suite;

import java.io.IOException;

import org.testng.SkipException;
import org.testng.annotations.BeforeSuite;

import com.qtpselenium.base.TestBase;
import com.qtpselenium.util.TestUtil;

public class TestSuiteBase extends TestBase {
	
	//check if the suite execution has to be skipped or not
	@BeforeSuite
	public void checkSuiteSkip() throws IOException
	{
		initialize();
		APP_LOGS.debug("Checking Run mode of Login Suite");
		if(!TestUtil.isSuiteRunnable(suiteXls, "Login Suite"))
		{
			APP_LOGS.debug("Skipped because run mode A suite was set to NO");
			throw new SkipException("Runmode of Suite A set to no. So Skipping al test in Suite A");
		
		}

}

}