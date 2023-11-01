package com.moraes.business;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import java.util.List;

import org.junit.jupiter.api.Test;

public class ListWithBddTest {

	// test[System Under Test]_[Condition or State Change]_[Expected Result]
	@Test
	void testMockingList_When_SizeIsCalled_ShouldReturn() {
		// Given / Arrange
		List<?> list = mock(List.class);
		given(list.size()).willReturn(10);

		// given / Act
		// Then / Assert
		assertThat(list.size(), is(10));
	}

	@Test
	void testMockingList_When_SizeIsCalled_ShouldReturnMultipleValues() {
		// Given / Arrange
		List<?> list = mock(List.class);
		given(list.size()).willReturn(10).willReturn(20);

		// When / Act
		// Then / Assert
		assertThat(list.size(), is(10));
		assertThat(list.size(), is(20));
		assertThat(list.size(), is(20));
	}

	@Test
	void testMockingList_When_GetIsCalled_ShouldReturnMoraes() {
		// Given / Arrange
		var list = mock(List.class);
		given(list.get(0)).willReturn("Moraes");

		// When / Act
		// Then / Assert
		assertThat(list.get(0), is("Moraes"));
		assertNull(list.get(1));
	}

	@Test
	void testMockingList_When_ThrowsAnException() {
		// Given / Arrange
		var list = mock(List.class);
		given(list.get(anyInt())).willThrow(new RuntimeException("Foo Bar!!"));

		// When / Act
		// Then / Assert
		assertThrows(RuntimeException.class, () -> list.get(anyInt()), () -> "Should have throw an RuntimeException");
	}
}
