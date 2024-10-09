package com.example.country_api.utils;

import static org.hamcrest.Matchers.equalTo;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;

public class RestAssuredValidator {

	// Generic method to validate a GET request with a specific endpoint and
	// expected body content
	public static String validateGetRequest(String endpoint, int expectedStatusCode, String expectedBody) {
		ValidatableResponse response = RestAssured.given().when().get(endpoint).then().statusCode(expectedStatusCode)
				.body(equalTo(expectedBody));
		return response.extract().body().asString();
	}

	// Generic method to validate a POST request with a body and expected response
	public static String validatePostRequest(String endpoint, String requestBody, int expectedStatusCode,
			String expectedResponseBody) {
		ValidatableResponse response = RestAssured.given().contentType("application/json").body(requestBody).when()
				.post(endpoint).then().statusCode(expectedStatusCode).body(equalTo(expectedResponseBody));
		return response.extract().body().asString();
	}

	// Generic method to validate a PUT request with a body and expected response
	public static String validatePutRequest(String endpoint, String requestBody, int expectedStatusCode,
			String expectedResponseBody) {
		ValidatableResponse response = RestAssured.given().contentType("application/json").body(requestBody).when()
				.put(endpoint).then().statusCode(expectedStatusCode).body(equalTo(expectedResponseBody));
		return response.extract().body().asString();
	}

	// Generic method to validate a DELETE request
	public static String validateDeleteRequest(String endpoint, int expectedStatusCode) {
		ValidatableResponse response = RestAssured.given().when().delete(endpoint).then()
				.statusCode(expectedStatusCode);
		return response.extract().body().asString();
	}

	// Generic method to validate a PATCH request with a body and expected response
	public static String validatePatchRequest(String endpoint, String requestBody, int expectedStatusCode,
			String expectedResponseBody) {
		ValidatableResponse response = RestAssured.given().contentType("application/json").body(requestBody).when()
				.patch(endpoint).then().statusCode(expectedStatusCode).body(equalTo(expectedResponseBody));
		return response.extract().body().asString();
	}
}
