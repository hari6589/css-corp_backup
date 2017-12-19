package com.appointmentplus;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
//
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.appointmentplus.model.Time;

public class BSROAppointmentPlusTime implements RequestHandler<Object, Object>{

	public Object handleRequest(Object arg0, Context arg1) {
		
		HttpClient httpClient = null;
		HttpPost httpPost = null;
		ObjectMapper objectMapper = new ObjectMapper();
		Time time = null;
		String res = "";
		JSONObject json = new JSONObject();
		HttpEntity responseEntity = null;
		
		String responseBody="";
		Integer numberOfDays = 30;
		String locationId = "1581";
		String selectedDate = "20170207";
		String primaryServiceIds = "2745"; // Required
		String secondaryServiceIds = "2746,2747"; // Optional
		String employeeId = "12709";
		String duplicateFlag = "no";
				
		String siteId = "appointplus846/776";
		String devApiKey = "b18371f34b0931963f62add253820169cfa05cf7";
		String liveApiKey = "123ba713955f286356423d59d03618db7ceecfc7";
		String responseType = "JSON";
		
		String queryDeli = "?";
		String nameValueDeli = "&";
		String nameValuePairDeli = "=";
		
		
		//String url = "https://sandbox-ws.appointment-plus.com/Bridgestone/Rules?store_number=011940&services=Tire%20Replacement&site_id=appointplus846/776&api_key=b18371f34b0931963f62add253820169cfa05cf7&response_type=JSON";
		String url = "https://sandbox-ws.appointment-plus.com/Bridgestone/GetOpenSlots";
		
		try {
			
			httpPost = new HttpPost(url);
			final HttpParams httpParams = new BasicHttpParams();
			httpClient = new DefaultHttpClient(httpParams);
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
			Date apptDate = null;
			try {
				apptDate = dateFormat.parse(selectedDate);
			} catch (ParseException e) {
				return "Selected Date parse exception";
			}
			
			//start from midnight, get slots for entire day if selected day is future
			Integer startTimeInMins = 0;
			
			Calendar today = Calendar.getInstance();
			Calendar cal = Calendar.getInstance();
			cal.setTime(apptDate);
			if(cal.before(today)){
				SimpleDateFormat hmFormat = new SimpleDateFormat("HH:mm");
				String currentHourMin = hmFormat.format(today.getTime());
				String[] hourMinSplits = currentHourMin.split(":");
				startTimeInMins = new Integer(hourMinSplits[0]) * 60 + new Integer(hourMinSplits[1]);
			}
			
			StringBuilder paramBuilder = new StringBuilder(); 
			paramBuilder.append(queryDeli).append("num_days").append(nameValuePairDeli).append(String.valueOf(numberOfDays));
			paramBuilder.append(nameValueDeli).append("c_id").append(nameValuePairDeli).append(locationId);
			paramBuilder.append(nameValueDeli).append("employee_id").append(nameValuePairDeli).append(employeeId);
			paramBuilder.append(nameValueDeli).append("start_date").append(nameValuePairDeli).append(selectedDate);
			paramBuilder.append(nameValueDeli).append("start_time").append(nameValuePairDeli).append(String.valueOf(startTimeInMins));
			paramBuilder.append(nameValueDeli).append("service").append(nameValuePairDeli).append(primaryServiceIds); //Primary service
			paramBuilder.append(nameValueDeli).append("addons").append(nameValuePairDeli).append(secondaryServiceIds); //Additional services
			paramBuilder.append(nameValueDeli).append("show_duplicates").append(nameValuePairDeli).append(duplicateFlag);
						
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
			responseEntity = response.getEntity();
			
			try {
				if(responseEntity != null) {
				    responseBody = EntityUtils.toString(responseEntity);
				    json = new JSONObject(responseBody);
					if(json.getString("data") != null) {
						responseBody = json.get("data").toString();
					}
					Time myObjects[] = objectMapper.readValue(responseBody, Time[].class);
				    res = objectMapper.writeValueAsString(myObjects);
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
