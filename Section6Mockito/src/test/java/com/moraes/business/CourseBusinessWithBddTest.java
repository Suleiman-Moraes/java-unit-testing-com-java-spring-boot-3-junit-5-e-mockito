package com.moraes.business;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import com.moraes.service.CourseService;

class CourseBusinessWithBddTest {

	private static final String SULEIMAN = "Suleiman";
	private static final String SOME_COURSE_WITHOUT_SPRING = "Java for ninjas";
	private static final String SOME_COURSE_WITH_SPRING = "Spring Frameork for ninjas";

	CourseService mockService;
	CourseBusiness business;
	List<String> courses;

	@BeforeEach
	void setup() {
		// Given / Arrange
		mockService = mock(CourseService.class);
		business = new CourseBusiness(mockService);

		courses = Arrays.asList("REST API's RESTFul do 0 à Azure com ASP.NET Core 5 e Docker",
				"Agile Desmistificado com Scrum, XP, Kanban e Trello", "Spotify Engineering Culture Desmistificado",
				"REST API's RESTFul do 0 à AWS com Spring Boot 3 Java e Docker",
				"Docker do Zero à Maestria - Contêinerização Desmistificada",
				"Docker para Amazon AWS Implante Apps Java e .NET com Travis CI",
				"Microsserviços do 0 com Spring Cloud, Spring Boot e Docker",
				"Arquitetura de Microsserviços do 0 com ASP.NET, .NET 6 e C#",
				"REST API's RESTFul do 0 à AWS com Spring Boot 3 Kotlin e Docker",
				"Kotlin para DEV's Java: Aprenda a Linguagem Padrão do Android",
				"Microsserviços do 0 com Spring Cloud, Kotlin e Docker", SOME_COURSE_WITHOUT_SPRING,
				SOME_COURSE_WITH_SPRING);
	}

	// test[System Under Test]_[Condition or State Change]_[Expected Result]
	@Test
	void testRetrieveCoursesRelatedToSpring_When_UsingAMock() {
		// Given / Arrange
		given(mockService.retrieveCourses(SULEIMAN)).willReturn(courses);

		// When / Act
		var filteredCourses = business.retrieveCoursesRelatedToSpring(SULEIMAN);

		// Then / Assert
		assertThat(filteredCourses.size(), is(5));
	}

	// test[System Under Test]_[Condition or State Change]_[Expected Result]
	@DisplayName("Delete Courses not Related to Spring Using Mockito should call Method deleteCourse")
	@Test
	void testDeleteCoursesNotRelatedToSpring_UsingMockitoVerify_ShouldCallMethod_deleteCourse() {

		// Given / Arrange
		given(mockService.retrieveCourses(SULEIMAN)).willReturn(courses);
		// When / Act
		business.deleteCoursesNotRelatedToSpring(SULEIMAN);
		// Then / Assert
		verify(mockService, times(1)).deleteCourse(SOME_COURSE_WITHOUT_SPRING);
		verify(mockService, atLeast(1)).deleteCourse(SOME_COURSE_WITHOUT_SPRING);
		verify(mockService, atLeastOnce()).deleteCourse(SOME_COURSE_WITHOUT_SPRING);
		verify(mockService, never()).deleteCourse(SOME_COURSE_WITH_SPRING);
	}

	// test[System Under Test]_[Condition or State Change]_[Expected Result]
	@DisplayName("Delete Courses not Related to Spring Using Mockito should call Method deleteCourse V2")
	@Test
	void testDeleteCoursesNotRelatedToSpring_UsingMockitoVerify_ShouldCallMethod_deleteCourseV2() {

		// Given / Arrange
		given(mockService.retrieveCourses(SULEIMAN)).willReturn(courses);
		// When / Act
		business.deleteCoursesNotRelatedToSpring(SULEIMAN);
		// Then / Assert
		then(mockService).should().deleteCourse(SOME_COURSE_WITHOUT_SPRING);
		then(mockService).should(never()).deleteCourse(SOME_COURSE_WITH_SPRING);
	}

	// test[System Under Test]_[Condition or State Change]_[Expected Result]
	@DisplayName("Delete Courses not Related to Spring Capturin Arguments should call Method deleteCourse")
	@Test
	void testDeleteCoursesNotRelatedToSpring_CapturinArguments_ShouldCallMethod_deleteCourse() {

		// Given / Arrange
		courses = Arrays.asList(SOME_COURSE_WITHOUT_SPRING, SOME_COURSE_WITH_SPRING);
		given(mockService.retrieveCourses(SULEIMAN)).willReturn(courses);
		ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);

		// When / Act
		business.deleteCoursesNotRelatedToSpring(SULEIMAN);

		// Then / Assert
		then(mockService).should().deleteCourse(argumentCaptor.capture());
		assertThat(argumentCaptor.getValue(), is(SOME_COURSE_WITHOUT_SPRING));
	}
	
	// test[System Under Test]_[Condition or State Change]_[Expected Result]
	@DisplayName("Delete Courses not Related to Spring Capturin Arguments should call Method deleteCourse V2")
	@Test
	void testDeleteCoursesNotRelatedToSpring_CapturinArguments_ShouldCallMethod_deleteCourseV2() {
		
		// Given / Arrange
		given(mockService.retrieveCourses(SULEIMAN)).willReturn(courses);
		ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
		
		// When / Act
		business.deleteCoursesNotRelatedToSpring(SULEIMAN);
		
		// Then / Assert
		then(mockService).should(times(8)).deleteCourse(argumentCaptor.capture());
		assertThat(argumentCaptor.getAllValues().size(), is(8));
	}
}
