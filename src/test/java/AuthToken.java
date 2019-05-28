
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.Header;

import static io.restassured.RestAssured.given; //import this
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import utility.AllureLogger;
import utility.BaseTest;
import utility.FrameworkConstants;
import utility.FrameworkUtility;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;

import org.json.simple.JSONObject;

public class AuthToken extends BaseTest {
	
    
	public static String post_CreateAuth(){
		
		AllureLogger.logToAllure("Starting the test for POST method for create authentication");
		/*******************************************************
		 * Send a POST request to /auth
		 * and check that the response has HTTP status code 200
		 ******************************************************/
		JSONObject jsonObject = returDefaultPayLoadObject(FrameworkConstants.POSTRequest_AUTH_DEFAULT_REQUEST);
		String username = readConfigurationFile("username");
		String password = readConfigurationFile("password");
		jsonObject.put("password", password);
		jsonObject.put("username", username);
		AllureLogger.logToAllure("Username from config file is : \n"+ username);
		AllureLogger.logToAllure("Password from config file is : \n"+ password);
		
		
		Response response = given().
								spec(requestSpec).
								contentType("application/json").
								body(jsonObject.toJSONString()).
							when().
								post("/auth");
		
		AllureLogger.logToAllure("Asserting the response if the status code returned is 200");
		response.then().spec(responseSpec);
		
		
		String token = response.then().extract().path("token");
		return token;
	}
}
