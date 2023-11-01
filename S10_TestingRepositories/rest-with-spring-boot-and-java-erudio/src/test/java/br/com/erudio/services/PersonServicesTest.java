package br.com.erudio.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.erudio.exceptions.ResourceNotFoundException;
import br.com.erudio.model.Person;
import br.com.erudio.repositories.PersonRepository;

@ExtendWith(MockitoExtension.class)
class PersonServicesTest {

    @Mock
    private PersonRepository repository;

    @InjectMocks
    private PersonServices service;

    private Person person0;

    @BeforeEach
    public void setup() {
        // Given / Arrange
        person0 = new Person(
                "Suleiman",
                "Moraes",
                "leandro@erudio.com.br",
                "Uberlândia - Minas Gerais - Brasil",
                "Male");
    }

    @Test
    @DisplayName("JUnit test Given Person Object When Save Person Then Return Person Object")
    void testGivenPersonObject_WhenSavePerson_ThenReturnPersonObject() {

        // Given / Arrange
        given(repository.findByEmail(anyString())).willReturn(Optional.empty());
        given(repository.save(person0)).willReturn(person0);

        // When / Act
        Person savedPerson = service.create(person0);

        // Then / Assert
        assertNotNull(savedPerson);
        assertEquals("Suleiman", savedPerson.getFirstName());
    }

    @Test
    @DisplayName("JUnit test Given Existing Email When Save Person Then Throws Exception")
    void testGivenExistingEmail_WhenSavePerson_ThenThrowsException() {

        // Given / Arrange
        given(repository.findByEmail(anyString())).willReturn(Optional.of(person0));

        // When / Act
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> service.create(person0));

        // Then / Assert
        verify(repository, never()).save(any(Person.class));
        assertEquals("Person alteady exist with given e-Mail: " + person0.getEmail(), exception.getMessage(),
                () -> "Unexpected exception message!");
    }

    @Test
    @DisplayName("JUnit test Given List When findAll Then Return List")
    void testGivenList_WhenFindAll_ThenReturnList() {

        final Person person1 = new Person("Leonardo",
                "Costa",
                "leonardo@erudio.com.br",
                "Uberlândia - Minas Gerais - Brasil", "Male");

        // Given / Arrange
        given(repository.findAll()).willReturn(List.of(person0, person1));

        // When / Act
        List<Person> list = service.findAll();

        // Then / Assert
        assertNotNull(list);
        assertEquals(2, list.size());
    }

    
    @Test
    @DisplayName("JUnit test Given List When findAll Then Return Empty List")
    void testGivenList_WhenFindAll_ThenReturnEmptyList() {

        // Given / Arrange
        given(repository.findAll()).willReturn(Collections.emptyList());

        // When / Act
        List<Person> list = service.findAll();

        // Then / Assert
        assertNotNull(list);
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
    }

    @Test
    @DisplayName("JUnit test Given Id When findById Then Return Object")
    void testGivenId_WhenFindById_ThenReturnObject() {

        // Given / Arrange
        given(repository.findById(anyLong())).willReturn(Optional.of(person0));

        // When / Act
        Person savedPerson = service.findById(1L);

        // Then / Assert
        assertNotNull(savedPerson);
        assertEquals("Suleiman", savedPerson.getFirstName());
    }

    @Test
    @DisplayName("JUnit test Given Object When Update Then Return Object")
    void testGivenObject_WhenUpdate_ThenReturnObject() {

        // Given / Arrange
        person0.setId(1L);
        given(repository.findById(anyLong())).willReturn(Optional.of(person0));

        person0.setFirstName("Susu");
        person0.setEmail("susu@susu.com");

        given(repository.save(person0)).willReturn(person0);

        // When / Act
        Person updatedPerson = service.update(person0);

        // Then / Assert
        assertNotNull(updatedPerson);
        assertEquals("Susu", updatedPerson.getFirstName());
        assertEquals("susu@susu.com", updatedPerson.getEmail());
    }

    @Test
    @DisplayName("JUnit test Given Id When Delete Then Do Nothing")
    void testGivenId_WhenDelete_ThenDoNothing() {

        // Given / Arrange
        person0.setId(1L);
        given(repository.findById(anyLong())).willReturn(Optional.of(person0));
        willDoNothing().given(repository).delete(person0);

        // When / Act
        service.delete(person0.getId());

        // Then / Assert
        verify(repository, times(1)).delete(person0);
    }
}
