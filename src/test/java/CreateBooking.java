
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given; //import this

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import pojoClasses.BookingDates;
import pojoClasses.BookingDetails;

import utility.AllureLogger;
import utility.BaseTest;
import utility.ExcelLib;

public class CreateBooking extends BaseTest {
	
	public static String newID = "";
	
	@DataProvider (name="DataFromExcel")
	public Object[][] data() throws Exception {
		ExcelLib xl = new ExcelLib("Booking", this.getClass().getSimpleName());
		return xl.getTestdata();
	}
    
	@Test(dataProvider="DataFromExcel", description="To retrieve the details of the booking IDs") 
	public void createNewBooking(String firstname, 
							  String lastname,
							  String totalprice, 
							  String depositpaid, 
							  String checkin, 
							  String checkout, 
							  String additionalneeds, String dontUseThis
							  ){
		
		AllureLogger.logToAllure("Starting the test to create new details");
		/*******************************************************
		 * Send a POST request to /booking/{id}
		 * and check that the response has HTTP status code 200
		 ******************************************************/
		
		//Sending the GET request for a specific booking id and receiving the response
		AllureLogger.logToAllure("Posting a new booking detail");
		
		BookingDetails bookingDetails = new BookingDetails();
		bookingDetails.setFirstname(firstname);
		bookingDetails.setLastname(lastname);
		bookingDetails.setTotalprice(Integer.parseInt(totalprice));
		bookingDetails.setDepositpaid(Boolean.parseBoolean(depositpaid));
		bookingDetails.setAdditionalneeds(additionalneeds);
		
		BookingDates bookingDates = new BookingDates();
		bookingDates.setCheckin(checkin);
		bookingDates.setCheckout(checkout);
		bookingDetails.setBookingdates(bookingDates);
				
		AllureLogger.logToAllure("Sending the POST request to create new booking");
		Response response = given().
								spec(requestSpec).
								contentType("application/json").
					            body(bookingDetails).log().body().
					        when().
					        	post("/booking");
		
		//Verify the response code
		AllureLogger.logToAllure("Asserting the response if the status code returned is 200");
		response.then().spec(responseSpec);		

		//To log the response to report
		logResponseAsString(response);
		
		
		//To get the newly created bookign id
		System.out.println(response.then().extract().path("bookingid"));
		newID = response.then().extract().path("bookingid").toString();
		AllureLogger.logToAllure("Retrieved booking id : "+response.then().extract().path("bookingid"));
		
	}	
}
