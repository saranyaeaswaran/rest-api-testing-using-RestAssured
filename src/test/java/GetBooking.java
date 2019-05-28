
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given; //import this
import io.restassured.response.Response;

import pojoClasses.BookingDetails;

import utility.AllureLogger;
import utility.BaseTest;
import utility.ExcelLib;

public class GetBooking extends BaseTest {
	
	@DataProvider (name="DataFromExcel")
	public Object[][] data() throws Exception {
		ExcelLib xl = new ExcelLib("Booking", this.getClass().getSimpleName());
		return xl.getTestdata();
	}
    
	@Test(dataProvider="DataFromExcel", description="To retrieve the details of the booking IDs") 
	public void getBookingIDs(String ID, 
							  String firstname, 
							  String lastname,
							  String totalprice, 
							  String depositpaid, 
							  String checkin, 
							  String checkout, 
							  String additionalneeds, String dontUseThis
							  ){
		
		AllureLogger.logToAllure("Starting the test to get booking details");
		/*******************************************************
		 * Send a GET request to /booking/{id}
		 * and check that the response has HTTP status code 200
		 ******************************************************/
		
		//Sending the GET request for a specific booking id and receiving the response
		AllureLogger.logToAllure("Getting the response for the Booking IDs from test data excel");
		Response response = given().
				spec(requestSpec).
				pathParam("id", ID).
			when().
				get("/booking/{id}");
		
		//Verify the response code
		AllureLogger.logToAllure("Asserting the response if the status code returned is 200");
		response.then().spec(responseSpec);		

		//To log the response to report
		logResponseAsString(response);
		
		
		//Using the POJO class
		AllureLogger.logToAllure("Asserting of response body with the data from test data excel");
		
		BookingDetails bookingDetails = response.as(BookingDetails.class);
		
		Assert.assertEquals(bookingDetails.getFirstname(), firstname);
		Assert.assertEquals(bookingDetails.getLastname(), lastname);
		Assert.assertEquals(bookingDetails.getTotalprice(), totalprice);
		Assert.assertEquals(bookingDetails.getDepositpaid(), depositpaid);
		Assert.assertEquals(bookingDetails.getBookingdates().getCheckin(), checkin);
		Assert.assertEquals(bookingDetails.getBookingdates().getCheckout(), checkout);
		
	}
}
