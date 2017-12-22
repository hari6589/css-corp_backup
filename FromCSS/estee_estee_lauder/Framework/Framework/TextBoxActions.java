package Framework;

import org.openqa.selenium.Keys;

import Constants.Constants;
import Execution.ObjectHandler;
import Execution.StorageArea;
import ResultLogs.ResultLogger;
import ResultLogs.ResultLogger.ISSTEPACTION;
import ResultLogs.ResultLogger.RESULT;

public class TextBoxActions {
	
	public static void type()
	{
		try
		{
			//Thread.sleep(30000);
			ObjectHandler.GetWebElement();
			Constants.webElement.click();
			Constants.webElement.clear();
			
			Constants.webElement.sendKeys(StorageArea.getHashTable(Constants.sValue));
			
			ResultLogger.log("Entered Text into the specified filed. ", ISSTEPACTION.True, RESULT.PASS);
		}
		catch (Exception e) 
		{
			ResultLogger.log("Exception occured while entering the text/value. System Message: "+e.getMessage(), ISSTEPACTION.True, RESULT.EXCEPTION);
		}
	}
	public static void type_clear()
	{
		try
		{
			ObjectHandler.GetWebElement();
			Constants.webElement.click();
			Constants.webElement.clear();
			ResultLogger.log("click and clear Text into the specified filed. ", ISSTEPACTION.True, RESULT.PASS);
		}
		catch (Exception e) 
		{
			ResultLogger.log("Exception occured while entering the text/value. System Message: "+e.getMessage(), ISSTEPACTION.True, RESULT.EXCEPTION);
		}
	}
}
