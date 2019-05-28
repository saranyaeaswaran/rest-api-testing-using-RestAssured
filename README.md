# Rest API testing using Rest-Assured Java Framework

- This is repository of basic REST API testing framework
- The public API used for this example is - https://restful-booker.herokuapp.com
- This framework is developed using **Rest-Assured** library for Rest API testing
- For JSON Parsing in java the library used is - **json-simple** and **Jackson API**
- Reporting is by **Allure API**

## Below are instructions are how to create Rest API test framework using Rest Assured Java library

	• Maven Dependency to add,
		<dependency>
		    <groupId>io.rest-assured</groupId>
		    <artifactId>rest-assured</artifactId>
		    <version>3.3.0</version>
		    <scope>test</scope>
		</dependency>
		
	• RestAssured.uri = "" // to specify the basic URL of the API

	• Basic building blocks of Rest Assured - given, when, then
	
	• Values that can be provided in the given block,
		○ 
	• To print the response body as string,
		```
		@Test
		public void RESTAutomation() {
			RestAssured.baseURI="http://api.zippopotam.us";
			RequestSpecification requestSpecification = given();
			Response response = requestSpecification.when().get("/us/10094");
			System.out.println(response.asString());
			System.out.println(response.then().extract().response().asString());
		}
		```
	• To specify particular header value when sending the request
		Header header = new Header("key","value");
		RequestSpecification requestSpecification = given().header(header);
				
	• To validate response content type,
		○ response.then().contentType(ContentType.JSON);
	
	• To get a cookie value,
		○ System.out.println(response.getCookie("__cfduid").toString());
	
	• To get log when response is being fetched,
		○ response.then().log().all();
	
	• To validate a particular item from the response body,
		○ REST-assured takes advantage of the power of Hamcrest matchers to perform its assertions,
		○ If equalTo is not getting detected, add this import 'import static org.hamcrest.Matchers.equalTo;'
		○ response.then().assertThat().body("places[0].'place name'", equalTo("New York City"));
		○ response.then().assertThat().body("places.'place name'", hasItem("New York City")); //to verify when array of objects are present of a particular value is present in any of the objects under the array in the given item
		○ response.then().assertThat().body("places.'place name'", hasSize(1)); // To check how many items are present in a array collection inside the json
		
	• Validating status code before every test run using ResponseBuilder
		    public static ResponseSpecBuilder builder;
		    public static ResponseSpecification responseSpec;
		    @BeforeClass
		    public static void setupResponseSpecBuilder()
		    {
		        builder = new ResponseSpecBuilder();
		        builder.expectStatusCode(200);
		        responseSpec = builder.build();
		    }
			
			@Test
			public void RESTAutomation() {
				RestAssured.baseURI="http://api.zippopotam.us";
				Header header = new Header("key","value");
				RequestSpecification requestSpecification = given().header(header);
				Response response = requestSpecification.when().get("/us/90210");
				response.then().spec(responseSpec);
	
	• RequestBuilder to specify header or login details
		    public static RequestSpecBuilder builder;
		    public static RequestSpecification requestSpec;
		    @BeforeClass
		    public static void setupRequestSpecBuilder()
		    {
		        builder = new RequestSpecBuilder();
		        builder.addHeader("Authorization", "abcd-123-xyz");
		        builder.addParameter("loginID", "joebloggs");
		        requestSpec = builder.build();
		    }
		
		@Test
		public void testAnApiPostCall()
		{
		    given().
		        spec(requestSpec).
	
	• Traversing:
		○ REST Assured uses JsonPath/GPath and XmlPath 

	• Schema Validation:
		○ get("/products").then().assertThat().body(matchesJsonSchemaInClasspath("products-schema.json").using(settings().with().checkedValidation(false)));
	
	• JAX-RX from Java to be used to develop APIs

	• Path and Query Parameters
		○ http://localhost:8081/SearchApp/book?name=java => QueryParameter
		○ http://localhost:8081/SearchApp/book/java => PathParameter
		
			§ Using pathParams
				□ Given().pathParam("key","value")
				□ When().get("//us/{key}/repose")
				□ Then.statusCode(200)
			§ Using Query parameters,
				□ Given().queryParam("key","value")  or Given().param("key","value")
				□ When().get("/us/target")
				□ Then.statusCode(200)

### Deserializing JSON response:
		
	• Using getters and setters of POJO classes, serialisation and deserialization of JSON objects can be made simple
		○ To convert a normal java class to POJO => provide the field names, ALT+SHIFT+S, generate getter and setter
	• We can use JacksonAPI to easily work with json objects and below are the dependencies to be added,
		<dependency>
		    <groupId>com.fasterxml.jackson.core</groupId>
		    <artifactId>jackson-databind</artifactId>
		    <version>2.9.8</version>
		</dependency>
		
		<!-- https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-core -->
		<dependency>
		    <groupId>com.fasterxml.jackson.core</groupId>
		    <artifactId>jackson-core</artifactId>
		    <version>2.9.8</version>
		</dependency>
		
		<!-- https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-annotations -->
		<dependency>
		    <groupId>com.fasterxml.jackson.core</groupId>
		    <artifactId>jackson-annotations</artifactId>
		    <version>2.9.8</version>
		</dependency>
	
	• In the pojo class, each class variables to be annotated with @JSONProperty. If left default the variable name is equal to JSON property name.  If we mention it as "@JSONProperty("name")" then the name mentioned here corresponds to the json object property name
	• Now to convert the json response into an object of POJO class, use 'response.as(ClassName.class)' after obtaining the response normally
		○ e.g. 
		BookingDetails bookingDetails = response.as(BookingDetails.class);
		System.out.println("Additional Details are : "+bookingDetails.getAdditionalneeds());


