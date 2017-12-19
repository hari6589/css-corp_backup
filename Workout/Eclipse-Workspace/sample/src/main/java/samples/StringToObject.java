package samples;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class StringToObject {

	public static void main(String[] args) {
		
		//Object obj = "{body={storeNumber=23817, locationId=1581, employeeId=12713, appointmentStatusId=4088, appointmentStatusDesc=Scheduled, vehicleYear=2010, vehicleMake=Chevrolet, vehicleModel=Colorado, vehicleSubmodel=WT, mileage=7500, customerFirstName=Stallin, customerLastName=Moorthy, customerDayTimePhone=227-876-5678, customerEmailAddress=test@bfrc.com, websiteName=FCAC, appointmentType=New, choice={choice=1, datetime=1455811200000, dropWaitOption=drop}, selectedServices=2751,2767}}";
		StringToObject sto = new StringToObject();
		String obj = sto.getJsonData();
		//sto.stringToList("");
		sto.stringToListDoubleQuote();
				
		ObjectMapper mapper = new ObjectMapper();
		//App result;
		TestPojo testPojo;
		try {
			//result = mapper.readValue(obj, App.class);
			//System.out.println("Property : " + result.toString());
			
			testPojo = mapper.readValue(obj, TestPojo.class);
			System.out.println("Property : " + testPojo.toString());
			
			testPojo.setAppointmentId(11111);
			
			System.out.println("After : " + testPojo.toString());
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public String getJsonData() {
		return "{"
				+ "\"storeNumber\":\"23817\","
				+ "\"locationId\":\"1581\","
				+ "\"employeeId\":\"12713\","
				+ "\"appointmentStatusId\":\"4088\","
				+ "\"appointmentStatusDesc\":\"Scheduled\","
				+ "\"vehicleYear\":\"2010\","
				+ "\"vehicleMake\":\"Chevrolet\","
				+ "\"vehicleModel\":\"Colorado\","
				+ "\"vehicleSubmodel\":\"WT\","
				+ "\"mileage\":\"7500\","
				+ "\"customerFirstName\":\"Stallin\","
				+ "\"customerLastName\":\"Moorthy\","
				+ "\"customerDayTimePhone\":\"227-876-5678\","
				+ "\"customerEmailAddress\":\"test@bfrc.com\","
				+ "\"websiteName\":\"FCAC\","
				+ "\"appointmentType\":\"New\","
				+ "\"choice\":{"
					+ "\"choice\":\"1\","
					+ "\"datetime\":\"1455811200000\","
					+ "\"dropWaitOption\":\"drop\""
				+ "},"
				+ "\"selectedServices\":\"2751,2767\""
				+ "}";
	}
	
	public void stringToList(String str) {
		String s1="[a,b,c,d]";
		String replace = s1.replace("[","");
		System.out.println(replace);
		String replace1 = replace.replace("]","");
		System.out.println(replace1);
		List<String> myList = new ArrayList<String>(Arrays.asList(replace1.split(",")));
		System.out.println(myList.toString());
	}
	
	public void stringToListDoubleQuote() {
		String str = "[\"Specified location 0 (c_id) does not exist for customer [64218], account [], date [20171120], start_time [944], end_time[944], employee_id [5523], c_id [0], service_id [2745]\",\"Specified service 2745 (service_id) does not exist for customer [64218], account [], date [20171120], start_time [944], end_time[944], employee_id [5523], c_id [0], service_id [2745]\"]";
		String[] splitStrings = str.split("\"");
		
		for(int i=1; i < splitStrings.length; i+=2) {
			System.out.println(splitStrings[i]);
		}
	}

}
