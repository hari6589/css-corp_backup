package Framework;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Constants.Constants;
import Execution.ObjectHandler;
import Execution.StorageArea;
import ResultLogs.ResultLogger;
import ResultLogs.ResultLogger.ISSTEPACTION;
import ResultLogs.ResultLogger.RESULT;
import Startup.EntryPoint;

public class CommonActions {

	public static void click()
	{
		try
		{
		
			ObjectHandler.GetWebElement();

			Constants.webElement.click();

			ResultLogger.log("Clicked element", ISSTEPACTION.True, RESULT.PASS);
		}
		catch (Exception e) 
		{
			ResultLogger.log("User Message: Unable to click the element. System Exception message: "+e.getMessage(), ISSTEPACTION.True,RESULT.EXCEPTION);
		}
	}
	
	
	
	public static void verifyAddress() 
	{
		try 
		{
			//WebElement elm = getElement(locaname, locvalue);

			ObjectHandler.splitTarget();
			List<WebElement> myElements = Constants.driver.findElements(Constants.by);
			for(WebElement e : myElements) 
			{
				System.out.println(e.getText());
				if(!e.getText().contains(Constants.sValue))
				{
					
				}
				else
				{
					ResultLogger.log("elemnet found successfully.", ISSTEPACTION.True, RESULT.PASS);
					break;
				}

			}
		}
		catch (Exception e) 
		{
			ResultLogger.log("couldnt find element: "+e.getMessage(), ISSTEPACTION.True,RESULT.EXCEPTION);
		}

	}

	public static Dimension get_screen_size()
	{
		return(Constants.driver.manage().window().getSize());
	}
	public static void clickAndWait()
	{
		try
		{
			ObjectHandler.GetWebElement();

			((JavascriptExecutor)Constants.driver).executeScript("var evt = document.createEvent('MouseEvents');" + "evt.initMouseEvent('click',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null);" + "arguments[0].dispatchEvent(evt);", Constants.webElement);

			ResultLogger.log("Clicked element successfully.", ISSTEPACTION.True, RESULT.PASS);
			Thread.sleep(25000);
		}
		catch (Exception e) 
		{
			ResultLogger.log("User Message: Unable to click the element. System Exception message: "+e.getMessage(), ISSTEPACTION.True,RESULT.EXCEPTION);
		}
	}
	public static void visibility_Wait()
	{
		try
		{
			ObjectHandler.splitTarget();
			int seconds = Integer.parseInt(Constants.sValue);
			long millis = seconds * 1000;
			Constants.visibility_wait = new WebDriverWait(Constants.driver, millis);
			Constants.visibility_wait.until(ExpectedConditions.visibilityOfElementLocated(Constants.by));
			ResultLogger.log("visibility of elemnet found successfully.", ISSTEPACTION.True, RESULT.PASS);
		}
		catch (Exception e) 
		{
			ResultLogger.log("User Message: Unable to click the element. System Exception message: "+e.getMessage(), ISSTEPACTION.True,RESULT.EXCEPTION);
		}
	}
	public static void visibility_elem_click()
	{
		try
		{
			ObjectHandler.splitTarget();
			if(Constants.driver.findElement(Constants.by).isDisplayed())
			{
				Constants.driver.findElement(Constants.by).click();
			}
			ResultLogger.log("action completed successfully.", ISSTEPACTION.True, RESULT.PASS);
		}
		catch (Exception e) 
		{
			ResultLogger.log("action not done: "+e.getMessage(), ISSTEPACTION.True,RESULT.EXCEPTION);
		}
	}
	public static void invisibility_Wait()
	{
		try
		{
			ObjectHandler.splitTarget();
			int seconds = Integer.parseInt(Constants.sValue);
			long millis = seconds * 1000;
			Constants.visibility_wait = new WebDriverWait(Constants.driver, millis);
			Constants.visibility_wait.until(ExpectedConditions.invisibilityOfElementLocated(Constants.by));
			ResultLogger.log("inVisibility of elemnet found successfully.", ISSTEPACTION.True, RESULT.PASS);
		}
		catch (Exception e) 
		{
			ResultLogger.log("User Message: Unable to click the element. System Exception message: "+e.getMessage(), ISSTEPACTION.True,RESULT.EXCEPTION);
		}
	}
	public static void storeText()
	{
		try
		{
			ObjectHandler.GetWebElement();

			String tempTargetValue = "";

			if (Constants.webElement.getTagName().toUpperCase().equals("INPUT"))
			{
				tempTargetValue = Constants.webElement.getAttribute("value").trim();

			}
			else
			{
				tempTargetValue = Constants.webElement.getText().trim();
			}

			Constants.sValue = Constants.sValue.trim();

			StorageArea.insertHashTable(Constants.sValue, tempTargetValue);

			ResultLogger.log("Stored the text/value into storage location",ISSTEPACTION.True, RESULT.PASS);

		}
		catch(Exception ex)
		{
			ResultLogger.log("Exception occured. System Exception Message: "+ex.getMessage(), ISSTEPACTION.True,RESULT.EXCEPTION);
		}
	}

	public static void verifyText()
	{
		try
		{
			ObjectHandler.GetWebElement();

			Constants.sActualValue = Constants.webElement.getText();

			String tempKeySplitter = StorageArea.getHashTable(Constants.sValue);

			ResultLogger.log("Expected Value : " + tempKeySplitter, ISSTEPACTION.False, RESULT.PASS);

			// ResultLogger.log("Actual Value : " + Constants.sActualValue, ISSTEPACTION.False, RESULT.PASS);

			if ((Constants.sActualValue.toLowerCase().contains(tempKeySplitter.toLowerCase()) || tempKeySplitter.toLowerCase().contains(Constants.sActualValue.toLowerCase())))
			{
				// ResultLogger.log("Actual and Expected texts are equal.",ISSTEPACTION.True,RESULT.PASS);
			}
			else
			{
				ResultLogger.log("Actual and Expected texts are not equal.",ISSTEPACTION.True,RESULT.WARNING);
			}

		}
		catch (Exception e) 
		{
			ResultLogger.log("Exception occured. System message: "+e.getMessage(), ISSTEPACTION.True, RESULT.EXCEPTION);
		}

	}

	public static void verifyElementPresent()
	{
		try
		{
			ObjectHandler.splitTarget();

			if (IsElementPresent())
			{
				// ResultLogger.log("Element is Present.", ISSTEPACTION.True,RESULT.PASS);
			}
			else
			{
				ResultLogger.log("Element is not Present.", ISSTEPACTION.True,RESULT.WARNING);
			}
		}
		catch (Exception ex)
		{
			ResultLogger.log("Exception occured at verifyElementPresent ", ISSTEPACTION.True,RESULT.EXCEPTION);
		}

	}

	public static boolean IsElementPresent()
	{
		boolean isPresent = false;

		try
		{
			Constants.driver.findElement(Constants.by);

			isPresent = true;
		}
		catch(Exception ex)
		{
			isPresent =false;
		}

		return isPresent;
	}

	public static void verifyElementNotPresent()
	{
		try
		{

			ObjectHandler.splitTarget();

			if (!IsElementPresent())
			{
				ResultLogger.log("Element is not Present.", ISSTEPACTION.True,RESULT.PASS);
			}
			else
			{
				// ResultLogger.log("Element is Present.", ISSTEPACTION.True,RESULT.WARNING);
			}
		}
		catch (Exception ex)
		{
			ResultLogger.log("Exception occured at verifyElementPresent ", ISSTEPACTION.True,RESULT.EXCEPTION);
		}

	}

	public static void storeUniqueEmail()
	{
		try
		{

			String randomEmail = RandomString();

			Constants.sActualValue = "TestEsuite" + "+" + Constants.Project_Locale + "-" + Constants.Project_Environment + "-" + randomEmail + "@gmail.com";

			StorageArea.insertHashTable(Constants.sValue, Constants.sActualValue);

		}
		catch (Exception ex)
		{
			ResultLogger.log("Unable to Generate Random Email Id",ISSTEPACTION.True,RESULT.EXCEPTION);
		}

	}

	public static void storeUniqueName()
	{
		try
		{

			String randomName = RandomString();

			Constants.sActualValue = Constants.Project_Locale + Constants.Project_Environment + randomName;

			StorageArea.insertHashTable(Constants.sValue, Constants.sActualValue);

		}
		catch (Exception ex)
		{
			// ResultLogger.log("Unable to Generate Random Name",ISSTEPACTION.True,RESULT.EXCEPTION);
		}

	}

	private static String RandomString() 
	{
		char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();

		StringBuilder sb = new StringBuilder();

		Random random = new Random();

		for (int i = 0; i < 20; i++) 
		{
			char c = chars[random.nextInt(chars.length)];

			sb.append(c);
		}

		return sb.toString();
	}

	public static void verifyImagePresent()
	{

		try
		{
			ObjectHandler.GetWebElement();

			WebElement imgVerificationObject = Constants.webElement;

			Object result = null;


			result = ((JavascriptExecutor)Constants.driver).executeScript("return arguments[0].complete && " + "typeof arguments[0].naturalWidth != \"undefined\" && " + "arguments[0].naturalWidth > 0", imgVerificationObject);


			Boolean loaded = false;

			if (result  instanceof Boolean) 
			{

				loaded = (Boolean) result;

				if (loaded)
				{
					// ResultLogger.log("Image is fully displayed and present ",ISSTEPACTION.True,RESULT.PASS);
				}

				else
				{
					// ResultLogger.log("Image is not fully loaded or present ",ISSTEPACTION.True,RESULT.WARNING);
				}
			}


		}
		catch (Exception ex)
		{
			ResultLogger.log("Exception occured while verifying the image. Source: "+ex.getStackTrace(),ISSTEPACTION.True,RESULT.WARNING);
		}
	}

	public static void CreateDirectory(String path)
	{
		File file= new File(path);

		if(!file.exists())
		{
			if(file.mkdirs())
			{
				ResultLogger.log("Folder created", ISSTEPACTION.False, RESULT.PASS);
			}
			else
			{
				ResultLogger.log("Failed to create multiple directories!", ISSTEPACTION.False, RESULT.PASS);
			}
		}
	}

	public static void TakeScreenshot(String saveLocation)
	{
		try
		{
			Calendar calobj = Calendar.getInstance();

			DateFormat df = new SimpleDateFormat("dd-MM-yy");

			Path pathString = Paths.get(Constants.Custom_ScreenCapture_Path, df.format(calobj.getTime()), Constants.Project_Environment.toUpperCase().trim(), Constants.Project_Locale.toUpperCase().trim(), Constants.TC_Name);

			CreateDirectory(pathString.toString());

			File screenshot = ((TakesScreenshot)Constants.driver).getScreenshotAs(OutputType.FILE);

			Path filePath = Paths.get(pathString.toString(), saveLocation + "_" + (calobj.getTimeInMillis())+ ".png" );

			FileUtils.copyFile(screenshot, new File(filePath.toString()));
		}
		catch(Exception ex)
		{
			ResultLogger.log("Exception", ISSTEPACTION.False, RESULT.EXCEPTION);
		}

	}

	public static void storeamount()
	{

	}

	public static void clickIfExist()
	{
		try
		{
			ObjectHandler.splitTarget();

			if(IsElementPresent())
			{
				Constants.driver.findElement(Constants.by).click();

				ResultLogger.log("Element presented and clicked the element", ISSTEPACTION.True, RESULT.PASS);
			}
			else
			{
				ResultLogger.log("Element not presented ", ISSTEPACTION.True, RESULT.PASS);
			}

		}
		catch (Exception e) 
		{
			ResultLogger.log("Exception occured. Sys Message: "+e.getMessage(), ISSTEPACTION.False, RESULT.EXCEPTION);
		}

	}

	public static void waitInSeconds()
	{
		try
		{
			int seconds = Integer.parseInt(Constants.sValue);

			long millis = seconds * 1000;

			Thread.sleep(millis);

			ResultLogger.log("Waiting for "+seconds, ISSTEPACTION.True, RESULT.PASS);
		}
		catch (Exception e) 
		{
			ResultLogger.log(e.getMessage(), ISSTEPACTION.True, RESULT.EXCEPTION);
		}
	}
	public static void quit()
	{
		try
		{
			if(Constants.Browser_Name.toUpperCase().equals("IE"))
			{
				Constants.driver.close();
				Process p = Runtime.getRuntime().exec("taskkill /im IEDriverServer.exe /f");
				ResultLogger.log("Element not presented ", ISSTEPACTION.True, RESULT.PASS);
			}
			if(Constants.Browser_Name.toUpperCase().equals("CHROME"))
			{
				Constants.driver.close();
				Process p = Runtime.getRuntime().exec("taskkill /im chromedriver.exe /f");
				ResultLogger.log("Element not presented ", ISSTEPACTION.True, RESULT.PASS);
			}

		}
		catch (Exception e) 
		{
			ResultLogger.log("driver not closed"+e.getMessage(), ISSTEPACTION.True, RESULT.EXCEPTION);
		}
	}
}
