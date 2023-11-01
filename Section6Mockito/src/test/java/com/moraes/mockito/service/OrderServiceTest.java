package com.moraes.mockito.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mockStatic;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

class OrderServiceTest {

	private final OrderService service = new OrderService();
	private final UUID defaultUuid = UUID.fromString("8d8b30e3-de52-4f1c-a71c-9905a8043dac");
	private final LocalDateTime defaultDateTime = LocalDateTime.of(2023, 10, 6, 17, 49);

	void testCreateOrder() {
	}
	
	// test[System Under Test]_[Condition or State Change]_[Expected Result]
	@DisplayName("Should Include Random OrderID When No OrderId Exists")
	@Test
	void testShouldIncludeOrderId_When_NoOrderIdExists() {
		
		// Given / Arrange
		try (MockedStatic<UUID> mockedUuid = mockStatic(UUID.class)) {
			mockedUuid.when(UUID::randomUUID).thenReturn(defaultUuid);
			
			// When / Act
			Order result = service.createOrder("MacBook Pro", 2L, null);
			
			// Then / Assert
			assertEquals(defaultUuid.toString(), result.getId());
		}
	}

	// test[System Under Test]_[Condition or State Change]_[Expected Result]
	@DisplayName("Should Include CurrentTime When Create A New Order")
	@Test
	void testShouldIncludeCurrentTime_When_CreateANewOrder() {

		// Given / Arrange
		try (MockedStatic<LocalDateTime> mockedLocalDateTime = mockStatic(LocalDateTime.class)) {
			mockedLocalDateTime.when(LocalDateTime::now).thenReturn(defaultDateTime);

			// When / Act
			Order result = service.createOrder("MacBook Pro", 2L, null);

			// Then / Assert
			assertEquals(defaultDateTime, result.getCreationDate());
		}
	}
}
