package samples;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.glassfish.jersey.apache.connector.ApacheClientProperties;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.json.JSONObject;
import com.fasterxml.jackson.core.type.TypeReference;

import com.fasterxml.jackson.databind.ObjectMapper;

public class MaintenanceHistoryStoreTest {

	private static Client securedClient = null;
		
	public static void main(String[] args) throws Exception {
		//getStore();
		
		Map<String, String> queryParams = new HashMap<String, String>();
		queryParams.put("storeNumber", "011304");
		WebTarget target = MaintenanceHistoryStoreTest.getSecuredWebTarget("http://dev01-api-aem.bsro.com/ws2/store/info", queryParams);
		
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type","application/json");
		headers.put("appName","FCAC");
		headers.put("tokenId","07b51830-4d43-4b63-ac6b-bc2ee5738a3f");
		
		Builder builder = target.request(MediaType.APPLICATION_JSON);
		//builder.property(ClientProperties.READ_TIMEOUT, 5000);
		addHeaders(builder, headers);
		System.out.println(1);
		Response response = builder.get(Response.class);
		int status = response.getStatus();
		String entity = response.readEntity(String.class);
		System.out.println("Status : " + status);
		System.out.println("Response : " + entity);
		
		Map<String, Object> coreSiteMap = new HashMap<String, Object>();
		ObjectMapper MAPPER = new ObjectMapper();
		coreSiteMap = MAPPER.readValue(entity, new TypeReference<Map<String, Object>>() {});
		
		System.out.println(coreSiteMap.get("payload"));
	}

	public static void addHeaders(Builder builder, Map<String, String> headers) {
		if (null != headers && headers.size() > 0) {
			for (Map.Entry<String, String> entry : headers.entrySet()) {
				String headerKey = entry.getKey();
				String headeValue = entry.getValue();
				builder.header(headerKey, headeValue);
			}
		}
	}
	
	public static void getStore() {
		
		String storeId = "011304"; 
		HttpClient httpClient = null;
		HttpPost httpPost = null;
		ObjectMapper objectMapper = new ObjectMapper();
		
		String queryDeli = "?";
		String nameValueDeli = "&";
		String nameValuePairDeli = "=";
		
		String url = "http://dev01-api-aem.bsro.com/ws2/store/info";
		
		try {
			
			httpPost = new HttpPost(url);
			httpPost.setHeader("Content-Type", "application/json");
			httpPost.addHeader("tokenId", "1f04ad80-b947-fe80-32bf4a78a69d54acb");
		    
			final HttpParams httpParams = new BasicHttpParams();
			httpClient = new DefaultHttpClient(httpParams);
			
			StringBuilder paramBuilder = new StringBuilder(); 
			paramBuilder.append(queryDeli).append("storeNumber").append(nameValuePairDeli).append(storeId);
			
			String finalUrl = "http://dev01-api-aem.bsro.com/ws2/store/info?storeNumber=11940"; //httpPost.getURI().toString() + paramBuilder.toString();
			System.out.println("finalUrl : " + finalUrl);
			
			try {
				httpPost.setURI(new URI(finalUrl));
			} catch (URISyntaxException e1) {
				System.err.println("Malformed URL while trying to build ");
			}
			
			org.apache.http.HttpResponse response = httpClient.execute(httpPost);
			System.out.println(response.getStatusLine());
			HttpEntity responseEntity = response.getEntity();
			
			try {
				if(responseEntity != null) {
					System.out.println("Entity not null");
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
	
	private static final synchronized void buildClient() throws Exception {
		if (null == securedClient) {
			ClientConfig clientConfig = new ClientConfig();
			// clientConfig.property(ClientProperties.READ_TIMEOUT, 30000);
			clientConfig.property(ClientProperties.CONNECT_TIMEOUT, 2500);
			PoolingHttpClientConnectionManager poolingClientConnectionManager = new PoolingHttpClientConnectionManager();
			poolingClientConnectionManager.setMaxTotal(200);
			poolingClientConnectionManager.setDefaultMaxPerRoute(200);
			clientConfig.property(ApacheClientProperties.CONNECTION_MANAGER, poolingClientConnectionManager);
			SSLContext sslContext = SSLContext.getInstance("SSL");
			sslContext.init(null, getCerts(), new SecureRandom());
//			securedClient = ClientBuilder.newBuilder().sslContext(sslContext).hostnameVerifier(getHostNameVerifier())
//					.withConfig(clientConfig).build();
			securedClient = ClientBuilder.newBuilder().sslContext(sslContext).withConfig(clientConfig).build();
		} else {
			System.out.println(" CoreSiteClientBuilderUtil is ALREADY BUILT ");
		}
	}

	private static TrustManager[] getCerts() {
		TrustManager[] certs = new TrustManager[] { new X509TrustManager() {
			@Override
			public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType)
					throws java.security.cert.CertificateException {
			}

			@Override
			public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType)
					throws java.security.cert.CertificateException {
			}

			@Override
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		} };
		return certs;
	}

	/**
	 * @return the securedClient
	 */
	private static Client getClient() {
		return securedClient;
	}

	private static HostnameVerifier getHostNameVerifier() {
		HostnameVerifier hostnameVerifier = new HostnameVerifier() {
			@Override
			public boolean verify(String hostname, SSLSession session) {
				return true;
			}
		};
		return hostnameVerifier;
	}

	/**
	 * @return the instance
	 * @throws Exception
	 */
	private static Client getSecuredClientInstance() throws Exception {
		if (securedClient == null) {
			buildClient();
		}
		return getClient();
	}

	public static synchronized WebTarget getSecuredWebTarget(String url, Map<String, String> queryParams)
			throws Exception {
		Client client = getSecuredClientInstance();
		UriBuilder fromUri = UriBuilder.fromUri(url);
		if ((queryParams != null) && !queryParams.isEmpty()) {
			addQueryParameters(fromUri, queryParams);
		}
		return client.target(fromUri);
	}
	
	public static void addQueryParameters(UriBuilder uriBuilder, Map<String, String> queryParams) {
		if (null != queryParams && queryParams.size() > 0) {
			System.out.print(" Adding the query parameters {} to the URI {} : " +  queryParams + " _ " + uriBuilder);
			for (Map.Entry<String, String> entry : queryParams.entrySet()) {
				String queryKey = entry.getKey();
				String queryValue = entry.getValue();
				uriBuilder.queryParam(queryKey, queryValue);
			}
		}
	}
}
