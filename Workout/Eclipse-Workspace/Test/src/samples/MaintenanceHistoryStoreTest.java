package samples;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;

public class MaintenanceHistoryStoreTest {

	public static void main(String[] args) {
		getStore();
	}

	public static void getStore() {
		
		String storeId = "011304"; 
		HttpClient httpClient = null;
		HttpPost httpPost = null;
		ObjectMapper objectMapper = new ObjectMapper();
		
		String queryDeli = "?";
		String nameValueDeli = "&";
		String nameValuePairDeli = "=";
		
		String url = "https://qa-css-api-aem.bsro.com/ws2/store/info";
		
		try {
			
			httpPost = new HttpPost(url);
			httpPost.setHeader("Content-Type", "application/json");
			httpPost.addHeader("tokenId", "1f04ad80-b947-fe80-32bf4a78a69d54acb");
		    
			final HttpParams httpParams = new BasicHttpParams();
			httpClient = new DefaultHttpClient(httpParams);
			
			StringBuilder paramBuilder = new StringBuilder(); 
			paramBuilder.append(queryDeli).append("storeNumber").append(nameValuePairDeli).append(storeId);
			
			String finalUrl = httpPost.getURI().toString() + paramBuilder.toString();
			System.out.println("finalUrl : " + finalUrl);
			
			try {
				httpPost.setURI(new URI(finalUrl));
			} catch (URISyntaxException e1) {
				System.err.println("Malformed URL while trying to build ");
			}
			
			org.apache.http.HttpResponse response = httpClient.execute(httpPost);
			HttpEntity responseEntity = response.getEntity();
			
			try {
				if(responseEntity != null) {
				    String responseBody = EntityUtils.toString(responseEntity);
				    System.out.println(responseBody);
				    JSONObject json = new JSONObject(responseBody);
					if(json.getString("data") != null) {
						responseBody = json.get("data").toString();
					}
					System.out.println("Response : " + responseBody);
				    //metadata = objectMapper.readValue(responseBody, Metadata.class);
				    //String str = objectMapper.writeValueAsString(metadata);
				    //System.out.println("str : " + str);
				    //System.out.println("mdatamdata : " + metadata.toString());
					System.out.println("jsonData : " + json.get("data").toString());
					
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
