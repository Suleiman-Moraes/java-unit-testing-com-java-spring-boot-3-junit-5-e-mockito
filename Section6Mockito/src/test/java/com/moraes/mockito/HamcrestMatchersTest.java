package com.moraes.mockito;

import static org.hamcrest.CoreMatchers.everyItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayContaining;
import static org.hamcrest.Matchers.arrayContainingInAnyOrder;
import static org.hamcrest.Matchers.arrayWithSize;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThanOrEqualTo;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

class HamcrestMatchersTest {

	@Test
	void test() {
		// Given
		List<Integer> scores = Arrays.asList(99, 100, 101, 105);

		// When & Then
		assertThat(scores, hasSize(4));
		assertThat(scores, hasItems(100, 101));
		assertThat(scores, everyItem(greaterThanOrEqualTo(99)));
		assertThat(scores, everyItem(lessThanOrEqualTo(105)));
		
		// Check Strings
		assertThat("", is(emptyString()));
		assertThat(null, is(emptyOrNullString()));
		
		// Arrays
		Integer[] vet = {1, 2, 3};
		assertThat(vet, arrayWithSize(3));
		assertThat(vet, arrayContaining(1, 2, 3));
		assertThat(vet, arrayContainingInAnyOrder(3, 1, 2));
	}
}
