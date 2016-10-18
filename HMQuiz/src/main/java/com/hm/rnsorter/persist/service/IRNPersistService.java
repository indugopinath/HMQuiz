package com.hm.rnsorter.persist.service;

import java.util.List;

import com.hm.rnsorter.model.RandomNumber;

/**
 * @author InduGopinath
 * The interface to do the filesystem operations(persist/delete/retrieve)
 */
public interface IRNPersistService {
	
	  /**
	 * The File path to sortedList
	 */
	final String filePathSorted="./NumberList.txt";
	  
	  /**
	 * The file path to the History file
	 */
	final String filePathHistory="./NumberListHistory.txt";
	  
	  /**
	 * Notation for Numbers field in file
	 */
	final String Numbers="NUMBERS";
	  
	  /**
	 * Notation for Duration field
	 */
	final String Duration="DURATION";
	  
	  /**
	 * Notation to Swaps file in Filesystem
	 */
	final String Swaps="SWAPS";

	  
	/**To persist the new Random numer(RN) generated to the filesystem
	 * @param newValue
	 */
	void persistNewRN(double newValue);
	
	/**
	 * To get all the random numbers persisted 
	 * includes the sorted List, duration,number of swaps
	 * @return RandomNumber
	 */
	RandomNumber getAllRandomNumbers();
	
	
	/** To get all random number history
	 * @return List
	 */
	List<RandomNumber> getAllRandomNumbersHistory();
	
	 /** To persist a Sorted List to the HistoryFile
	 * @param randomList
	 */
	void persistsortedHistory(List<RandomNumber> randomList);
	
	
	/** To persist the sorted List
	 * @param newList
	 */
	void persistsortedList(double[] newList);

	
	/** To delete the content of sorted list from Filesystem
	 * 
	 */
	void deletecontent();
	
}
