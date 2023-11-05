package br.com.erudio.integration.controller;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.List;

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
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class PersonControllerIntegrationTest extends AbstractIntegrationTest {

    private static RequestSpecification specification;
    private static ObjectMapper mapper;
    private static Person person;
    private Long id2 = 0l;

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
        var response = create(person);

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

    private Response create(Person person) {
        var response = given()
                .spec(specification)
                .contentType(TestConfig.CONTENT_TYPE_JSON)
                .body(person)
                .when()
                .post();

        // Verify that the response status code is 200 OK
        response.then().statusCode(200);
        return response;
    }

    @DisplayName("JUnit form Given Object When Updated Then Return Saved Object")
    @Test
    @Order(2)
    void testIntegrationGivenObject_When_UpdateOnePerson_ShouldReturnObject() throws Exception {
        final String firstName = "Suleiman";
        final String lastName = "Alves de Moraes";
        final String address = "Goiânia - Goiás - Brasil";
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setAddress(address);
        var response = given()
                .spec(specification)
                .contentType(TestConfig.CONTENT_TYPE_JSON)
                .body(person)
                .when()
                .put();

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
        assertEquals(firstName, createdPerson.getFirstName());
        assertEquals(lastName, createdPerson.getLastName());
        assertEquals("susu@susu.com.br", createdPerson.getEmail());
        assertEquals(address, createdPerson.getAddress());
        assertEquals("Male", createdPerson.getGender());
    }

    @DisplayName("JUnit form Given Id When FindById Then Return Saved Object")
    @Test
    @Order(3)
    void testIntegrationGivenId_When_FindByIdOnePerson_ShouldReturnObject() throws Exception {
        final String firstName = "Suleiman";
        final String lastName = "Alves de Moraes";
        final String address = "Goiânia - Goiás - Brasil";
        var response = given()
                .spec(specification)
                .pathParam("id", person.getId())
                .when()
                .get("{id}");

        // Verify that the response status code is 200 OK
        response.then().statusCode(200);

        // Extract the response body as a string
        Person person = mapper.readValue(response.getBody().asString(), Person.class);

        assertNotNull(person);
        assertNotNull(person.getId());
        assertNotNull(person.getFirstName());
        assertNotNull(person.getLastName());
        assertNotNull(person.getEmail());
        assertNotNull(person.getAddress());
        assertNotNull(person.getGender());
        assertEquals(firstName, person.getFirstName());
        assertEquals(lastName, person.getLastName());
        assertEquals("susu@susu.com.br", person.getEmail());
        assertEquals(address, person.getAddress());
        assertEquals("Male", person.getGender());
    }

    @DisplayName("JUnit form When FindAll Then Return List of Object")
    @Test
    @Order(4)
    void testIntegration_When_FindAll_ShouldReturnObject() throws Exception {
        create(new Person("Susu", "Moraes", "susu2@susu.com.br", "Uberlandia - Minas Gerais - Brasil", "Male"));
        final String firstName = "Suleiman";
        final String lastName = "Alves de Moraes";
        final String address = "Goiânia - Goiás - Brasil";
        var response = given()
                .spec(specification)
                .when()
                .get();

        // Verify that the response status code is 200 OK
        response.then().statusCode(200);

        // Extract the response body as a string
        List<Person> persons = Arrays.asList(mapper.readValue(response.getBody().asString(), Person[].class));

        Person person1 = persons.get(0);
        Person person2 = persons.get(1);
        id2 = person2.getId();
        delete(id2);

        assertNotNull(person1);
        assertNotNull(person1.getId());
        assertNotNull(person1.getFirstName());
        assertNotNull(person1.getLastName());
        assertNotNull(person1.getEmail());
        assertNotNull(person1.getAddress());
        assertNotNull(person1.getGender());
        assertEquals(firstName, person1.getFirstName());
        assertEquals(lastName, person1.getLastName());
        assertEquals("susu@susu.com.br", person1.getEmail());
        assertEquals(address, person1.getAddress());

        assertNotNull(person2);
        assertNotNull(person2.getId());
        assertNotNull(person2.getFirstName());
        assertNotNull(person2.getLastName());
        assertNotNull(person2.getEmail());
        assertNotNull(person2.getAddress());
        assertNotNull(person2.getGender());
        assertEquals("Susu", person2.getFirstName());
        assertEquals("Moraes", person2.getLastName());
        assertEquals("susu2@susu.com.br", person2.getEmail());
        assertEquals("Uberlandia - Minas Gerais - Brasil", person2.getAddress());
    }

    @DisplayName("JUnit form Given Id When Delete Then Return No Content")
    @Test
    @Order(5)
    void testIntegrationGivenId_When_DeleteOnePerson_ShouldReturnNoContent() throws Exception {
        var response = delete(person.getId());

        // Verify that the response status code is 204 No Content
        response.then().statusCode(204);
    }

    private Response delete(Long id) {
        var response = given()
                .spec(specification)
                .pathParam("id", id)
                .when()
                .delete("{id}");
        return response;
    }
}
