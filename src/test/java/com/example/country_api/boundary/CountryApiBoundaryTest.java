package com.example.country_api.boundary;

import static com.example.country_api.utils.TestUtils.boundaryTestFile;
import static com.example.country_api.utils.TestUtils.currentTest;
import static com.example.country_api.utils.TestUtils.testReport;
import static com.example.country_api.utils.TestUtils.yakshaAssert;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import com.example.country_api.utils.RestAssuredValidator;
import com.example.country_api.utils.RestUtils;
import com.example.country_api.utils.TestCodeValidator;

import io.restassured.response.Response;

public class CountryApiBoundaryTest {

	@AfterAll
	public static void afterAll() {
		testReport();
	}

	private final RestUtils restUtils = new RestUtils();

	private final String filePath = "src/main/java/com/example/country_api/utils/RestUtils.java";

	// Test case for adding a new country (POST request)
	@Test
	public void testAddCountry() throws IOException {
		Response response = restUtils.addCountry("Canada");

		// Validate if the necessary keywords are present in the method's body
		boolean isValidationSuccessful = TestCodeValidator.validateTestMethodFromFile(filePath, "addCountry",
				List.of("given", "when", "body", "post", "then", "extract", "response"));

		String endpoint = "http://localhost:8081/api/countries/get/1";
		int expectedStatusCode = 200;
		String expectedResponseBody = "{\"id\":1,\"name\":\"India\"}";

		String res = RestAssuredValidator.validateGetRequest(endpoint, expectedStatusCode, expectedResponseBody);

		try {
			yakshaAssert(currentTest(),
					isValidationSuccessful && expectedResponseBody.equalsIgnoreCase(res)
							&& response.getStatusCode() == 201
							&& response.getBody().asString().contains("Country added with ID"),
					boundaryTestFile);
		} catch (Exception e) {
			yakshaAssert(currentTest(), false, boundaryTestFile);
		}
	}

//	 Test case for getting a country by ID (GET request with content negotiation)
	@Test
	public void testGetCountryByIdAsJson() throws IOException {
		Response response = restUtils.getCountryById(1, "application/json");

		// Validate if the necessary keywords are present in the method's body
		boolean isValidationSuccessful = TestCodeValidator.validateTestMethodFromFile(filePath, "getCountryById",
				List.of("given", "when", "header", "get", "response", "then", "extract"));

		String endpoint = "http://localhost:8081/api/countries/get/1";
		int expectedStatusCode = 200;
		String expectedResponseBody = "{\"id\":1,\"name\":\"India\"}";

		String res = RestAssuredValidator.validateGetRequest(endpoint, expectedStatusCode, expectedResponseBody);

		try {
			yakshaAssert(currentTest(),
					isValidationSuccessful && response.getStatusCode() == 200
							&& expectedResponseBody.equalsIgnoreCase(res)
							&& response.getContentType().equalsIgnoreCase("application/json")
							&& response.getBody().asString().contains("India"),
					boundaryTestFile);
		} catch (Exception e) {
			yakshaAssert(currentTest(), false, boundaryTestFile);
		}
	}

	// Test case for getting a country by ID (GET request with XML)
	@Test
	public void testGetCountryByIdAsXml() throws IOException {
		Response response = restUtils.getCountryById(1, "application/xml");

		// Validate if the necessary keywords are present in the method's body
		boolean isValidationSuccessful = TestCodeValidator.validateTestMethodFromFile(filePath, "getCountryById",
				List.of("given", "when", "header", "get", "response", "then", "extract"));

		try {
			yakshaAssert(currentTest(),
					isValidationSuccessful && response.getStatusCode() == 200
							&& (response.getContentType()).equalsIgnoreCase("application/xml")
							&& (response.getBody().asString()).contains("<name>India</name>"),
					boundaryTestFile);
		} catch (Exception e) {
			yakshaAssert(currentTest(), false, boundaryTestFile);
		}
	}

	// Test case for updating an existing country (PUT request)
	@Test
	public void testUpdateCountry() throws IOException {
		Response response = restUtils.updateCountry(1, "India Updated");

		String endpoint = "http://localhost:8081/api/countries/update/1";
		String requestBody = "{ \"name\": \"India Updated\" }";
		int expectedStatusCode = 200;
		String expectedResponseBody = "Country updated";

		// Validate if the necessary keywords are present in the method's body
		boolean isValidationSuccessful = TestCodeValidator.validateTestMethodFromFile(filePath, "updateCountry",
				List.of("given", "when", "body", "put", "response", "then", "extract", "contentType"));

		// Call validatePutRequest and store the actual response
		String actualResponseBody = RestAssuredValidator.validatePutRequest(endpoint, requestBody, expectedStatusCode,
				expectedResponseBody);

		// Validate if the response body is correct and contains "Country updated"
		boolean isResponseCorrect = actualResponseBody.contains(expectedResponseBody);

		try {
			yakshaAssert(currentTest(), isValidationSuccessful && response.getStatusCode() == 200 && isResponseCorrect
					&& (response.getBody().asString()).contains("Country updated"), boundaryTestFile);
		} catch (Exception e) {
			yakshaAssert(currentTest(), false, boundaryTestFile);
		}
	}

	// Test case for deleting a country by ID (DELETE request)
	@Test
	public void testDeleteCountry() throws IOException {
		Response response = restUtils.deleteCountry(2);

		String endpoint = "http://localhost:8081/api/countries/delete/2";
		int expectedStatusCode = 204;

		// Validate if the necessary keywords are present in the method's body
		boolean isValidationSuccessful = TestCodeValidator.validateTestMethodFromFile(filePath, "deleteCountry",
				List.of("given", "when", "delete", "response", "then", "extract"));

		String actualResponseBody = RestAssuredValidator.validateDeleteRequest(endpoint, expectedStatusCode);

		try {
			yakshaAssert(currentTest(), isValidationSuccessful && response.getStatusCode() == 204, boundaryTestFile);
		} catch (Exception e) {
			yakshaAssert(currentTest(), false, boundaryTestFile);
		}
	}

	// Test case for partially updating a country name by ID (PATCH request)
	@Test
	public void testPatchCountryName() throws IOException {
		Response response = restUtils.patchCountryName(1, "India Patched");

		String endpoint = "http://localhost:8081/api/countries/patch/1";
		String requestBody = "{ \"name\": \"India Patched\" }";
		int expectedStatusCode = 200;
		String expectedResponseBody = "Country name updated";

		// Validate if the necessary keywords are present in the method's body
		boolean isValidationSuccessful = TestCodeValidator.validateTestMethodFromFile(filePath, "patchCountryName",
				List.of("given", "contentType", "body", "when", "patch", "extract", "response", "then"));

		// Call validatePatchRequest and store the actual response
		String actualResponseBody = RestAssuredValidator.validatePatchRequest(endpoint, requestBody, expectedStatusCode,
				expectedResponseBody);

		// Validate if the response body is correct and contains "Country name updated"
		boolean isResponseCorrect = actualResponseBody.contains(expectedResponseBody);
		try {
			yakshaAssert(currentTest(), isValidationSuccessful && response.getStatusCode() == 200 && isResponseCorrect
					&& (response.getBody().asString()).contains("Country name updated"), boundaryTestFile);
		} catch (Exception e) {
			yakshaAssert(currentTest(), false, boundaryTestFile);
		}
	}

	// Test case for searching a country by name (GET request with query parameter)
	@Test
	public void testSearchCountryByName() throws IOException {
		Response response = restUtils.searchCountryByName("India");

		String endpoint = "http://localhost:8081/api/countries/search?name=India";
		int expectedStatusCode = 200;
		String expectedResponseBody = "{\"id\":1,\"name\":\"India\"}";

		// Validate if the necessary keywords are present in the method's body
		boolean isValidationSuccessful = TestCodeValidator.validateTestMethodFromFile(filePath, "searchCountryByName",
				List.of("given", "queryParam", "when", "get", "extract", "response", "then"));

		// Call validateGetRequest (since it is a GET request) and store the actual
		// response
		String actualResponseBody = RestAssuredValidator.validateGetRequest(endpoint, expectedStatusCode,
				expectedResponseBody);

		// Validate if the response body is correct and contains "India"
		boolean isResponseCorrect = actualResponseBody.contains("India");
		try {
			yakshaAssert(currentTest(), isValidationSuccessful && response.getStatusCode() == 200 && isResponseCorrect
					&& (response.getBody().asString()).contains("India"), boundaryTestFile);
		} catch (Exception e) {
			yakshaAssert(currentTest(), false, boundaryTestFile);
		}
	}

	// Test case for retrieving a country by code (GET request with URI template)
	@Test
	public void testGetCountryByCode() throws IOException {
		String endpoint = "http://localhost:8081/api/countries/code/IN";
		int expectedStatusCode = 200;
		String expectedResponseBody = "India";

		Response response = restUtils.getCountryByCode("IN");
		// Validate if the necessary keywords are present in the method's body
		boolean isValidationSuccessful = TestCodeValidator.validateTestMethodFromFile(filePath, "getCountryByCode",
				List.of("given", "when", "get", "extract", "response", "then"));

		// Call validateGetRequest and store the actual response
		String actualResponseBody = RestAssuredValidator.validateGetRequest(endpoint, expectedStatusCode,
				expectedResponseBody);

		// Validate if the response body is correct and contains "India"
		boolean isResponseCorrect = actualResponseBody.contains(expectedResponseBody);

		try {
			yakshaAssert(currentTest(), isValidationSuccessful && response.getStatusCode() == 200 && isResponseCorrect
					&& (response.getBody().asString()).contains("India"), boundaryTestFile);
		} catch (Exception e) {
			yakshaAssert(currentTest(), false, boundaryTestFile);
		}
	}

	// Test case for bulk adding multiple countries (POST request)
	@Test
	public void testAddMultipleCountries() throws IOException {
		Map<Integer, String> countries = Map.of(4, "France", 5, "Germany");
		Response response = restUtils.addMultipleCountries(countries);

		String endpoint = "http://localhost:8081/api/countries/bulkAdd";
		String requestBody = "{ \"4\": \"France\", \"5\": \"Germany\" }";
		int expectedStatusCode = 201;
		String expectedResponseBody = "Countries added";

		// Validate if the necessary keywords are present in the method's body
		boolean isValidationSuccessful = TestCodeValidator.validateTestMethodFromFile(filePath, "addMultipleCountries",
				List.of("given", "contentType", "body", "when", "post", "extract", "response", "then"));

		// Call validatePostRequest and store the actual response
		String actualResponseBody = RestAssuredValidator.validatePostRequest(endpoint, requestBody, expectedStatusCode,
				expectedResponseBody);

		// Validate if the response body is correct and contains "Countries added"
		boolean isResponseCorrect = actualResponseBody.contains(expectedResponseBody);
		try {
			yakshaAssert(currentTest(), isValidationSuccessful && isResponseCorrect
					&& (response.getBody().asString()).contains("Countries added") && response.getStatusCode() == 201,
					boundaryTestFile);
		} catch (Exception e) {
			yakshaAssert(currentTest(), false, boundaryTestFile);
		}
	}

	// Test case for handling different response body types (GET request for JSON)
	@Test
	public void testGetCountryResponseTypeAsJson() throws IOException {
		String endpoint = "http://localhost:8081/api/countries/responseType/1";
		String acceptHeader = "application/json";
		int expectedStatusCode = 200;
		String expectedResponseBody = "{\"id\":1,\"name\":\"India\"}";

		Response response = restUtils.getCountryByIdWithResponseType(1, "application/json");

		// Validate if the necessary keywords are present in the method's body
		boolean isValidationSuccessful = TestCodeValidator.validateTestMethodFromFile(filePath,
				"getCountryByIdWithResponseType",
				List.of("given", "header", "get", "when", "extract", "response", "then"));

		// Call validateGetRequest and store the actual response
		String actualResponseBody = RestAssuredValidator.validateGetRequest(endpoint, expectedStatusCode,
				expectedResponseBody);

		// Validate if the response content-type and body are correct
		boolean isResponseCorrect = actualResponseBody.contains("India");
		try {
			yakshaAssert(currentTest(),
					isValidationSuccessful && response.getStatusCode() == 200 && isResponseCorrect
							&& (response.getContentType()).equalsIgnoreCase("application/json")
							&& (response.getBody().asString()).contains("India"),
					boundaryTestFile);
		} catch (Exception e) {
			yakshaAssert(currentTest(), false, boundaryTestFile);
		}
	}

	// Test case for handling different response body types (GET request for XML)
	@Test
	public void testGetCountryResponseTypeAsXml() throws IOException {
		Response response = restUtils.getCountryByIdWithResponseType(1, "application/xml");

		// Validate if the necessary keywords are present in the method's body
		boolean isValidationSuccessful = TestCodeValidator.validateTestMethodFromFile(filePath,
				"getCountryByIdWithResponseType",
				List.of("given", "header", "get", "when", "extract", "response", "then"));
		try {
			yakshaAssert(currentTest(), isValidationSuccessful && response.getStatusCode() == 200
					&& (response.getContentType()).equalsIgnoreCase("application/xml"), boundaryTestFile);
		} catch (Exception e) {
			yakshaAssert(currentTest(), false, boundaryTestFile);
		}
	}

	// Test case for handling different response body types (GET request for text)
	@Test
	public void testGetCountryResponseTypeAsText() throws IOException {
		Response response = restUtils.getCountryByIdWithResponseType(1, "text/plain");

		// Validate if the necessary keywords are present in the method's body
		boolean isValidationSuccessful = TestCodeValidator.validateTestMethodFromFile(filePath,
				"getCountryByIdWithResponseType",
				List.of("given", "header", "get", "when", "extract", "response", "then"));
		try {
			yakshaAssert(currentTest(),
					isValidationSuccessful && response.getStatusCode() == 200
							&& (response.getContentType()).contains("text/plain")
							&& (response.getBody().asString()).contains("Country: India"),
					boundaryTestFile);
		} catch (Exception e) {
			yakshaAssert(currentTest(), false, boundaryTestFile);
		}
	}
}
