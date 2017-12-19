package com.soap;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

public class HTTPPostSOAP {

	public static void main(String[] args) {
		
		HttpClient httpClient = null;
		HttpPost httpPost = null;
		
		String responseBody="";
		
		String url = "http://www.webservicex.net/FedWire.asmx";
		
		try {
			
			httpPost = new HttpPost(url);
			
			final HttpParams httpParams = new BasicHttpParams();
			httpClient = new DefaultHttpClient(httpParams);
			
			String finalUrl = httpPost.getURI().toString();
			System.out.println("finalUrl : " + finalUrl);
			
			try {
				httpPost.setURI(new URI(finalUrl));
				//httpPost.setHeader("SOAPAction", "http://bfrco/EDWQuery");
				//httpPost.setHeader("Content-Type", "text/xml");
			} catch (URISyntaxException e1) {
				System.err.println("Malformed URL while trying to build ");
			}
			
			String xml = "<?xml version='1.0' encoding='utf-8'?><soap:Envelope xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' xmlns:xsd='http://www.w3.org/2001/XMLSchema' xmlns:soap='http://schemas.xmlsoap.org/soap/envelope/'><soap:Header><AuthHeader xmlns='http://bfrco/'><Password>wY6#1Mkq$K</Password></AuthHeader></soap:Header><soap:Body><EDWQuery xmlns='http://bfrco/'><XMLString>&lt;EDWRTQ AppId='MOBILE' ReqId='RTQ2010' RequestVersion='1' ResponseVersion='1' AreaCode='323' Exchange='804' Line='5647'&gt;&lt;/EDWRTQ&gt;</XMLString></EDWQuery></soap:Body></soap:Envelope>";
	        HttpEntity entity = new ByteArrayEntity(xml.getBytes("UTF-8"));
	        httpPost.setEntity(entity);
			
			HttpResponse response;
			response = httpClient.execute(httpPost);
			HttpEntity responseEntity = response.getEntity();
			
			try {
				if(responseEntity != null) {
				    responseBody = EntityUtils.toString(responseEntity);
				    
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
	}
}
