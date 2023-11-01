package br.com.erudio.integration.swagger;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.erudio.config.TestConfig;
import br.com.erudio.integration.testcontainer.AbstractIntegrationTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class SwaggerIntegrationTest extends AbstractIntegrationTest {

	/**
	 * JUnit test for displaying Swagger UI Page.
	 *
	 */
	@Test
	@DisplayName("JUnit test for displaying Swagger UI Page")
	void testDisplaySwaggerUiPage() {
		// Send GET request to /swagger-ui/index.html endpoint
		var response = given()
				.basePath("/swagger-ui/index.html")
				.port(TestConfig.SERVER_PORT)
				.when()
				.get();

		// Verify that the response status code is 200 OK
		response.then().statusCode(200);

		// Extract the response body as a string
		var responseBody = response.getBody().asString();

		// Verify that the response body contains "Swagger UI"
		assertTrue(responseBody.contains("Swagger UI"));
	}
}
