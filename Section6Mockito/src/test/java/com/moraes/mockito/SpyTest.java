package com.moraes.mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

@SuppressWarnings("unchecked")
class SpyTest {
	
	@Test
	void test() {
		// given
		List<String> mockArrayList = spy(ArrayList.class);
		
		//When and Then
		assertEquals(0, mockArrayList.size());
		
		when(mockArrayList.size()).thenReturn(5);
		mockArrayList.add("Foo-Bar");
		
		assertEquals(5, mockArrayList.size());
	}
	
	@Test
	void testV2() {
		// given
		List<String> spyArrayList = spy(ArrayList.class);
		
		//When and Then
		assertEquals(0, spyArrayList.size());
		
		spyArrayList.add("Foo-Bar");
		assertEquals(1, spyArrayList.size());
		
		spyArrayList.remove("Foo-Bar");
		assertEquals(0, spyArrayList.size());
	}
	
	@Test
	void testV3() {
		// given
		List<String> spyArrayList = spy(ArrayList.class);
		
		//When and Then
		assertEquals(0, spyArrayList.size());
		
		when(spyArrayList.size()).thenReturn(5);
		
		assertEquals(5, spyArrayList.size());
	}

	@Test
	void testV4() {
		// given
		List<String> spyArrayList = spy(ArrayList.class);
		
		//When and Then
		spyArrayList.add("Foo-Bar");
		
		verify(spyArrayList).add("Foo-Bar");
		verify(spyArrayList, never()).remove("Foo-Bar");
		verify(spyArrayList, never()).remove(anyString());
	}
}
