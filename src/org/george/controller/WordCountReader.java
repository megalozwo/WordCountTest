package org.george.controller;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/* Coding test for Ensemble, written  using Google coding standards
 *Requires Java 8 
 *The Project can be tested either by running the main method or 
 *the corresponding JUnit Tests
 *The Project was built using Maven 3
 *
 *by George Tsigouris
 *gtsigouris@gmail.com
 *15/6/2016
 */


// The WordCountReader class aims to:  
// 1. Reading a txt file as an input
// 2. Finding patterns between the words
// 3. Remove a word if it's partial match of another
// 4. Sort the data with the largest words on top
// 5. Count the amount of times a non-partial match word appears

public class WordCountReader {
	
	public final static String filelocation = "./resources/test1";
	
	//String fileLocation = WordCountReader.class.getResource("test1.txt").getPath();

	public static HashSet<String> uniqueWords;
	public ArrayList<String> testfoo;
	
	// Using Java 8 Streams instead of bufferedReader to read the input file
    public static List<String> readFileIntoListOfWords() {
        try {
            return Files.readAllLines(Paths.get(filelocation))
                .stream()
                .map(l -> l.split("[\\W]+"))   //splitting the words so that we ll ignore symbols (i.e. !, * etc.)
                .flatMap(Arrays::stream)
                .collect(Collectors.toList());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
	
    // In this method we use a HashSet to remove duplicate values with 0(1) efficiency.
    private static <T> ArrayList<T> getUniqueWords(List<T> inputList) {
    	return new ArrayList<T>(new HashSet<T>(inputList));
    }
    
    
    // This method checks the List for any word that is a partial match of another word 
    // and removes it.
    public static List<String> removePartialMatches (List<String> finalList) {
    	
    	// Using CopyOnWriteArrayList because it's threadsafe(good practice) 
    	// and to be able to remove items while iterating.
    	List<String> finalWords = new CopyOnWriteArrayList<>();
    	List<String> copyOfFinal = new CopyOnWriteArrayList<>();
    	
    	for (String copy: finalList) {
    		if (!copy.contentEquals(""))
    		finalWords.add(copy);
    		copyOfFinal.add(copy);
    	}
    	
    	for (String unique1 : finalWords) {
    		for (String unique2 : copyOfFinal) {
	    			if (unique2.toLowerCase().contains(unique1.toLowerCase()) && unique2.length() > unique1.length()) {					
	    				finalWords.remove(unique1);	
	    			}
    		}
    	}
    	
    	finalList = finalWords;
    	return finalList;
    	
    }
    
    // The Last method focuses on sorting the List by using the appropriate data structures
    // and to count how many times a word appears (descending order in console output)
    private static List<String> wordCountAndSorting(List<String> originalList) {
    	
    	List<String>     checker  = removePartialMatches(originalList);
    	ListLengthSort   sorter   = new ListLengthSort();
    	TreeSet<String>  unique   = new TreeSet<String>(checker);
    	
    	Collections.sort(checker, sorter);
    	
    	System.out.println("Results: ");
    	for (String key : unique) {
    		System.out.println(key + ": " + Collections.frequency(checker, key ));
    	}

    	return checker;
    }
    
    // The main class that is used for testing
	public static void main (String args[]) throws IOException {
		System.out.println(System.getProperty("user.dir")); 
		List<String> test1 = readFileIntoListOfWords();

		ArrayList<String> test2 = getUniqueWords(test1);
		removePartialMatches(test2);
    	wordCountAndSorting(test1);

		
	}
	
	// Helper class that implements the Comparator interface for String Sorting
	static class ListLengthSort implements Comparator<String> 
	{
		@Override
		public int compare(String str1, String str2) {
			return str2.length() - str1.length();
	}
	}


	
		
}
