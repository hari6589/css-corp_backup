package ResultLogs;


import Constants.Constants;
import Execution.dbConnections;
import Framework.CommonActions;
import junit.framework.Assert;

@SuppressWarnings("deprecation")
public class ResultLogger {

	public static enum RESULT
	{
		WARNING,FAIL,EXCEPTION, PASS
	}
	
	public static enum ISSTEPACTION
	{
		True, False;
	}
	
	
	public static void log(String argMessage, ISSTEPACTION isStepAction, RESULT result)
	{
		//System.out.println(argMessage);
		
		if(ISSTEPACTION.True == isStepAction)
		{
			Constants.stepLog.StepResult.add(result.toString());
			
			Constants.stepLog.StepComments.add(Constants.Step_info + "\r\n"+ argMessage);
		}
		
	
			
			if(result == RESULT.FAIL || result == RESULT.EXCEPTION)
			{
				Constants.TestCaseResult = "FAILED";
				
				Constants.ErrorMessage += Constants.Step_info + "\r\n"+ argMessage;
				
				Constants.isTCFailed = true;
				
				
				//Take Screenshot
				CommonActions.TakeScreenshot(Constants.TC_Name+"_"+Constants.Browser_Name+"_Warnings"+(Constants.stepNumber+1)+"_Action-"+Constants.sAction);
				
				Constants.driver.close();
				
				getnerateReport();
	
				Assert.fail();
			}
			else if(result == RESULT.WARNING)
			{
				Constants.TestCaseResult = "FAILED";
				
				Constants.Warnings_Count++;
				
				Constants.stepLog.StepComments.add(Constants.Step_info + "\r\n"+ argMessage);
				
				Constants.ErrorMessage += Constants.Step_info + "\r\n"+ argMessage;
				
				//Take Screenshot
				
				CommonActions.TakeScreenshot(Constants.TC_Name+"_"+Constants.Browser_Name+"_Warnings"+(Constants.stepNumber+1)+"_Action-"+Constants.sAction);
				
			}
		

	}

	public static void getnerateReport()
	{
		if(Constants.Warnings_Count>=1)
		{
			StringBuilder builder= new StringBuilder(Constants.ErrorMessage);
			
			builder.insert(0, "Test Case failed with "+Constants.Warnings_Count+" Warnings");
			
						
			Constants.ErrorMessage = builder.toString();
			
			
			
			
		}
		Constants.ErrorMessage=Constants.ErrorMessage.replace("\'","\"");
//		Calendar calobj = Calendar.getInstance();
//		 
//		DateFormat df = new SimpleDateFormat("dd-MM-yy HH:MM:SS");
		
		dbConnections.openDBConnection();
		
		String query= " INSERT INTO testcaseresults(testcaseid, testresult, duration, runnedby, owner, createddate,"
				+ " modifieddate, modifiedby, comments)	"
				+ "VALUES ( (select ID from testcases where testcasename='"+Constants.TC_Name+"'),"
				+ "'"+Constants.TestCaseResult+"' ,"+
			            "'5 min','ADMIN', 'ADMIN', curdate(),curdate(), 'ADMIN',  '"+Constants.ErrorMessage+"')";
		
		//System.out.println("Executed Query: "+query);
		
		int rows = dbConnections.ExecuteNonQuery(query);
		
		dbConnections.closeDBConnection();
		
		if(rows==0)
		{
			ResultLogger.log("No records updated. Unable to generate report.", ISSTEPACTION.False, RESULT.FAIL);
		}
		else
		{
			for(int i=0;i<=Constants.Actions.size()-1;i++)
			{
				dbConnections.openDBConnection();
				try{
			// Inserting the Step result
					query ="INSERT INTO teststepresults(	 testresultslogsid, stepid, actiontype, target, "+
							"valuetext, stepresult, errorcomments, screenshotlocation)	"
									+ "VALUES ( (select MAX(ID) as ID from testcaseresults), "+Constants.stepLog.stepNumber.get(i)+",'"+
							Constants.stepLog.StepAction.get(i).replace("\'","\"")+"','"+Constants.stepLog.StepTarget.get(i).replace("\'","\"")+
							"','"+Constants.stepLog.StepValue.get(i).replace("\'","\"")+"','"+Constants.stepLog.StepResult.get(i).replace("\'","\"")+"','"+
							Constants.stepLog.StepComments.get(i).replace("\'","\"")+"','screenshotpathDummy')";
					
					//System.out.println("Executed Query: "+query);
					
					rows = dbConnections.ExecuteNonQuery(query);
					
					dbConnections.closeDBConnection();
				}
				catch(Exception ex)
				{

				}
				
				
			}
		}
		
	}
}


