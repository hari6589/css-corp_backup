package geo;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.json.JSONArray;
import org.json.JSONObject;

public class GeoTest {
	
	private final String NULL_TEXT = "null";
	
	private static final String BINGMAPS_GEOLOCATION_API_RESPONSE_CODE_OK 				= "200";
	private static final String BINGMAPS_GEOLOCATION_API_RESPONSE_CODE_BAD 				= "400";
	private static final String BINGMAPS_GEOLOCATION_API_RESPONSE_CODE_NOTFOUND 		= "404";
	private static final String BINGMAPS_GEOLOCATION_API_RESPONSE_CODE_UNAUTHORIZED 	= "401";
	private static final String BINGMAPS_GEOLOCATION_API_RESPONSE_AUTH_VALID 			= "ValidCredentials";
	private static final String BINGMAPS_GEOLOCATION_API_RESPONSE_AUTH_INVALID 			= "InvalidCredentials";
	private static final String BINGMAPS_GEOLOCATION_API_RESPONSE_AUTH_NONE 			= "NoCredentials";
	
	private static String bingmapsGeoLocationApiUrl = null;
	private static String bingmapsGeoLocationOutputType = null;
	private static String bingmapsApiKey=null;
	private static String bingmapsGeoLocationApiVersion = null;
	
	GeoTest() {
		bingmapsGeoLocationApiUrl = "http://dev.virtualearth.net/REST/v1/Locations";
		bingmapsGeoLocationOutputType = "json";
		bingmapsApiKey = "Av_7iBRODmYJT5dawDxNVbiC1pg48etY4tP9BfPLyp39c_JzC-sAx6hJ5vt4OwjV";
		bingmapsGeoLocationApiVersion = "1";
	}
	
	public static void main(String[] args) {
		GeoTest geoTest = new GeoTest();
		try {
			geoTest.getBingData("CA");
		} catch (Exception e) {
			System.out.println("Exception : " + e.getMessage());
		}
	}
	
	public Float[] getBingData(String key) throws Exception {
		String country="";
		String statusCode="";
    	Float[] longitudeAndLatitude = new Float[2];
    	key = java.net.URLEncoder.encode(key.trim(), "UTF-8");
    	
    	StringBuilder urlBuilder = new StringBuilder();
    	urlBuilder.append(bingmapsGeoLocationApiUrl+"?q=");
    	urlBuilder.append(key);
    	urlBuilder.append("&o="+java.net.URLEncoder.encode(bingmapsGeoLocationOutputType, "UTF-8"));
    	urlBuilder.append("&key="+bingmapsApiKey);
    	
    	String url = urlBuilder.toString();

    	System.out.println("URL : " + urlBuilder.toString());
    	
    	String response = null;

    	try {
    		System.out.println("GeocodeOperator(Bing Rest Api v"+bingmapsGeoLocationApiVersion+":"+bingmapsGeoLocationApiUrl+")");
    		response = grabPage(url);
    		JSONObject json = new JSONObject(response);
    		JSONArray resourceSets = null;
    		JSONObject resource = null;
    		JSONArray geocodePoints = null;
    		String lat = null;
    		String lng = null;

    		statusCode = String.valueOf(json.getInt("statusCode"));

    		String authResultsCode = json.getString("authenticationResultCode");
    		
    		if(BINGMAPS_GEOLOCATION_API_RESPONSE_AUTH_VALID.equalsIgnoreCase(authResultsCode)){
    			
    			if(BINGMAPS_GEOLOCATION_API_RESPONSE_CODE_OK.equalsIgnoreCase(statusCode)) {
    				
    				resourceSets = json.getJSONArray("resourceSets");
    				if (resourceSets.length() > 0 && resourceSets.getJSONObject(0).getJSONArray("resources").length() > 0) {

    					resource = resourceSets.getJSONObject(0).getJSONArray("resources").getJSONObject(0);
    					if(resource != null && resource.getJSONArray("geocodePoints").length() > 0){
    						geocodePoints = resource.getJSONArray("geocodePoints").getJSONObject(0).getJSONArray("coordinates");
    						lat = String.valueOf(geocodePoints.get(0));
    						lng = String.valueOf(geocodePoints.get(1));
    						longitudeAndLatitude[0] = new Float(lng);
    						longitudeAndLatitude[1] = new Float(lat);
    					}

    					JSONObject addressJSON = resource.getJSONObject("address"); 
    					country = addressJSON.getString("countryRegion");

    					if("United States".equals(country) && longitudeAndLatitude[1] != null && longitudeAndLatitude[0] != null) {
    						System.out.println("Lat Lng : " + longitudeAndLatitude[0] + " _ " + longitudeAndLatitude[1]);
    						return longitudeAndLatitude;
    					} else {
    							String output = generateBingLocationGeoDataLogOutput(null, "Unable to find a US location, or missing location data", url, response);	
    							System.out.println(output);
    					}
    				}
    			} else {					
    				if (BINGMAPS_GEOLOCATION_API_RESPONSE_CODE_UNAUTHORIZED.equalsIgnoreCase(statusCode)) {
    					// status code - 401
    					String output = generateBingLocationGeoDataLogOutput(null, "Uuathorized Url", url, response);					
    					System.out.println(output);
    				} else if(BINGMAPS_GEOLOCATION_API_RESPONSE_CODE_BAD.equalsIgnoreCase(statusCode)) {
    					// status code - 404
    					String output = generateBingLocationGeoDataLogOutput(null, "Bad Request - check again", url, response);					
    					System.out.println(output);
    				} else if(BINGMAPS_GEOLOCATION_API_RESPONSE_CODE_NOTFOUND.equalsIgnoreCase(statusCode)) {
    					// status code - 404
    					String output = generateBingLocationGeoDataLogOutput(null, "No Results Found", url, response);					
    					System.out.println(output);				
    				}
    			}	
    		} else {
    			if(BINGMAPS_GEOLOCATION_API_RESPONSE_AUTH_NONE.equalsIgnoreCase(authResultsCode)){
    				//missing credentials
    				String output = generateBingLocationGeoDataLogOutput(null, "No Bing maps key entered", url, response);					
    				System.out.println(output);	
    			} else if(BINGMAPS_GEOLOCATION_API_RESPONSE_AUTH_INVALID.equalsIgnoreCase(authResultsCode)){
    				// invalid credentials
    				String output = generateBingLocationGeoDataLogOutput(null, "Invalid Bing maps key entered", url, response);					
    				System.out.println(output);	
    			} else {
    				//other issues with credentials
    				String output = generateBingLocationGeoDataLogOutput(null, "Issue with Bing maps credentials", url, response);					
    				System.out.println(output);	
    			}
    		}
    	} catch (Throwable throwable) {
    		String output = generateBingLocationGeoDataLogOutput(throwable, "Exception", url, response);					
    		System.out.println(output);
    	}
    	System.out.println("Lat Lng : " + longitudeAndLatitude[0] + " _ " + longitudeAndLatitude[1]);
		return longitudeAndLatitude;
	}
	
	public static String grabPage(String url) {
		if (url == null)
			return "";
		java.net.URL url1 = null;
		java.net.URLConnection conn = null;
		java.io.OutputStreamWriter osw = null;
		BufferedReader dis = null;
		try {
			url1 = new java.net.URL(url);
			conn = url1.openConnection();
			
			StringBuffer buf = new StringBuffer();
			String inputLine;
			dis = new java.io.BufferedReader(new java.io.InputStreamReader(
					conn.getInputStream()));
			while ((inputLine = dis.readLine()) != null) {
				buf.append(inputLine + "\n");
				// System.out.println(inputLine);
			}
			if (osw != null) {
				osw.close();
			}
			dis.close();
			return buf.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (osw != null)
					osw.close();
				if (dis != null)
					dis.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "";
	}
	
	private String generateBingLocationGeoDataLogOutput(Throwable throwable, String subject, String url, String response) {
		StringBuilder sb = new StringBuilder();
		sb.append("\n=========================== ");
		if (subject != null) {
			sb.append(subject).append(": ");
		}
		sb.append("GeocodeOperator(v");
		sb.append(bingmapsGeoLocationApiVersion);
		sb.append(").getGoogleGeoData:");
		sb.append("\n\trequestUrl:");
		sb.append(url == null ? NULL_TEXT : url);
		sb.append("\n");
		sb.append("\nresponse:\n");
		sb.append(response);
		sb.append("\n");
		if (throwable != null) {
			sb.append(getStackTraceAsString(throwable));
		}
		return sb.toString();
	}
	
	private String getStackTraceAsString(Throwable throwable) {
	    StringWriter stackTrace = new StringWriter();
	    throwable.printStackTrace(new PrintWriter(stackTrace));
	    return stackTrace.toString();
	}
	
}
