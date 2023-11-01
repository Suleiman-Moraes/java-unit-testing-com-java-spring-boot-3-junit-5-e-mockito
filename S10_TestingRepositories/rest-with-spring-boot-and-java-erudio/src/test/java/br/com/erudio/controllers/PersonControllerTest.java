package br.com.erudio.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.erudio.exceptions.ResourceNotFoundException;
import br.com.erudio.model.Person;
import br.com.erudio.services.PersonServices;

@WebMvcTest
class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private PersonServices service;

    private Person person;

    @BeforeEach
    public void setup() {
        // Given / Arrange
        person = new Person(
                "Suleiman",
                "Moraes",
                "leandro@erudio.com.br",
                "Uberlândia - Minas Gerais - Brasil",
                "Male");
    }

    // test[System Under Test]_[Condition or State Change]_[Expected Result]
    @DisplayName("JUnit form Given Object When Created Then Return Saved Object")
    @Test
    void testGivenObject_WhenCreated_ThenReturnSavedObject() throws Exception {
        // BDDMockito;
        // Given / Arrange
        given(service.create(any(Person.class))).willAnswer((invocation) -> invocation.getArgument(0));

        // When / Act
        ResultActions response = mockMvc.perform(post("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(person)));

        // Then / Assert
        response.andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(person.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(person.getLastName())))
                .andExpect(jsonPath("$.email", is(person.getEmail())));
    }

    // test[System Under Test]_[Condition or State Change]_[Expected Result]
    @DisplayName("JUnit form Given List Object When findAll Then Return List Object")
    @Test
    void testGivenListObject_WhenFindAll_ThenReturnListObject() throws Exception {
        // Given / Arrange
        List<Person> persons = new LinkedList<>();
        persons.add(person);
        persons.add(new Person(
                "Susu",
                "Alves",
                "susu@susu.com.br",
                "Uberlândia - Minas Gerais - Brasil",
                "Male"));

        given(service.findAll()).willReturn(persons);

        // When / Act
        ResultActions response = mockMvc.perform(get("/person"));

        // Then / Assert
        response
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", is(persons.size())));
    }

    // test[System Under Test]_[Condition or State Change]_[Expected Result]
    @DisplayName("JUnit form Given Id When findById Then Return Object")
    @Test
    void testGivenId_WhenFindById_ThenReturnObject() throws Exception {
        // Given / Arrange
        final long id = 1l;
        given(service.findById(id)).willReturn(person);

        // When / Act
        ResultActions response = mockMvc.perform(get("/person/{id}", id));

        // Then / Assert
        response.andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(person.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(person.getLastName())))
                .andExpect(jsonPath("$.email", is(person.getEmail())));
    }

    // test[System Under Test]_[Condition or State Change]_[Expected Result]
    @DisplayName("JUnit form Given Invalid Id When findById Then Return Not Found")
    @Test
    void testGivenInvalidId_WhenFindById_ThenReturnNotFound() throws Exception {
        // Given / Arrange
        final long id = 1l;
        given(service.findById(id)).willThrow(ResourceNotFoundException.class);

        // When / Act
        ResultActions response = mockMvc.perform(get("/person/{id}", id));

        // Then / Assert
        response
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    // test[System Under Test]_[Condition or State Change]_[Expected Result]
    @DisplayName("JUnit form Given Update Object When update Then Return Updated Object")
    @Test
    void testGivenUpdateObject_WhenUpdate_ThenReturnObject() throws Exception {
        // Given / Arrange
        final long id = 1l;
        Person updatedPerson = new Person(
                "Susu",
                "Alves",
                "susu@susu.com.br",
                "Uberlândia - Minas Gerais - Brasil",
                "Male");
        given(service.findById(id)).willReturn(person);
        given(service.update(any(Person.class))).willAnswer((invocation) -> invocation.getArgument(0));

        // When / Act
        ResultActions response = mockMvc.perform(put("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(updatedPerson)));

        // Then / Assert
        response.andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(updatedPerson.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(updatedPerson.getLastName())))
                .andExpect(jsonPath("$.email", is(updatedPerson.getEmail())));
    }

    // test[System Under Test]_[Condition or State Change]_[Expected Result]
    @DisplayName("JUnit form Given Unexistent Object When update Then Return Not Found")
    @Test
    void testGivenUnexistentObject_WhenUpdate_ThenReturnNotFound() throws Exception {
        // Given / Arrange
        final long id = 1l;
        Person updatedPerson = new Person(
                "Susu",
                "Alves",
                "susu@susu.com.br",
                "Uberlândia - Minas Gerais - Brasil",
                "Male");
        given(service.findById(id)).willThrow(ResourceNotFoundException.class);
        given(service.update(any(Person.class))).willAnswer((invocation) -> invocation.getArgument(1));

        // When / Act
        ResultActions response = mockMvc.perform(put("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(updatedPerson)));

        // Then / Assert
        response
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    // test[System Under Test]_[Condition or State Change]_[Expected Result]
    @DisplayName("JUnit form Given Id When delete Then Return Not Content")
    @Test
    void testGivenId_WhenDelete_ThenReturnNotContent() throws Exception {
        // Given / Arrange
        final long id = 1l;
        willDoNothing().given(service).delete(id);

        // When / Act
        ResultActions response = mockMvc.perform(delete("/person/{id}", id));

        // Then / Assert
        response
                .andExpect(status().isNoContent())
                .andDo(print());
    }
}
