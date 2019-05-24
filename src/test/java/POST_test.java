

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Header;

import static io.restassured.RestAssured.given; //import this
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import utility.AllureLogger;
import utility.FrameworkConstants;
import utility.FrameworkUtility;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;

import org.json.simple.JSONObject;


public class POST_test extends FrameworkUtility {
	
    public static ResponseSpecBuilder builder;
    public static ResponseSpecification responseSpec;
	
    @BeforeClass
    public static void setupResponseSpecBuilder()
    {
        builder = new ResponseSpecBuilder();
        builder.expectStatusCode(200);
        responseSpec = builder.build();
        AllureLogger.logToAllure("Checking Report from before class");
    }
    
 	
	@Test (description="To test the auth token creation using Post Method")
	public void post_CreateAuth(){
		AllureLogger.logToAllure("Checking Report");		
		RestAssured.baseURI=readConfigurationFile("Base_URI");
		System.out.println(RestAssured.baseURI);
								
		
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
		Header acceptHeader = new Header("accept","application/json");
		Response response = given()
				.contentType("application/json")
				.body(jsonObject.toJSONString())
				.post("/auth");
		AllureLogger.logToAllure("The response is : \n"+ response.asString());		
		System.out.println("The response is : \n"+ response.asString());
		
		
		//1
//		printResponseAsString(response);
		
		//2
//		printOutputLog(response);
		
		//3
//		assertingSingleElementVlaue(response, "Beverly Hills");
		
		//4
//		assertingItemValueUsingHasItem(response, "Beverly Hills");
				
		//5
//		assertingItemSizeUsingHasItem(response, 1);
		

	}

	/*******************************************************
	 * Print the response JSON
	 ******************************************************/

	public void printResponseAsString(Response response) {
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
	
	public void assertingSingleElementVlaue(Response response, String cityName) {
		response.then().assertThat().body("places[0].'place name'",equalTo(cityName));
		
	}
	
	/*******************************************************
	 * Asserting if the given value exist in the response
	 ******************************************************/
	
	public void assertingItemValueUsingHasItem(Response response, String string) {
		
		response.then().assertThat().body("places.'place name'", hasItem(string));
	}
	
	
	/*******************************************************
	 * Asserting if the given value exist in the response using size
	 ******************************************************/
	
	public void assertingItemSizeUsingHasItem(Response response, int size) {
		
		response.then().assertThat().body("places.'place name'", hasSize(size));
	}

}
