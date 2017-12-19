package com.bsro;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;

import com.bsro.pojo.HolidayHours;
import com.bsro.pojo.Holidays;
import com.bsro.pojo.Hours;
import com.bsro.pojo.StoreHours;
import com.bsro.pojo.Stores;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
public class StoreSyncUpApplication {
	static String ACCESS_KEY;
	static String SECRET_KEY;
	static String ORACLE_URL;
	static String ORACLE_USER;
	static String ORACLE_PASSWD;
	static List<String> errorStatements = new ArrayList<String>();
	
	protected static String defaultLogFile = "log.txt";
	private static String tableName= "StoreHours1";
	
    public static void write(String s) throws IOException {
    	write(defaultLogFile, s);
    }
    
    public static void write(String f, String s) throws IOException {
    	TimeZone tz = TimeZone.getTimeZone("IST"); 
        Date now = new Date();
        DateFormat df = new SimpleDateFormat ("dd.mm.yyyy hh:mm:ss ");
        df.setTimeZone(tz);
        String currentTime = df.format(now);
    	
    	FileWriter aWriter = new FileWriter(f, true);
    	aWriter.write(currentTime + " " + s+"\r\n"); 
        aWriter.flush();
        aWriter.close();
    }

	public static void main(String[] args) throws Exception {
		File file = new File(defaultLogFile); 
		if(file.exists()){
			file.delete();
		}
		Connection connection= null;
		/*Reading configuration file*/
		Properties prop = new Properties();
		InputStream input = null;
		try {
			input = new FileInputStream("config.properties");
			prop.load(input);
			ORACLE_URL = prop.getProperty("oracle_url");
			ORACLE_USER = prop.getProperty("oracle_user");
			ORACLE_PASSWD = prop.getProperty("oracle_password");
			ACCESS_KEY = prop.getProperty("ACCESS_KEY");
			SECRET_KEY = prop.getProperty("SECRET_KEY");
		}catch(Exception e){
			e.printStackTrace();
			write("Error occurs while reading config.properties file");
			System.out.println("Error occurs while reading config.properties file "+e.getMessage());
			return;
		}finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					write("Error occurs while reading config.properties file "+e.getMessage());
					System.out.println("Error occurs while reading config.properties file "+e.getMessage());
					return;
				}
			}
		}
    	/*Oracle database connection*/
    	try {
    		Class.forName("oracle.jdbc.driver.OracleDriver");
    		connection= DriverManager.getConnection(ORACLE_URL.trim(),ORACLE_USER.trim(),ORACLE_PASSWD.trim()); 
    		if (connection != null) {
    			write("Oracle database connected sucessfully...");
    			System.out.println("Oracle database connected sucessfully...");
    		} else {
    			write("Oracle Connection failed");
    			System.out.println("Oracle Connection failed");
    		}
    	}catch(SQLException e){
    		e.printStackTrace();
    		write("Oracle Connection failed");
    		System.out.println("Oracle Connection failed ");
    		return;
    	}catch(ClassNotFoundException e) {
    		write("Please provide Oracle JDBC Driver?");
			System.out.println("Please provide Oracle JDBC Driver?");
			return;
		}
    	try{
    		StorHoursServices services = new StorHoursServices();
    		List<Stores> storesList = new ArrayList<Stores>();
    		Map<Long,List<Hours>> hoursList = new HashMap<Long,List<Hours>>();
    		List<Holidays> holidaysList = new ArrayList<Holidays>();
    		Map<Long,List<HolidayHours>> holidayHoursList = new HashMap<Long,List<HolidayHours>>();
    		try{
    			storesList = services.getStoreList(connection);
    		}catch(SQLException e){
    			write("SQLException while getting the store list from STORE table");
    			System.out.println("SQLException while getting the store list");
    		}
    		try{
    			hoursList = services.getHours(connection);
    		}catch(SQLException e){
    			write("SQLException while getting the store hours from STORE_HOUR table");
    			System.out.println("SQLException while getting the store list");
    		}
    		try{
    			holidaysList = services.getHolidays(connection);
	    	}catch(SQLException e){
				write("SQLException while getting the store holiday from STORE_HOLIDAY table");
				System.out.println("SQLException while getting the store list");
			}
    		try{
    			holidayHoursList = services.getHolidayHours(connection);
    		}catch(SQLException e){
				write("SQLException while getting the store holiday hours from STORE_HOLIDAY_HOURS table");
				System.out.println("SQLException while getting the store list");
			}
    		List<StoreHours> storeHours = new ArrayList<StoreHours>();
    		
    		List<StoreHours.Holidays> shHolidays = new ArrayList<StoreHours.Holidays>();
    		for(Holidays h : holidaysList){
    			StoreHours.Holidays shHoliday = new StoreHours.Holidays();
    			shHoliday.setYear(h.getYear());
    			shHoliday.setMonth(h.getMonth());
    			shHoliday.setDay(h.getDay());
    			shHoliday.setDescription(h.getDescription());
    			shHoliday.setHolidayId(h.getHolidayId());
    			shHolidays.add(shHoliday);
    		}
    		for(Stores s : storesList){
    			StoreHours sh = new StoreHours();
    			sh.setStoreNumber(s.getStoreNumber());
    			sh.setSiteName(s.getSiteName());
    			sh.setStoreType(s.getStoreType());
    			sh.setHolidays(shHolidays);

    			List<Hours> getHoursForStore = (List<Hours>) hoursList.get(s.getStoreNumber());
    			System.out.println("Hours List Size "+hoursList.size()+"StoreNumber "+s.getStoreNumber());
    			List<StoreHours.Hours> shHours = new ArrayList<StoreHours.Hours>();
    			if(getHoursForStore!=null && getHoursForStore.size()!=0){
	    			for(Hours h:getHoursForStore){
	    				StoreHours.Hours shHour = new StoreHours.Hours();
	    				shHour.setCloseTime(h.getCloseTime());
	    				shHour.setOpenTime(h.getOpenTime());
	    				shHour.setWeekDay(h.getWeekDay());
	    				shHours.add(shHour);
	    			}
    			}
    			List<HolidayHours> getHolidayHoursForStore = (List<HolidayHours>) holidayHoursList.get(s.getStoreNumber());
    			List<StoreHours.HolidayHours> shHolidayHours = new ArrayList<StoreHours.HolidayHours>();
    			if(getHolidayHoursForStore!=null && getHolidayHoursForStore.size()!=0){
	    			for(HolidayHours h:getHolidayHoursForStore){
	    				for(Holidays hl:holidaysList){
	    					if(hl.getHolidayId().equals(h.getHolidayId())){
	    						StoreHours.HolidayHours shHolidayHour = new StoreHours.HolidayHours();
	    						shHolidayHour.setCloseTime(h.getCloseTime());
	    						shHolidayHour.setOpenTime(h.getOpenTime());
	    						shHolidayHour.setHolidayId(h.getHolidayId());
	    	    				shHolidayHours.add(shHolidayHour);
	    					}
	    				}
	    			}
    			}
    			if(shHolidayHours!= null && shHolidayHours.size()!=0)
    				sh.setHolidayHours(shHolidayHours);
    			if(shHolidays!= null && shHolidays.size()!=0)
    				sh.setHolidays(shHolidays);
    			if(shHours!= null && shHours.size()!=0)
    				sh.setHours(shHours);
    			storeHours.add(sh);
    		}
    		writeListInJSON(storeHours);
//    		deleteTableBeforeMove(tableName);
//    		moveToDynamoDB();
    	}catch(Exception e){
    		write("Error occurs please restart the application123 "+e.getMessage());
    		System.out.println("Error occurs please restart the application1233 "+e.getMessage());
    		return;
    	}finally {
            if (connection != null) connection.close();
        }
	}
	
	public static void writeListInJSON(List<StoreHours> storeHours) throws IOException{
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(new File("storeHours.json"), storeHours);
			String jsonInString = mapper.writeValueAsString(storeHours);
//			System.out.println(jsonInString);

			//Convert object to JSON string and pretty print
			jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(storeHours);
			write(jsonInString);
			//System.out.println(jsonInString);

		} catch (JsonGenerationException e) {
			write("Error occurs please restart the application "+e.getMessage());
    		System.out.println("Error occurs please restart the application "+e.getMessage());
		} catch (JsonMappingException e) {
			write("Error occurs please restart the application "+e.getMessage());
    		System.out.println("Error occurs please restart the application "+e.getMessage());
		} catch (IOException e) {
			write("Error occurs please restart the application "+e.getMessage());
    		System.out.println("Error occurs please restart the application "+e.getMessage());
		}
	}
	
	/*public static String doesTableExist(AmazonDynamoDBClient ddbClient, String tableName) throws IOException {
		try {
			ClientConfiguration clientCfg = new ClientConfiguration();
			clientCfg.setProtocol(Protocol.HTTP);
			clientCfg.setProxyPort(443);
			DescribeTableRequest describeTableRequest = new DescribeTableRequest().withTableName(tableName);
			DescribeTableResult describeTableResult = ddbClient.describeTable(describeTableRequest);
			return describeTableResult.getTable().getTableName();
		} catch (AmazonServiceException ase) {
			// If the table isn't found, there's no problem.
			// If the error is something else, re-throw the exception to bubble it up to the caller.
			if (!ase.getErrorCode().equals("ResourceNotFoundException")) {
				throw ase;
			}
			return null;
		}
	}
	public static void deleteTableBeforeMove(String tableName)throws IOException, InterruptedException{
		AWSCredentials creds = new BasicAWSCredentials(ACCESS_KEY,SECRET_KEY);
		AmazonDynamoDBClient client = new AmazonDynamoDBClient(creds);
		AmazonDynamoDB dynamodb = AmazonDynamoDBClientBuilder.standard().build();
		ClientConfiguration clientCfg = new ClientConfiguration();
			clientCfg.setProtocol(Protocol.HTTP);
			clientCfg.setProxyPort(443);
		DynamoDB dynamo = new DynamoDB(client);
		try{
			try{
				if(doesTableExist(client,tableName)!=null){
//			TableDescription tableDesc = dynamodb.describeTable(new DescribeTableRequest(tableName)).getTable();
//				System.out.println(tableDesc.getTableName());
				System.out.println("Table Exist....Started Deleting Table...");
				DeleteTableRequest deleteTableRequest= new DeleteTableRequest().withTableName(tableName);
				try{
			        DeleteTableResult result = client.deleteTable(deleteTableRequest);
	//		        TableUtils.deleteTableIfExists(dynamodb, deleteTableRequest);
			        Table table = dynamo.getTable(tableName);
			        table.waitForDelete();
			        System.out.println("Table Status.."+result.getTableDescription().getTableStatus());
			        if(TableStatus.DELETING.equals(result.getTableDescription().getTableStatus())){
			        	try{
			        	if(createTableUtil().equals(tableName)){
							System.out.println("table doesnnotexist");
							moveToDynamoDB();
							}
						}catch(Exception e){
							write("After Creating table while moving to dynamodb0",e.getLocalizedMessage());
							e.printStackTrace();
						}
			        }
		        }catch(ResourceInUseException e){
		        	write("while table Deletion exception happend."+e.getErrorMessage());		        	
		        	e.printStackTrace();
		        }
				}else {
					try{
						System.out.println("table doesn't exist..Started Creating Table..");
						if(createTableUtil().equals(tableName)){
							System.out.println("table doesnnotexist");
							moveToDynamoDB();
							}
						}catch(Exception e){
							write("After Creating table while moving to dynamodb0",e.getLocalizedMessage());
							e.printStackTrace();
						}
				}
			}catch(NullPointerException np){
				write("While Searching for  Table Name");
				np.printStackTrace();
			}
		}catch(Exception e){
			write("while Deleting table before insert",e.getMessage());
			e.printStackTrace();
		}
	}
		public static String createTableUtil()throws IOException{
			AWSCredentials creds = new BasicAWSCredentials(ACCESS_KEY,SECRET_KEY);
			AmazonDynamoDB dynamodb = AmazonDynamoDBClientBuilder.standard().build();
        	DynamoDB dynamoDB = new DynamoDB(dynamodb);
        	ClientConfiguration clientCfg = new ClientConfiguration();
			clientCfg.setProtocol(Protocol.HTTP);
			clientCfg.setProxyPort(443);
        	String table_Name =null;
			List<AttributeDefinition> attributeDefinitions= new ArrayList<AttributeDefinition>();
	        	attributeDefinitions.add(new AttributeDefinition().withAttributeName("storeNumber").withAttributeType("N"));
	        	attributeDefinitions.add(new AttributeDefinition().withAttributeName("siteName").withAttributeType("S"));
	        	attributeDefinitions.add(new AttributeDefinition().withAttributeName("storeType").withAttributeType("S"));
        	 Adding Hash and Range Key 
        	List<KeySchemaElement> keySchema = new ArrayList<KeySchemaElement>();
	        	keySchema.add(new KeySchemaElement().withAttributeName("storeNumber").withKeyType(KeyType.HASH));
	        	keySchema.add(new KeySchemaElement().withAttributeName("siteName").withKeyType(KeyType.RANGE));
	        	
	        	// Initial provisioned throughput settings for the indexes
	            ProvisionedThroughput ptIndex = new ProvisionedThroughput().withReadCapacityUnits(50L)
	                .withWriteCapacityUnits(500L);
	            
        	   // storesHoursBySiteName-index
            GlobalSecondaryIndex gsi1 = new GlobalSecondaryIndex().withIndexName("storesHoursBySiteName-index")
                .withProvisionedThroughput(ptIndex)
                .withKeySchema(new KeySchemaElement().withAttributeName("siteName").withKeyType(KeyType.HASH)) // Partition
                .withProjection(new Projection().withProjectionType("ALL"));
//            storeHoursByStoreType-index
            GlobalSecondaryIndex gsi2 = new GlobalSecondaryIndex().withIndexName("storeHoursByStoreType-index")
                    .withProvisionedThroughput(ptIndex)
                    .withKeySchema(new KeySchemaElement().withAttributeName("storeType").withKeyType(KeyType.HASH)) // Partition
                    .withProjection(new Projection().withProjectionType("ALL"));
            
            List<GlobalSecondaryIndex> list =new ArrayList<GlobalSecondaryIndex>();
            	list.add(gsi1);list.add(gsi2);
            	
            	
        	CreateTableRequest createTableRequest = new CreateTableRequest()
    	        .withTableName(tableName)
    	        .withKeySchema(keySchema)
    	        .withAttributeDefinitions(attributeDefinitions)
    	        .withGlobalSecondaryIndexes(list)
    	        .withProvisionedThroughput(ptIndex);

				try {
					AmazonDynamoDBClient client = new AmazonDynamoDBClient(creds);
					CreateTableResult createdTableDescription = client.createTable(createTableRequest); 
					table_Name =createdTableDescription.getTableDescription().getTableName();
//					System.out.println("Status Checking..."+table.waitForActive().equals(TableStatus.ACTIVE));
					waitForTableAvailable(tableName);
					 if (describeTable(tableName) != null) { 
			             System.out.println("Table Exist"); 
			            } 
			        } catch (ResourceInUseException rie) { 
			  } catch (AmazonServiceException ase) { 
				  ase.printStackTrace();
			  } catch (AmazonClientException ace) {
				  ace.printStackTrace();
			  } 
			return table_Name;
		} 
		public static void waitForTableAvailable(String tableName) { 
			AWSCredentials creds = new BasicAWSCredentials(ACCESS_KEY,SECRET_KEY);
			AmazonDynamoDBClient client = new AmazonDynamoDBClient(creds);
			ClientConfiguration clientCfg = new ClientConfiguration();
			clientCfg.setProtocol(Protocol.HTTP);
			clientCfg.setProxyPort(443);
	        long startTime = System.currentTimeMillis(); 
	        long endTime = startTime + (10 * 60 * 1000); 
	        while (System.currentTimeMillis() < endTime) { 
	            DescribeTableRequest describeTableRequest = new DescribeTableRequest().withTableName(tableName); 
	            TableDescription tableDescription = client.describeTable(describeTableRequest).getTable(); 
	            // Display current status of table 
	            String tableStatus = tableDescription.getTableStatus(); 
	            if (tableStatus.equals(TableStatus.ACTIVE.toString())) { 
	                return; 
	            } 
	            try { 
	                Thread.sleep(1000 * 20); 
	            } catch (Exception ex) { 
	            } 
	        } 
	        throw new RuntimeException("Table " + tableName + " never went active"); 
	    } 

		public static void moveToDynamoDB() throws IOException {
			System.out.println("AccessKEy "+ACCESS_KEY);
			System.out.println("SecretKey "+SECRET_KEY);
			
			AWSCredentials creds = new BasicAWSCredentials(ACCESS_KEY,SECRET_KEY);
			AmazonDynamoDBClient client = new AmazonDynamoDBClient(creds);
	
	        DynamoDB dynamoDB = new DynamoDB(client);
	
	        Table table = dynamoDB.getTable("StoreHours1");
	
	        JsonParser parser = new JsonFactory().createParser(new File("storeHours.json"));
	
	        ObjectMapper objectMapper = new ObjectMapper();
	        try{
		        JsonNode rootNode = objectMapper.readTree(parser);
		        Iterator<JsonNode> iter = rootNode.iterator();
	        ObjectNode currentNode;
	        while (iter.hasNext()) {
	            currentNode = (ObjectNode) iter.next();
	            long storeNumber = currentNode.path("storeNumber").asLong();
	            String siteName  = currentNode.path("siteName").asText();
	            String storeType  = currentNode.path("storeType").asText();
	            try {
	            	System.out.println(storeNumber+"-"+siteName);
	            	if(currentNode.iterator().next().hasNonNull(currentNode.path("hours").toString())&&currentNode.iterator().next().hasNonNull(currentNode.path("holidays").toString())
	            			&& currentNode.iterator().next().hasNonNull(String.valueOf(Arrays.asList(currentNode.path("holidayHours"))))){
	                table.putItem(new Item().withPrimaryKey("storeNumber", storeNumber,"siteName", siteName)
	                		.withString("storeType", storeType)
	                		.withJSON("hours",currentNode.path("hours").toString())
	                		.withJSON("holidayHours",String.valueOf(Arrays.asList(currentNode.path("holidayHours"))))
	                		.withJSON("holidays",currentNode.path("holidays").toString()));
	            }
	            }
	            catch (Exception e) {
	            	e.printStackTrace();
	                System.err.println("Unable to add: " + storeNumber + " " + siteName);
	                System.err.println(e.getMessage());
	            }
	        }
	        }catch(JsonParseException ex){
	        	write("Json Parse Exception "+ex.getMessage());
	        	System.out.println("Json Parse Exception ");
	        }catch(JsonMappingException ex1){
	        	write("Json Mapping Exception "+ex1.getMessage());
	        	System.out.println("Json Mapping Exception");
	        }
	        parser.close();
	    }
		
		private static TableDescription describeTable(String tableName) { 
	        try { 
	        	AWSCredentials creds = new BasicAWSCredentials(ACCESS_KEY,SECRET_KEY);
	        	AmazonDynamoDBClient client = new AmazonDynamoDBClient(creds);
	        	ClientConfiguration clientCfg = new ClientConfiguration();
				clientCfg.setProtocol(Protocol.HTTP);
				clientCfg.setProxyPort(443);
	            DescribeTableRequest describeTableRequest = new DescribeTableRequest().withTableName(tableName); 
	            TableDescription tableDescription = client.describeTable(describeTableRequest).getTable(); 
	            if (tableDescription != null) { 
	            } 
	            return tableDescription; 
	        } catch (ResourceNotFoundException rnfe) { 
	        } 
	        return null; 
	    } */
}
