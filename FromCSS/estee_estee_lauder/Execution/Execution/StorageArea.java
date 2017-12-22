package Execution;

import Constants.Constants;
import ResultLogs.ResultLogger;
import ResultLogs.ResultLogger.ISSTEPACTION;
import ResultLogs.ResultLogger.RESULT;

public class StorageArea {
	
	public static void insertHashTable(String hKey, String hValue)
    {

        String temp = "";
        
        if (hKey.startsWith("${"))
        {
            temp = hKey.substring(2, (hKey.length() - 1));
        }
        else
        {
            temp = hKey;
        }

        if (!Constants.storageLocation.containsKey(temp))
        {
            Constants.storageLocation.put(temp, hValue);
        }
        else
            ResultLogger.log("Already in Hash Table - Key "+temp,ISSTEPACTION.False,RESULT.WARNING);

    }
	
	 public static void removeHashTableData(String hKey)
     {
         try
         {
             String temp = "";
             
             if (hKey.startsWith("${"))
             {
                 temp = hKey.substring(2, (hKey.length() - 3));
             }
             else
             {
                 temp = hKey;
             }
             if (Constants.storageLocation.containsKey(temp))
             {
            	 Constants.storageLocation.remove(temp);
                 
             }
             ResultLogger.log("Removed Data from hash table", ISSTEPACTION.False, RESULT.PASS);
         }
         catch (Exception e)
         {
             ResultLogger.log("Exception occured while Removing Data from hash table : "+e.getMessage(),ISSTEPACTION.False,RESULT.EXCEPTION);
         }
     }

	 public static void clearHashTableKeys()
     {
         
         try
         {
             Constants.storageLocation.clear();
             
             ResultLogger.log("storage location cleared...",ISSTEPACTION.False,RESULT.EXCEPTION);
         }
         catch (Exception e)
         {
        	 ResultLogger.log("Exception occured while Removing Data from hash table : "+e.getMessage(),ISSTEPACTION.False,RESULT.EXCEPTION);
         }
     }

	 public static String getHashTable(String hKey)
     {
         String temp = "", tempData = "";
         
         try
         {

             if (hKey.startsWith("${"))
             {
                 temp = hKey.substring(2, (hKey.length() - 1));
             }
             else
             {
                 temp = hKey;
             }

             if (Constants.storageLocation.size() > 0 && Constants.storageLocation.containsKey(temp))
             {
                 tempData = Constants.storageLocation.get(temp).toString();
             }
             else
             {
              //   Console.WriteLine("Else Part Hash Table Key : " + temp + " - Value : " + tempData);
                 tempData = hKey;
             }

         }
         catch (Exception e)
         {
             ResultLogger.log("Hash Table Data error occurred :method getHashTable() for Data " + hKey+" - "+Constants.stepNumber,ISSTEPACTION.False,RESULT.EXCEPTION);
         }
         
         return tempData;
     }
}
