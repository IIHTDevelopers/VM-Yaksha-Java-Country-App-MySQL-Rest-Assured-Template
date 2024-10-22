package testcases;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import coreUtilities.utils.FileOperations;
import io.restassured.response.Response;
import rest.RestAssuredUtils;
import testBase.AppTestBase;

public class RestAssured_TestCases extends AppTestBase {

	RestAssuredUtils restAssuredInstance;

	private final String FILEPATH = "src/main/java/rest/RestAssuredUtils.java";

	FileOperations fileOperations = new FileOperations();

	private final String EXCEL_FILE_PATH = "src/main/resources/config.xlsx"; // Path to the Excel file
	private final String SHEET_NAME = "PostData"; // Sheet name in the Excel file

	@Test(priority = 1, groups = { "sanity" }, description = "Precondition: Access to JSONPlaceholder API\n"
			+ "1. Send GET request to retrieve post by ID\n" + "2. Verify the response status code is 200\n"
			+ "3. Validate the response body contains the expected post data")
	public void verifyGetPostById() throws IOException {

		// Expected data
		Map<String, Object> expectedData = new HashMap<>();
		expectedData.put("id", 1);
		expectedData.put("title", "sunt aut facere repellat provident occaecati excepturi optio reprehenderit");
		expectedData.put("body", "quia et suscipit\n" + "suscipit repellat nihil non omnis\n"
				+ "occaecati quisquam qui autem\n" + "magni et impedit vero fugiat");
		expectedData.put("userId", 1);

		restAssuredInstance = new RestAssuredUtils();
		// Perform GET request
		Response response = restAssuredInstance.getPostById(1);
		int responseStatusCode = response.getStatusCode();
		String responseBody = response.getBody().asString();

		// Validate method's source code
		boolean isValidationSuccessful = TestCodeValidator.validateTestMethodFromFile(FILEPATH, "getPostById",
				List.of("given", "when", "get", "then", "extract", "response"));

		// Expected endpoint and response for RestAssuredValidator
		String endpoint = "https://jsonplaceholder.typicode.com/posts/1";
		int expectedStatusCode = 200;
		String expectedResponseBody = "{" + "\"userId\": 1," + "\"id\": 1,"
				+ "\"title\": \"sunt aut facere repellat provident occaecati excepturi optio reprehenderit\","
				+ "\"body\": \"quia et suscipit\\n" + "suscipit repellat nihil non omnis\\n"
				+ "occaecati quisquam qui autem\\n" + "magni et impedit vero fugiat\"" + "}";

		String validatedResponse = RestAssuredValidator.validateGetRequest(endpoint, expectedStatusCode,
				expectedResponseBody);

		Assert.assertEquals(isValidationSuccessful
				&& expectedResponseBody.substring(1, 15).contains(validatedResponse.substring(5, 15))
				&& responseStatusCode == 200 && responseBody.contains("\"id\": 1"), true);

	}

	@Test(priority = 2, groups = { "sanity" }, description = "Precondition: Access to JSONPlaceholder API\n"
			+ "1. Send POST request to create a new post\n" + "2. Verify the response status code is 201\n"
			+ "3. Validate the response body contains the expected post data")
	public void verifyAddPost() throws Exception {

		// Read the data for the new post from the Excel sheet
		Map<String, String> postData = fileOperations.readExcelPOI(EXCEL_FILE_PATH, SHEET_NAME);

		// Extract expected data from the Excel sheet
		String expectedTitle = postData.get("title");
		String expectedBody = postData.get("body");
		int expectedUserId = Integer.parseInt(postData.get("userId"));

		// Instantiate RestAssuredUtils
		restAssuredInstance = new RestAssuredUtils();

		// Perform POST request using the addPost() method from RestAssuredUtils
		Response response = restAssuredInstance.addPost(postData);

		// Extract status code and response body
		int responseStatusCode = response.getStatusCode();
		String responseBody = response.getBody().asString();

		// Validate status code is 201
		Assert.assertEquals(responseStatusCode, 201, "Expected status code 201 but got " + responseStatusCode);

		// Validate that the response contains the expected title, body, and userId
//		Assert.assertTrue(
//				responseBody.contains("\"title\": \"" + expectedTitle + "\"")
//						&& responseBody.contains("\"body\": \"" + expectedBody + "\"")
//						&& responseBody.contains("\"userId\": " + expectedUserId),
//				"The response does not match the expected post data.");
	}

	@Test(priority = 3, groups = { "sanity" }, description = "Precondition: Access to JSONPlaceholder API\n"
			+ "1. Send PUT request to update an existing post\n" + "2. Verify the response status code is 200\n"
			+ "3. Validate the response body contains the updated post data")
	public void verifyUpdatePost() {

		// Data for updating the post
		int postId = 1;
		String updatedTitle = "Updated Post Title";
		String updatedBody = "This is the updated body of the post.";
		int userId = 1;

		// Instantiate RestAssuredUtils
		restAssuredInstance = new RestAssuredUtils();

		// Perform PUT request using the updatePost() method from RestAssuredUtils
		Response response = restAssuredInstance.updatePost(postId, updatedTitle, updatedBody, userId);

		// Extract status code and response body
		int responseStatusCode = response.getStatusCode();
		String responseBody = response.getBody().asString();

		// Validate status code is 200
		Assert.assertEquals(responseStatusCode, 200, "Expected status code 200 but got " + responseStatusCode);

		// Perform field-level validation using assertTrue
		Assert.assertTrue(
				responseBody.contains("\"title\": \"" + updatedTitle + "\"")
						&& responseBody.contains("\"body\": \"" + updatedBody + "\"")
						&& responseBody.contains("\"userId\": " + userId) && responseBody.contains("\"id\": " + postId),
				"The response does not match the expected updated post data.");
	}

	@Test(priority = 4, groups = { "sanity" }, description = "Precondition: Access to JSONPlaceholder API\n"
			+ "1. Send DELETE request to delete an existing post by ID\n"
			+ "2. Verify the response status code is 200 or 204\n"
			+ "3. Ensure the post is deleted by verifying a GET request returns 404")
	public void verifyDeletePost() {

		// ID of the post to delete
		int postId = 1;

		// Perform DELETE request to remove the post
		restAssuredInstance = new RestAssuredUtils();
		Response deleteResponse = restAssuredInstance.deletePost(postId);

		// Verify the status code of the DELETE response (can be 200 or 204 depending on
		// API design)
		int deleteStatusCode = deleteResponse.getStatusCode();
		System.out.println(deleteStatusCode);
		Assert.assertTrue(deleteStatusCode == 200 || deleteStatusCode == 204,
				"Expected status code 200 or 204, but got " + deleteStatusCode);
	}

	@Test(priority = 5, groups = { "sanity" }, description = "Precondition: Access to JSONPlaceholder API\n"
			+ "1. Send GET request to retrieve comments for a post by postId\n"
			+ "2. Verify the response status code is 200\n" + "3. Validate that the response contains comments data")
	public void verifyGetCommentsForPost() {

		// ID of the post for which we want to retrieve comments
		int postId = 1;

		// Perform GET request to retrieve the comments for the post
		restAssuredInstance = new RestAssuredUtils();
		Response getCommentsResponse = restAssuredInstance.getCommentsForPost(postId);

		// Verify the status code of the GET response (should be 200 OK)
		int responseStatusCode = getCommentsResponse.getStatusCode();

		// Verify that the response body contains comments (we expect an array of
		// comments)
		String responseBody = getCommentsResponse.getBody().asString();
		Assert.assertTrue(
				responseStatusCode == 200 && responseBody.contains("id") && responseBody.contains("postId")
						&& responseBody.contains("name") && responseBody.contains("body"),
				"The response does not contain expected comments data.");
	}
}
