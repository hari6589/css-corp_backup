package com.test;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.AWSLambdaClient;
import com.amazonaws.services.lambda.model.InvokeRequest;

public class Invoke {
	
	private static final Log logger = LogFactory.getLog(Invoke.class);
    private static final String awsAccessKeyId = "AKIAJN6CNV7NFM3MJM3Q";
    private static final String awsSecretAccessKey = "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";
    private static final String regionName = "us-west-2";
    private static final String functionName = "HelloLambda";
    private static Region region;
    private static AWSCredentials credentials;
    private static AWSLambdaClient lambdaClient;
    
	public static void main(String[] args) {
		credentials = new BasicAWSCredentials(awsAccessKeyId, awsSecretAccessKey);

		lambdaClient = (credentials == null) ? new AWSLambdaClient() : new AWSLambdaClient(credentials);
		lambdaClient.configureRegion(Regions.US_WEST_2);
		region = Region.getRegion(Regions.fromName(regionName));
		lambdaClient.setRegion(region);
		
		try {
		    InvokeRequest invokeRequest = new InvokeRequest();
		    invokeRequest.setFunctionName(functionName);
		    invokeRequest.setPayload("\" AWS Lambda\"");
		    System.out.println(byteBufferToString(
		            lambdaClient.invoke(invokeRequest).getPayload(),
		            Charset.forName("UTF-8")));
		} catch (Exception e) {
		    logger.error(e.getMessage());
		    // System.out.println(e.getMessage());
		}
	}
	
	public static String byteBufferToString(ByteBuffer buffer, Charset charset) {
        byte[] bytes;
        if (buffer.hasArray()) {
            bytes = buffer.array();
        } else {
            bytes = new byte[buffer.remaining()];
            buffer.get(bytes);
        }
        return new String(bytes, charset);
    }

}
