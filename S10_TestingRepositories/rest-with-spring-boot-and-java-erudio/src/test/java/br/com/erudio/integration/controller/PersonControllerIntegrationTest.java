package br.com.erudio.integration.controller;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.erudio.config.TestConfig;
import br.com.erudio.integration.testcontainer.AbstractIntegrationTest;
import br.com.erudio.model.Person;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class PersonControllerIntegrationTest extends AbstractIntegrationTest {

    private static RequestSpecification specification;
    private static ObjectMapper mapper;
    private static Person person;

    /**
     * Initializes the necessary objects and configurations for the test.
     */
    @BeforeAll
    public static void setup() {
        // Create an ObjectMapper instance
        mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        // Create a RequestSpecification instance
        specification = new RequestSpecBuilder()
                .setBasePath("/person")
                .setPort(TestConfig.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        // Create a Person instance
        person = new Person("Susu", "Moraes", "susu@susu.com.br", "Uberlandia - Minas Gerais - Brasil", "Male");
    }

    // test[System Under Test]_[Condition or State Change]_[Expected Result]
    @DisplayName("JUnit form Given Object When Created Then Return Saved Object")
    @Test
    @Order(1)
    void testIntegrationGivenObject_When_CreateOnePerson_ShouldReturnObject() throws Exception {
        var response = given()
                .spec(specification)
                .contentType(TestConfig.CONTENT_TYPE_JSON)
                .body(person)
                .when()
                .post();

        // Verify that the response status code is 200 OK
        response.then().statusCode(200);

        // Extract the response body as a string
        Person createdPerson = mapper.readValue(response.getBody().asString(), Person.class);
        person = createdPerson;

        assertNotNull(createdPerson);
        assertNotNull(createdPerson.getId());
        assertNotNull(createdPerson.getFirstName());
        assertNotNull(createdPerson.getLastName());
        assertNotNull(createdPerson.getEmail());
        assertNotNull(createdPerson.getAddress());
        assertNotNull(createdPerson.getGender());
        assertEquals("Susu", createdPerson.getFirstName());
        assertEquals("Moraes", createdPerson.getLastName());
        assertEquals("susu@susu.com.br", createdPerson.getEmail());
        assertEquals("Uberlandia - Minas Gerais - Brasil", createdPerson.getAddress());
        assertEquals("Male", createdPerson.getGender());
    }
}
