package Startup;

import java.awt.Image;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

import javax.swing.ImageIcon;

import Constants.Constants;
import Execution.dbConnections;
import Framework.Browser;
import Framework.CommonActions;
import Framework.ListBoxActions;
import Framework.TextBoxActions;
import ResultLogs.ResultLogger;
import ResultLogs.ResultLogger.ISSTEPACTION;
import ResultLogs.ResultLogger.RESULT;
import junit.framework.Assert;


//This is the method where execution will starts. 
@SuppressWarnings("deprecation")
public class EntryPoint {

	static SystemTray tray=null;
	static TrayIcon trayIcon=null;
	//public static WebDriver driver;


	EntryPoint(){

		try
		{
			InetAddress addr;

			addr = InetAddress.getLocalHost();

			Constants.machine_name = addr.getHostName();

		}
		catch (UnknownHostException ex)
		{
			// ResultLogger.log("Exception occured While getting the Host name",ISSTEPACTION.False,RESULT.EXCEPTION);
		}

	}


	public static void main(String[] args)
	{

		try
		{
			Constants.storageLocation.clear();

			//Initialize test cases
			InitializeTestExecution();

			//Initialize the webDriver
			Browser.launchBrowser();

			//Read Test case steps
			dbConnections.readTestCaseDesignSteps();

			//ResultLogger.log("Execution started...", ISSTEPACTION.False,RESULT.PASS);

			for(Constants.stepNumber=0;Constants.stepNumber <= Constants.Actions.size()-1;Constants.stepNumber++)
			{
				Constants.sAction = Constants.Actions.get(Constants.stepNumber);

				Constants.sTarget = Constants.Targets.get(Constants.stepNumber);

				Constants.sValue = Constants.Values.get(Constants.stepNumber);

				Constants.Step_info = "Step number: "+(Constants.stepNumber)+"||Action: "+Constants.sAction+"|| Target: "+Constants.sTarget;

				Constants.stepLog.stepNumber.add(((Integer)Constants.stepNumber).toString());

				Constants.stepLog.StepAction.add(Constants.sAction);

				Constants.stepLog.StepTarget.add(Constants.sTarget);

				Constants.stepLog.StepValue.add(Constants.sValue);

				ResultLogger.log("Step Number:"+(Constants.stepNumber)+"|Action:"+Constants.sAction+"|Target:"+Constants.sTarget+"|Value:"+Constants.sValue, ISSTEPACTION.False, RESULT.PASS);
				
				System.out.println(Constants.sAction);
				
				if(Constants.sAction.toUpperCase().equals("visibilityofelementclick".toUpperCase()))
					{
					//	CommonActions.visibility_elem_click();
						//trayIcon.displayMessage("Hello, World", "notification demo", MessageType.INFO);
		
					}

				executeAction();

			}

			ResultLogger.getnerateReport();
			
		}
		catch(Exception ex)
		{
			//ResultLogger.getnerateReport();
			CommonActions.quit();
		}
	}

	public static void InitializeTestExecution(){

		try
		{
			Constants.isLocalExecution = true;

			if(Constants.isLocalExecution)
			{
				//ResultLogger.log("Execution starts from Local Code base...", ISSTEPACTION.False,RESULT.PASS);

				Constants.Browser_Name = "Chrome"; //Browser Names: Chrome/IE/ Firefox

				Constants.TC_Name = "Store_Verify_Locator"; //Provide the Test case name to run

			}

			else
			{
				dbConnections.openDBConnection();

				String query = "select * from buildconfiguration order by id desc";

				ResultSet res = dbConnections.ExecuteQuery(query);

				if(res.next())
				{
					Constants.TC_Name = res.getString(3);

					Constants.Browser_Name = res.getString(2);

				}
			}
		}
		catch(Exception ex)
		{
			//ResultLogger.log("Exception occured. System Message: "+ex.getMessage(),ISSTEPACTION.False, RESULT.EXCEPTION);
		}

	}

	public static void executeAction()
	{
		try
		{
			Browser.waitForPageLoad();

			if(Constants.sAction.toUpperCase().equals("OPEN"))
			{
				Browser.open();
			}
			else if(Constants.sAction.toUpperCase().equals("DeleteAllVisibleCookies".toUpperCase()))
			{
				Browser.DeleteVisibleCookies();
			}
			else if(Constants.sAction.toUpperCase().equals("click".toUpperCase()))
			{
				CommonActions.click();
				//trayIcon.displayMessage("Hello, World", "notification demo", MessageType.INFO);
			}
			else if(Constants.sAction.toUpperCase().equals("verifytitle".toUpperCase()))
			{
				Browser.verifytitle();
			}
			else if(Constants.sAction.toUpperCase().equals("clickandwait".toUpperCase()))
			{
				CommonActions.clickAndWait();
			}
			else if(Constants.sAction.toUpperCase().equals("storetext".toUpperCase()))
			{
				CommonActions.storeText();
			}
			else if(Constants.sAction.toUpperCase().equals("storeamount".toUpperCase()))
			{
				CommonActions.storeamount();
			}
			else if(Constants.sAction.toUpperCase().equals("verifyimage".toUpperCase()))
			{
				CommonActions.verifyImagePresent();
			}
			else if(Constants.sAction.toUpperCase().equals("storeuniqueemail".toUpperCase()))
			{
				CommonActions.storeUniqueEmail();
			}
			else if(Constants.sAction.toUpperCase().equals("storeuniquename".toUpperCase()))
			{
				CommonActions.storeUniqueName();
			}
			else if(Constants.sAction.toUpperCase().equals("type".toUpperCase()))
			{
				TextBoxActions.type();
			}
			else if(Constants.sAction.toUpperCase().equals("select".toUpperCase()))
			{
				ListBoxActions.select();
			}
			else if(Constants.sAction.toUpperCase().equals("clickIfExist".toUpperCase()))
			{
				CommonActions.clickIfExist();

			}
			else if(Constants.sAction.toUpperCase().equals("verifyText".toUpperCase()))
			{
				CommonActions.verifyText();

			}
			else if(Constants.sAction.toUpperCase().equals("waitInSeconds".toUpperCase()))
			{
				CommonActions.waitInSeconds();

			}
//			else if(Constants.sAction.toUpperCase().equals("visibilityofwait".toUpperCase()))
//			{
//				//CommonActions.visibility_Wait();
//				//trayIcon.displayMessage("Hello, World", "notification demo", MessageType.INFO);
//
//			}
//			else if(Constants.sAction.toUpperCase().equals("visibilityofelementclick".toUpperCase()))
//			{
//				//CommonActions.visibility_elem_click();
//				//trayIcon.displayMessage("Hello, World", "notification demo", MessageType.INFO);
//
//			}
			else if(Constants.sAction.toUpperCase().equals("invisibilityofwait".toUpperCase()))
			{
				CommonActions.invisibility_Wait();
			}
			else if(Constants.sAction.toUpperCase().equals("verifyAddress".toUpperCase()))
			{
				CommonActions.verifyAddress();

			}

			else if(Constants.sAction.toUpperCase().equals("Textclear".toUpperCase()))
			{
				TextBoxActions.type_clear();

			}

			else if(Constants.sAction.toUpperCase().equals("quit".toUpperCase()))
			{
				CommonActions.quit();
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
}
