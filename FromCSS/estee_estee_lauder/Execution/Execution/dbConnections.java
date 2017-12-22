package Execution;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import Constants.Constants;
import Constants.DBConstants;
import ResultLogs.ResultLogger;
import ResultLogs.ResultLogger.ISSTEPACTION;
import ResultLogs.ResultLogger.RESULT;

public class dbConnections {
	
	public static void openDBConnection()
	{
		try
		{
			

			Class.forName("com.mysql.jdbc.Driver");  
			
			/*Connection con=DriverManager.getConnection("jdbc:mysql://10.8.35.53:3306/automation","username","password");  
			//"server=10.8.35.53;uid=username;pwd=password;database=automation;"
			
			Statement stmt=con.createStatement();  
			
			ResultSet rs=stmt.executeQuery("select * from project"); 
			
			while(rs.next())  
			{
				System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));  
			}
			con.close();  
			*/
			
			//String url = "jdbc:postgresql://localhost:5432/postgres";
			String url = "jdbc:mysql://10.8.35.53:3306/automation";
			
			DBConstants.dBConnectionProperties=new Properties();
			
			DBConstants.dBConnectionProperties.setProperty("user","username");
			
			DBConstants.dBConnectionProperties.setProperty("password","password");
			
			//DBConstants.dBConnectionProperties.setProperty("ssl","true");
			
			DBConstants.connection = DriverManager.getConnection(url, DBConstants.dBConnectionProperties);
			
			ResultLogger.log("Connection established successfully.", ISSTEPACTION.False,RESULT.PASS);
		}
		catch(Exception ex)
		{
			ResultLogger.log("Exception occured while establishing the connection", ISSTEPACTION.False,RESULT.EXCEPTION);
		}
	}
	
	public static void closeDBConnection()
	{
		try
		{
			if(!DBConstants.connection.isClosed())
			{
				DBConstants.connection.close();
				
				ResultLogger.log("Connection closed successfully.", ISSTEPACTION.False,RESULT.PASS);
			}
		}
		catch(Exception ex)
		{
			ResultLogger.log("Exception occured while closing the connection", ISSTEPACTION.False,RESULT.EXCEPTION);
		}
	}
	
	public static ResultSet ExecuteQuery(String query)
	{
		ResultSet queryResults = null;
		
		try
		{
			queryResults =  DBConstants.connection.createStatement().executeQuery(query);
		}
		catch(Exception ex)
		{
			ResultLogger.log("Exception occured while reading the records from DB", ISSTEPACTION.False,RESULT.EXCEPTION);
		}
		
		return queryResults;
	}
	
	public static int ExecuteNonQuery(String query)
	{
		int rows=0;
		
		try
		{
			
			dbConnections.openDBConnection();
			
			rows = DBConstants.connection.createStatement().executeUpdate(query);
			
			ResultLogger.log("No. Of records updated: "+rows, ISSTEPACTION.False, RESULT.PASS);
		}
		catch(Exception ex)
		{
			ResultLogger.log(ex.getMessage(), ISSTEPACTION.False, RESULT.EXCEPTION);
		}
		
		return rows;
		
		
	}

	public static void readTestCaseDesignSteps()
	{
		try
		{
			openDBConnection();
			
			readSteps();
			
			closeDBConnection();
			
		}
		catch(Exception ex)
		{
			ResultLogger.log("Exception occured while reading the test case design steps.", ISSTEPACTION.False,RESULT.EXCEPTION);
		}
	}
	
	
	public static void readSteps()
	{
		try
		{
			//Clearing the Actions, Targets, and Values lists
			Constants.Actions.clear();
			
			Constants.Targets.clear();
			
			Constants.Values.clear();
			
			//Reading the Steps by executing the DB query			
			String query=" SELECT STEPS.ID,STEPS.stepid,STEPS.obj_ref_id,obj.obj_ref_name,obj.target_property,STEPS.testcaseid, STEPS.htmlelementtype,ACTION.actiontypename,STEPS.status,STEPS.valuetext FROM test_case_design_steps STEPS inner join actiontype ACTION on ACTION.ID=STEPS.actiontype_id  inner join obj_repository obj on STEPS.obj_ref_id =  obj.id where  STEPS.testcaseid = (SELECT ID FROM testcases WHERE testcasename ='"+Constants.TC_Name+"')ORDER BY STEPS.stepid ASC";
						
			ResultSet queryResult =  ExecuteQuery(query);
			
			ResultLogger.log("Exceuted Query: "+query, ISSTEPACTION.False,RESULT.PASS);
			
			while(queryResult.next())
			{
				Constants.Actions.add(queryResult.getString(8));
				
				Constants.Targets.add(queryResult.getString(5));
				
				Constants.Values.add(queryResult.getString(10));
			}
		}
		catch(Exception ex)
		{
			ResultLogger.log("Exception occured while reading the test steps", ISSTEPACTION.False,RESULT.EXCEPTION);
		}
		
		if(Constants.Actions.size()==0)
		{
			ResultLogger.log("No Test cases found. Please design test case steps to execute", ISSTEPACTION.False,RESULT.FAIL);
		}
	}
	
}
