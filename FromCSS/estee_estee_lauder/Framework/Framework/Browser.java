package Framework;

import Constants.Constants;
import Execution.StorageArea;
import ResultLogs.ResultLogger;
import ResultLogs.ResultLogger.ISSTEPACTION;
import ResultLogs.ResultLogger.RESULT;
import Startup.EntryPoint;

import org.junit.Test;
import org.openqa.selenium.ie.*;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.UnreachableBrowserException;





import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.apache.http.conn.HttpHostConnectException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.firefox.*;

public class Browser {
	public static void launchBrowser()
	{
		try
		{
			if(Constants.Browser_Name.toUpperCase().equals("CHROME"))
			{
				DesiredCapabilities caps=DesiredCapabilities.chrome();
				
				ChromeOptions options = new ChromeOptions();
				
				options.addArguments("test-type");
				
				caps.setCapability(ChromeOptions.CAPABILITY, options);
				caps.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
				//System.setProperty("webdriver.chrome.driver","./lib/ChromeDriver/chromedriver.exe");	
			
				System.setProperty("webdriver.chrome.driver", "D:\\webdriver\\chromedriver.exe");
				
				 //Constants.remoteWebDriver = new RemoteWebDriver(new URL(machine_ip),caps);
				//Constants.driver = Constants.remoteWebDriver;
				Constants.driver =new ChromeDriver(caps);
				
			}
			else if(Constants.Browser_Name.toUpperCase().equals("FIREFOX"))
			{
				FirefoxProfile profile = new FirefoxProfile();

				DesiredCapabilities dc=DesiredCapabilities.firefox();

				profile.setAcceptUntrustedCertificates(false);

				profile.setAssumeUntrustedCertificateIssuer(true);

				profile.setPreference("browser.download.folderList", 2);

				profile.setPreference("browser.helperApps.alwaysAsk.force", false);

				profile.setPreference("browser.download.manager.showWhenStarting",false); 

				profile.setPreference("browser.download.dir", "C:\\Downloads"); 

				profile.setPreference("browser.download.downloadDir","C:\\Downloads"); 

				profile.setPreference("browser.download.defaultFolder","C:\\Downloads");

				profile.setPreference("browser.helperApps.neverAsk.saveToDisk","text/anytext ,text/plain,text/html,application/plain" );

				dc = DesiredCapabilities.firefox();

				dc.setCapability(FirefoxDriver.PROFILE, profile);

				//Constants.driver =  new FirefoxDriver(dc);

			}
			else if(Constants.Browser_Name.toUpperCase().equals("IE"))
			{
				try
				{   				      
				       Process p = Runtime.getRuntime().exec("RunDll32.exe InetCpl.cpl,ClearMyTracksByProcess 255");
				       System.out.println("Waiting for batch file ...");
				       p.waitFor();
				       System.out.println("Batch file done.");
				} 
				catch (IOException e)
				{
				    e.printStackTrace();
				}
				System.setProperty("webdriver.ie.driver","D:\\webdriver\\IEDriverServer.exe");

				DesiredCapabilities caps = DesiredCapabilities.internetExplorer();

				caps.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR,true);

				caps.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);

				Constants.driver = new InternetExplorerDriver(caps);

			}
			else
			{
				////ResultLogger.log("Invalid Browser Found",ISSTEPACTION.False,RESULT.FAIL);
			}

			////ResultLogger.log(Constants.Browser_Name +"Browser Launched Successfully", ISSTEPACTION.False, RESULT.PASS);

			Constants.driver.manage().window().maximize();
			Constants.driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
			Constants.driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
			Constants.driver.manage().timeouts().setScriptTimeout(60, TimeUnit.SECONDS);
		}
		catch (Exception e) 
		{
			ResultLogger.log(e.getMessage(),ISSTEPACTION.False,RESULT.EXCEPTION);
		}

	}
	public static void verifytitle()
	{
		try
		{
			String title = Constants.driver.getTitle();

			Constants.sActualValue = StorageArea.getHashTable(Constants.sValue);

			if(title.equals(Constants.sActualValue))
			{
				////ResultLogger.log("Titles are  Matched.", ISSTEPACTION.True, RESULT.PASS);
			}
			else
			{
				////ResultLogger.log("Titles are mismatched.", ISSTEPACTION.True, RESULT.WARNING);
			}

		}
		catch(Exception ex)
		{
			////ResultLogger.log("Exception occured while verifying the Title of the page. Exception: "+ex.getStackTrace(), ISSTEPACTION.True, RESULT.EXCEPTION);
		}
	}

	public static void open()
	{
		try
		{
			Constants.driver.navigate().to(Constants.sValue);

			////ResultLogger.log("Navigated the "+Constants.sValue+" Successfully. ", ISSTEPACTION.True,RESULT.PASS);

			waitForPageLoad();

		}
		catch (Exception e) {

			////ResultLogger.log(e.getMessage(),ISSTEPACTION.True,RESULT.EXCEPTION);
		}

	}

	public static void waitForPageLoad()
	{
		try
		{
			String pageLoadState = ((JavascriptExecutor)Constants.driver).executeScript("if (document != undefined && document.readyState) { return document.readyState;} else { return undefined;}").toString();

			while(true)
			{
				if(pageLoadState.toUpperCase().equals("COMPLETE") || pageLoadState.toUpperCase().equals("LOADED"))
				{
					//ResultLogger.log("Page Load State: "+pageLoadState,ISSTEPACTION.False,RESULT.PASS);

					break;
				}
			}
		}
		catch(Exception ex)
		{

		}
	}
	public static void DeleteVisibleCookies()
	{
		try
		{
			Constants.driver.manage().deleteAllCookies();

			////ResultLogger.log("Deleted cookies Successfully.",ISSTEPACTION.True,RESULT.PASS);
		}
		catch(Exception ex)
		{
			////ResultLogger.log("Exception occured while deleting the cookies.",ISSTEPACTION.True,RESULT.EXCEPTION);
		}
	}


}
