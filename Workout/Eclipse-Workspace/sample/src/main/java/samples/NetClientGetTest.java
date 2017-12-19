package samples;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import javax.net.ssl.SSLSession;


public class NetClientGetTest {

	// http://localhost:8080/RESTfulExample/json/product/get
	public static void main(String[] args) {

	  try {

		//URL url = new URL("https://1rw1yvhop9.execute-api.us-east-1.amazonaws.com/Dev/ws2/vehicle/battery/options/year-make-model-engine/years");
		  URL url = new URL("https://api.twilio.com/2010-04-01/Accounts/AC01ac249e16a48de0931e626a18c86454/Messages.json?PageSize=1000&DateSent=2017-07-7");
		
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("AccountSid","AC01ac249e16a48de0931e626a18c86454");
		conn.setRequestProperty("AuthToken","725fecc80e5296ff514f4966b9d4dee7");
		
		System.out.println(conn.getResponseCode());
		
		if (conn.getResponseCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
					+ conn.getResponseCode() + conn.getHeaderField("Location"));
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(
			(conn.getInputStream())));

		String output;
		System.out.println("Output from Server .... \n");
		while ((output = br.readLine()) != null) {
			System.out.println(output);
		}

		conn.disconnect();
		
		

	  } catch (MalformedURLException e) {

		e.printStackTrace();

	  } catch (IOException e) {

		e.printStackTrace();

	  }

	}


}