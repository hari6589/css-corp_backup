package samples;

import org.springframework.web.client.RestTemplate;

public class RestTemplateTest {
	public static void main(String[] args) {
		
		RestTemplateTest restTemplateTest = new RestTemplateTest();
		
		restTemplateTest.One();
		
	}
	
	public void One() {
		final String uri = "https://1rw1yvhop9.execute-api.us-east-1.amazonaws.com/Dev/ws2/appointment/services";
		
		RestTemplate restTemplate = new RestTemplate();
		String result = restTemplate.getForObject(uri, String.class);
		 
		System.out.println(result);
	}
	
}
