package aws.dynamodb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

public class QueryByIndex {

	public static void main(String[] args) {
		QueryByIndex qbi = new QueryByIndex();
		//qbi.queryByIndex();
		qbi.scan1();
	}
	
	public void queryByIndex() {
		System.out.println("Query By Index : ");
		AWSCredentials awsCredentials = new BasicAWSCredentials("AKIAJNC7RBGEV3HFSAUQ", "+9ryX+JrqadbmZ3n6CgUpqFz3zwEX9VTad/3Nbd3");
		DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(new AmazonDynamoDBClient(awsCredentials));
		System.out.println("Test");
		Map<String, AttributeValue> deviceMsgEav = new HashMap<String, AttributeValue>();
		deviceMsgEav.put(":createdDate", new AttributeValue().withS("2017-08-18"));
		//deviceMsgEav.put(":webSite", new AttributeValue().withS("FCAC"));
        
        DynamoDBQueryExpression<Appointment> queryExpression = new DynamoDBQueryExpression<Appointment>()
           		.withIndexName("createdDate-webSite-index")
    		        .withConsistentRead(false)
    		        .withKeyConditionExpression("createdDate = :createdDate")
    		        //.withKeyConditionExpression("createdDate = :createdDate AND webSite = :webSite")
    		        .withExpressionAttributeValues(deviceMsgEav);
        
        List<Appointment> appointment = new ArrayList<Appointment>(dynamoDBMapper.query(Appointment.class, queryExpression));
        
        System.out.println("Size : " + appointment.size() + " _ " + appointment.get(0).getAppointmentId());
	}
	
	public void scan() {
		System.out.println("Scan : ");
		AWSCredentials awsCredentials = new BasicAWSCredentials("AKIAJNC7RBGEV3HFSAUQ", "+9ryX+JrqadbmZ3n6CgUpqFz3zwEX9VTad/3Nbd3");
		DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(new AmazonDynamoDBClient(awsCredentials));
		
		Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
	    eav.put(":statusFlag", new AttributeValue().withS("1"));

    	DynamoDBScanExpression dynamoDBScanExpression = new DynamoDBScanExpression()
    		.withFilterExpression("siteName = :statusFlag")
	    	.withExpressionAttributeValues(eav);
    	
    	List<MWSUser> user = new ArrayList<MWSUser>(dynamoDBMapper.scan(MWSUser.class, dynamoDBScanExpression));
    	
    	System.out.println("Size : " + user.size());
	}
	
	public void queryByIndexTest() {
		System.out.println("Query By Index : ");
		AWSCredentials awsCredentials = new BasicAWSCredentials("AKIAJNC7RBGEV3HFSAUQ", "+9ryX+JrqadbmZ3n6CgUpqFz3zwEX9VTad/3Nbd3");
		DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(new AmazonDynamoDBClient(awsCredentials));
		System.out.println("Test");
		Map<String, AttributeValue> deviceMsgEav = new HashMap<String, AttributeValue>();
		deviceMsgEav.put(":createdDate", new AttributeValue().withS("2017-08-18"));
		//deviceMsgEav.put(":webSite", new AttributeValue().withS("FCAC"));
        
        DynamoDBQueryExpression<Appointment> queryExpression = new DynamoDBQueryExpression<Appointment>()
           		.withIndexName("createdDate-webSite-index")
    		        .withConsistentRead(false)
    		        .withKeyConditionExpression("createdDate = :createdDate")
    		        //.withKeyConditionExpression("createdDate = :createdDate AND webSite = :webSite")
    		        .withExpressionAttributeValues(deviceMsgEav);
        
        List<Appointment> appointment = new ArrayList<Appointment>(dynamoDBMapper.query(Appointment.class, queryExpression));
        
        System.out.println("Size : " + appointment.size() + " _ " + appointment.get(0).getAppointmentId());
	}

	public void scan1() {
		System.out.println("Scan1 : ");
		//AWSCredentials awsCredentials = new BasicAWSCredentials("AKIAIO6KVA4ID6VFURAA", "aaSNiXJVyTafsNBJfuLXp0+/KeiPbUUeel54XcKF");
		AWSCredentials awsCredentials = new BasicAWSCredentials("AKIAJOMUDY3BRDXIIMEQ", "eR7n7aACSRjx3Hi+B+Mx3v4D5rZ24OgVkHZu+at1");
		DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(new AmazonDynamoDBClient(awsCredentials));
		
		Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
	    //eav.put(":emailAddress", new AttributeValue().withS("aravindhan.jayakumar@csscorp.com"));
	    //eav.put(":startDate", new AttributeValue().withS("2017-10-20 00:00:00"));
	    //eav.put(":endDate", new AttributeValue().withS("2017-11-03 00:00:00"));
		eav.put(":status", new AttributeValue().withS("L"));

    	DynamoDBScanExpression dynamoDBScanExpression = new DynamoDBScanExpression()
    		//.withFilterExpression("registerDate between :startDate AND :endDate")
    		.withFilterExpression("#s = :status")
	    	.withExpressionAttributeValues(eav);
    	
    	Map<String, String> expression = new HashMap<>();
        expression.put("#s", "status");
        dynamoDBScanExpression.withExpressionAttributeNames(expression);
 
    	List<MWSUser> mwsUserList = new ArrayList<MWSUser>(dynamoDBMapper.scan(MWSUser.class, dynamoDBScanExpression));
    	System.out.println("Size : " + mwsUserList.size());
    	
//    	for(int i=0; i < mwsUserList.size(); i++) {
//    		MWSUser user = mwsUserList.get(i);
//        	System.out.println(user.toString());
//        	user.setStatus("0");
//        	dynamoDBMapper.save(user, new DynamoDBMapperConfig(DynamoDBMapperConfig.SaveBehavior.CLOBBER));
//    	}
    	
	}
}
