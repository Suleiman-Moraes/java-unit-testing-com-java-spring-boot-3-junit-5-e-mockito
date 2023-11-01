package com.moraes.business;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;

public class ListTest {

	// test[System Under Test]_[Condition or State Change]_[Expected Result]
	@Test
	void testMockingList_When_SizeIsCalled_ShouldReturn() {
		// Given / Arrange
		List<?> list = mock(List.class);
		when(list.size()).thenReturn(10);

		// When / Act
		// Then / Assert
		assertEquals(list.size(), 10);
	}

	@Test
	void testMockingList_When_SizeIsCalled_ShouldReturnMultipleValues() {
		// Given / Arrange
		List<?> list = mock(List.class);
		when(list.size()).thenReturn(10).thenReturn(20);

		// When / Act
		// Then / Assert
		assertEquals(10, list.size());
		assertEquals(20, list.size());
		assertEquals(20, list.size());
	}

	@Test
	void testMockingList_When_GetIsCalled_ShouldReturnMoraes() {
		// Given / Arrange
		var list = mock(List.class);
		when(list.get(0)).thenReturn("Moraes");

		// When / Act
		// Then / Assert
		assertEquals("Moraes", list.get(0));
		assertNull(list.get(1));
	}

	@Test
	void testMockingList_When_ThrowsAnException() {
		// Given / Arrange
		var list = mock(List.class);
		when(list.get(anyInt())).thenThrow(new RuntimeException("Foo Bar!!"));

		// When / Act
		// Then / Assert
		assertThrows(RuntimeException.class, () -> list.get(anyInt()), () -> "Should have throw an RuntimeException");
	}
}
