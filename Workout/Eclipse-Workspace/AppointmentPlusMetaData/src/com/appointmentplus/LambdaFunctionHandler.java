package com.appointmentplus;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class LambdaFunctionHandler implements RequestHandler<Object, Object> {

    @Override
    public Object handleRequest(Object input, Context context) {
    	
    	AWSCredentials credential = new BasicAWSCredentials("AKIAJN6CNV7NFM3MJM3Q", "cTls28RIJdrw3gBAsm8u/FUR9Jo6VA7iFcH+u1ck");
    	AmazonDynamoDBClient amazonDynamoDBClient = new AmazonDynamoDBClient(credential);
    	amazonDynamoDBClient.configureRegion(Regions.US_WEST_2);
    	DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(amazonDynamoDBClient);
    	
		HttpClient httpClient = null;
		HttpPost httpPost = null;
		JSONObject jsonData = new JSONObject();
		
		String responseBody="";
		String storeNumber = "11940";
		String serviceDesc = "Tire%20Replacement";
		
		String siteId = "appointplus846/776";
		String devApiKey = "b18371f34b0931963f62add253820169cfa05cf7";
		String liveApiKey = "123ba713955f286356423d59d03618db7ceecfc7";
		String responseType = "JSON";
		
		String queryDeli = "?";
		String nameValueDeli = "&";
		String nameValuePairDeli = "=";
		
		
		//String url = "https://sandbox-ws.appointment-plus.com/Bridgestone/Rules?store_number=011940&services=Tire%20Replacement&site_id=appointplus846/776&api_key=b18371f34b0931963f62add253820169cfa05cf7&response_type=JSON";
		String url = "https://sandbox-ws.appointment-plus.com/Bridgestone/Rules";
		
		try {
			
			httpPost = new HttpPost(url);
			
			final HttpParams httpParams = new BasicHttpParams();
			httpClient = new DefaultHttpClient(httpParams);
			
			String paddedStoreNumber = ("000000" + storeNumber);
			StringBuilder paramBuilder = new StringBuilder(); 
			paramBuilder.append(queryDeli).append("store_number").append(nameValuePairDeli).append(paddedStoreNumber.substring(paddedStoreNumber.length()-6));
			paramBuilder.append(nameValueDeli).append("services").append(nameValuePairDeli).append(serviceDesc);
			paramBuilder.append(nameValueDeli).append("site_id").append(nameValuePairDeli).append(siteId);
			paramBuilder.append(nameValueDeli).append("api_key").append(nameValuePairDeli).append(URLEncoder.encode(devApiKey,"UTF-8"));
			paramBuilder.append(nameValueDeli).append("response_type").append(nameValuePairDeli).append(URLEncoder.encode(responseType,"UTF-8"));
			
			String finalUrl = httpPost.getURI().toString() + paramBuilder.toString();
			System.out.println("finalUrl : " + finalUrl);
			
			try {
				httpPost.setURI(new URI(finalUrl));
			} catch (URISyntaxException e1) {
				System.err.println("Malformed URL while trying to build ");
			}
			
			HttpResponse response;
			response = httpClient.execute(httpPost);
			HttpEntity responseEntity = response.getEntity();
			
			try {
				if(responseEntity != null) {
				    responseBody = EntityUtils.toString(responseEntity);
				    
					JSONObject json = new JSONObject(responseBody);
					
					if(json.getString("data") != null) {
						responseBody = (String) json.get("data");
						jsonData = new JSONObject(responseBody);
					}
					
					System.out.println("Response : " + responseBody);
					
					httpClient.getConnectionManager().shutdown();
				}
			} catch (Exception e) {
				System.err.println("JSON Parsing Exception in parsing response from Subscribe API : " + e);
				e.printStackTrace();
			}
		} catch (ClientProtocolException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return responseBody;
    }

}
