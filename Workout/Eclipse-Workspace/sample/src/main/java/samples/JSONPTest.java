package samples;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;

import samples.pojo.BSROWebServiceWifiResponse;

public class JSONPTest {

	public static void main(String[] args) throws JsonProcessingException {
		JSONPTest jsonpTest = new JSONPTest();
		jsonpTest.formatJsonString();
	}
	
	private void jsonp() throws JsonProcessingException {
		BSROWebServiceWifiResponse bsroWebServiceWifiResponse = new BSROWebServiceWifiResponse();
		bsroWebServiceWifiResponse.setResponseCode("000");
		bsroWebServiceWifiResponse.setResponseMessage("Success");
	
		System.out.println(bsroWebServiceWifiResponse.toString("bsroSignupCallback"));
		
		String jsonpStr = new ObjectMapper().writeValueAsString(new JSONPObject("callbackTest", bsroWebServiceWifiResponse));
		System.out.println(">> " + jsonpStr);
	}

	private void formatJsonString() {
        String json = "{\"id\":1,\"title\":\"Yellow Submarine\",\"releaseDate\":\"1969-01-17\",\"artist\":{\"id\":1,\"name\":\"The Beatles\"},\"label\":{\"id\":1,\"name\":\"Apple\"}}";
        ObjectMapper mapper = new ObjectMapper();
        try {
            Object jsonObject = mapper.readValue(json, Object.class);
            String prettyJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
            System.out.println(prettyJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
