package utility;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import io.restassured.response.Response;


public abstract class FrameworkUtility {
	
	protected static Properties properties;

	public static String readConfigurationFile(String key) {
		try{
			properties = new Properties();
			properties.load(new FileInputStream(FrameworkConstants.CONFIG_FILE_PATH));
			
		} catch (Exception e){
			System.out.println("Cannot find key: "+key+" in Config file due to exception : "+e);
		}
		return properties.getProperty(key).trim();	
	}
	
	public static JSONObject returDefaultPayLoadObject(String filePath) {
		// To get the JSON request from external file			
		JSONParser parser = new JSONParser();
		Object obj = null;
		try {
			obj = parser.parse(new FileReader(filePath));
		} catch (Exception e) {
			AllureLogger.logToAllure("Error in JSON object parsing with exception : "+e);
			
		}
		JSONObject jsonObject = (JSONObject) obj;
		return jsonObject;
	}
	
	public JSONObject loadRequestWithAuthData(JSONObject jsonObject, String username, String password) {
		
		jsonObject.put("username", username);
		jsonObject.put("password", password);
		
		return jsonObject;
		
	}
	
	/*******************************************************
	 * Print the response JSON
	 ******************************************************/

	public void logResponseAsString(Response response) {
		AllureLogger.logToAllure(response.asString());
		System.out.println(response.asString());
		
	}
	
	/*******************************************************
	 * Print the all output log along with the response json (headers, cookies etc)
	 ******************************************************/
	
	public void printOutputLog(Response response) {
		response.then().log().all();		
	}
	
	/*******************************************************
	 * Asserting value of a single element to the given value
	 ******************************************************/
	
	public void assertingSingleElementVlaue(Response response, String jsonPathOfValue, String expectedValue) {
//		eg: response.then().assertThat().body("places[0].'place name'",equalTo(cityName));
		
		response.then().assertThat().body(jsonPathOfValue,equalTo(expectedValue));
		
	}
	
	/*******************************************************
	 * Asserting if the given value exist in the response
	 ******************************************************/
	
	public void assertingItemValueUsingHasItem(Response response, String jsonPathOfValue, String expectedValue) {
		
//		eg : response.then().assertThat().body("places.'place name'", hasItem(expectedValue));
		
		response.then().assertThat().body(jsonPathOfValue, hasItem(expectedValue));
	}
	
	
	/*******************************************************
	 * Asserting if the given value exist in the response using size
	 ******************************************************/
	
	public void assertingItemSizeUsingHasItem(Response response, String jsonPathOfValue, int size) {
		
//		eg : response.then().assertThat().body("places.'place name'", hasSize(size));
		response.then().assertThat().body(jsonPathOfValue, hasSize(size));
	}
}
