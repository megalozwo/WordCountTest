package org.george.tests;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import org.george.controller.WordCountReader;
import org.junit.Assert;
import org.junit.Test;

// Simple test class to showcase the WordCountReader functionality
// Using JUnit  4.11
// written by George Tsigouris
// gtsigouris@gmail.com


public class WordCounterReaderTest {
	
	@Test
	public void bufferedReaderSanityTest() throws IOException {
		
			String testString = "test\nstring";
			InputStream stream = new ByteArrayInputStream(testString.getBytes(StandardCharsets.UTF_8));
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
			
			Assert.assertEquals("test", reader.readLine());
			Assert.assertEquals("string", reader.readLine());

	}
	
	@Test
	public void removePartialMatchesTest() {
		List<String> partialTest = Arrays.asList(names());
		
		Assert.assertTrue(WordCountReader.removePartialMatches(partialTest).contains("maybe"));
		Assert.assertTrue(WordCountReader.removePartialMatches(partialTest).contains("random"));
		Assert.assertTrue(!WordCountReader.removePartialMatches(partialTest).contains("Java"));
		
		
	}
	
	@Test
	public void wordCountAndSortingTest(){
		
	}
	
	//A singleton to populate the list with mock data
	public enum E {
	    may,
	    maybe,
	    mayhem,
	    random,
	    word
	}
	
	 public static String[] names() {
	        String valuesStr = Arrays.toString(E.values());
	        return valuesStr.substring(1, valuesStr.length()-1).replace(" ", "").split(",");
	    }
	

}
