package geo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

public class GeoDataSample {

	public static void main(String[] args) {
		try {
			AWSCredentials awsCredentials = new BasicAWSCredentials("AKIAIO6KVA4ID6VFURAA", "aaSNiXJVyTafsNBJfuLXp0+/KeiPbUUeel54XcKF");
	    	AmazonDynamoDBClient amazonDynamoDBClient = new AmazonDynamoDBClient(awsCredentials);
	    	DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(amazonDynamoDBClient);
	    	
	    	 Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
	         eav.put(":val1", new AttributeValue().withN("1"));
	         eav.put(":val2", new AttributeValue().withN("5"));

	         /*
	         DynamoDBQueryExpression<GeoDataTest> queryExpression = new DynamoDBQueryExpression<GeoDataTest>()
	        	.withConditionalOperator("age between :val1 and :val2").withExpressionAttributeValues(eav);
	        	//.withKeyConditionExpression("id = :val1").withExpressionAttributeValues(eav);
	         */
	         
	         DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
	        	.withFilterExpression("age BETWEEN :val1 AND :val2").withExpressionAttributeValues(eav);
	         
	         List<GeoDataTest> geoDataList = dynamoDBMapper.scan(GeoDataTest.class, scanExpression);
	    	
	         System.out.println(geoDataList.size());
	         
        }  catch (Exception e) {
			System.out.println("Exception in GeoDataSample : " + e.getMessage());
		}
	}

}
