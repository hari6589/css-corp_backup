package storehour;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import storehour.StoreHours.Hours;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class StoreHourTest {
	
	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss SSS");
		AWSCredentials awsCredentials = new BasicAWSCredentials("AKIAIO6KVA4ID6VFURAA", "aaSNiXJVyTafsNBJfuLXp0+/KeiPbUUeel54XcKF");
		AmazonDynamoDBClient dyndbclient = new AmazonDynamoDBClient(awsCredentials);
		DynamoDB dynamoDB = new DynamoDB(dyndbclient);
		
		System.out.println(dateFormat.format(new Date()));
		System.out.println(0);
		Table table = dynamoDB.getTable("StoreHours");
		GetItemSpec spec = new GetItemSpec()
			.withPrimaryKey("storeNumber", 24457, "siteName", "FCAC")
			.withConsistentRead(true);
		Item item1 = table.getItem(spec);
		System.out.println(1);
		StoreHours storeHours = null;
		if(item1 != null) {
			ObjectMapper mapper1 = new ObjectMapper();
			storeHours = mapper1.readValue(item1.toJSON(), StoreHours.class);
			System.out.println(2);
		}
		System.out.println(storeHours.toString());
		
		System.out.println(dateFormat.format(new Date()));
		DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(new AmazonDynamoDBClient(awsCredentials));
		List<StoreHours> storeHoursList = new ArrayList<StoreHours>();
		Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
		eav.put(":n", new AttributeValue().withN("24457"));
		eav.put(":o", new AttributeValue().withS("FCAC"));
	    DynamoDBQueryExpression<StoreHours> shqueryExpression = new DynamoDBQueryExpression<StoreHours>()
		        .withKeyConditionExpression("storeNumber = :n AND siteName = :o")
		        .withExpressionAttributeValues(eav);
	    storeHoursList = dynamoDBMapper.query(StoreHours.class, shqueryExpression);
	    
	    if(storeHoursList.size() > 0) {
	    	System.out.println(storeHoursList.get(0).toString());
	    	StoreHours sh = storeHoursList.get(0);
	    	String out = getStoreHourHTML(sh);
	    	System.out.println(out);
	    }
	}

	public static String getStoreHourHTML(StoreHours sh) {
		StringBuilder stringBuilder = new StringBuilder();
		for(int i=0; i < DAY_ABBREV.length; i++) {
    		System.out.println(DAY_ABBREV[i]);
	    	List<Hours> hl = sh.getHours();
	    	for(int j=0; j < hl.size(); j++) {
	    		Hours hours = hl.get(j);
	    		if(DAY_ABBREV[i].equals(hours.getWeekDay())) {
	    			stringBuilder.append("<br><b>" + hours.getWeekDay() + ":</b><span>" + hours.getOpenTime() + "&#45;" + format(hours.getCloseTime()) + "</span>");
	    		}
	    		
	    	}
    	}
		return stringBuilder.toString();
	}
	/*
	public static String getStoreHourHTML(StoreHours storeHours, boolean isFormatted) {
		String out = "";
		
		if (storeHours == null || storeHours.isEmpty())
			return out;
		for (int i=0; i<7; i++) {
			StoreHour1 curr = getStoreHour(storeHours, DAY_ABBREV[i]);
			if (curr == null)
				continue;
			for (int j=i+1; j<=7; j++) {
				StoreHour1 test = null;
				if (j<7)
					test = getStoreHour(storeHours, DAY_ABBREV[j]);
				if (test != null && test.equals(curr))
                    continue;
				if (isFormatted)
					out = out + getHourFormattedHTML(i, j - 1, curr);
				else
					out = out + getHourHTML(i, j - 1, curr);
				i = j - 1;
				break;
			}		
		}
		return out;		
	}*/
	
	public String getStoreHourHTML(Collection<StoreHour1> storeHour1s, boolean isFormatted) {
		String out = "";
		if (storeHour1s == null || storeHour1s.isEmpty())
			return out;
		for (int i=0; i<7; i++) {
			StoreHour1 curr = getStoreHour(storeHour1s, DAY_ABBREV[i]);
			if (curr == null)
				continue;
			for (int j=i+1; j<=7; j++) {
				StoreHour1 test = null;
				if (j<7)
					test = getStoreHour(storeHour1s, DAY_ABBREV[j]);
				if (test != null && test.equals(curr))
                    continue;
				if (isFormatted)
					out = out + getHourFormattedHTML(i, j - 1, curr);
				else
					out = out + getHourHTML(i, j - 1, curr);
				i = j - 1;
				break;
			}		
		}
		return out;		
	}
	
	public static final String DAY_ABBREV[] = {
        "MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN"
    };
	
	public StoreHour1 getStoreHour(Collection<StoreHour1> l, String day) {	
		StoreHour1 hour = null;		
		for (StoreHour1 temp: l) {
			if (temp != null && temp.getId().getWeekDay().equals(day)) {
				hour = temp;
				break;
			}				
		}
		return hour;
	}
	
	public String getHourFormattedHTML(int begin, int end, StoreHour1 hour) {
		if(hour == null || hour.getOpenTime() == null || hour.getCloseTime() == null)
            return "";
        String out = "<b>" + DAY_ABBREV[begin];
        if(end > begin)
        {
            out = out + "&#45;" + DAY_ABBREV[end];            
        }
        return out + ":</b> " + format(hour.getOpenTime()) + "&#45;" + format(hour.getCloseTime()) + "<br />";
	}
	
	public static String format(String time) {
        String temp = pad(time);
        int hour = Integer.valueOf(temp.substring(0, 2)).intValue();
        String m = "am";
        if(hour > 11)
        {
            m = "pm";
            if(hour > 12)
                hour -= 12;
        }
        String out = Integer.toString(hour) + ":" + temp.substring(3, 5);
        return out + m;
    }
	
	public static String pad(String param) {
        if(param == null)
            return null;
        String temp = param.trim();
        if(temp.length() < 5)
            return "0" + temp;
        return temp;
    }
	
	public String getHourHTML(int begin, int end, StoreHour1 hour) {
		if(hour == null || hour.getOpenTime() == null || hour.getCloseTime() == null)
            return "";
        String out = DAY_ABBREV[begin];
        if(end > begin)
        {
            out = out + "&#45;" + DAY_ABBREV[end];            
        }
        return out + " " + format(hour.getOpenTime()) + "&#45;" + format(hour.getCloseTime()) + "<br />";
	}
}
