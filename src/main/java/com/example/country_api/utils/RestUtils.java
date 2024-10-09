package com.example.country_api.utils;

import java.util.Map;

import io.restassured.response.Response;

public class RestUtils {

	// Dynamic URL Generation based on base URL and endpoint
	public RestUtils() {
	}

	// Load shared global variables (e.g., Base URL) from a properties file
	private String loadGlobalVariable(String key) {
		// write your logic here
		return null;
	}

	// 1. HTTP GET request to retrieve a country by ID (with optional content
	// negotiation)
	public Response getCountryById(int id, String acceptHeader) {
		// write your logic here
		return null;
	}

	// 2. HTTP POST request to add a new country
	public Response addCountry(String countryName) {
		// write your logic here
		return null;
	}

	// 3. HTTP PUT request to update an existing country by ID
	public Response updateCountry(int id, String newCountryName) {
		// write your logic here
		return null;
	}

	// 4. HTTP DELETE request to delete a country by ID
	public Response deleteCountry(int id) {
		// write your logic here
		return null;
	}

	// 5. HTTP PATCH request to partially update a country's name by ID
	public Response patchCountryName(int id, String newCountryName) {
		// write your logic here
		return null;
	}

	// 6. HTTP GET request to search for a country by name (query parameter)
	public Response searchCountryByName(String countryName) {
		// write your logic here
		return null;
	}

	// 7. HTTP GET request to retrieve a country by code (URI template)
	public Response getCountryByCode(String code) {
		// write your logic here
		return null;
	}

	// Dynamic URL generation with Base URL + Endpoint + Query/Path parameters
	public String buildURL(String endpoint, Map<String, String> queryParams) {
		// write your logic here
		return null;
	}

	// GET request with dynamic URL generation and query parameters
	public Response getCountryById(int id) {
		// write your logic here
		return null;
	}

	// GET request with dynamic query parameters
	public Response searchCountryByName2(String countryName) {
		// write your logic here
		return null;
	}

	// POST request to add a new country (Example for CSV data)
	public Response addCountry2(String countryName) {
		// write your logic here
		return null;
	}

	// DELETE request (Example with dynamic URL)
	public Response deleteCountry2(int id) {
		// write your logic here
		return null;
	}

	// 10. POST request for adding a country (validating request body)
	public Response addCountry3(String countryName) {
		// write your logic here
		return null;
	}

	// 11 & 12. GET request to retrieve a country by ID (validating response body
	// and status code)
	public Response getCountryById2(int id) {
		// write your logic here
		return null;
	}

	// 13. POST request to handle bulk add of countries (resources & payloads)
	public Response addMultipleCountries(Map<Integer, String> countries) {
		// write your logic here
		return null;
	}

	// 14. GET request to test different response body types (JSON, XML, Text)
	public Response getCountryByIdWithResponseType(int id, String acceptType) {
		// write your logic here
		return null;
	}
}
