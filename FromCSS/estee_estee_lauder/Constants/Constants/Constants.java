package Constants;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.openqa.selenium.*;

import org.openqa.selenium.remote.RemoteWebDriver;

import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import ResultLogs.StepLogger;

//This class contains all static and constants

public class Constants {
	
	/* Test Case Variables */ 
	
	public static  int EmailStringSizeRandom = 12;

	public static String Project_Environment = "QA";

	public static String Project_Locale = "US";

	public static String  machine_name = "";
	
	public static String TC_Name = "";
	
	public static String Market = "";
	
	public static String Environment = "";
	
	public static boolean isLocalExecution = false;
	
	public static String sAction = "";
	
	public static String sTarget = "";
	
	public static String sValue = "";
	
	public static String sActualValue = "";
	
	public static String tPropValue = "";
	
	public static String tPropType = "";
	
	public static int stepNumber = 0;
	
	public static StepLogger stepLog = new StepLogger();
	
	public static String Custom_ScreenCapture_Path ="C:\\Automation\\Estee\\Screenshots\\";
	
	public static String TestCaseResult = "PASSED";
	
	public static int Warnings_Count = 0;
	
	public static String Step_info = null;
	
	public static String ErrorMessage = "";
	
	public static boolean isTCFailed = false; 
	
	/* WebDriver Variables */
	
	public static WebDriver driver = null;
	
	public static WebElement webElement = null;
	public static WebDriverWait visibility_wait=null;
	public static RemoteWebDriver remoteWebDriver = null;
	public static List<WebElement> webElements = null;
	public static RemoteWebElement remoteWebElement = null;
	
	public static By by = null;
	
	/* Execution Variables */
	
	public static String Browser_Name = "";
	
	public static ArrayList<String> Actions = new ArrayList<String>();
	
	public static ArrayList<String> Targets = new ArrayList<String>();
	
	public static ArrayList<String> Values =new ArrayList<String>();
	

	
	public static Hashtable< Object,Object> storageLocation=new Hashtable<Object,Object>();
	
	
	

}
