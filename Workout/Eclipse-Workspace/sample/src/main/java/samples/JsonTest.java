package samples;

import org.json.JSONException;
import org.json.JSONObject;

import net.sf.json.util.JSONUtils;

public class JsonTest {
	public static void main(String[] args) throws JSONException {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("name", "test");
		jsonObj.put("age", "null");
		System.out.println(jsonObj.toString());
		if(!JSONUtils.mayBeJSON(jsonObj.toString())){
			System.out.println("InValid JSON Structure!");
		} else {
			System.out.println("Valid JSON Structure!");
		}
	}
}
