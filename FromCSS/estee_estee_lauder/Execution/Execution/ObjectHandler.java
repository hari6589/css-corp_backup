package Execution;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.RemoteWebElement;

import Constants.Constants;
import ResultLogs.ResultLogger;
import ResultLogs.ResultLogger.ISSTEPACTION;
import ResultLogs.ResultLogger.RESULT;

public class ObjectHandler {

	public static void GetWebElement()
	{
		try
		{

			splitTarget();
			// String source= Constants.driver.getPageSource();
			//Constants.webElement=Constants.driver.findElement(By.id("org.hola:id/cont_btn"));
			Constants.driver.manage().timeouts().implicitlyWait(5000,TimeUnit.MILLISECONDS);
			Constants.webElement=Constants.driver.findElement(Constants.by);
			HighlightMyElement(Constants.webElement);
			//            //WebElement element = driver.findElement(By.xpath("Value"));
			//            Coordinates coordinate = ((Locatable)Constants.webElement).getCoordinates(); 
			//            coordinate.onPage(); 
			//            coordinate.inViewPort();
			//            

			ResultLogger.log("Element Found in the page", ISSTEPACTION.False, RESULT.PASS);

		}
		catch (Exception ex)
		{
			ResultLogger.log(ex.getMessage(),ISSTEPACTION.False,RESULT.EXCEPTION);
		}
	}
	public static void HighlightMyElement(WebElement element) throws InterruptedException
	{
		JavascriptExecutor highlight=(JavascriptExecutor)Constants.driver;
		//To highlight an Element
		highlight.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "background: cyan; border: 5px solid yellow;");
		Thread.sleep(2000);
		//To make the element default
		highlight.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");

	}

	public static void GetWebElements()
	{
		try
		{
			splitTarget();

			Constants.webElements=Constants.driver.findElements(Constants.by);

			ResultLogger.log("Element Found in the page", ISSTEPACTION.False, RESULT.PASS);

		}
		catch (Exception ex)
		{
			ResultLogger.log(ex.getMessage(),ISSTEPACTION.False,RESULT.EXCEPTION);
		}
	}
	public static void splitTarget()
	{	
		/* To Split and Store the Property type and Property Values from Target */

		if (Constants.sTarget.toUpperCase().startsWith("ID"))
		{
			Constants.tPropValue = Constants.sTarget.split("=")[1];

			Constants.tPropType = "ID";

			Constants.by = By.id(Constants.tPropValue);



		}
		else if (Constants.sTarget.toUpperCase().startsWith("NAME"))
		{
			Constants.tPropValue = Constants.sTarget.split("=")[1];

			Constants.tPropType = "NAME";

			Constants.by = By.name(Constants.tPropValue);

		}
		else if (Constants.sTarget.toUpperCase().startsWith("CSS"))
		{
			Constants.tPropValue = Constants.sTarget.split("=")[1];

			Constants.tPropType = "CSS";

			Constants.by = By.cssSelector(Constants.tPropValue);

		}
		else if (Constants.sTarget.toUpperCase().startsWith("CLASS"))
		{
			Constants.tPropValue = Constants.sTarget.split("=")[1];

			Constants.tPropType = "CLASS";

			Constants.by = By.className(Constants.tPropValue);

		}
		else if (Constants.sTarget.toUpperCase().startsWith("LINK"))
		{
			Constants.tPropValue = Constants.sTarget.split("=")[1];

			Constants.tPropType = "LINK";

			Constants.by = By.linkText(Constants.tPropValue);

		}
		else if (Constants.sTarget.toUpperCase().startsWith("XPATH"))
		{
			Constants.tPropValue = Constants.sTarget.split("=")[1];

			Constants.tPropType = "XPATH";

			Constants.by = By.xpath(Constants.tPropValue);

		}
		else if (Constants.sTarget.toUpperCase().startsWith("//"))
		{
			Constants.tPropValue = Constants.sTarget;

			Constants.tPropType = "XPATH";

			Constants.by = By.xpath(Constants.tPropValue);

		}
		else if(Constants.sTarget.toUpperCase().startsWith(".//"))
		{
			Constants.tPropValue = Constants.sTarget;

			Constants.tPropType = "XPATH";

			Constants.by = By.xpath(Constants.tPropValue);
		}
		else
		{
			ResultLogger.log("Invalid Target Identified - "+Constants.sTarget, ISSTEPACTION.False,RESULT.FAIL);
		}
	}


}
