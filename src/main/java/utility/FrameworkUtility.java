package utility;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public abstract class FrameworkUtility {
	
	protected static Properties properties;

	public String readConfigurationFile(String key) {
		try{
			properties = new Properties();
			properties.load(new FileInputStream(FrameworkConstants.CONFIG_FILE_PATH));
			
		} catch (Exception e){
			System.out.println("Cannot find key: "+key+" in Config file due to exception : "+e);
		}
		return properties.getProperty(key).trim();	
	}
	
	public JSONObject returDefaultPayLoadObject(String filePath) {
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
}
