package samples;

import com.twilio.Twilio;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import javax.net.ssl.SSLSession;


public class Test2 {
    public static final String ACCOUNT_SID = "AC01ac249e16a48de0931e626a18c86454";
  public static final String AUTH_TOKEN = "725fecc80e5296ff514f4966b9d4dee7";

	public static void main(String[] args) {
            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
            
	  try {

		URL url = new URL("https://api.twilio.com/2010-04-01/Accounts/AC01ac249e16a48de0931e626a18c86454/Messages.json?PageSize=1000&DateSent=2017-07-7");
		
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
                conn.setConnectTimeout(500000);
                conn.setDoOutput(true);
                String userpass = ACCOUNT_SID + ":" + AUTH_TOKEN;
                String basicAuth = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userpass.getBytes("UTF-8"));
                conn.setRequestProperty ("Authorization", basicAuth);

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