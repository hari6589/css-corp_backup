package samples;

import java.io.InputStream;
import java.util.Properties;

public class PropertiesTest {

	public static void main(String[] args) {
		System.out.println("Test!");
		InputStream inputStream = PropertiesTest.class.getClassLoader().getResourceAsStream("dev//application.properties");
		Properties properties = new Properties();
		try {
			properties.load(inputStream);
			System.out.println(properties.getProperty("environment"));
		} catch(Exception e) {
			System.out.println("Exception : " + e.getMessage());
		}
	}

}
