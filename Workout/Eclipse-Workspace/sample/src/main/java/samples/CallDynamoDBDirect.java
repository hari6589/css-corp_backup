package samples;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import samples.pojo.GeoTrigger;
import samples.pojo.SequenceNumber;
import samples.pojo.User;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig.SaveBehavior;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

public class CallDynamoDBDirect {

	public static void main(String[] args) {
		try {
			AWSCredentials awsCredentials = new BasicAWSCredentials("AKIAIO6KVA4ID6VFURAA", "aaSNiXJVyTafsNBJfuLXp0+/KeiPbUUeel54XcKF");
	    	AmazonDynamoDBClient amazonDynamoDBClient = new AmazonDynamoDBClient(awsCredentials);
	    	DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(amazonDynamoDBClient);
	    	
	    	User user = new User(); 
	    	user.setId(1);
	    	user.setName("Aravind");
	    	user.setCreatedDateTime(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date()));
	    	
			dynamoDBMapper.save(user, new DynamoDBMapperConfig(DynamoDBMapperConfig.SaveBehavior.CLOBBER));
			
	    	/*
	    	SequenceNumber keys = dynamoDBMapper.load(SequenceNumber.class, 1,new DynamoDBMapperConfig(DynamoDBMapperConfig.ConsistentReads.CONSISTENT));
	    	System.out.println("Got data!");
	    	Long signupId = keys.getSignupId();
	    	System.out.println("Signup Id : " + signupId);
	    	keys.setSignupId(signupId+1);
	    	
	    	DynamoDBMapperConfig dynamoDBMapperConfig = new DynamoDBMapperConfig(SaveBehavior.UPDATE);
	    	dynamoDBMapper.save(keys, dynamoDBMapperConfig);
	    	*/
	    	
	    	/*
	    	GeoTrigger geoTrigger = dynamoDBMapper.load(GeoTrigger.class, 1,new DynamoDBMapperConfig(DynamoDBMapperConfig.ConsistentReads.CONSISTENT));
	    	geoTrigger.setCreatedDate("triggered");
	    	
	    	dynamoDBMapper.save(geoTrigger, new DynamoDBMapperConfig(SaveBehavior.UPDATE));
	    	*/
	    	
	    	/*
	    	GeoTrigger geoTrigger = dynamoDBMapper.load(GeoTrigger.class, 1,new DynamoDBMapperConfig(DynamoDBMapperConfig.ConsistentReads.CONSISTENT));
	    	if(geoTrigger.getActiveFlag() == 1) {
		    	for(int i=0; i < 80; i++) {
		    		System.out.println(i);
		    		if(i != 0 && i%50 == 0) {
		    			geoTrigger.setCreatedDate(String.valueOf(i));
		    			dynamoDBMapper.save(geoTrigger, new DynamoDBMapperConfig(SaveBehavior.UPDATE));
		    			break;
		    		}
		    	}
	    	}*/
	    	
	    	/*
	    	//2017-06-20 20:59:59
	    	String from = "2017-06-21 00";
	    	String to = "2017-06-21 23";
			
	    	String startDateStr = from+":00:00";
	    	String endDateStr = to+":59:59";
	    	
	    	System.out.println(startDateStr + " & " + endDateStr);
	    	
		    Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
		    eav.put(":startDateTime", new AttributeValue().withS(startDateStr));
		    eav.put(":endDateTime", new AttributeValue().withS(endDateStr));

	    	DynamoDBScanExpression dynamoDBScanExpression = new DynamoDBScanExpression()
	    		.withFilterExpression("createdDateTime between :startDateTime and :endDateTime")
		    	.withExpressionAttributeValues(eav);
	    	
	    	ArrayList<User> user = new ArrayList<User>(dynamoDBMapper.scan(User.class, dynamoDBScanExpression));
	    	
			for(int i=0; i < user.size(); i++) {
				System.out.println(user.get(i).getId() + " _ " + user.get(i).getName() + " _ " + user.get(i).getCreatedDateTime());
			}
			*/
        }  catch (Exception e) {
			System.out.println("Exception Occured while saving Appointment : " + e.getMessage());
		}
	}
}
