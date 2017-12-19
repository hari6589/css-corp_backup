package com.dynamodb;

import java.util.Arrays;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;

public class Test {

	public static void main(String[] args) {
		
		AmazonDynamoDBClient client = new AmazonDynamoDBClient()
        .withEndpoint("http://localhost:8000");

	    DynamoDB dynamoDB = new DynamoDB(client);
	
	    String tableName = "Movies";
	
	    try {
	        System.out.println("Attempting to create table; please wait...");
	        Table table = dynamoDB.createTable(tableName,
	            Arrays.asList(
	                new KeySchemaElement("year", KeyType.HASH),  //Partition key
	                new KeySchemaElement("title", KeyType.RANGE)), //Sort key
	                Arrays.asList(
	                    new AttributeDefinition("year", ScalarAttributeType.N),
	                    new AttributeDefinition("title", ScalarAttributeType.S)), 
	                new ProvisionedThroughput(10L, 10L));
	        table.waitForActive();
	        System.out.println("Success.  Table status: " + table.getDescription().getTableStatus());
	
	    } catch (Exception e) {
	        System.err.println("Unable to create table: ");
	        System.err.println(e.getMessage());
	    }
	    
	}
}
