package com.appointmentplus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.appointmentplus.model.AppointmentService;

public class BSROAppointmentServices implements RequestHandler<Object, Object>{

	public Object handleRequest(Object arg0, Context arg1) {
		AWSCredentials credential = new BasicAWSCredentials("AKIAJN6CNV7NFM3MJM3Q", "cTls28RIJdrw3gBAsm8u/FUR9Jo6VA7iFcH+u1ck");
    	AmazonDynamoDBClient amazonDynamoDBClient = new AmazonDynamoDBClient(credential);
    	//amazonDynamoDBClient.configureRegion(Regions.US_WEST_2);
    	amazonDynamoDBClient.setRegion(Region.getRegion(Regions.US_WEST_2));
    	DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(amazonDynamoDBClient);
    	
    	Map<String, AttributeValue> eav = new HashMap<String, AttributeValue> ();
        //eav.put(":val1", new AttributeValue().withN("2"));
    	//eav.put(":val1", new AttributeValue().withN("0"));
    	
    	DynamoDBScanExpression dynamoDBScanExpression = new DynamoDBScanExpression();
    	//.withFilterExpression("StoreNumber = :val1")
    	//.withFilterExpression("OnlineAppointmentFlag = :val1")
        //.withExpressionAttributeValues(eav);
    	
    	List<AppointmentService> scanResult = dynamoDBMapper.scan(AppointmentService.class, dynamoDBScanExpression);
    	
        return scanResult;
	}

}
