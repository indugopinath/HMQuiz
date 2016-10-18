/**
 * 
 */
package com.hm.rnsorter.model;


/**
 * @author InduGopinath
 * 
 * Model compoenent to store the information about a random number 
 */
public class RandomNumber {
	
	/**
	 * To store the list of sorted numbers
	 */
	private double[] sortedList;
	
	/**
	 * To store the duration
	 */
	private long  sortDuration;
	
	/**
	 * To store the swapnumber
	 */
	private int swapNumber;

	public double[] getSortedList() {
		return sortedList;
	}

	public void setSortedList(double[] sortedList) {
		this.sortedList = sortedList;
	}

	public long getSortDuration() {
		return sortDuration;
	}

	public void setSortDuration(long sortDuration) {
		this.sortDuration = sortDuration;
	}

	public int getSwapNumber() {
		return swapNumber;
	}

	public void setSwapNumber(int swapNumber) {
		this.swapNumber = swapNumber;
	}
	
	

}
