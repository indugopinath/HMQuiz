/**
 * The service class to persist/retrieve/delete filesystem content
 */
package com.hm.rnsorter.persist.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.hm.rnsorter.model.RandomNumber;
import com.hm.rnsorter.sorting.service.IRNSortService;

/**
 * @author InduGopinath
 *
 */
@Qualifier(value = "RNPersistService")
@Service
public class RNPersistService implements IRNPersistService {

	/**
	 * Wiring the RNSort service to sort
	 */
	@Autowired
	private IRNSortService RNService;

	//TODO Implement the logging
	private static final org.slf4j.Logger log=org.slf4j.LoggerFactory.getLogger(RNPersistService.class);
	
	
	/**
	 * Method to Persist the Random values to FileSystem
	 * @param newValue
	 */
	public void persistNewRN(double newValue) {
		String strVal = newValue + "\n";
		try {
			Files.write(Paths.get(filePathSorted), strVal.getBytes(), StandardOpenOption.APPEND,
					StandardOpenOption.CREATE);
		} catch (IOException io) {
			log.error("In the persistNewRN method:" + io.getMessage());
		}

	}

	
	/**
	 * To get all the Random numbers
	 * @see com.hm.rnsorter.sorting.service.IRNSortService#getAllRandomNumbers()
	 */
	@Override
	public RandomNumber getAllRandomNumbers() {
		// Retrieve the List from the file system
		double[] listArray = null;
		try (Stream<String> allines = Files.lines(Paths.get(filePathSorted))) {
			listArray = allines.mapToDouble(Double::parseDouble).toArray();

		} catch (IOException e) {
			System.out.println("In the getAllRandomNumbers:" + e.getMessage());
		}
		// To sort the List
		RandomNumber random = RNService.sortList(listArray);
		return random;
	}

	
	/**
	 * To get all the Sorting history details
	 * @see com.hm.rnsorter.sorting.service.IRNPersistService#getAllRandomNumbersHistory()
	 */
	@Override
	public List<RandomNumber> getAllRandomNumbersHistory() {
		List<String> allContent = null;
		double[] listArray = null;
		List<RandomNumber> randomList = new ArrayList<>();

		boolean numberFlag = false;
		boolean swapFlag = false;
		boolean durationFlag = false;
		List<Double> randomNS = new ArrayList<>();
		RandomNumber rn = null;
		try (Stream<String> allines = Files.lines(Paths.get(filePathHistory))) {
			allContent = allines.collect(Collectors.toList());
			if (allContent != null && !allContent.isEmpty()) {
				for (int i = 0; i < allContent.size(); i++) {
					if (allContent.get(i) != null && allContent.get(i).trim().equals(Numbers)) {
						//Reset the values
						rn = new RandomNumber();
						randomNS = new ArrayList<>();
						// set the flags to read
						numberFlag = true;
						swapFlag = false;
						durationFlag = false;
						
					} else if (numberFlag && !(allContent.get(i).trim().equals(Swaps))
							&& !(allContent.get(i).trim().equals(Duration))) {
						randomNS.add(Double.parseDouble(allContent.get(i)));
					
					} else if (allContent.get(i) != null && allContent.get(i).trim().equals(Swaps)) {
						//System.out.println("Came to swaps");
						numberFlag = false;
						swapFlag = true;
						durationFlag = false;
					
					} else if (swapFlag && !(allContent.get(i).trim().equals(Numbers))
							&& !(allContent.get(i).trim().equals(Duration))) {
						//System.out.println("Adding swaps");
						rn.setSwapNumber(Integer.parseInt(allContent.get(i).trim()));
					
					} else if (allContent.get(i) != null && allContent.get(i).trim().equals(Duration)) {
						//System.out.println("Came to duration");
						numberFlag = false;
						swapFlag = false;
						durationFlag = true;
					
					} else if (durationFlag && !(allContent.get(i).trim().equals(Numbers))
							&& !(allContent.get(i).trim().equals(Swaps))) {
						rn.setSortDuration(Integer.parseInt(allContent.get(i).trim()));

						// To set the double array forRandomNumbers
						//TODO:revist the code for better logic(if time permits)
						if (randomNS != null && randomNS.size() > 0) {
							listArray = new double[randomNS.size()];
							for (int j = 0; j < randomNS.size(); j++) {
								listArray[j] = randomNS.get(j);
							}
						}

						rn.setSortedList(listArray);

						//Add to the final list
						randomList.add(rn);

					}

				}
			} else
			{
				log.info("There are no history details available");
			}
		} catch (IOException e) {
			System.out.println("In the getAllRandomNumbers:" + e.getMessage());
		}
	
		return randomList;
	}


	/**
	 * Write to the History File
	 * 
	 * @param randomList
	 */
	public void persistsortedHistory(List<RandomNumber> randomList) {

		try {
			if (randomList != null && randomList.size() > 0) {
		
				Files.write(Paths.get(filePathHistory), "NUMBERS \n".getBytes(), StandardOpenOption.APPEND,
						StandardOpenOption.CREATE);
				
				for (RandomNumber rn : randomList) {
					for (int i = 0; i < rn.getSortedList().length; i++) {
						String strVal = rn.getSortedList()[i] + " \n";
						
						Files.write(Paths.get(filePathHistory), strVal.getBytes(), StandardOpenOption.APPEND,
								StandardOpenOption.CREATE);
					}

					String swapnum = rn.getSwapNumber() + " \n";
					String swapduration = rn.getSortDuration() + " \n";
					Files.write(Paths.get(filePathHistory), "SWAPS \n".getBytes(), StandardOpenOption.APPEND,
							StandardOpenOption.CREATE);
					Files.write(Paths.get(filePathHistory), swapnum.getBytes(), StandardOpenOption.APPEND,
							StandardOpenOption.CREATE);
					Files.write(Paths.get(filePathHistory), "DURATION \n".getBytes(), StandardOpenOption.APPEND,
							StandardOpenOption.CREATE);
					Files.write(Paths.get(filePathHistory), swapduration.getBytes(), StandardOpenOption.APPEND,
							StandardOpenOption.CREATE);
				}
			}
		} catch (IOException io) {
			System.out.println("In the persistsortedList method:" + io.getMessage());
		}
	}

	
	/**To persist the sorted list
	 * @see com.hm.rnsorter.sorting.service.IRNPersistService#persistsortedList(double[])
	 */
	public void persistsortedList(double[] newList) {
		if (newList != null && newList.length > 0) {
			for (int i = 0; i < newList.length; i++) {
				String strVal = newList[i] + " \n";
				// String newLine="\n";
				try {
					Files.write(Paths.get(filePathSorted), strVal.getBytes(), StandardOpenOption.APPEND,
							StandardOpenOption.CREATE);
				} catch (IOException io) {
					System.out.println("In the persistsortedList method:" + io.getMessage());
				}
			}
		}

	}

	/**To delete content of  afile system id required
	 *  @see com.hm.rnsorter.sorting.service.IRNPersistService#deletecontent()
	 */
	public void deletecontent() {
		try {
			Files.delete(Paths.get(filePathSorted));
		} catch (IOException io) {
			System.out.println("In the persistsortedList method:" + io.getMessage());
		}

	}

}
