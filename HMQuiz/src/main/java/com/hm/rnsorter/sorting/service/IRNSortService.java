/**
 * 
 */
package com.hm.rnsorter.sorting.service;

import com.hm.rnsorter.model.RandomNumber;

/**
 * @author InduGopinath
 *
 */
public interface IRNSortService {
	
	/**To sort the unsorted Array
	 * @param unsortedList
	 * @return 
	 */
	RandomNumber sortList(double[] unsortedList);
	
	
}
